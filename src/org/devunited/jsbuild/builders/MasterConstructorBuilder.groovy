package org.devunited.jsbuild.builders

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 8/17/11
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
class MasterConstructorBuilder implements CommandLineUserInterfaceReady {

    def mainContext

    String indent = "    "

    public MasterConstructorBuilder(mainContext) {
        this.mainContext = mainContext
    }

    public String build() {
        String data = ""

        JsAnnotationEngine annotationEngine = new JsAnnotationEngine(data)

        annotationEngine.processAlias(mainContext.aliasedProperties)

        annotationEngine.processOverrides(mainContext.overrideProperties)

        data = annotationEngine.contents

        data += "\nwindow.onload = function(){"

        data += ("\n" + getSessionDeclarations())

        data += ("\n" + getLoggerAndWatcher())

        mainContext.constructors.each {
            data += "\n${indent}${it}();"
        }

        mainContext.eventRegistry.each {key, val ->
            data += indentEachLine(annotationEngine.buildEventCode(key, val))
        }

        mainContext.intervalRegistry.each {key, val ->
            data += indentEachLine(annotationEngine.processIntervals(key, val))
        }

        data += "\n}"

        data + '\n'
    }

    private String indentEachLine(String content) {
        String outBuffer = ""
        content.eachLine {
            outBuffer += "\n${indent}${it}"
        }
        outBuffer
    }

    private String getLoggerAndWatcher() {
        String base = mainContext.basePackage
        return """    window.log = function(str){try{${base}.console.log(str,'white')}catch(c){}};
    window.watch = function(k,v){try{${base}.console.watch(k,v)}catch(c){}};"""
    }

    private String getSessionDeclarations() {
        String base = mainContext.basePackage
        return """    ${base}.session = new Object();
    window.session = ${base}.session;"""
    }
}
