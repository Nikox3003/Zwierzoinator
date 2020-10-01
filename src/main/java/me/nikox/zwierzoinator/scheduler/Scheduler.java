package me.nikox.zwierzoinator.scheduler;

import me.nikox.zwierzoinator.scheduler.tasks.AutoReleaseTask;
import me.nikox.zwierzoinator.scheduler.tasks.EntryUpdateTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private static List<Task> scheduledTasks = new ArrayList<>();
    private static ScheduledThreadPoolExecutor scheduler;

    private static void scheduleTasks(){
        scheduleRepeatingTask(new AutoReleaseTask(), 1, 1, TimeUnit.MINUTES);
        scheduleRepeatingTask(new EntryUpdateTask(), 1, 1, TimeUnit.MINUTES);
    }

    public static void setup() {
        scheduler = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        scheduler.setRemoveOnCancelPolicy(true);
        scheduleTasks();
    }

    public static Task scheduleTask(Runnable run, long delay, TimeUnit unit) {
        Task task = new Task(run, scheduledTasks.size() + 1);
        Runnable r = () -> {
            run.run();
            Scheduler.getScheduledTasks().remove(task);
        };
        task.setExecutor(scheduler.schedule(r, delay, unit));
        scheduledTasks.add(task);
        return task;
    }

    public static Task scheduleRepeatingTask(Runnable run, long delay, long period, TimeUnit unit) {
        Task task = new Task(run, scheduledTasks.size() + 1);
        Runnable r = () -> {
            run.run();
            Scheduler.getScheduledTasks().remove(task);
        };
        task.setExecutor(scheduler.scheduleAtFixedRate(r, delay, period, unit));
        scheduledTasks.add(task);
        return task;
    }

    public static void cancelAllTasks() {
        for (Task task : scheduledTasks) {
            task.cancel();
        }
    }

    public static void cancelTask(Task task) {
        task.cancel();
    }

    public static void cancelTask(int taskID) {
        for (Task task : scheduledTasks) {
            if (task.getTaskId() == taskID) {
                task.cancel();
                break;
            }
        }
    }

    public static List<Task> getScheduledTasks() {
        return scheduledTasks;
    }
}
