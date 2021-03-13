package com.acrylic.universalnms.particles;

import org.jetbrains.annotations.NotNull;

public final class RGB {

    private float red = 0;
    private float green = 0;
    private float blue = 0;

    public RGB() {}

    public RGB(float red, float green, float blue) {
        setRGB(red, green, blue);
    }

    public void setRGB(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public float getNMSRed() {
        return red / 255f;
    }

    public float getRed() {
        return red;
    }

    public RGB setRed(float red) {
        this.red = red;
        return this;
    }

    public float getNMSGreen() {
        return green / 255f;
    }

    public float getGreen() {
        return green;
    }

    public RGB setGreen(float green) {
        this.green = green;
        return this;
    }

    public float getNMSBlue() {
        return blue / 255f;
    }

    public float getBlue() {
        return blue;
    }

    public RGB setBlue(float blue) {
        this.blue = blue;
        return this;
    }

    public RGB rainbow() {
        this.red = 65025;
        this.green = 65025;
        this.blue = 65025;
        return this;
    }

    public RGB cloneTo(@NotNull RGB cloneTo) {
        cloneTo.setRGB(red, green, blue);
        return cloneTo;
    }

}
