## Building and running the sample using the command line

### Clone Git Repo
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#clone-git-repo)

```bash
$ git clone https://github.com/WASdev/sample.async.websockets.git
```

### Building the sample
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#building-the-sample-in-eclipse)

###### [Apache Maven](http://maven.apache.org/) commands

```bash
$ mvn install
```

You can skip tests with the following:

```bash
$ mvn install -DskipTests=true
```

In addition to publishing the WAR to the local Maven repository, the built WAR file is copied into the apps directory of the server configuration located in the async-websocket-wlpcfg directory:

```text
async-websocket-wlpcfg
 +- target
     +- async-websocket-wlpcfg-1.0.zip                         <-- packaged server file containing the server, application, and configuration
        +- wlp
            +- usr
                +- server
                    +- websocketSample
                        +- server.xml                          <-- server configuration
                        +- apps                                <- directory for applications
                            +- async-websocket-application.war <- sample application
                        +- logs                                <- created by running the server locally
                        +- workarea                            <- created by running the server locally
```

### Running the application locally
:pushpin: [Switch to Eclipse example](/docs/Using-WDT.md/#running-the-application-locally)

Change the current directory to the `async-websocket-wlpcfg` sub-project, and use the following commands to start the server and run the application:

To run the server in the foreground:

```bash
$ mvn liberty:run-server
```

To run the server in a background process:

```bash
$ mvn liberty:start-server
```

To stop the server:
```bash
$ mvn liberty:stop-server
```


