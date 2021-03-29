package com.acrylic.universalnms.render;

import com.acrylic.universal.interfaces.Terminable;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universal.threads.TaskType;
import com.acrylic.universalnms.NMSLib;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

public class EntityRendererWorker implements Runnable, Terminable {

    protected static AtomicInteger RENDERER_ID_COUNTER = new AtomicInteger(0);

    private final Scheduler<TaskType.RepeatingTask> scheduler;


    public EntityRendererWorker() {
        this(Scheduler.sync().runRepeatingTask(1, 1).plugin(NMSLib.getPlugin()));
    }

    public EntityRendererWorker(@NotNull Scheduler<TaskType.RepeatingTask> scheduler) {
        this.scheduler = scheduler;
        scheduler.handle(this);
    }

    @Override
    public void terminate() {

    }

    @Override
    public void run() {

    }
}
