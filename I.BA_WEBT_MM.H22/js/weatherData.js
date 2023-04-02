const canvas = document.getElementById('result-canvas');
const ctx = canvas.getContext('2d');

const api_data = JSON.parse(canvas.dataset.weatherdata);
const units = canvas.dataset.units;

console.log(api_data);

let speedUnits = "m/s";
let temperatureUnit = "C";
if (units == "I"){
    speedUnits = "mph";
    temperatureUnit = "F";
}
if (units == "S"){
    temperatureUnit = "K";
}

drawCanvasWeatherIcon();
drawCanvasArrow(200, 55, 30, api_data.data[0].wind_dir);    // api_data.data[0].wind_dir is the wind direction as angle
writeText("black", "16px Arial", 195, 20, "N");
writeText("black", "16px Arial", 240, 60, "E");
writeText("black", "16px Arial", 150, 60, "W");
writeText("black", "16px Arial", 195, 100, "S");
// plot wind speed
let windspeed_text = "Wind: " + api_data.data[0].wind_spd + " " + speedUnits + " from " + api_data.data[0].wind_cdir;
writeText("black", "10px Arial", 150, 130, windspeed_text);
// plot location info
let loc_info_text = api_data.data[0].city_name + ", " + api_data.data[0].country_code;
writeText("black", "10px Arial", 20, 130, loc_info_text);
// plot temperature info
let temperature_text = api_data.data[0].temp + " °" + temperatureUnit;
writeText("black", "10px Arial", 100, 130, temperature_text);

// draw weather icon
function drawCanvasWeatherIcon(){
    var imageUrl = 'https://www.weatherbit.io/static/img/icons/' + api_data.data[0].weather.icon + '.png';  // knapp 40 icon vom API Anbieter angeboten und das passende direkt in der response vorgeschlagen.
    imageUrl = encodeURI(imageUrl);
    var image = new Image();
    image.src = imageUrl;
    image.onload = function() {
        ctx.drawImage(image, 30, 10, 100, 100);
    }
}

// draw arrow into wind direction
function drawCanvasArrow(x1, y1, length, angle){
    ctx.strokeStyle = "blue";
    let angleTo = angle + 180;  // convert angleFrom to angleTo
    let pos = lineToAngle(x1, y1, length, angleTo);
    lineToAngle(pos.x, pos.y, 10, angleTo - 150);
    lineToAngle(pos.x, pos.y, 10, angleTo + 150);
}

function lineToAngle(x1, y1, length, angle){
    angle = (angle -90) * Math.PI / 180;
    let x2 = x1 + length * Math.cos(angle),
        y2 = y1 + length * Math.sin(angle);
    ctx.beginPath();
    ctx.moveTo(x1, y1);
    ctx.lineTo(x2, y2);
    ctx.stroke();
    ctx.fill();

    return {
        x: x2,
        y: y2
    };
}

function writeText(color, font, x, y, content){
    ctx.font = font;
    ctx.fillStyle = color;
    ctx.fillText(content, x, y);
}