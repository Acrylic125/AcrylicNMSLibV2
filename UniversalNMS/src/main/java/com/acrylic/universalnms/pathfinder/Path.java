package com.acrylic.universalnms.pathfinder;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface Path {

    @NotNull
    Location[] getLocations();

    default int getTotalLocations() {
        return getLocations().length;
    }

    @NotNull
    PathTraversal createTraversal();

    Location getLocation(int index);

}
