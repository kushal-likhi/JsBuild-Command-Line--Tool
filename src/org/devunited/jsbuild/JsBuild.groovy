package org.devunited.jsbuild

import org.devunited.jsbuild.messages.ConsolePosters

import org.devunited.jsbuild.messages.MessageTemplate
import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady
import org.devunited.jsbuild.builders.JsCommentBuilder
import org.devunited.jsbuild.builders.JsPackageBuilder
import org.devunited.jsbuild.builders.JsAnnotationEngine

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 7/28/11
 * Time: 11:01 PM
 */

class JsBuild implements CommandLineUserInterfaceReady {

    public String baseDir = ""

    public String targetFilePath = ""

    public String minFilePath = "N/A"

    public boolean isFileCommentsEnabled = true

    public Integer filesScanned = 0

    public Integer commentsFound = 0

    public Integer totalProperties = 0

    public Integer totalPackages = 0

    public Integer totalLoc = 0

    public Integer totalBlankLines = 0

    public Integer totalLinesInBuild = 0

    public Map exportedProperties = [:]

    public Map aliasedProperties = [:]

    public Map overrideProperties = [:]

    public Map eventRegistry = [:]

    public Map intervalRegistry = [:]

    public List errors = []

    public List<String> constructors = []


    public static void main(String[] args) {

        new JsBuild().start(args)

    }


    public void start(String[] args) {


        OptionAccessor optionAccessor = parseCliSwitches(args)
        if (!optionAccessor) {
            exitWithMessage "Exiting ${BuildInfo.projectName} System"
        }
        if (optionAccessor.n) {
            isFileCommentsEnabled = false
        }



        showToUser ConsolePosters.introPoster



        File presentWorkingDirectory = new File(".")
        File sourceDir = optionAccessor.s ? new File(optionAccessor.s as String) : presentWorkingDirectory
        File targetDir = optionAccessor.t ? new File(optionAccessor.t as String) : presentWorkingDirectory
        if (targetDir.getCanonicalPath().equals(sourceDir.getCanonicalPath())) {
            targetDir = new File(targetDir.getCanonicalPath() + File.separatorChar + "..")
        }
        File targetFile = new File(targetDir.getCanonicalPath() + File.separatorChar + optionAccessor.f)
        baseDir = sourceDir.getCanonicalPath()


        showToUserFromTemplate MessageTemplate.WORKING_DIR, [workDir: presentWorkingDirectory.getCanonicalPath()]
        showToUserFromTemplate MessageTemplate.SOURCE_DIR, [sourceDir: sourceDir.getCanonicalPath()]
        showToUserFromTemplate MessageTemplate.TARGET_DIR, [targetDir: targetDir.getCanonicalPath()]
        showToUserFromTemplate MessageTemplate.TARGET_FILE, [targetFile: targetFile.getCanonicalPath()]
        putLineBreakWithHeight 2



        builderState "Creating Target File"
        try {
            targetFile.createNewFile()
            targetFilePath = targetFile.getCanonicalPath()
        } catch (Exception e) {
            showToUser e.getStackTrace()
            exitWithError "Error Creating Target File"
        }
        showToUserFromTemplate MessageTemplate.TARGET_FILE_CREATED, [targetFile: targetFile.getCanonicalPath()]
        putLineBreakWithHeight 1



        builderState "Initiating File Buffer"
        String targetFileContents = ""
        showToUser "Buffer Initiated..."
        putLineBreakWithHeight 1



        builderState "Fetching Comments"
        targetFileContents += new JsCommentBuilder(sourceDir, this).comments
        putLineBreakWithHeight 1



        builderState "Starting Recursive Build"
        targetFileContents += new JsPackageBuilder([recursionLevel: 1, recursionSibling: 1], this).build(sourceDir)
        JsAnnotationEngine annotationEngine = new JsAnnotationEngine(targetFileContents)
        annotationEngine.processExports(exportedProperties)
        targetFileContents = annotationEngine.contents
        showToUser "Recursive Build Complete"
        putLineBreakWithHeight 1



        if (optionAccessor.e) {
            builderState "Printing Generated File Contents"
            println targetFileContents
            putLineBreakWithHeight 1
        }



        builderState "Writing buffer to file"
        targetFile.write targetFileContents
        targetFileContents.eachLine {totalLinesInBuild++}
        showToUser "File Saved Successfully -> ${targetFilePath}"



        if (optionAccessor.m) {
            putLineBreakWithHeight 1
            builderState "Building and Saving the minnified JavaScript file"
            minFilePath = targetFilePath + ".min.js"
            String[] arg = [targetFilePath, "-o", minFilePath]
            try {
                com.yahoo.platform.yui.compressor.Bootstrap.main(arg)
                showToUser "File Saved Successfully -> ${minFilePath}"
            } catch (Exception e) {
                showToUser "Unable To Minnify The File"
                minFilePath = "ERROR OCCOURED WHILE BUILDING"
            }
        }



        showToUser ConsolePosters.summaryPoster(this)


        if (!errors.isEmpty()) {
            showToUser "NOTE: There Were Few Errors/Warnings During Build"
            showToUser "-------------------------------------------------"
            errors.each {
                showToUser it
            }
            putLineBreakWithHeight 2
        }

    }


}
