package com.acrylic.universalnms.pathfinder;

import com.acrylic.universalnms.pathfinder.astar.AbstractAStarPathfinder;
import org.jetbrains.annotations.NotNull;

/**
 * g = |c - start|
 * h = |c - end|
 * f = g + h
 */
public interface AStarPathNode extends PathNode {

    @NotNull
    @Override
    AbstractAStarPathfinder<?> getPathfinder();

    double getGCost();

    double getHCost();

    default double getFCost() {
        return getGCost() + getHCost();
    }

    static double calculateDistance2D(PathNode node1, PathNode node2) {
        return calculateDistance2D(node1.getX(), node1.getZ(), node2.getX(), node2.getZ());
    }

    static double calculateDistance2D(double x1, double z1, double x2, double z2) {
        double x = (x1 - x2), z = (z1 - z2);
        return Math.sqrt((x * x) + (z * z));
    }

}
