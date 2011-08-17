package org.devunited.jsbuild.messages

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 7/29/11
 * Time: 8:07 PM
 */
public static abstract interface MessageTemplate {
    public static final TARGET_DIR = "Target Directory Is Set To:  ###targetDir###"
    public static final TARGET_FILE = "Target File Is Set To:  ###targetFile###"
    public static final TARGET_FILE_CREATED = "Created Target File:  ###targetFile###"
    public static final WORKING_DIR = "Working Directory Is Set To:  ###workDir###"
    public static final SOURCE_DIR = "Source Directory Is Set To:  ###sourceDir###"


    public static final COMMENT_FACTORY_ENTRY_NOTICE = "Comment Factory will scan Directory : ###path###"
    public static final COMMENT_FACTORY_EXIT_NOTICE = "Comments Fetched : ###found###, Total Files Scanned : ###scanned###"
    public static final COMMENT_FACTORY_FOUND_NOTICE = "Found Comment File : ###file###"
    public static final COMMENT_FACTORY_HEADER_NOTICE = "Built Using ###project### Version ###version###"


    public static final PACKAGE_BUILDER_ENTRY_MESSAGE = "[Package Builder] Building Package [###packageName###], Recursion Level: ###recursionLevel### ; Recursion Sibling: ###recursionSibling###"


    public static final NAMESPACE_BUILDER_ENTRY_MESSAGE = "[Namespace Builder] Building Namespace [###packageName###], Recursion Level: ###recursionLevel### ; Recursion Sibling: ###recursionSibling###"


    public static final FILE_PARSER_ENTRY_MESSAGE = "[File Parser] Parsing File : ###targetFile###"


}