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

    def mainContext
    def parentContext

    String propertyAddress

    public JsAnnotationProcessor(String annotation, File file, mainContext, parentContext) {
        this.annotation = decryptAnnotationString(annotation)
        this.mainContext = mainContext
        this.parentContext = parentContext
        propertyAddress = determineAddress(file.getCanonicalPath())
        process()
    }

    def determineAddress = {path ->
        String basePath = new File((mainContext.baseDir + File.separatorChar + "..")).getCanonicalPath() + File.separatorChar
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
                mainContext.exportedProperties.put((!annotation.args.isEmpty() ? annotation.args.first().trim() : propertyAddress.tokenize('.').last()), propertyAddress)
                break;
            case "alias":
                if (annotation.args.isEmpty()) {
                    mainContext.errors.add("ERROR: Annotation Alias Has no Argument specified, Hence Annotation Ignored")
                } else {
                    List temp = propertyAddress.tokenize(".")
                    temp.pop()
                    temp << annotation.args.first().trim()
                    mainContext.aliasedProperties.put(temp.join('.'), propertyAddress)
                }
                break;
            case "override":
                if (annotation.args.isEmpty()) {
                    mainContext.errors.add("ERROR: Annotation Override Has no Argument specified, Hence Annotation Ignored")
                } else {
                    mainContext.overrideProperties.put(annotation.args.first().trim(), propertyAddress)
                }
                break;
            case "ignore":
                parentContext.ignored = true
                break;
            case "event":
                if (annotation.args.size() < 2) {
                    mainContext.errors.add("ERROR: Annotation Event Has ${annotation.args.size()} Argument specified, Requires 2 Arguments <eventType> <selector>, Hence Annotation Ignored")
                } else {
                    mainContext.eventRegistry.put(propertyAddress, [event: annotation.args.first().trim(), selector: annotation.args.last().trim()])
                }
                break;
            default:
                mainContext.errors.add("WARNING: Annotation '${annotation.type}' Not Recognised, Hence Ignored")
                break;
        }
    }
}
