package com.acrylic.universalnms.pathfinder;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Path {

    @NotNull
    Pathfinder getPathfinder();

    @NotNull
    Location[] getLocations();

    default int getTotalLocations() {
        return getLocations().length;
    }

    @NotNull
    PathTraversal createTraversal();

    @Nullable
    Location getLocation(int index);

}
