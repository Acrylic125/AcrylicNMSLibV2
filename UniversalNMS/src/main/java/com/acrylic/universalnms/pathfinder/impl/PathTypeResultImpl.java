package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universalnms.pathfinder.PathReader;
import com.acrylic.universalnms.pathfinder.PathType;
import com.acrylic.universalnms.pathfinder.PathTypeResult;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PathTypeResultImpl implements PathTypeResult {

    private final float x ,y, z;
    private final World world;
    private float passableX, passableY, passableZ;
    private final PathReader pathReader;
    private PathType pathType;

    public PathTypeResultImpl(@NotNull Pathfinder pathfinder, float x, float y, float z) {
        this.pathReader = pathfinder.getPathWorldBlockReader();
        this.x = x;
        this.y = y;
        this.z = z;
        this.passableX = x;
        this.passableY = y;
        this.passableZ = z;
        this.world = pathfinder.getWorld();
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

    @NotNull
    public PathReader getPathWorldBlockReader() {
        return pathReader;
    }

    public void setPassable(float x, float y, float z) {
        this.passableX = x;
        this.passableY = y;
        this.passableZ = z;
    }

    public void setPathType(PathType pathType) {
        this.pathType = pathType;
    }

    @Nullable
    @Override
    public PathType getPathType() {
        return pathType;
    }

    @Override
    public float getResultX() {
        return passableX;
    }

    @Override
    public float getResultY() {
        return passableY;
    }

    @Override
    public float getResultZ() {
        return passableZ;
    }

    @Override
    public String toString() {
        return "PathTypeResultByHeightImpl{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", world=" + world +
                ", passableX=" + passableX +
                ", passableY=" + passableY +
                ", passableZ=" + passableZ +
                ", pathWorldBlockReader=" + pathReader +
                ", pathType=" + pathType +
                '}';
    }
}
