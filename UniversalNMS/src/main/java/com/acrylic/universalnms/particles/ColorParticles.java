package com.acrylic.universalnms.particles;

import org.jetbrains.annotations.NotNull;

public interface ColorParticles extends AbstractParticles {

    float getSize();

    void setSize(float size);

    RGB getRGB();

    void setRGB(@NotNull RGB rgb);

    default void setRGB(float red, float green, float blue) {
        RGB rgb = getRGB();
        rgb.setRGB(red, green, blue);
        setRGB(rgb);
    }

    default void rainbow() {
        RGB rgb = getRGB();
        rgb.rainbow();
        setRGB(rgb);
    }

}
