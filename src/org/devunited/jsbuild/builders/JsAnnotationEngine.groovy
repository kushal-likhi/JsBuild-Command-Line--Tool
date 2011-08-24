package org.devunited.jsbuild.builders

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady
import org.devunited.jsbuild.templates.TemplateBuilder
import groovy.text.TemplateEngine

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
        exportedProperties.each {key, value ->
            showToUser "[Annotation Engine] Exporting Property"
            addLine "var ${key} = ${value};"
            showToUser "Done. Exported '${value}' Property with name '${key}'"
        }
    }

    public void processAlias(Map aliasedProperties) {
        aliasedProperties.each {key, value ->
            showToUser "[Annotation Engine] Building Alias"
            addLine "${key} = ${value};"
            showToUser "Done. Created Alias '${key}' for Property '${value}'"
        }
    }

    public void processOverrides(Map overrideProperties) {
        overrideProperties.each {key, value ->
            showToUser "[Annotation Engine] Building Override"
            addLine "${key} = ${value};"
            showToUser "Done. Overrided Property '${key}' With '${value}'"
        }
    }

    public String processIntervals(String identifier, Map args) {
        String data
        showToUser "[Annotation Engine] Building Interval"
        data = TemplateBuilder.buildTemplate(codeTemplates.createInterval,
                [
                        interval: args.interval,
                        target: args.target,
                        name: identifier
                ]
        )
        showToUser "Done. Added Interval '${identifier}' With '${args}'"
        data
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

    public String processInjectProperties(String name, String address) {
        String data = TemplateBuilder.buildTemplate(codeTemplates.inject,
                [
                        name: name,
                        property: address.tokenize('.').last(),
                        handler: address
                ]
        )
        data
    }


    private Map codeTemplates = [
            eventHandlerIdBased: "if(document.getElementById('###id###')){document.getElementById('###id###').###event### = ###handler###}",
            eventHandlerIdBasedTryCatch: "try{document.getElementById('###id###').###event### = ###handler###}catch(c){}",
            eventHandlerImplicit: "###object###.###event### = ###handler###;",
            createInterval: "###name### = setInterval('###target###()',###interval###);",
            inject: "###name###.prototype.###property### = ###handler###;",
            eventHandlerClassBased: "try{var aht = document.getElementsByTagName(\"*\");for(idx in aht){if(aht[idx].className == \"###className###\"){aht[idx].###event### = ###handler###}}}catch(c){}"
    ]

}