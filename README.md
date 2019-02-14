# OSGi Whiteboard Pattern
This repository is just a showcase on how simple it is to have your own OSGi whiteboard implementation within 5 minutes (or less)... depending on how fast Maven is able to download the internet ;)
 
 ![Gitpod Whiteboard](https://github.com/Sandared/io.jatoms.osgi.whiteboard/blob/master/gitpod-whiteboard.PNG)


## Get started
* [![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io#https://github.com/Sandared/io.jatoms.osgi.whiteboard)
* Wait for Maven to download the internet
* See the OSGi whiteborad in action, greeting you the most awesome way ever: The whiteboard way ;)

## Things to do
* Play around with the code
* Debug the whiteboard via `debug app/target/app.jar` (More information on [how to debug in GitPod](https://github.com/Sandared/io.jatoms.osgi.base/blob/master/README.md#how-to-debug-an-application-without-a-main-method))

## Contribute
* Any suggestions/comments/additional awesomeness? open an Issue :)
* Anything else? Write me on [Twitter](https://twitter.com/SanfteSchorle)

## How to reproduce
1) [![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io#https://github.com/Sandared/io.jatoms.osgi.base)
1) Within the terminal at the bottom type `project` and fill out *groupId* and *artifactId* as you wish
1) Within your newly created project create
   1) The Whiteboard implementation
   1) An additional package "api" and within it
      1) An interface `ITask` that extends `Runnable`
      1) A `package-info.java` file declaring the api package to be exported via the `Export` annotation
1) `cd ..` to go back into your parent project
1) `type ds` to create a new component and fill out groupId and artifactId
1) Within the `pom.xml` of your new component declare a dependency to your `impl` project
1) Implement the `ITask` interface in your new component
1) Within `app pom.xml` declare a dependency to your new component project
1) Within `app.bndrun` change the `-runrequires:` to `osgi.identity;filter:='(osgi.identity=<your impl>)', osgi.identity;filter:='(osgi.identity=<your comp>)'` where impl and comp are the bundle names of your whiteboard impl and the additional component project.
1) cd to parent project, type `resolve app` and then `run app/target/app.jar`
1) see your whiteboard implementation in action

## What's going on behind the curtains?
This section is for those who want to understand what is going on in the background so that this example works.

### What is a whiteboard?
