package com.acrylic.universalnms.entity.wrapper;

import com.acrylic.universal.entity.LivingEntityInstance;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfiguration;
import com.acrylic.universalnms.entity.manager.NMSEntities;
import com.acrylic.universalnms.renderer.AbstractEntityRenderer;
import org.jetbrains.annotations.NotNull;

public interface NMSLivingEntityWrapper extends NMSEntityWrapper {

    @NotNull
    @Override
    NMSLivingEntityInstance getEntityInstance();

    default void onDeath() {
        LivingEntityConfiguration configuration = getEntityInstance().getEntityConfiguration();
        if (configuration.shouldRemoveFromRetrieverOnDeath())
            Scheduler.sync()
                    .runDelayedTask(configuration.getTimeAfterDeathToRemoveFromRetriever())
                    .setName("Unregister Entity on death")
                    .plugin(NMSLib.getPlugin())
                    .handleThenBuild(() -> {
                        NMSLivingEntityInstance livingEntityInstance = getEntityInstance();
                        NMSEntities entities = NMSLib.getNMSEntities();
                        AbstractEntityRenderer renderer = livingEntityInstance.getPacketHandler().getRenderer();
                        renderer.unbindEntity(livingEntityInstance);
                        entities.getDefaultRendererWorker().checkForRemoval(renderer);
                        entities.getEntityRetriever().unregister(livingEntityInstance);
                    });
        if (configuration.shouldDeleteOnDeath())
            Scheduler.sync()
                    .runDelayedTask(configuration.getTimeAfterDeathToDelete())
                    .setName("Delete Entity on death")
                    .plugin(NMSLib.getPlugin())
                    .handleThenBuild(() -> getEntityInstance().delete());
    }

}
