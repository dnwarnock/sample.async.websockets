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

## Building with Gradle

This sample can be built using [Gradle](http://gradle.org/).

```bash
$ gradle build publishToMavenLocal
```

If you want to also run the functional tests then you need to [Download WAS Liberty](/docs/Downloading-WAS-Liberty.md) and set the libertyRoot property in the gradle.properties file to point to your Liberty install.
:pushpin: Note: If you are using Windows, installing Liberty into C:\Liberty make the tests run without having to set a property.

## Building with maven

This sample can be built using [Apache Maven](http://maven.apache.org/).

```bash
$ mvn install
```

If you want to also run the functional tests then you need to [Download WAS Liberty](/docs/Downloading-WAS-Liberty.md) and pass in the location of your install as the system property libertyRoot:

```bash
$ mvn -DlibertyRoot=<LibertyInstallLocation> install
```

:pushpin: Note: If you are using Windows, installing Liberty into C:\Liberty make the tests run without having to pass in a property.

## Next step

[Downloading WAS Liberty](/docs/Downloading-WAS-Liberty.md)
