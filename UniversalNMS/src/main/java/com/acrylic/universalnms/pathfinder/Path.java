package com.acrylic.universalnms.pathfinder;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface Path {

    @NotNull
    Collection<Location> getLocations();

    default int getTotalLocations() {
        return getLocations().size();
    }

    @NotNull
    PathTraversal createTraversal();

    Location getLocation(int index);

}
