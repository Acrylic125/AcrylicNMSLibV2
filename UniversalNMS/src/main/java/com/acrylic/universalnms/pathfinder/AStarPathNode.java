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

}
