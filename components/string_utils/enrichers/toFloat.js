function(idx) {
    var index = idx;
    if (typeof(idx) == 'undefined') {
        index = 10
    }
    return parseFloat(this.toString(), index)
}