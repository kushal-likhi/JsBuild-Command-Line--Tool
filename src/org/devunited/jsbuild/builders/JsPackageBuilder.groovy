package org.devunited.jsbuild.builders

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady
import org.devunited.jsbuild.messages.MessageTemplate

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 7/30/11
 * Time: 11:56 AM
 */
class JsPackageBuilder implements CommandLineUserInterfaceReady {

    File basePackage

    def parentContext

    Integer recursionLevel = 1
    Integer recursionSibling = 1

    JsPackageBuilder(Integer recursionLevel, Integer recursionSibling) {
        this.recursionLevel = recursionLevel
        this.recursionSibling = recursionSibling
    }

    JsPackageBuilder(Map options, parentContext) {
        this.recursionLevel = options.recursionLevel
        this.recursionSibling = options.recursionSibling
        this.parentContext = parentContext
    }

    public String build(File base) {
        basePackage = base

        parentContext.totalPackages++

        showToUserFromTemplate MessageTemplate.PACKAGE_BUILDER_ENTRY_MESSAGE, [
                packageName: determinePackage(basePackage, parentContext),
                recursionLevel: recursionLevel,
                recursionSibling: recursionSibling
        ]

        String contentBuffer
        if (recursionLevel == 1) {
            contentBuffer = "var ${basePackage.getCanonicalPath().split(File.separatorChar.toString()).last()} = "
        } else {
            contentBuffer = "${basePackage.getCanonicalPath().split(File.separatorChar.toString()).last()}: "
        }


        return (contentBuffer + new JsNamespaceBuilder(
                [
                        recursionLevel: recursionLevel,
                        recursionSibling: recursionSibling
                ],
                parentContext
        ).build(basePackage) + (recursionLevel == 1 ? ";" : ""))
    }

    public static String determinePackage(File packageLocation, parentContext) {
        String basePath = new File((parentContext.baseDir + File.separatorChar + "..")).getCanonicalPath() + File.separatorChar
        String packageDir = packageLocation.getCanonicalPath()
        String packageName = packageDir - basePath
        return packageName.trim().replace('/', '.').replace(File.separator, '.')
    }
}
