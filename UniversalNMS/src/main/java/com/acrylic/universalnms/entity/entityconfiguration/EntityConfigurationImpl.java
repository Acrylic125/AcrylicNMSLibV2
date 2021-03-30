package com.acrylic.universalnms.entity.entityconfiguration;

import com.acrylic.universal.utils.BitMaskUtils;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class EntityConfigurationImpl implements EntityConfiguration {

    public static EntityBuilder entityBuilder() {
        return entityBuilder(new EntityConfigurationImpl());
    }

    public static EntityBuilder entityBuilder(@NotNull EntityConfigurationImpl entityConfiguration) {
        return new EntityBuilder(entityConfiguration);
    }

    public static class EntityBuilder extends AbstractBuilder<EntityBuilder> {

        private final EntityConfigurationImpl entityConfiguration;

        public EntityBuilder(EntityConfigurationImpl entityConfiguration) {
            this.entityConfiguration = entityConfiguration;
        }

        @Override
        public EntityConfigurationImpl getBuildFrom() {
            return entityConfiguration;
        }

        @Override
        public EntityConfigurationImpl build() {
            return entityConfiguration;
        }
    }

    @SuppressWarnings("unchecked")
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B>> {

        public B ticksToCheckRenderer(int ticks) {
            getBuildFrom().ticksToCheckRenderer = ticks;
            return (B) this;
        }

        public B runAIIf(@Nullable Predicate<NMSEntityInstance> condition) {
            getBuildFrom().runAIIf = condition;
            return (B) this;
        }

        public B silentAIIfNoOneIsRenderer(boolean b) {
            getBuildFrom().flags = BitMaskUtils.setBitToMask(getBuildFrom().flags, 0x01, b);
            return (B) this;
        }

        public B runAIByNMSEntities(boolean b) {
            getBuildFrom().flags = BitMaskUtils.setBitToMask(getBuildFrom().flags, 0x02, b);
            return (B) this;
        }

        public B useTeleportForPathfinderStrategy(boolean b) {
            getBuildFrom().flags = BitMaskUtils.setBitToMask(getBuildFrom().flags, 0x04, b);
            return (B) this;
        }

        public abstract EntityConfigurationImpl getBuildFrom();

        public abstract EntityConfigurationImpl build();
    }

    protected int
            /*
            * Default flags:
            * Silent AI if no one is rendered for (0x01),
            * Run AI by NMSEntities (0x02),
            */
            flags = 0x01 | 0x02,
            ticksToCheckRenderer = 20;
    private Predicate<NMSEntityInstance>
            runAIIf;

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
    public boolean useTeleportForPathfindingStrategy() {
        return (flags & 0x04) == 0x04;
    }

}
