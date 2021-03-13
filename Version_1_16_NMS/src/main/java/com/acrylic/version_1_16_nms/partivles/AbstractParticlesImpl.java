package com.acrylic.version_1_16_nms.partivles;

import com.acrylic.universalnms.particles.AbstractParticles;
import com.acrylic.universalnms.particles.Particles;
import com.acrylic.version_1_16_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;

public abstract class AbstractParticlesImpl
        extends SinglePacketWrapperImpl
        implements AbstractParticles {

    protected PacketPlayOutWorldParticles packet;
    protected boolean longDistance = true;
    protected float speed = 0;
    protected int amount = 1;
    protected float[] location;
    protected float[] offset;

    @Override
    public float[] getOffset() {
        return offset;
    }

    @Override
    public boolean isLongDistance() {
        return longDistance;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public float[] getLocation() {
        return location;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setLongDistance(boolean longDistance) {
        this.longDistance = longDistance;
    }

    @Override
    public void setLocation(double x, double y, double z) {
        this.location = new float[]{(float) x, (float) y, (float) z};
    }

    @Override
    public void setOffset(float x, float y, float z) {
        this.offset = new float[]{x, y, z};
    }

    @Override
    public PacketPlayOutWorldParticles getPacket() {
        return packet;
    }
}
