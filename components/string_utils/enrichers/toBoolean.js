function() {
    var s = this.toString().toLowerCase();
    var ret = "Not A BOOLEAN";
    if (s == "true") {
        ret = true;
    } else if (s == "false") {
        ret = false
    }
    return ret;
}