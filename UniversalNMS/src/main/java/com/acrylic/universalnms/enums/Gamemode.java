package com.acrylic.universalnms.enums;

import org.jetbrains.annotations.NotNull;

public enum Gamemode {

    CREATIVE("CREATIVE"), SURVIVAL("SURVIVAL"), ADVENTURE("ADVENTURE"), SPECTATOR("SPECTATOR");

    private final String identifier;

    Gamemode(@NotNull final String identifier) {
        this.identifier = identifier;
    }

    @NotNull
    public String getIdentifier() {
        return identifier;
    }
}
