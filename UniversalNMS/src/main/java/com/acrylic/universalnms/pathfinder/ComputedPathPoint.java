package com.acrylic.universalnms.pathfinder;

import org.bukkit.Location;
import org.bukkit.World;

public class ComputedPathPoint {

    private final float x, y, z;
    private final PathType pathType;

    public ComputedPathPoint(float x, float y, float z, PathType pathType) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pathType = pathType;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public PathType getPathType() {
        return pathType;
    }

    public Location getLocation(World world) {
        return new Location(world, x, y, z);
    }
}
