function() {
    var value = this.toString();
    if (/[^0-9-]+/.test(value))
        return false;
    var nCheck = 0,
            nDigit = 0,
            bEven = false;

    value = value.replace(/\D/g, "");

    for (n = value.length - 1; n >= 0; n--) {
        var cDigit = value.charAt(n);
        nDigit = parseInt(cDigit, 10);
        if (bEven) {
            if ((nDigit *= 2) > 9)
                nDigit -= 9;
        }
        nCheck += nDigit;
        bEven = !bEven;
    }

    return (nCheck % 10) == 0;
}