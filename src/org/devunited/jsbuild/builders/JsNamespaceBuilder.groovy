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

    def mainContext

    def TYPE_PROPERTY = true
    def TYPE_COMMENT = false

    boolean hasConstructor = false

    JsNamespaceBuilder(Integer recursionLevel, Integer recursionSibling) {
        this.recursionLevel = recursionLevel
        this.recursionSibling = recursionSibling
    }

    JsNamespaceBuilder(Map options, mainContext) {
        this.recursionLevel = options.recursionLevel
        this.recursionSibling = options.recursionSibling
        this.mainContext = mainContext
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
                packageName: JsPackageBuilder.determinePackage(namespace, mainContext),
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
                            mainContext
                    ).build(file)
                }, \n"""
                recursionSibling++
            } else {
                if (file.getName().endsWith(".js")) {
                    if (file.getName() == "Constructor.js") {
                        hasConstructor = true
                        mainContext.totalConstructors++
                    } else {
                        mainContext.totalProperties++
                    }
                    JsFileParser jsFileParser = new JsFileParser(file, mainContext)
                    contentBuffer += "${indentEachLine jsFileParser.comments}"
                    contentBuffer += (indentEachLine("${file.getName() - ".js"}: ${jsFileParser.property},"))
                }
            }
        }

        String constructor = "init_${namespace.getCanonicalPath().split(File.separatorChar.toString()).last()}_jsBuild_generated"
        mainContext.constructors.add(JsPackageBuilder.determinePackage(namespace, mainContext) + "." + constructor)
        String constructorReference = JsPackageBuilder.determinePackage(namespace, mainContext) + "." + "Constructor()"
        contentBuffer += indentEachLine("${constructor}: function(){${hasConstructor ? constructorReference : ''}}${(recursionLevel == 1) ? ',' : ''} \n")

        if (recursionLevel == 1) {
            contentBuffer += indentEachLine("MasterConstructor_jsBuild_generated: new function(){${indentEachLine(new MasterConstructorBuilder(mainContext).build())}} \n")
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
