package com.acrylic.universalnms.entityai.processors;

import com.acrylic.universal.interfaces.Terminable;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universal.threads.TaskType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentLinkedDeque;

public class AsyncProcessor<T extends Runnable> implements Runnable, Terminable {

    public static AsyncProcessor<Runnable> TEMP;

    private final ConcurrentLinkedDeque<T> processes = new ConcurrentLinkedDeque<>();
    private final Scheduler<TaskType.RepeatingTask> scheduler;

    public AsyncProcessor(@NotNull JavaPlugin plugin) {
        scheduler = Scheduler.async()
                .runRepeatingTask(1, 1)
                .plugin(plugin)
                .setName("Async Processor")
                .handle(this);
        scheduler.build();
    }

    public void addProcess(@NotNull T process) {
        processes.add(process);
    }

    public void removeProcess(@NotNull T process) {
        processes.remove(process);
    }

    @Override
    public synchronized void run() {
        processes.removeIf(process -> {
            process.run();
            return true;
        });
    }

    @Override
    public void terminate() {
        scheduler.cancel();
    }
}
