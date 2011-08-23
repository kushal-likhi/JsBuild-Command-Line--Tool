@event onkeydown #osecin
function(e) {
    if (e.keyCode == 13) {
        var scr = document.getElementById("osecin").value;
        jsbuildRuntimeBasePackage.console.stack.push(scr);
        jsbuildRuntimeBasePackage.console.currentIndex = jsbuildRuntimeBasePackage.console.stack.length;
        jsbuildRuntimeBasePackage.console.log(scr, "green");
        document.getElementById("osecin").value = "";
        try {
            var cmm = new String(scr).split(' ');
            switch (cmm[0]) {
                case "echo":
                    try {
                        eval("jsbuildRuntimeBasePackage.console.log(" + cmm[1] + ",'yellow')");
                    } catch(c) {
                        jsbuildRuntimeBasePackage.console.log("Invalid argument", "red")
                        for (arg in c) {
                            jsbuildRuntimeBasePackage.console.log(arg + " : " + c[arg], "red");
                        }
                    }
                    break;
                case "clear":
                    document.getElementById("osecta").innerHTML = "";
                    jsbuildRuntimeBasePackage.console.log("OSE Console Cleared");
                    break;
                case "export":
                    eval("ex." + cmm[1] + " = " + cmm[2]);
                    break;
                case "alias":
                    eval("a." + cmm[1] + " = " + cmm[2]);
                    break;
                case "edit":
                    eval("jsbuildRuntimeBasePackage.console.getFunc(" + cmm[1] + ")");
                    jsbuildRuntimeBasePackage.console.launchEditor(a.evalOSD1234, cmm[1]);
                    break;
                case "prop-f":
                    eval("var o = " + cmm[1] + ";for (att in o) {jsbuildRuntimeBasePackage.console.log('<span style=color:yellow>' + att + ' </span>  ' + o[att],'blue')};jsbuildRuntimeBasePackage.console.log('Legend: <span style=color:yellow>Yellow = Property</span> And <span style=color:blue>Blue = Value</span>')");
                    break;
                case "prop":
                    eval("var o = " + cmm[1] + ";for (att in o) {d = new String(o[att]).trim();if(d.substr(0,8) == 'function'){d = '[function]'};jsbuildRuntimeBasePackage.console.log('<span style=color:yellow>' + att + ' </span>  ' + d,'blue')};jsbuildRuntimeBasePackage.console.log('Legend: <span style=color:yellow>Yellow = Property</span> And <span style=color:blue>Blue = Value</span>')");
                    break;
                case "help":
                    jsbuildRuntimeBasePackage.console.log("<div style='width:100%;background-color:#333'><h1 align='center'>Console Help</h1><h2 align='center'>&copy;Kushal Likhi</h2></div>Following Commands Are Available:<br><br>1) <b style=color:yellow>echo &lt;parameter&gt; :</b> <span style=color:blue>Echoes the value of the parameter passed.</span><br>2) <b style=color:yellow>clear :</b> <span style=color:blue>Clears the console screen.</span><br>3) <b style=color:yellow>alias &lt;alias name&gt; &lt;reference&gt; :</b> <span style=color:blue>Creates a shorthand reference to the delegate for easy use. reference can be made to any entity ex.Object, function, string etc. To refer this alias you can type a.&lt;alias name&gt;</span><br>4) <b style=color:yellow>export &lt;variable name&gt; &lt;value&gt; :</b> <span style=color:blue>Exports the variable for future use. reference can be made to any entity ex.Object, function, string etc. To refer this alias you can type e.&lt;variable name&gt;</span><br>5) <b style=color:yellow>prop &lt;object&gt; or prop-f(displays function definition too):</b> <span style=color:blue>List all properties of object.</span><br>6) <b style=color:yellow>edit &lt;function&gt; :</b> <span style=color:blue>Launches editor for editing function.</span><br>7) <b style=color:yellow>size &lt;size&gt; :</b> <span style=color:blue>Changes the console font size.</span><br>8) <b style=color:yellow>help :</b> <span style=color:blue>Launches help.</span><br><br><div style='width:100%;background-color:#333'>End Of Help</div>");
                    break;
                case "size":
                    document.getElementById("osecta").style.fontSize = cmm[1] + "px";
                    document.getElementById("osecin").style.fontSize = cmm[1] + "px";
                    break;
                case "yo":
                    jsbuildRuntimeBasePackage.console.log("YO! :-)", "yellow");
                    break;
                default:
                    eval(scr);
                    break;
            }

        } catch(c) {
            jsbuildRuntimeBasePackage.console.log("Error Executing Command, see manual for details", "red");
            for (arg in c) {
                jsbuildRuntimeBasePackage.console.log(arg + " : " + c[arg], "red");
            }
        }
    }
    if (e.keyCode == 38) {
        if (jsbuildRuntimeBasePackage.console.currentIndex > 0) {
            document.getElementById("osecin").value = jsbuildRuntimeBasePackage.console.stack[jsbuildRuntimeBasePackage.console.currentIndex - 1];
            jsbuildRuntimeBasePackage.console.currentIndex--;
        }
    }
    if (e.keyCode == 40) {
        if (jsbuildRuntimeBasePackage.console.currentIndex < jsbuildRuntimeBasePackage.console.stack.length) {
            jsbuildRuntimeBasePackage.console.currentIndex++;
            document.getElementById("osecin").value = jsbuildRuntimeBasePackage.console.stack[jsbuildRuntimeBasePackage.console.currentIndex - 1];
        }
    }
}