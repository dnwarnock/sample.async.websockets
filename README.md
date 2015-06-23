# Java EE7: WebSockets

Java EE7 added support for WebSockets. This sample contains a few variations
to illustrate how to use WebSockets in EE7 applications.

* [Downloading WAS Liberty](#downloadingwasliberty)
* [Building with maven](#buildingwithmaven)
* [Starting the server](#startingtheserver)

## Downloading WAS Liberty

There are lots of ways to get your hands on WAS Liberty, but two of them follow. Note that you will need a version of Liberty that has support for Servlet 3.1, WebSockets 1.1, and CDI 1.2 for this sample, as it has a little bit of all of those (Java EE 7 Web Profile will do).

### Direct download

To download just the WAS Liberty runtime, go to the [wasdev.net Downloads page][wasdev], and choose between the [latest version of the runtime][wasdev-latest] or the [latest beta][wasdev-beta].

There are a few options to choose from (especially for the beta drivers): choose the one that is most appropriate.

[wasdev]: https://developer.ibm.com/wasdev/downloads/
[wasdev-latest]: https://developer.ibm.com/wasdev/downloads/liberty-profile-using-non-eclipse-environments/
[wasdev-beta]: https://developer.ibm.com/wasdev/downloads/liberty-profile-beta/

You have the option of choosing one of the minimal images, and adding features from the repository as you need them using the installUtility (described on the download pages linked above) or the liberty-maven-plugin.

### Eclipse / WDT

The WebSphere Development Tools for Eclipse provide:

* content-assist for server configuration (a nice to have: server configuration is minimal, but the tools can help you find what you need and identify finger-checks, etc.)
* automatic incremental publish of applications so that you can write and test your changes locally without having to go through a build/publish cycle or restart the server (which is not that big a deal given the server restarts lickety-split, but less is more!).

Installing WDT on Eclipse is as simple as a drag-and-drop, but the process is explained [on wasdev.net] [wasdev-wdt]. Note that in when defining a server (step 3 of the linked instructions), you can either download a copy of Liberty, or use the install that you grabbed via the instructions above.

[wasdev-wdt]: https://developer.ibm.com/wasdev/downloads/liberty-profile-using-eclipse/


## Building with maven

This sample can be build using [Apache Maven](http://maven.apache.org/).

```bash
$ mvn install
```
 In addition to publishing the war to the local maven repository, the built war file is copied into the apps directory of the server configuration located in the async-websocket-wlpcfg directory:
```async-websocket-wlpcfg
 +- servers
     +- websocketSample       <-- specific server configuration
        +- server.xml         <-- server configuration
        +- apps               <-- directory for applications
           +- websocket.war   <-- sample application
        +- logs               <-- created if/when you run the server locally
        +- workarea           <-- created if/when you run the server locally
```

## Starting the server

There are a few options for starting the server, three of which are below. Once the server has been started, go to [http://localhost:9082/websocket/](http://localhost:9082/websocket/) to view the sample.

### Running on the command line

Based on the server directory generated above, running the build application is easy:

```bash
$ export WLP_USER_DIR=/path/to/sample.async.websockets/target/usr
$ /path/to/wlp/bin/server run websocketSample
```

* ```run``` runs the server in the foreground.
* ```start``` runs the server in the background. Look in the logs directory for console.log to see what's going on, e.g.
```bash
$ tail -f ${WLP_USER_DIR}/servers/websocketSample/logs/console.log
```

### Running with maven

The liberty-maven-plugin can also control and manipulate the server for use in automated builds to support continuous integration.



### Using WDT

WDT can be used to control the server (start/stop/dump/etc.), it also supports incremental publishing with minimal restarts, working with a debugger to step through your applications, etc.

1. After building with maven, import the sample into Eclipse using
*(File or right-click in an explorer view) -> Import -> Maven -> Existing Maven Projects*

3. Create a new server in Eclipse
    * This step is trying something experimental, so please let us know if you like it!




## Tips

* When importing the existing maven project into Eclipse, Eclipse will (by default)
  "helpfully" add this project to an (extraneous) ear. To turn this off, go to
  Preferences -> Java EE -> Project, and uncheck "Add project to an EAR" before you
  import the project. If you forgot to do this, just delete the ear project; no harm.


* If you use bash, consider trying the [command line tools](https://github.com/WASdev/util.bash.completion), which provide tab-completion for the server and other commands.


## Notice

© Copyright IBM Corporation 2015.

## License

```text
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````
