package io.jatoms.task;

import org.osgi.service.component.annotations.Component;

import io.jatoms.osgi.whiteboard.api.ITask;

@Component
public class MyTask implements ITask{
	@Override
	public void run() {
        System.out.println("Hello Whiteboard!");
	}
}