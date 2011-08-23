function() {
    var ret = false;
    try {
        ret = !/Invalid|NaN/.test(new Date(this));
    } catch(c) {
    }
    return ret;
}