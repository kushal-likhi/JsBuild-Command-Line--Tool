function(delegate, id) {
    var el = document.createElement("div");
    el.style.background = "#fff";
    el.style.position = "fixed";
    el.style.zIndex = 1000;
    el.style.width = "70%";
    el.style.height = "70%";
    el.style.top = "15%";
    el.style.left = "15%";
    el.id = "osedit";
    el.innerHTML = "<textarea id='ostaed' style='width:100%;height:90%;background:#ffc'></textarea><br><button onclick=\"jsbuildRuntimeBasePackage.console.updateFunc('" + id + "')\">Update</button><button onclick='jsbuildRuntimeBasePackage.console.closeEditor()'>Close</button>";
    document.getElementsByTagName("body")[0].appendChild(el);
    var el2 = document.getElementById("ostaed");
    if (!el2) {
        jsbuildRuntimeBasePackage.console.log("not Found")
    }
    el2.innerHTML = el2.innerHTML + delegate;
}