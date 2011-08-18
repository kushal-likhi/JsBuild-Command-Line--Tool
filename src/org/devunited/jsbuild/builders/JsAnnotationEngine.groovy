package org.devunited.jsbuild.builders

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 8/17/11
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
class JsAnnotationEngine implements CommandLineUserInterfaceReady{

    public String contents

    JsAnnotationEngine(String contents) {
        this.contents = contents
    }

    def addLine = {line ->
        contents += ('\n' + line)
    }

    public void processExports(Map exportedProperties) {
        showToUser "[Annotation Engine] Building Exports"
        exportedProperties.each {key, value ->
            addLine "var ${key} = ${value};"
        }
        showToUser "Done. Exported ${exportedProperties.size()} Properties"
    }

    public void processAlias(Map aliasedProperties) {
        showToUser "[Annotation Engine] Building Aliases"
        aliasedProperties.each {key, value ->
            addLine "${key} = ${value};"
        }
        showToUser "Done. Created ${aliasedProperties.size()} Alias"
    }

}
