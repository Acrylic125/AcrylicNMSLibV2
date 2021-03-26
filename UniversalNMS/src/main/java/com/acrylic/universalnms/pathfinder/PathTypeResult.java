package com.acrylic.universalnms.pathfinder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PathTypeResult {

    @Nullable
    PathType getPathType();

    default boolean isPassable() {
        return getPathType() != null;
    }

    float getResultX();

    float getResultY();

    float getResultZ();

}
