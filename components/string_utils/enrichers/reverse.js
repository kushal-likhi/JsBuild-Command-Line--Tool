function() {
    var ret;
    var s = this.toString();
    for (i = s.length - 1; i >= 0; i--) {
        ret += s.charAt(i);
    }
    return ret;
}