package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universalnms.pathfinder.Path;
import com.acrylic.universalnms.pathfinder.PathReader;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class PathImpl implements Path {

    private final Pathfinder pathfinder;
    private final Collection<Location> locations;
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
    public Collection<Location> getLocations() {
        return locations;
    }

    @NotNull
    @Override
    public Iterator<Location> iterator() {
        return locations.iterator();
    }

    @Override
    public float getPointsPerBlock() {
        return pointsPerBlocks;
    }

    private static Collection<Location> map(Pathfinder pathfinder, Location[] points, float pointsPerBlocks) {
        if (points.length <= 0)
            return new LinkedList<>();
        PathReader pathReader = pathfinder.getPathWorldBlockReader();
        Collection<Location> map = new LinkedList<>();
        Location cursor = points[0];
        Vector direction = new Vector(0, 0, 0),
                normal = new Vector(0, 0, 0);
        for (int section = 1; section < points.length; section++) {
            Location endOfSection = points[section];
            double d = cursor.distance(endOfSection);
            if (d <= 0)
                continue;
            direction.setX((endOfSection.getX() - cursor.getX()) / d)
                    .setZ((endOfSection.getZ() - cursor.getZ()) / d);
            normal.setX(direction.getX() / d)
                    .setZ(direction.getZ() / d);
            int endSectionIndex = (int) Math.ceil(d * pointsPerBlocks);
            for (int j = 0; j < endSectionIndex; j++) {
                cursor.add(direction);
                float y = pathReader.getPathTypeResultAt(cursor).getResultY();
                cursor.setY(y);
                map.add(cursor.clone());
            }
        }
        return map;
    }

}
