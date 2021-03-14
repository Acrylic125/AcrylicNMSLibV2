package com.acrylic.version_1_16_nms.partivles;

import com.acrylic.universalnms.particles.Particles;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.ParticleParam;
import net.minecraft.server.v1_16_R3.ParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ParticlesImpl
        extends AbstractParticlesImpl
        implements Particles {

    protected ParticleParam particleType;

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
    public ParticleParam getParticleType() {
        return particleType;
    }

    public void setParticleType(@NotNull ParticleType particle) {
        this.particleType = particle;
    }

    @Override
    public void setParticleType(@NotNull EnumWrappers.Particle particle) {
        this.particleType = convertParticleType(particle);
    }

    @Override
    public void setOffset(float x, float y, float z) {
        this.offset = new float[]{x, y, z};
    }

    @Nullable
    public static ParticleType convertParticleType(EnumWrappers.Particle particle) {
        switch (particle) {
            case CRIT:
                return net.minecraft.server.v1_16_R3.Particles.CRIT;
            case LAVA:
                return net.minecraft.server.v1_16_R3.Particles.LAVA;
            case NOTE:
                return net.minecraft.server.v1_16_R3.Particles.NOTE;
            case CLOUD:
                return net.minecraft.server.v1_16_R3.Particles.CLOUD;
            case SPIT:
                return net.minecraft.server.v1_16_R3.Particles.SPIT;
            case FLAME:
                return net.minecraft.server.v1_16_R3.Particles.FLAME;
            case HEART:
                return net.minecraft.server.v1_16_R3.Particles.HEART;
            case SLIME:
                return net.minecraft.server.v1_16_R3.Particles.ITEM_SLIME;
            case SPELL:
                return net.minecraft.server.v1_16_R3.Particles.EFFECT;
            case TOTEM:
                return net.minecraft.server.v1_16_R3.Particles.TOTEM_OF_UNDYING;
            case PORTAL:
                return net.minecraft.server.v1_16_R3.Particles.PORTAL;
            case BARRIER:
                return net.minecraft.server.v1_16_R3.Particles.BARRIER;
            case END_ROD:
                return net.minecraft.server.v1_16_R3.Particles.END_ROD;
            case FOOTSTEP:
                //Unsure
                return net.minecraft.server.v1_16_R3.Particles.AMBIENT_ENTITY_EFFECT;
            case SNOWBALL:
            case SNOW_SHOVEL:
                return net.minecraft.server.v1_16_R3.Particles.ITEM_SNOWBALL;
            case DRIP_LAVA:
                return net.minecraft.server.v1_16_R3.Particles.DRIPPING_LAVA;
            case SPELL_MOB:
                //Unsure
                return net.minecraft.server.v1_16_R3.Particles.INSTANT_EFFECT;
            case SPELL_WITCH:
                return net.minecraft.server.v1_16_R3.Particles.WITCH;
            case DRIP_WATER:
                return net.minecraft.server.v1_16_R3.Particles.DRIPPING_WATER;
            case WATER_DROP:
                return net.minecraft.server.v1_16_R3.Particles.FALLING_WATER;
            case SWEEP_ATTACK:
                return net.minecraft.server.v1_16_R3.Particles.SWEEP_ATTACK;
            case SMOKE_LARGE:
                return net.minecraft.server.v1_16_R3.Particles.LARGE_SMOKE;
            case ENCHANTMENT_TABLE:
                return net.minecraft.server.v1_16_R3.Particles.ENCHANT;
            case CRIT_MAGIC:
                return net.minecraft.server.v1_16_R3.Particles.ENCHANTED_HIT;
            case VILLAGER_HAPPY:
                return net.minecraft.server.v1_16_R3.Particles.HAPPY_VILLAGER;
            case VILLAGER_ANGRY:
                return net.minecraft.server.v1_16_R3.Particles.ANGRY_VILLAGER;
            case DAMAGE_INDICATOR:
                return net.minecraft.server.v1_16_R3.Particles.DAMAGE_INDICATOR;
            case FIREWORKS_SPARK:
                return net.minecraft.server.v1_16_R3.Particles.FIREWORK;
            case WATER_SPLASH:
                return net.minecraft.server.v1_16_R3.Particles.SPLASH;
            case DRAGON_BREATH:
                return net.minecraft.server.v1_16_R3.Particles.DRAGON_BREATH;
            case WATER_BUBBLE:
                return net.minecraft.server.v1_16_R3.Particles.UNDERWATER;
            case EXPLOSION_NORMAL:
                //Unsure
                return net.minecraft.server.v1_16_R3.Particles.EXPLOSION_EMITTER;
            case EXPLOSION_HUGE:
            case EXPLOSION_LARGE:
                return net.minecraft.server.v1_16_R3.Particles.EXPLOSION;
            case SMOKE_NORMAL:
                return net.minecraft.server.v1_16_R3.Particles.SMOKE;
        }
        return null;
    }

}
