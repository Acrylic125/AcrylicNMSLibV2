package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.packets.MultiPacketWrapper;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntityOrientationPackets extends MultiPacketWrapper {

    default void apply(@NotNull Entity entity) {
        Location location = entity.getLocation();
        apply(entity, location.getYaw(), location.getPitch());
    }

    void apply(@NotNull Entity entity, float yaw, float pitch);

    default void applyLookOrientation(@NotNull Entity entity) {
        Location location = entity.getLocation();
        applyLookOrientation(entity, location.getYaw(), location.getPitch());
    }

    void applyLookOrientation(@NotNull Entity entity, float yaw, float pitch);

    default void applyHeadOrientation(@NotNull Entity entity) {
        Location location = entity.getLocation();
        applyHeadOrientation(entity, location.getYaw());
    }

    void applyHeadOrientation(@NotNull Entity entity, float yaw);

    static byte floatToByteValue(float value) {
        return (byte) (value * 256 / 360);
    }

}
