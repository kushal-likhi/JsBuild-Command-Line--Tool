/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


CliBuilder cli = new CliBuilder()
cli.d( longOpt: 'destfile', argName: 'destfile', args: 1, 'jar destintation filename' )
cli.s( longOpt: 'sourcefile', argName: 'sourcefile', args: 1, 'jar sourcefile name' )
cli.g( longOpt: 'groovyhome', argName: 'groovyhome', args: 1, 'Groovy Home Path' )
cli.m( longOpt: 'groovyhome', argName: 'groovyhome', args: 1, 'Groovy Home Path' )

OptionAccessor commandLineOptions = cli.parse(args)

if (!commandLineOptions) { return }

String sourceFile = commandLineOptions.s
String destFile = commandLineOptions.d
String groovyHome = commandLineOptions.g
String mainClass = commandLineOptions.m

AntBuilder ant = new AntBuilder()

File groovyHomeDir = new File( groovyHome )
File currentDir = new File( "." )


ant.jar( destfile: destFile, compress: true, index: true ) {

  zipgroupfileset( dir: currentDir, includes: sourceFile )
  zipgroupfileset( dir: groovyHomeDir, includes: 'embeddable/groovy-all-*.jar' )
  zipgroupfileset( dir: groovyHomeDir, includes: 'lib/commons*.jar' )

  manifest {
    attribute( name: 'Main-Class', value: mainClass )
  }
}
