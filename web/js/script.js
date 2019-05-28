function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
function checkCookie() {
    var user = getCookie("mail");
    console.log("checking cookie "+user);
    if (user == "" || user=='""') {
        console.log("user not logged in : ");
    } else {
        console.log("user already logged in : " + user);
        window.location.href = "searchtrain.html";
    }
}