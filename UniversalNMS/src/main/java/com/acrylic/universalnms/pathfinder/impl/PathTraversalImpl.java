package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universalnms.pathfinder.Path;
import com.acrylic.universalnms.pathfinder.PathTraversal;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PathTraversalImpl implements PathTraversal {

    private final Path path;
    private final Location cursor;
    private int pointsPerBlock = 1;
    private int pathIndex = 0, sectionIndex = 0, maximumSectionIndex = 0;
    private final Vector vector = new Vector(0, 0, 0);

    public PathTraversalImpl(@NotNull Path path) {
        this.path = path;
        this.cursor = path.getLocation(0);
    }

    @NotNull
    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void setPointsPerBlock(int pointsPerBlock) {
        this.pointsPerBlock = pointsPerBlock;
    }

    @Override
    public int getPointsPerBlock() {
        return pointsPerBlock;
    }

    @Override
    public boolean hasNext() {
        return pathIndex < path.getTotalLocations();
    }

    @Nullable
    @Override
    public Location next() {
        if (sectionIndex >= maximumSectionIndex) {
            pathIndex++;
            if (hasEnded())
                return null;
            Location next = path.getLocation(pathIndex);
            if (next == null)
                return null;
            double d = cursor.distance(next);
            sectionIndex = 0;
            maximumSectionIndex = (int) Math.ceil(d * pointsPerBlock);
            vector.setX((next.getX() - cursor.getX()) / d)
                    .setY((next.getY() - cursor.getY()) / d)
                    .setZ((next.getZ() - cursor.getZ()) / d);
        }
        sectionIndex++;
        return cursor.add(vector).clone();
    }
}
