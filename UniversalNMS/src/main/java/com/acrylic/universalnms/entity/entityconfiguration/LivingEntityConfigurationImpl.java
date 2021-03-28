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
            entityConfiguration.livingFlags = BitMaskUtils.setBitToMask(entityConfiguration.livingFlags, 0x01, b);
            return this;
        }

        public LivingBuilder deleteOnDeath(boolean b) {
            entityConfiguration.livingFlags = BitMaskUtils.setBitToMask(entityConfiguration.livingFlags, 0x02, b);
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

    /*
     * Remove from retriever on death (0x01),
     * Delete on death (0x2).
     */
    protected int livingFlags = 0x01 | 0x02;
    private long
            timeAfterDeathToRemoveFromRetriever = 0,
            timeAfterDeathToDelete = 3_000;

    //0x08
    @Override
    public boolean shouldRemoveFromRetrieverOnDeath() {
        return (livingFlags & 0x01) == 0x01;
    }

    @Override
    public long getTimeAfterDeathToRemoveFromRetriever() {
        return timeAfterDeathToRemoveFromRetriever;
    }

    //0x10
    @Override
    public boolean shouldDeleteOnDeath() {
        return (livingFlags & 0x02) == 0x02;
    }

    @Override
    public long getTimeAfterDeathToDelete() {
        return timeAfterDeathToDelete;
    }
}
