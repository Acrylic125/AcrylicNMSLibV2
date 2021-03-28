package com.acrylic.universalnms.entity.entityconfiguration;

import com.acrylic.universal.utils.BitMaskUtils;
import org.jetbrains.annotations.NotNull;

public class LivingEntityConfigurationImpl
        extends EntityConfigurationImpl
        implements LivingEntityConfiguration {

    public static LivingBuilder livingBuilder() {
        return livingBuilder(new LivingEntityConfigurationImpl());
    }

    public static LivingBuilder livingBuilder(@NotNull LivingEntityConfigurationImpl entityConfiguration) {
        return new LivingBuilder(entityConfiguration);
    }

    public static class LivingBuilder extends AbstractBuilder<LivingBuilder> {

        private final LivingEntityConfigurationImpl entityConfiguration;

        private LivingBuilder(LivingEntityConfigurationImpl entityConfiguration) {
            this.entityConfiguration = entityConfiguration;
        }

        public LivingBuilder removeFromRetrieverOnDeath(boolean b) {
            entityConfiguration.flags = BitMaskUtils.setBitToMask(entityConfiguration.flags, 0x08, b);
            return this;
        }

        public LivingBuilder deleteOnDeath(boolean b) {
            entityConfiguration.flags = BitMaskUtils.setBitToMask(entityConfiguration.flags, 0x10, b);
            return this;
        }

        public LivingBuilder timeAfterDeathToRemoveFromRetriever(long time) {
            entityConfiguration.timeAfterDeathToRemoveFromRetriever = time;
            return this;
        }

        public LivingBuilder timeAfterDeathToDelete(long time) {
            entityConfiguration.timeAfterDeathToDelete = time;
            return this;
        }

        @Override
        public LivingEntityConfigurationImpl getBuildFrom() {
            return entityConfiguration;
        }

        @Override
        public LivingEntityConfigurationImpl build() {
            return entityConfiguration;
        }
    }

    private long
            timeAfterDeathToRemoveFromRetriever = 0,
            timeAfterDeathToDelete = 3_000;


    public LivingEntityConfigurationImpl() {
        /*
         * Remove from retriever on death (0x08),
         * Delete on death (0x10).
         */
        this.flags |= 0x08 | 0x10;
    }

    //0x08
    @Override
    public boolean shouldRemoveFromRetrieverOnDeath() {
        return (flags & 0x08) == 0x08;
    }

    @Override
    public long getTimeAfterDeathToRemoveFromRetriever() {
        return timeAfterDeathToRemoveFromRetriever;
    }

    //0x10
    @Override
    public boolean shouldDeleteOnDeath() {
        return (flags & 0x10) == 0x10;
    }

    @Override
    public long getTimeAfterDeathToDelete() {
        return timeAfterDeathToDelete;
    }
}
