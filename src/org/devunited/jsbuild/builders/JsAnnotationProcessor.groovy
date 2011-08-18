package org.devunited.jsbuild.builders

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady
import org.devunited.jsbuild.messages.MessageTemplate

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 8/17/11
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
class JsAnnotationProcessor implements CommandLineUserInterfaceReady {

    Map annotation

    def parentContext

    String propertyAddress

    public JsAnnotationProcessor(String annotation, File file, parentContext) {
        this.annotation = decryptAnnotationString(annotation)
        this.parentContext = parentContext
        propertyAddress = determineAddress(file.getCanonicalPath())
        process()
    }

    def determineAddress = {path ->
        String basePath = new File((parentContext.baseDir + File.separatorChar + "..")).getCanonicalPath() + File.separatorChar
        path = (path - basePath)
        path = (path - ".js")
        return path.trim().replace('/', '.').replace(File.separator, '.')
    }

    def decryptAnnotationString = {String str ->
        List tokens = (str.trim() - "@").tokenize(' ')
        String type = tokens.first()
        tokens.remove(type)
        return [
                type: type.trim().toLowerCase(),
                args: tokens
        ]
    }

    private void process() {

        showToUserFromTemplate MessageTemplate.ANNOTATION_PROCESSOR_ENTRY_MESSAGE, [type: annotation.type, args: annotation.args]

        switch (annotation.type) {
            case "export":
                parentContext.exportedProperties.put((!annotation.args.isEmpty() ? annotation.args.first().trim() : propertyAddress.tokenize('.').last()), propertyAddress)
                break;
            case "alias":
                if (annotation.args.isEmpty()) {
                    parentContext.errors.add("ERROR: Annotation Alias Has no Argument specified, Hence Annotation Ignored")
                } else {
                    List temp = propertyAddress.tokenize(".")
                    temp.pop()
                    temp << annotation.args.first().trim()
                    parentContext.aliasedProperties.put(temp.join('.'), propertyAddress)
                }
                break;
            default:
                parentContext.errors.add("WARNING: Annotation '${annotation.type}' Not Recognised, Hence Ignored")
                break;
        }
    }
}
