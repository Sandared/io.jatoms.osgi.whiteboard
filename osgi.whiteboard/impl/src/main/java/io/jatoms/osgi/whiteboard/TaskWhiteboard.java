package io.jatoms.osgi.whiteboard;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import io.jatoms.osgi.whiteboard.api.ITask;

@Component
public class TaskWhiteboard {
    private List<ITask> tasks = new CopyOnWriteArrayList<>();
    private Executor exec = Executors.newSingleThreadExecutor();
    private volatile boolean stopped = false;

    @Activate
    void activate () {
        exec.execute(() -> {
            while(!stopped){
                tasks.forEach((task)->{
                    try {
                        task.run();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                });
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        });
    }

    @Deactivate
    void deactivate() {
        stopped = true;
    }

    @Reference(cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.DYNAMIC, policyOption=ReferencePolicyOption.GREEDY)
    void addTask(ITask task){
        tasks.add(task);
    }

    void removeTask(ITask task){
        tasks.remove(task);
    }
}