package org.devunited.jsbuild.templates

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 7/29/11
 * Time: 8:06 PM
 */
class TemplateBuilder {
    public static String buildTemplate(Object template, Map model) {
        String renderedOutput = new String(template.toString())
        model.each {key, val ->
            renderedOutput = renderedOutput.replace("###${key}###".toString(), val as String)
        }
        return renderedOutput
    }
}
