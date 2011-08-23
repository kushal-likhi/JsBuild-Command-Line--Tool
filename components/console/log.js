function(text, col) {
    text = "$(" + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds() + ")> <span style='color:" + col + "'>" + text + "</span>";
    var con = document.getElementById("osecta");
    con.innerHTML = con.innerHTML + text + "<br>";
    con.scrollTop = con.scrollHeight;
}