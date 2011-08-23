function() {
    var a = new Array();
    var s = this.toString();
    for (i = 0; i < s.length; i++) {
        a.push(s.charAt(i));
    }
    return a
}