package com.acrylic.version_1_8_nms.partivles;

import com.acrylic.universalnms.particles.ColorParticles;
import com.acrylic.universalnms.particles.RGB;
import org.jetbrains.annotations.NotNull;

public class ColorParticlesImpl
        extends ParticlesImpl
        implements ColorParticles {

    private final RGB rgb = new RGB(0, 0 ,0);

    @Override
    public RGB getRGB() {
        return rgb;
    }

    @Override
    public void setRGB(@NotNull RGB rgb) {
        rgb.cloneTo(this.rgb);
    }
}
