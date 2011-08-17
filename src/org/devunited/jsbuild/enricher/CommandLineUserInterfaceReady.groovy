package org.devunited.jsbuild.enricher

import org.devunited.jsbuild.templates.TemplateBuilder
import org.devunited.jsbuild.messages.ConsolePosters

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 7/29/11
 * Time: 8:31 PM
 */
public static interface CommandLineUserInterfaceReady {

    public static showToUser = {delegate ->
        println delegate
    }

    public static builderState = {String state ->
        println "----[BUILDER] ${state.toUpperCase()}"
    }

    public static showToUserFromTemplate = {template, model ->
        println TemplateBuilder.buildTemplate(template, model)
    }

    public static putLineBreakWithHeight = {Integer num ->
        num.times {
            println ""
        }
    }

    def exitWithError = {error ->
        println "ERROR: ${error}"
        System.exit 1
    }

    def exitWithMessage = {message ->
        println message
        System.exit 0
    }

    public static parseCliSwitches = {args ->
        CliBuilder cli = new CliBuilder(usage: "jsbuild <Options: -h -s -t -e -n> -f[target file name, Required]")
        cli.h(longOpt: 'help', required: false, 'show usage information')
        cli.s(longOpt: 'source', argName: 'source', required: false, args: 1, 'Sources Directory Path')
        cli.t(longOpt: 'target', argName: 'target', required: false, args: 1, 'target Directory Path')
        cli.f(longOpt: 'filename', argName: 'TargetFile', required: false, args: 1, 'Name of the file to be generated')
        cli.e(longOpt: 'echo', required: false, 'Echo the Final Output on the console')
        cli.n(longOpt: 'nofilecomments', required: false, 'if You dont want file comments to be echoed in the final build')
        OptionAccessor opt = cli.parse(args)
        if (opt.h) {
            println ConsolePosters.introPoster
            cli.usage()
            opt = null
        }
        if (opt && !opt?.f) {
            println "ERROR: No Destination file name specified"
            cli.usage()
            opt = null
        }
        return opt
    }
}