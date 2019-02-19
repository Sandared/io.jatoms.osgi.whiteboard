package io.jatoms.osgi.whiteboard;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import io.jatoms.osgi.whiteboard.api.ITask;

@Component(immediate = true)
public class TaskWhiteboard extends TimerTask{
    @Reference(policy=ReferencePolicyOption.GREEDY)
    private volatile List<ITask> tasks;

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
