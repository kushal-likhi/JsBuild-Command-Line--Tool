package org.devunited.jsbuild.builders

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady
import org.devunited.jsbuild.messages.MessageTemplate

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 7/30/11
 * Time: 8:37 PM
 */
class JsNamespaceBuilder implements CommandLineUserInterfaceReady {

    File namespace

    Integer recursionLevel = 1
    Integer recursionSibling = 1

    String indent = ""
    String closingIndent = ""

    def parentContext

    def TYPE_PROPERTY = true
    def TYPE_COMMENT = false

    JsNamespaceBuilder(Integer recursionLevel, Integer recursionSibling) {
        this.recursionLevel = recursionLevel
        this.recursionSibling = recursionSibling
    }

    JsNamespaceBuilder(Map options, parentContext) {
        this.recursionLevel = options.recursionLevel
        this.recursionSibling = options.recursionSibling
        this.parentContext = parentContext
    }

    public String build(File baseDir) {
        namespace = baseDir

        (recursionLevel * 4).times {
            indent += " "
        }
        ((recursionLevel - 1) * 4).times {
            closingIndent += " "
        }

        showToUserFromTemplate MessageTemplate.NAMESPACE_BUILDER_ENTRY_MESSAGE, [
                packageName: JsPackageBuilder.determinePackage(namespace, parentContext),
                recursionLevel: recursionLevel,
                recursionSibling: recursionSibling
        ]

        String contentBuffer = ""

        namespace.eachFile {file ->
            if (file.isDirectory()) {
                contentBuffer += """${indent}${
                    new JsPackageBuilder(
                            [
                                    recursionLevel: recursionLevel + 1,
                                    recursionSibling: recursionSibling
                            ],
                            parentContext
                    ).build(file)
                }, \n"""
                recursionSibling++
            } else {
                if (!file.getName().endsWith(".comment")) {
                    parentContext.totalProperties++
                    JsFileParser jsFileParser = new JsFileParser(file, parentContext)
                    contentBuffer += "${indentEachLine jsFileParser.comments}"
                    contentBuffer += (indentEachLine("${file.getName() - ".js"}: ${jsFileParser.property},"))
                }
            }
        }

        String constructor = "init_${namespace.getCanonicalPath().split(File.separatorChar.toString()).last()}_jsBuild_generated"
        parentContext.constructors.add(JsPackageBuilder.determinePackage(namespace, parentContext) + "." + constructor)
        contentBuffer += indentEachLine("${constructor}: function(){}${(recursionLevel == 1) ? ',' : ''} \n")

        if (recursionLevel == 1) {
            contentBuffer += indentEachLine("MasterConstructor_jsBuild_generated: new function(){${indentEachLine(new MasterConstructorBuilder(parentContext).build())}} \n")
        }

        return ('{ \n' + contentBuffer + "${closingIndent}}")
    }

    private String indentEachLine(String content) {
        String outBuffer = ""
        content.eachLine {
            outBuffer += "${indent}${it} \n"
        }
        outBuffer
    }

}
