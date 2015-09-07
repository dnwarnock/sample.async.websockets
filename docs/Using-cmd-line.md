## Building and running the sample using the command line

### Clone Git Repo

:pushpin: [Switch to Eclipse](/docs/Using-WDT.md#Clone-Git-Repo)

```bash

$ git clone https://github.com/WASdev/sample.async.websockets.git

```

### Building the sample
This sample can be built using either [Gradle](#building-with-gradle) or [Maven](#building-with-maven).

In addition to publishing the war to the local maven repository, the built war file is copied into the apps directory of the server configuration located in the async-websocket-wlpcfg directory:

```text
async-websocket-wlpcfg
 +- servers
     +- websocketSample                        <-- specific server configuration
        +- server.xml                          <-- server configuration
        +- apps                                <- directory for applications
           +- async-websocket-application.war  <- sample application
        +- logs                                <- created by running the server locally
        +- workarea                            <- created by running the server locally
```

#### Building using the Command Line

###### [Gradle](http://gradle.org/) commands

```bash
$ gradle build publishToMavenLocal
```

If you want to also run the functional tests then you need to [Download WAS Liberty](/docs/Downloading-WAS-Liberty.md) and set the libertyRoot property in the gradle.properties file to point to your Liberty install.

###### [Apache Maven](http://maven.apache.org/) commands

```bash
$ mvn install
```

If you want to also run the functional tests then you need to [Download WAS Liberty](/docs/Downloading-WAS-Liberty.md) and pass in the location of your install as the system property libertyRoot:

```bash
$ mvn -DlibertyRoot=<LibertyInstallLocation> install
```

### Running the application locally :pushpin: [in Eclipse](/docs/Using-WDT.md/###Running-the-application-locally)

Pre-requisite: [Download WAS Liberty](/docs/Downloading-WAS-Liberty.md)

Use the following to start the server and run the application:

```bash
$ export WLP_USER_DIR=/path/to/sample.async.websockets/async-websocket-wlpcfg
$ /path/to/wlp/bin/server run websocketSample
```

* `run` runs the server in the foreground.
* `start` runs the server in the background. Look in the logs directory for console.log to see what's going on, e.g.

```bash
$ tail -f ${WLP_USER_DIR}/servers/websocketSample/logs/console.log
```
