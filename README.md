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

## How to reproduce
1) [![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io#https://github.com/Sandared/io.jatoms.osgi.base)
1) Within the terminal at the bottom type `project` and fill out *groupId* and *artifactId* as you wish
1) Within your newly created project create
  1) The Whiteboard implementation
  1) An additional package "api" and within it
    1) An interface `ITask`
    1) An package-info.java file declaring the api package to be exported
    ...
TODO
