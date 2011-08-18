
package org.devunited.jsbuild.messages
import org.devunited.jsbuild.BuildInfo
import org.devunited.jsbuild.JsBuild

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 7/28/11
 * Time: 11:03 PM
 */

public static interface ConsolePosters{

    public static String introPoster = """
*******************WELCOME*********************
         ___            ___
        /  /\\          /__/\\
       /  /~~\\         \\  \\~\\
      /  /~/\\~\\         \\  \\~\\
     /  /~/\\ \\~\\    ___  \\  \\~\\
    /__/~/  \\ \\~\\  /__/\\  \\  \\~\\D
    \\  \\~\\  / /~/  \\  \\~\\ /  /~/E
     \\  \\~\\/ /~/V   \\  \\~\\  /~/T
      \\  \\~\\/~/E     \\  \\~\\/~/I
       \\  \\~~/D       \\  \\~~/N
        \\__\\/          \\__\\/U
          www.devunited.org

Welcome To ${BuildInfo.projectName}
Version: ${BuildInfo.version}
Author: ${BuildInfo.author}
Community: ${BuildInfo.community}
Licence: ${BuildInfo.licence}
***********************************************
"""

    public static summaryPoster = {parentContext->"""
*******************SUMMARY*********************
Total Files Scanned: ${parentContext.filesScanned}
Total Comment Files Found: ${parentContext.commentsFound}
Total Properties: ${parentContext.totalProperties}
Total Packages: ${parentContext.totalPackages}
Total Lines Scanned: ${parentContext.totalLoc}
Total Blank Lines: ${parentContext.totalBlankLines}
Total Lines In Built File: ${parentContext.totalLinesInBuild}
Target File: ${parentContext.targetFilePath}
***********************************************
"""}

}