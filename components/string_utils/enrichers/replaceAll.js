function(what, withThis) {
    return this.toString().replace(new RegExp(what, 'g'), withThis);
}