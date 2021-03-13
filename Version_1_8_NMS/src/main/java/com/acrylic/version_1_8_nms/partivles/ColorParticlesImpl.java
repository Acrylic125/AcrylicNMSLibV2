package com.acrylic.version_1_8_nms.partivles;

import com.acrylic.universalnms.particles.ColorParticles;
import com.acrylic.universalnms.particles.RGB;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.jetbrains.annotations.NotNull;

public class ColorParticlesImpl
        extends ParticlesImpl
        implements ColorParticles {

    private final RGB rgb = new RGB(0, 0 ,0);

    @Override
    public void build() {
        if (particleType == null || location == null)
            throw new IllegalStateException("A location and the particle type must be specified in order to build a particle.");
        this.packet = new PacketPlayOutWorldParticles(particleType,
                false, this.location[0], this.location[1], this.location[2],
                this.rgb.getNMSRed(), this.rgb.getNMSGreen(), rgb.getNMSBlue(),
                1, 0
        );
    }

    @Override
    public RGB getRGB() {
        return rgb;
    }

    @Override
    public void setRGB(@NotNull RGB rgb) {
        rgb.cloneTo(this.rgb);
    }
}
