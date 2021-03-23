package com.acrylic.universalnms.skins;

import org.jetbrains.annotations.NotNull;

public interface Skin {

    @NotNull
    String getTexture();

    @NotNull
    String getSignature();

    @NotNull
    String getID();

}
