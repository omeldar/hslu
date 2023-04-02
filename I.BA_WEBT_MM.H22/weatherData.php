<?php
  header('Content-Type: text/html; charset=utf-8');

  // Init form data
  $city = $_POST['city'];
  $country = $_POST['country'];
  $units = $_POST['units'];

  $maxValues = 5;
  if(isset($_COOKIE['values'])){
    $values = unserialize($_COOKIE['values']);
  } else{
    $values = array();
  }
  $values[] = $city . ", " . $country;
  if(count($values) > $maxValues){
    array_shift($values);
  }
  setcookie('values', serialize($values), time() + (86400 * 30), '/');

  // validate inputs
  $supported_countries = ["Switzerland", "Germany", "Austria", "United States", "United Kingdom", "Russia", "Japan"];

  $formdataValid = true;
  $regex = '/^[a-zA-Zäöü ]+$/';
  if(!preg_match($regex, $city)){
    $formdataValid = false;
  }
  $validCountry = in_array($country, $supported_countries);
  if((!preg_match($regex, $country)) && !$validCountry){
    $formdataValid = false;
  }
  if((!preg_match($regex, $units)) && !strlen($units) === 1){
    $formdataValid = false;
  }

  $weatherbiturl = 'https://api.weatherbit.io/v2.0/current?city=' . urlencode($city) .'&country=' . urlencode($country) . '&units=' . urlencode($units) . '&key=';
  $response = file_get_contents($weatherbiturl);    // not directly used json_decode(file_get_contents()) because the response as json is needed later in canvas
  $responsecode = http_response_code();
  if($response != null){
    $weatherdata = json_decode($response)->data;
  }
?>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Weatheldar</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <!-- Link to a stylesheet for both devices -->
    <link rel="stylesheet" href="../css/main.css">
    <!-- Link to a stylesheet for desktop devices -->
    <link rel="stylesheet" href="../css/desktop.css" media="screen and (min-width: 600px)">
    <!-- Link to a stylesheet for mobile devices -->
    <link rel="stylesheet" href="../css/mobile.css" media="screen and (max-width: 600px)">
  </head>
  <body>
    <nav class="w3-bar w3-deep-purple">
      <a href="/index.html" class="w3-bar-item w3-button">Home</a>
      <a href="#result" class="w3-bar-item w3-button">Result</a>
    </nav>
    <?php if($formdataValid && $response != null && $responsecode == 200) { ?>
    <section id="information-section">
      <h1>Search Result</h1>
      <div id="canvas-container" class="w3-row">
        <canvas class="w3-mobile" data-weatherdata='<?php echo $response; ?>' data-units='<?php echo $units; ?>' id="result-canvas"></canvas> 
        <div>
          <h2>Search History</h2>
          <div id="recent-results">
          <?php
            if (isset($_COOKIE['values'])) {
              // If the cookie exists, get the values from the cookie
              $values = unserialize($_COOKIE['values']);

              // Output the values
              echo '<ul>';
              foreach (array_reverse($values) as $value) {
                echo '<li>' . $value . '</li>';
              }
              echo '</ul>';
            } else {
              // If the cookie does not exist, output a message
              echo 'No recent searches';
            }
          ?>
          </div>
        </div>
      </div>
    </section>
    <?php } else { ?>
    <section id="error-section" >
      <h1>Oops, something went wrong.</h1>
      <div class="w3-row">
        <div class="w3-rest" >
          <h2>These may be the reasons your request failed</h2>
          <ul class="w3-ul">
            <li class="w3-li w3-margin-left">The city does not exist in the selected country.</li>
            <li class="w3-li w3-margin-left">Your entered data is wrong or in the wrong format.</li>
            <li class="w3-li w3-margin-left">The Weather API is down.</li>
          </ul>
        </div>    
      </div>
    </section>
    <?php } ?>
    <footer id="result-footer" class="w3-container w3-teal">
      <h5>Weatheldar</h5>
      <p>This is a project created for the WEBT class at the University of Applied Sciences and Arts by Eldar Omerovic. By using this site you accept all cookies.</p>
    </footer>
    <script src="../js/weatherData.js"></script>
  </body>
</html>