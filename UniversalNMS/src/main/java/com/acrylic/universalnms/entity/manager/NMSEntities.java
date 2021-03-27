package com.acrylic.universalnms.entity.manager;

import com.acrylic.universal.interfaces.Terminable;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universal.threads.TaskType;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class NMSEntities implements Terminable, Runnable {

    private NMSEntityRetriever<NMSEntityInstance> entityRetriever;
    /**
     * The entity ticker triggers the {@link NMSEntityInstance#tick()}
     * method which functions as a gateway to manipulate the entity be it
     * via the AI or other functionalities of the entity that cannot be
     * accessed elsewhere.
     *
     * In order to tick an entity, you will need to register it via
     * the retriever.
     */
    private final Scheduler<TaskType.RepeatingTask> entityTicker;

    public NMSEntities(@NotNull JavaPlugin plugin) {
        this(plugin, new NMSEntityRetriever<>());
    }

    public NMSEntities(@NotNull JavaPlugin plugin, @NotNull NMSEntityRetriever<NMSEntityInstance> entityRetriever) {
        this.entityRetriever = entityRetriever;
        this.entityTicker = Scheduler.sync()
                .runRepeatingTask(1, 1)
                .plugin(plugin)
                .setName("NMS Entity Ticker @ " + plugin.getName())
                .handle(this);
        this.entityTicker.build();
    }

    public void setEntityRetriever(@NotNull NMSEntityRetriever<NMSEntityInstance> entityRetriever) {
        this.entityRetriever = entityRetriever;
    }

    @NotNull
    public NMSEntityRetriever<NMSEntityInstance> getEntityRetriever() {
        return entityRetriever;
    }

    @Override
    public void terminate() {
        entityTicker.cancel();
    }

    @Override
    public void run() {
        for (NMSEntityInstance entityInstance : entityRetriever.getCached().values())
            entityInstance.tick(NMSEntityInstance.TickSource.NMS_ENTITIES);
    }
}
