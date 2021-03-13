package com.acrylic.version_1_8_nms.partivles;

import com.acrylic.universalnms.particles.Particles;
import com.acrylic.version_1_8_nms.packets.SinglePacketWrapperImpl;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.jetbrains.annotations.NotNull;

public class ParticlesImpl
        extends SinglePacketWrapperImpl
        implements Particles {

    protected EnumParticle particleType;
    protected boolean longDistance = true;
    protected float speed = 0;
    protected int amount = 1;
    protected float[] location;
    protected float[] offset;
    protected PacketPlayOutWorldParticles packet;

    @Override
    public void build() {
        if (particleType == null || location == null)
            throw new IllegalStateException("A location and the particle type must be specified in order to build a particle.");
        this.packet = (offset == null) ?
                new PacketPlayOutWorldParticles(particleType,
                        this.longDistance, this.location[0], this.location[1], this.location[2],
                        0, 0, 0,
                        this.speed, this.amount
                )
                :
                new PacketPlayOutWorldParticles(particleType,
                        this.longDistance, this.location[0], this.location[1], this.location[2],
                        offset[0], offset[1], offset[2],
                        this.speed, this.amount
                );
    }

    @Override
    public float[] getOffset() {
        return offset;
    }

    @Override
    public EnumParticle getParticleType() {
        return particleType;
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

    public void setParticleType(@NotNull EnumParticle particle) {
        this.particleType = particle;
    }

    @Override
    public void setParticleType(@NotNull EnumWrappers.Particle particle) {
        setParticleType(EnumParticle.a(particle.getId()));
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
