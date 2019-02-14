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
I know there are quite some explanations out there about what the OSGi whiteboard pattern is all about, e.g., the explanation on [enRoute](https://enroute.osgi.org/FAQ/400-patterns.html) or the [official whitepaper about OSGi's whiteboard pattern](https://www.osgi.org/wp-content/uploads/whiteboard1.pdf). But both seem to rather focus on a technical perspective, e.g., why this pattern is better than others (Factory, Listeners, etc.). It indeed is better but that's not the point. I as a developer want to know what fancy stuff I can do with it and this is where things get interesting, because I see whiteboards as the perfect pattern to sparate business logic from technical logic.

### Why are whiteboards so awesome?
Let's take the example implementation of this repository: The business logic is WHAT the task shall do (print out an awesome hello world message) and is encapsulated in the Task I wrote. The technical logic is HOW this task is executed (There's a timer that calls all tasks every 1 second).

During my day to day work I often think about the WHAT I want my code to do, but actually don't want to be bothered by the HOW my code is executed. So wouldn't it be perfect if there was a magic black box that just takes my WHAT and executes it the right way?
This is what whiteboards in OSGi are made for! And they are all over the place in OSGi.

* You want to write a Servlet for your fancy business application? Just make your Servlet a component and annotete it with the path under which you want to reach it. A Servlet Whiteboard takes care of the nasty rest.
* You want to receive Events that are broadcastet by the OSGi framework (Or any other entity that publishes them over EventAdmin)? Just implement the EventListener interface and annotate the event channel you are interested in. OSGi's EventListener Whiteboard takes care of the rest
* You want a REST endpiont? Guess what! OSGi got you cvoered too!
* Scheduling? Amdatu has a whiteboard for that!

You see it can be super easy to get stuff up and running without worrying about how it actually runs. This way you can separate your business logic from technical details.

### But what if there is no whiteboard for XY?
So you've stumbled upon some new library and it would be super useful for you if there were something like a whiteboard mechanism for it, but there is none? Well then this tutorial is the right starting point to get you up and running for writing your OWN whiteboard implementation. It's super easy!

For this I will now explain how the example in this repository works in deatil:

First, let's have a look at the whiteboard implementation itself. It is separated into two packages, the API, i.e., the `ITask` interface that shall be used by those who want to make use of our whiteboard, and the IMPL which only contains one component: our whiteboard.

Our whiteboard looks like this: 
```java
@Component(immediate = true)
public class TaskWhiteboard extends TimerTask{
    @Reference(policy=ReferencePolicy.DYNAMIC)
    private final List<ITask> tasks = new CopyOnWriteArrayList<>();

    private Timer timer = new Timer();

    @Activate
    void activate () {
        timer.scheduleAtFixedRate(this, 0, 1000);
    }

    @Deactivate
    void deactivate() {
        timer.cancel();
    }

    @Override
    public void run() {
           tasks.forEach(task -> task.run());
    }
}
```

The important part is actually just ` @Reference(policy=ReferencePolicy.DYNAMIC) private final List<ITask> tasks = new CopyOnWriteArrayList<>();`. This line tells OSGi that we want to have all components it can find that are registered as an `ITask` service. (Note you MUST define this variable to be final, otherwise it is not injected). The reference is defined to be DYNAMIC so that new `ITask`s that are registered during runtime are picked up without restarting our own component. This list of `ITask`s is the only thign we are interested in, as we now can do the "heavy lifting", i.e., the code that actually executes the WHAT of those tasks. This is a rather simplistic implementation, relaized through a Timer that just calls all tasks that are registered every 1 second.

The api I defined is just an interface that has to be implemented by those components that want to get executed by our whiteboard. The api package is marked as exported, i.e., it is made visible to other bundles, so that the components in those bundles actually are able to implement the `ITask` interface.

Finally, I created another bundle that only containes one component, i.e., `MyTask` that implements the api interface and therefore is picked up by our whiteboard at runtime:
```java
@Component
public class MyTask implements ITask{
	@Override
	public void run() {
        System.out.println("Hello Whiteboard!");
	}
}
```

## Conclusion
OSGi Whiteboards are one of the most powerful tools in OSGi and the perfect place to separate business and technical logic. If there is no implementation for you favorite API/Framework, then just start to implement one yourself! It is super easy and other developers will thank you!
