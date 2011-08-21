package org.devunited.jsbuild.component

import org.devunited.jsbuild.enricher.CommandLineUserInterfaceReady

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 8/21/11
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
class ComponentRegistryAccess implements CommandLineUserInterfaceReady {

    def mainContext

    Map registry = [:]

    public ComponentRegistryAccess(mainContext) {
        this.mainContext = mainContext
        parseRegistry()
    }

    private parseRegistry() {
        showToUser "[Registry Parser] Parsing Component Registry"
        File registryFile = new File(mainContext.homeDir + File.separatorChar + "component.registry")
        if (registryFile.exists()) {
            registryFile.eachLine {line ->
                if (line.trim() != "") {
                    List<String> tokens = line.tokenize(",")
                    String name = tokens.get(0).trim()
                    String basePackage = tokens.get(1).trim()
                    String exportName = tokens.get(2).trim()
                    String priority = tokens.get(3).trim()
                    showToUser "[Registry Parser] Found Component: ${name}"
                    registry.put(
                            name,
                            [
                                    name: name,
                                    basePackage: basePackage,
                                    exportName: exportName,
                                    priority: priority
                            ]
                    )
                }
            }
            showToUser "[Registry Parser] Parsed Registry File"
        } else {
            exitWithError "Component Registry file Not Found at ${registryFile.getCanonicalPath()}. Reinstall or check configurations."
        }
    }
}
