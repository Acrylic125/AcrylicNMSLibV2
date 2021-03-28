package com.acrylic.universalnms.entity.wrapper;

import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfiguration;
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
                    .handleThenBuild(() -> NMSLib.getNMSEntities().getEntityRetriever().unregister(getEntityInstance()));
        if (configuration.shouldDeleteOnDeath())
            Scheduler.sync()
                    .runDelayedTask(configuration.getTimeAfterDeathToDelete())
                    .setName("Delete Entity on death")
                    .plugin(NMSLib.getPlugin())
                    .handleThenBuild(() -> getEntityInstance().delete());
    }

}
