package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.enums.EntityAnimationEnum;
import com.acrylic.universalnms.packets.MultiPacketWrapper;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntityAnimationPackets extends MultiPacketWrapper {

    default void apply(@NotNull Entity entity, @NotNull EntityAnimationEnum animation) {
        switch (animation) {
            case ARM_SWING:
                attachArmSwingingAnimation(entity);
                break;
            case ARM_SWING_OFFHAND:
                attachLeftArmSwingingAnimation(entity);
                break;
            case CRIT:
                attachCritAnimation(entity);
                break;
            case MAGIC_CRIT:
                attachMagicCritAnimation(entity);
                break;
            case HURT:
                attachHurtAnimation(entity);
                break;
            case SLEEP:
                attachSleepAnimation(entity);
                break;
            case STOP_SLEEPING:
                attachStopSleepAnimation(entity);
                break;
            case SNEAK:
                attachSneakAnimation(entity);
                break;
            case STOP_SNEAKING:
                attachStopSneakAnimation(entity);
                break;
            case START_ELYTRA:
                attachStartElytraAnimation(entity);
                break;
            case STOP_ELYTRA:
                attachStopElytraAnimation(entity);
                break;
        }
    }

    default void apply(@NotNull Entity entity, @NotNull EntityAnimationEnum... animations) {
        for (EntityAnimationEnum animation : animations)
            apply(entity, animation);
    }

    EntityAnimationPackets attachArmSwingingAnimation(@NotNull Entity entity);

    Object getNewArmSwingPacket(@NotNull Entity entity);

    EntityAnimationPackets attachLeftArmSwingingAnimation(@NotNull Entity entity);

    Object getNewLeftArmSwingPacket(@NotNull Entity entity);

    EntityAnimationPackets attachCritAnimation(@NotNull Entity entity);

    Object getNewCritPacket(@NotNull Entity entity);

    EntityAnimationPackets attachMagicCritAnimation(@NotNull Entity entity);

    Object getNewMagicCritPacket(@NotNull Entity entity);

    EntityAnimationPackets attachHurtAnimation(@NotNull Entity entity);

    Object getNewHurtPacket(@NotNull Entity entity);

    EntityAnimationPackets attachSleepAnimation(@NotNull Entity entity, @NotNull Location location);

    Object getNewSleepPacket(@NotNull Entity entity, @NotNull Location location);

    EntityAnimationPackets attachSleepAnimation(@NotNull Entity entity);

    Object getNewSleepPacket(@NotNull Entity entity);

    EntityAnimationPackets attachStopSleepAnimation(@NotNull Entity entity);

    Object generateStopSleepPacket(@NotNull Entity entity);

    EntityAnimationPackets attachSneakAnimation(@NotNull Entity entity);

    Object getNewSneakPacket(@NotNull Entity entity);

    EntityAnimationPackets attachStopSneakAnimation(@NotNull Entity entity);

    Object getNewStopSneakPacket(@NotNull Entity entity);

    EntityAnimationPackets attachStartElytraAnimation(@NotNull Entity entity);

    Object getNewStartElytraPacket(@NotNull Entity entity);

    EntityAnimationPackets attachStopElytraAnimation(@NotNull Entity entity);

    Object getNewStopElytraPacket(@NotNull Entity entity);

}
