package com.acrylic.universalnms.pathfinder;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

public interface Path {

    @NotNull
    Pathfinder getPathfinder();

    @NotNull
    Collection<ComputedPathPoint> getLocations();

    @NotNull
    Iterator<ComputedPathPoint> iterator();

    float getPointsPerBlock();

}
