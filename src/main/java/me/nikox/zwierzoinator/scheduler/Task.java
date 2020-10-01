package me.nikox.zwierzoinator.scheduler;

import java.util.concurrent.ScheduledFuture;

public class Task {

    private final Runnable runnable;
    private ScheduledFuture<?> executor;
    private int taskId;

    public Task(Runnable run) {
        runnable = run;
    }

    public Task(Runnable run, int taskId) {
        runnable = run;
        this.taskId = taskId;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public int getTaskId() {
        return taskId;
    }

    public void run() {
        runnable.run();
    }

    public void setId(int id) {
        this.taskId = id;
    }

    public void setExecutor(ScheduledFuture<?> executor) {
        this.executor = executor;
    }

    public void cancel() {
        executor.cancel(true);
        Scheduler.getScheduledTasks().remove(this);
    }

}
