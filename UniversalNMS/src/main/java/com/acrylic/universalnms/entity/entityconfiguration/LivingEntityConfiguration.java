package com.acrylic.universalnms.entity.entityconfiguration;

import com.acrylic.universalnms.entity.NMSEntityInstance;

public interface LivingEntityConfiguration extends EntityConfiguration {

    LivingEntityConfiguration
            DEFAULT_LIVING_ENTITY = new LivingEntityConfigurationImpl(),
            PERSISTENT_LIVING_ENTITY = LivingEntityConfigurationImpl.livingBuilder()
                    .silentAIIfNoOneIsRenderer(false)
                    .build(),
            NO_AI_LIVING_ENTITY = LivingEntityConfigurationImpl.livingBuilder()
                    .runAIByNMSEntities(false)
                    .build();


    /**
     *
     * @return If the entity instance should be removed
     * from the retriever on death.
     */
    boolean shouldRemoveFromNMSEntitiesOnDeath();

    long getTimeAfterDeathToRemoveFromNMSEntities();

    /**
     *
     * @return Should {@link NMSEntityInstance#deleteDisplay()} method be executed on death.
     */
    boolean shouldDeleteDisplayOnDeath();

    long getTimeAfterDeathToDeleteDisplay();

}
