package com.acrylic.universalnms.pathfinder.astar;

import com.acrylic.universalnms.pathfinder.PathNode;
import com.acrylic.universalnms.pathfinder.astar.AbstractAStarPathfinder;
import org.jetbrains.annotations.NotNull;

/**
 * g = |c - p| + gP
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
