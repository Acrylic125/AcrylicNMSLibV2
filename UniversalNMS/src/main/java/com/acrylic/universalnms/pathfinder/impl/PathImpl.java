package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universalnms.pathfinder.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class PathImpl implements Path {

    private final Pathfinder pathfinder;
    private final Collection<ComputedPathPoint> locations;
    private final float pointsPerBlocks;

    public PathImpl(Pathfinder pathfinder, Location[] points, float pointsPerBlocks) {
        this.pathfinder = pathfinder;
        this.locations = map(pathfinder, points, pointsPerBlocks);
        this.pointsPerBlocks = pointsPerBlocks;
    }

    public PathImpl(Pathfinder pathfinder, Collection<Location> points, float pointsPerBlocks) {
        this(pathfinder, (Location[]) points.toArray(), pointsPerBlocks);
    }

    @NotNull
    @Override
    public Pathfinder getPathfinder() {
        return pathfinder;
    }

    @NotNull
    @Override
    public Collection<ComputedPathPoint> getLocations() {
        return locations;
    }

    @NotNull
    @Override
    public Iterator<ComputedPathPoint> iterator() {
        return locations.iterator();
    }

    @Override
    public float getPointsPerBlock() {
        return pointsPerBlocks;
    }

    private static Collection<ComputedPathPoint> map(Pathfinder pathfinder, Location[] points, float pointsPerBlocks) {
        if (points.length <= 0)
            return new LinkedList<>();
        PathReader pathReader = pathfinder.getPathReader();
        Collection<ComputedPathPoint> map = new LinkedList<>();
        Location cursor = points[0];
        Vector direction = new Vector(0, 0, 0);
        for (int section = 1; section < points.length; section++) {
            Location endOfSection = points[section];
            double d = cursor.distance(endOfSection);
            if (d <= 0)
                continue;
            int endSectionIndex = (int) Math.ceil(d * pointsPerBlocks);
            direction.setX((endOfSection.getX() - cursor.getX()) / endSectionIndex)
                    .setZ((endOfSection.getZ() - cursor.getZ()) / endSectionIndex);
            for (int j = 0; j < endSectionIndex; j++) {
                cursor.add(direction);
                PathTypeResult pathTypeResult = pathReader.getPathTypeResultAt(cursor);
                float y = pathTypeResult.getResultY();
                cursor.setY(y);
                map.add(new ComputedPathPoint((float) cursor.getX(), (float) cursor.getY(), (float) cursor.getZ(), pathTypeResult.getPathType()));
            }
        }
        return map;
    }

}
