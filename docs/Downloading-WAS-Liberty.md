## Downloading WAS Liberty

There are lots of ways to get your hands on WAS Liberty. Note that you will need a version of Liberty that has support for Servlet 3.1, WebSockets 1.1, and CDI 1.2 for this sample, as it has a little bit of all of those (Java EE 7 Web Profile will do).

To download just the WAS Liberty runtime, go to the [wasdev.net Downloads page][wasdev], and choose between the [latest version of the runtime][wasdev-latest] or the [latest beta][wasdev-beta]. You can also download Liberty via [Eclipse and WDT](/docs/Downloading-WAS-Liberty.md)

There are a few options to choose from (especially for the beta drivers). Choose the one that is most appropriate.
* There are convenience archives for downloading pre-defined content groupings
* You can add additional features from the repository using the [installUtility][installUtility] or the [maven][maven-plugin]/[Gradle][gradle-plugin] plugins.

[wasdev]: https://developer.ibm.com/wasdev/downloads/
[wasdev-latest]: https://developer.ibm.com/wasdev/downloads/liberty-profile-using-non-eclipse-environments/
[wasdev-beta]: https://developer.ibm.com/wasdev/downloads/liberty-profile-beta/
[installUtility]: http://www-01.ibm.com/support/knowledgecenter/#!/was_beta_liberty/com.ibm.websphere.wlp.nd.multiplatform.doc/ae/rwlp_command_installutility.html
[maven-plugin]: https://github.com/WASdev/ci.maven
[gradle-plugin]: https://github.com/WASdev/ci.gradle

#### Tips

* If you use bash, consider trying the [command line tools](https://github.com/WASdev/util.bash.completion), which provide tab-completion for the server and other commands.

## Next step

* [Start the server using the command line, or Maven/Gradle plugins](/docs/Starting-the-server.md), or
* [Start the server using Eclipse and WebSphere Devlopment Tools (WDT)](/docs/Using-WDT.md)

## Downloading WebSphere Development Tools (WDT)

The WebSphere Development Tools (WDT) for Eclipse can be used to control the server (start/stop/dump/etc.), it also supports incremental publishing with minimal restarts, working with a debugger to step through your applications, etc.

WDT also provides:

* content-assist for server configuration (a nice to have: server configuration is minimal, but the tools can help you find what you need and identify finger-checks, etc.)
* automatic incremental publish of applications so that you can write and test your changes locally without having to go through a build/publish cycle or restart the server (which is not that big a deal given the server restarts lickety-split, but less is more!).

Installing WDT on Eclipse is as simple as a drag-and-drop, but the process is explained [on wasdev.net] [wasdev-wdt].

[wasdev-wdt]: https://developer.ibm.com/wasdev/downloads/liberty-profile-using-eclipse/