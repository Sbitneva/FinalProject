function required() {
    var email = document.forms["authorization"]["email"].value;
    var password = document.forms["authorization"]["password"].value;
    if (email == null) {
        alert('Email field is empty!');
    }
    if (password == null) {
        alert(' Password field is empty!');
    }
    return false;
}