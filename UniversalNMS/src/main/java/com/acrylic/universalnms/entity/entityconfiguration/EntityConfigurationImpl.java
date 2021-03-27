package com.acrylic.universalnms.entity.entityconfiguration;

import com.acrylic.universal.utils.BitMaskUtils;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class EntityConfigurationImpl implements EntityConfiguration {

    public static Builder builder() {
        return builder(new EntityConfigurationImpl());
    }

    public static Builder builder(@NotNull EntityConfigurationImpl entityConfiguration) {
        return new Builder(entityConfiguration);
    }

    public static class Builder {

        private final EntityConfigurationImpl entityConfiguration;

        private Builder(EntityConfigurationImpl entityConfiguration) {
            this.entityConfiguration = entityConfiguration;
        }

        public Builder ticksToCheckRenderer(int ticks) {
            entityConfiguration.ticksToCheckRenderer = ticks;
            return this;
        }

        public Builder checkRendererIf(@Nullable Predicate<NMSEntityInstance> condition) {
            entityConfiguration.checkRendererIf = condition;
            return this;
        }

        public Builder runAIIf(@Nullable Predicate<NMSEntityInstance> condition) {
            entityConfiguration.runAIIf = condition;
            return this;
        }

        public Builder silentAIIfNoOneIsRenderer(boolean b) {
            entityConfiguration.flags = BitMaskUtils.setBitToMask(entityConfiguration.flags, 0x01, b);
            return this;
        }

        public Builder runAIByNMSEntities(boolean b) {
            entityConfiguration.flags = BitMaskUtils.setBitToMask(entityConfiguration.flags, 0x02, b);
            return this;
        }

        public Builder dropItemsOnDeath(boolean b) {
            entityConfiguration.flags = BitMaskUtils.setBitToMask(entityConfiguration.flags, 0x04, b);
            return this;
        }

        public EntityConfigurationImpl build() {
            return entityConfiguration;
        }

    }

    private int
            flags = 0x01 | 0x02,
            ticksToCheckRenderer = 20;
    private Predicate<NMSEntityInstance>
            checkRendererIf,
            runAIIf;

    @Override
    public int getTicksToCheckRender() {
        return ticksToCheckRenderer;
    }

    @Nullable
    @Override
    public Predicate<NMSEntityInstance> getCheckRendererIf() {
        return checkRendererIf;
    }

    //0x01
    @Override
    public boolean silentAIIfNoOneIsRendered() {
        return (flags & 0x01) == 0x01;
    }

    @Nullable
    @Override
    public Predicate<NMSEntityInstance> getRunAIIf() {
        return runAIIf;
    }

    //0x02
    @Override
    public boolean shouldRunAIByNMSEntities() {
        return (flags & 0x02) == 0x02;
    }

    //0x04
    @Override
    public boolean shouldDropItemsOnDeath() {
        return (flags & 0x04) == 0x04;
    }
}
