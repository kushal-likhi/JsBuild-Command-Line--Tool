function(id) {
    var v = document.getElementById('ostaed').value;
    v = id + " = " + v;
    eval(v);
    jsbuildRuntimeBasePackage.console.closeEditor();
}