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

        public LivingBuilder removeFromNMSEntitiesOnDeath(boolean b) {
            entityConfiguration.livingFlags = BitMaskUtils.setBitToMask(entityConfiguration.livingFlags, 0x01, b);
            return this;
        }

        public LivingBuilder deleteDisplayOnDeath(boolean b) {
            entityConfiguration.livingFlags = BitMaskUtils.setBitToMask(entityConfiguration.livingFlags, 0x02, b);
            return this;
        }

        public LivingBuilder timeAfterDeathToRemoveFromNMSEntities(long time) {
            entityConfiguration.timeAfterDeathToRemoveFromNMSEntities = time;
            return this;
        }

        public LivingBuilder timeAfterDeathToDeleteDisplay(long time) {
            entityConfiguration.timeAfterDeathToDeleteFromDisplay = time;
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
     * Remove from NMS Entities on death (0x01),
     * Delete Display on death (0x2).
     */
    protected int livingFlags = 0x01 | 0x02;
    private long
            timeAfterDeathToRemoveFromNMSEntities = 0,
            timeAfterDeathToDeleteFromDisplay = 3_000;

    @Override
    public boolean shouldRemoveFromNMSEntitiesOnDeath() {
        return (flags & 0x01) == 0x01;
    }

    @Override
    public long getTimeAfterDeathToRemoveFromNMSEntities() {
        return timeAfterDeathToRemoveFromNMSEntities;
    }

    @Override
    public boolean shouldDeleteDisplayOnDeath() {
        return (flags & 0x02) == 0x02;
    }

    @Override
    public long getTimeAfterDeathToDeleteDisplay() {
        return timeAfterDeathToDeleteFromDisplay;
    }

}
