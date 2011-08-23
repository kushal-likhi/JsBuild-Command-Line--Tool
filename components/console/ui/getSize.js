function() {
    var ret = {};
    if (typeof( window.innerWidth ) == 'number') {
        ret.width = window.innerWidth;
        ret.height = window.innerHeight;
    }
    else if (document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight)) {
        ret.width = document.documentElement.clientWidth;
        ret.height = document.documentElement.clientHeight;
    }
    else if (document.body && ( document.body.clientWidth || document.body.clientHeight )) {
        ret.width = document.body.clientWidth;
        ret.height = document.body.clientHeight;
    }
    return ret;
}