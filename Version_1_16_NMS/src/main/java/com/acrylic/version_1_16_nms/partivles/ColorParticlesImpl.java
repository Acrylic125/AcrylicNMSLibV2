package com.acrylic.version_1_16_nms.partivles;

import com.acrylic.universalnms.particles.ColorParticles;
import com.acrylic.universalnms.particles.RGB;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.ParticleParamRedstone;
import org.jetbrains.annotations.NotNull;

public class ColorParticlesImpl
        extends AbstractParticlesImpl
        implements ColorParticles {

    private final RGB rgb = new RGB(0, 0 ,0);
    private float size = 1;

    @Override
    public void build() {
        if (location == null)
            throw new IllegalStateException("A location must be specified in order to build a particle.");

        this.packet = (offset != null) ?
                new PacketPlayOutWorldParticles(new ParticleParamRedstone(this.rgb.getNMSRed(), this.rgb.getNMSGreen(), this.rgb.getNMSBlue(), size),
                        false, this.location[0], this.location[1], this.location[2],
                        offset[0], offset[1], offset[2],
                        speed, amount
                )
                :
                new PacketPlayOutWorldParticles(new ParticleParamRedstone(this.rgb.getNMSRed(), this.rgb.getNMSGreen(), this.rgb.getNMSBlue(), size),
                        false, this.location[0], this.location[1], this.location[2],
                        0, 0, 0,
                        speed, amount
                );
    }

    @Override
    public float getSize() {
        return size;
    }

    @Override
    public void setSize(float size) {
        this.size = size;
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
