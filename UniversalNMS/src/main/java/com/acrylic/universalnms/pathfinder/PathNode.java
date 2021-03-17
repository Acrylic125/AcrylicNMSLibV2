package com.acrylic.universalnms.pathfinder;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface PathNode {

    @NotNull
    Pathfinder getPathfinder();

    @Nullable
    PathNode getParent();

    default boolean hasParent() {
        return getParent() != null;
    }

    World getWorld();

    int getX();

    int getY();

    int getZ();

    default Location getLocation() {
        return new Location(getWorld(), getX(), getY(), getZ());
    }

    static double calculateDistance2DSquared(PathNode node1, PathNode node2) {
        return calculateDistance2DSquared(node1.getX(), node1.getZ(), node2.getX(), node2.getZ());
    }

    static double calculateDistance2D(PathNode node1, PathNode node2) {
        return calculateDistance2D(node1.getX(), node1.getZ(), node2.getX(), node2.getZ());
    }

    static double calculateDistance2DSquared(double x1, double z1, double x2, double z2) {
        double x = (x1 - x2), z = (z1 - z2);
        return (x * x) + (z * z);
    }

    static double calculateDistance2D(double x1, double z1, double x2, double z2) {
        return Math.sqrt(calculateDistance2DSquared(x1, z1, x2, z2));
    }

}
