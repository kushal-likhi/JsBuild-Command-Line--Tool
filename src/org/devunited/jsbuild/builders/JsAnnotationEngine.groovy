package org.devunited.jsbuild.builders

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady
import org.devunited.jsbuild.templates.TemplateBuilder

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 8/17/11
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
class JsAnnotationEngine implements CommandLineUserInterfaceReady {

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

    public void processOverrides(Map overrideProperties) {
        showToUser "[Annotation Engine] Building Overrides"
        overrideProperties.each {key, value ->
            addLine "${key} = ${value};"
        }
        showToUser "Done. Overrided ${overrideProperties.size()} Properties"
    }

    public String buildEventCode(String handler, Map args) {
        showToUser "[Annotation Engine] Binding EventHandler"
        String code
        String selector = args.selector
        String event = args.event
        if (selector.startsWith("#")) {
            code = TemplateBuilder.buildTemplate(
                    codeTemplates.eventHandlerIdBasedTryCatch,
                    [
                            id: (selector - "#"),
                            event: event,
                            handler: handler
                    ]
            )
        } else if (selector.startsWith(".")) {
            code = TemplateBuilder.buildTemplate(
                    codeTemplates.eventHandlerClassBased,
                    [
                            className: (selector - "."),
                            event: event,
                            handler: handler
                    ]
            )
        } else {
            code = TemplateBuilder.buildTemplate(
                    codeTemplates.eventHandlerImplicit,
                    [
                            object: (selector),
                            event: event,
                            handler: handler
                    ]
            )
        }

        showToUser "Done. Binded Event '${event}' For '${selector}' To '${handler}'"

        code
    }

    private Map codeTemplates = [
            eventHandlerIdBased: "if(document.getElementById('###id###')){document.getElementById('###id###').###event### = ###handler###}",
            eventHandlerIdBasedTryCatch: "try{document.getElementById('###id###').###event### = ###handler###}catch(c){}",
            eventHandlerImplicit: "###object###.###event### = ###handler###;",
            eventHandlerClassBased: "try{var aht = document.getElementsByTagName(\"*\");for(idx in aht){if(aht[idx].className == \"###className###\"){aht[idx].###event### = ###handler###}}}catch(c){}"
    ]

}