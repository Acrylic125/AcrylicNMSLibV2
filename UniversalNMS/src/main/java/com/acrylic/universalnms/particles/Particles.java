package com.acrylic.universalnms.particles;

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.jetbrains.annotations.NotNull;

public interface Particles
        extends AbstractParticles {

    Object getParticleType();

    void setParticleType(@NotNull EnumWrappers.Particle particle);

}
