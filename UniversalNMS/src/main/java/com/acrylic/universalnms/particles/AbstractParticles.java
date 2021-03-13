package com.acrylic.universalnms.particles;

import com.acrylic.universalnms.packets.SinglePacketWrapper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public interface AbstractParticles
        extends SinglePacketWrapper {

    void build();

    float[] getOffset();

    boolean isLongDistance();

    float getSpeed();

    int getAmount();

    float[] getLocation();

    void setSpeed(float speed);

    void setAmount(int amount);

    void setLongDistance(boolean longDistance);

    void setLocation(double x, double y, double z);

    default void setLocation(@NotNull Location location) {
        setLocation(location.getX(), location.getY(), location.getZ());
    }

    default void setLocation(@NotNull Block block) {
        setLocation(block.getX(), block.getY(), block.getZ());
    }

    void setOffset(float x, float y, float z);

}
