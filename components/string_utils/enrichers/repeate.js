function(times) {
    var s = this.toString();
    var ret = "";
    for (i = 0; i < parseInt(times, 10); i++) {
        ret += s;
    }
    return ret;
}