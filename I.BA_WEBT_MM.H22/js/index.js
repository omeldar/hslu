function submitForm(event){
    // get text input (city), other input fields are not frontend validated because it requires manipulation of the site to send wrong data
    // and since the person willing to manipulate the site can manipulate everything it does not make any sense frontend validating them.
    let inputtedCity = document.getElementById('inp_location').value;
    // check if input valid and if not display error messag and prevent form submition if not valid
    let el_city_message;
    if (!/^[a-zA-Z\u00E4\u00F6\u00FC ]+$/.test(inputtedCity)) {
        el_city_message = document.getElementById('city_message');
        el_city_message.removeAttribute('hidden');
        event.preventDefault();
    }
    else {   // we need the else to add the hidden attribute again after user tries again and one field has turned valid but others still arent
        el_city_message.setAttribute('hidden');
    }
}