package com.acrylic.universalnms.entity.entityconfiguration;

import com.acrylic.universal.entity.EntityInstance;

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
     * @return Should {@link EntityInstance#delete()} method be executed on death.
     */
    boolean shouldDeleteOnDeath();

    long getTimeAfterDeathToDelete();

}
