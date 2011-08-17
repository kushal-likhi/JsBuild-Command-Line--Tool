package org.devunited.jsbuild.builders

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady
import org.devunited.jsbuild.messages.MessageTemplate
import org.devunited.jsbuild.JsBuild

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 7/31/11
 * Time: 12:14 AM
 */
class JsFileParser implements CommandLineUserInterfaceReady {

    File targetFile

    String comments
    String property


    JsFileParser(File target) {
        targetFile = target
        parse()
    }

    private void parse() {
        showToUserFromTemplate MessageTemplate.FILE_PARSER_ENTRY_MESSAGE, [targetFile: targetFile.getCanonicalPath()]

        String commentBuffer
        String propertyBuffer = targetFile.getText()

        Map channels = splitFileContentToChannels(propertyBuffer)

        commentBuffer = channels.commentChannel
        propertyBuffer = channels.propertyChannel

        comments = decorateComments(commentBuffer)
        property = removeBlankLines(propertyBuffer)
    }

    public String getComments() {
        if (JsBuild.isFileCommentsEnabled) {
            return comments
        } else {
            return ""
        }
    }

    private String removeBlankLines(String text) {
        String outBuffer = ""
        boolean isFirst = true
        text.eachLine {line ->
            if (line.trim() != "") {
                outBuffer += ((isFirst ? '' : '\n') + line)
                isFirst = false
            }
        }
        return outBuffer
    }

    private String decorateComments(String comments) {
        String decoratedComment = ""

        comments = comments.replace('/*', '').replace('*/', '').replace('//', '')

        comments.eachLine {line ->
            if (line.startsWith(" *")) {
                decoratedComment += (line + '\n')
            } else if (line.startsWith("*")) {
                decoratedComment += (' ' + line + '\n')
            } else {
                decoratedComment += (' *' + line + '\n')
            }
        }

        ('/* \n' + decoratedComment + ' */')
    }

    private Map splitFileContentToChannels(String text) {
        Map channels = [
                commentChannel: "",
                propertyChannel: ""
        ]

        boolean commentBlockLock = false
        boolean propertyLock = false
        boolean isFirst = true

        text.eachLine {line ->
            if (line.trim() != "") {
                if (!propertyLock && !commentBlockLock && line.trim().startsWith("//")) {
                    channels.commentChannel += ((isFirst ? '' : '\n') + line)
                } else if (!propertyLock && !commentBlockLock && line.trim().startsWith("/*")) {
                    commentBlockLock = true
                    channels.commentChannel += ((isFirst ? '' : '\n') + line)
                } else if (!propertyLock && commentBlockLock) {
                    channels.commentChannel += ((isFirst ? '' : '\n') + line)
                    if (line.trim().endsWith("*/")) {
                        commentBlockLock = false
                    }
                } else {
                    channels.propertyChannel += ((isFirst ? '' : '\n') + line)
                    propertyLock = true
                }
                isFirst = false
            } else {
                JsBuild.totalBlankLines++
            }
            JsBuild.totalLoc++
        }

        channels
    }
}
