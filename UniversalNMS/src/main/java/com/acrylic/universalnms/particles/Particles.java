package com.acrylic.universalnms.particles;

import com.acrylic.universalnms.packets.SinglePacketWrapper;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public interface Particles
        extends SinglePacketWrapper {

    void build();

    float[] getOffset();

    Object getParticleType();

    boolean isLongDistance();

    float getSpeed();

    int getAmount();

    float[] getLocation();

    void setParticleType(@NotNull EnumWrappers.Particle particle);

    void setSpeed(float speed);

    void setAmount(int amount);

    void setLongDistance(boolean longDistance);

    void setLocation(double x, double y, double z);

    default void setLocation(@NotNull Location location) {
        setLocation(location.getZ(), location.getY(), location.getZ());
    }

    default void setLocation(@NotNull Block block) {
        setLocation(block.getZ(), block.getY(), block.getZ());
    }

    void setOffset(float x, float y, float z);

    @Override
    default boolean validateUse() {
        return (getLocation() == null || getParticleType() == null);
    }

}
