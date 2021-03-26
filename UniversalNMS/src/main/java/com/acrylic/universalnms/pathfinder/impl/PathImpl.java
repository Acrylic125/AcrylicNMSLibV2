package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universalnms.pathfinder.Path;
import com.acrylic.universalnms.pathfinder.PathTraversal;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class PathImpl implements Path {

    private final Pathfinder pathfinder;
    private final Location[] points;

    public PathImpl(Pathfinder pathfinder, Location[] points) {
        this.pathfinder = pathfinder;
        this.points = points;
    }

    public PathImpl(Pathfinder pathfinder, Collection<Location> points) {
        this(pathfinder, (Location[]) points.toArray());
    }

    @NotNull
    @Override
    public Pathfinder getPathfinder() {
        return pathfinder;
    }

    @NotNull
    @Override
    public Location[] getLocations() {
        return points;
    }

    @NotNull
    @Override
    public PathTraversal createTraversal() {
        return new PathTraversalImpl(this);
    }

    @Override
    @Nullable
    public Location getLocation(int index) {
        return (index < 0 || index >= points.length) ? null : points[index];
    }
}
