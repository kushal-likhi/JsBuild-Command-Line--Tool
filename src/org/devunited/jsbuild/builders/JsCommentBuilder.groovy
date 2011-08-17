package org.devunited.jsbuild.builders

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady
import org.devunited.jsbuild.BuildInfo
import org.devunited.jsbuild.messages.MessageTemplate
import org.devunited.jsbuild.templates.TemplateBuilder
import org.devunited.jsbuild.JsBuild

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 7/30/11
 * Time: 12:44 AM
 */
class JsCommentBuilder implements CommandLineUserInterfaceReady {

    File directoryToBeScanned

    Integer filesScanned = 0
    Integer filesFound = 0

    public static String COMMENT_START = "/*" + '\n'
    public static String COMMENT_END = " */" + '\n\n'

    public JsCommentBuilder(File file) {
        directoryToBeScanned = file
        showToUserFromTemplate MessageTemplate.COMMENT_FACTORY_ENTRY_NOTICE, [path: directoryToBeScanned.getCanonicalPath()]
    }

    public String getComments() {
        String comments = ""
        def addComment = {comm ->
            comments += " * ${comm}" + '\n'
        }
        addComment TemplateBuilder.buildTemplate(MessageTemplate.COMMENT_FACTORY_HEADER_NOTICE, [project: BuildInfo.projectName, version: BuildInfo.version])

        addComment "\n"

        directoryToBeScanned.eachFileRecurse {file ->
            if (file.getName().endsWith(".comment")) {
                showToUserFromTemplate MessageTemplate.COMMENT_FACTORY_FOUND_NOTICE, [file: file.getCanonicalPath()]
                addComment "PACKAGE: " + JsPackageBuilder.determinePackage(file.parentFile)
                file.eachLine {line ->
                    addComment line
                }
                addComment "\n"
                filesFound++
            }
            filesScanned++
        }

        showToUserFromTemplate MessageTemplate.COMMENT_FACTORY_EXIT_NOTICE, [found: filesFound, scanned: filesScanned]
        JsBuild.filesScanned = filesScanned
        JsBuild.commentsFound = filesFound

        (COMMENT_START + comments + COMMENT_END)
    }
}
