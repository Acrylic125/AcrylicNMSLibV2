package com.acrylic.universalnms.entity.entityconfiguration;

import com.acrylic.universal.entity.EntityInstance;

public interface LivingEntityConfiguration extends EntityConfiguration {

    LivingEntityConfiguration
            DEFAULT_LIVING_ENTITY = new LivingEntityConfigurationImpl(),
            PERSISTENT_LIVING_ENTITY = LivingEntityConfigurationImpl.livingBuilder()
                    .silentAIIfNoOneIsRenderer(false)
                    .build();

    /**
     *
     * @return If the entity instance should be removed
     * from the retriever on death.
     */
    boolean shouldRemoveFromRetrieverOnDeath();

    /**
     * THIS WILL NOT REMOVE THE ENTITY INSTANCE FROM
     * THE RETRIEVER UNLESS {@link #shouldRemoveFromRetrieverOnDeath()}
     * IS TRUE!
     *
     * @return Runs the {@link EntityInstance#delete()} method on death.
     */
    boolean shouldDeleteOnDeath();
}
