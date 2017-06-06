## Eclipse / WDT

The WebSphere Development Tools (WDT) for Eclipse can be used to control the server (start/stop/dump/etc.), it also supports incremental publishing with minimal restarts, working with a debugger to step through your applications, etc.

WDT also provides:

* content-assist for server configuration (a nice to have: server configuration is minimal, but the tools can help you find what you need and identify finger-checks, etc.)
* automatic incremental publish of applications so that you can write and test your changes locally without having to go through a build/publish cycle or restart the server (which is not that big a deal given the server restarts lickety-split, but less is more!).
* improved Maven integration for web projects starting with WDT 17.0.0.2 including support for loose applications.

Installing WDT on Eclipse is as simple as a drag-and-drop, but the process is explained [on wasdev.net] [wasdev-wdt].

[wasdev-wdt]: https://developer.ibm.com/wasdev/downloads/liberty-profile-using-eclipse/

### Clone Git Repo
:pushpin: [Switch to cmd line example](/docs/Using-cmd-line.md/#clone-git-repo)

If the sample git repository hasn't been cloned yet, WDT has git tools integrated into the IDE:

1.  Open the Git repositories view
    * *Window -> Show View -> Other*
    * Type "git" in the filter box, and select *Git Repositories*
2.  Copy Git repo url by finding the textbox under "HTTPS clone URL" at the top of this page, and select *Copy to clipboard*
3.  In the Git repositories view, select the hyperlink `Clone a Git repository`
4.  The git repo url should already be filled in.  Select *Next -> Next -> Finish*
5.  The "sample.async.websockets [master]" repo should appear in the view

### Building the sample in Eclipse with Maven
:pushpin: [Switch to cmd line example](/docs/Using-cmd-line.md/#building-the-sample)

###### Import Maven projects into WDT

1.  In the Git Repository view, expand the websocket repo to see the "Working Tree" folder
2.  Right-click on this folder, and select *Copy path to Clipboard*
3.  Select menu *File -> Import -> Maven -> Existing Maven Projects*
4.  In the Root Directory textbox, Paste in the repository directory.
5.  Select *Browse...* button and select *Finish* (confirm it finds 3 pom.xml files)
6.  This will create 3 projects in Eclipse: sample.async-websockets, async-websocket-application, and async-websocket-wlpcfg

:star: *Note:* If you did not use Eclipse/WDT to clone the git repository, follow from step 3, but navigate to the cloned repository directory rather than pasting its name in step 4.

###### Run Maven install

1. Right-click on sample.async.websocket/pom.xml
2. *Run As > Maven build...*
3. In the *Goals* section enter "install"
4. Click *Run*

### Running the application locally
:pushpin: [Switch to cmd line example](/docs/Using-cmd-line.md/#running-the-application-locally)

For the purposes of this sample, we will create the Liberty server (step 3 in the wasdev.net instructions) a little differently to create and customize a Runtime Environment that will allow the server to directly use the configuration in the `async-websocket-wlpcfg` project.

###### Create a Runtime Environment in Eclipse

1. Open the 'Runtime Explorer' view:
    * *Window -> Show View -> Other*
    * type `runtime` in the filter box to find the view (it's under the Server heading).
2. Right-click in the view, and select *New -> Runtime Environment*
3. Give the Runtime environment a name, e.g. `wlp_websocket_server`
4. Select the installation created at `sample.async.websocket/async-websocket-wlpcfg/target/wlp/`
5. Follow the prompts until you *Finish* creating the Runtime Environment. (Needed features were installed during `mvn install`)

###### Add the User directory from the maven project, and create a server

1. From the Runtime Explorer, right-click *wlp-websocket-server -> Servers -> websocketSample*, and select *Server*
2. Look in the Servers view for your new server! 

###### Running Liberty and the sample application from WDT

1.  Navigate to the Servers view
2.  Right-click *websocketSample -> Start*
3.  Confirm web browser opens on "http://localhost:9082/websocket/" with 5 hyperlinks to run samples

:star: *Note:* You can also right-click *async-websocket-application -> Run As -> Run on Server* to start the application. Some versions of WDT incorrectly map the cdi-1.2 dependency to the CDI 1.0 Facet, which prevents the *Run As ...* operation in step 2 from succeeding. If this happens, Right-click on the `async-websocket-application` project, and select *Properties*, then select *Project Facets* in the left-hand pane. Change the the "Context and dependency injection (CDI)" facet to use version 1.2.

#### Tips

* When importing the existing maven project into Eclipse, Eclipse will (by default) "helpfully" add this project to an (extraneous) ear. To turn this off, go to Preferences -> Java EE -> Project, and uncheck "Add project to an EAR" before you import the project. If you forgot to do this, just delete the ear project; no harm.
