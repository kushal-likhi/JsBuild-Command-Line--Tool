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

    def parentContext

    public MasterConstructorBuilder(parentContext) {
        this.parentContext = parentContext
    }

    public String build() {
        String data = ""

        JsAnnotationEngine annotationEngine = new JsAnnotationEngine(data)

        annotationEngine.processAlias(parentContext.aliasedProperties)

        data = annotationEngine.contents

        data += "\nwindow.onload = function(){"
        //call constructors/event handlers here
        parentContext.constructors.each {
            data += "\n    ${it}();"
        }
        data += "\n}"

        data + '\n'
    }

}
