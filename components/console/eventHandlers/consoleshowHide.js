@event onclick #consoleShowHideButton

function() {
    if (jsbuildRuntimeBasePackage.console.expanded) {
        document.getElementById("ccmdsp").style.display = "none";
        document.getElementById("osecin").style.display = "none";
        document.getElementById("osecta").style.display = "none";
        document.getElementById("osehbc").style.top = "94%";
        jsbuildRuntimeBasePackage.console.expanded = false;
    } else {
        document.getElementById("ccmdsp").style.display = "block";
        document.getElementById("osecin").style.display = "block";
        document.getElementById("osecta").style.display = "block";
        document.getElementById("osehbc").style.top = "60%";
        jsbuildRuntimeBasePackage.console.expanded = true;
    }
}