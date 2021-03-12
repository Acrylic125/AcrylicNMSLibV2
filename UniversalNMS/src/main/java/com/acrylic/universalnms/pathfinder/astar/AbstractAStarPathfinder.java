package com.acrylic.universalnms.pathfinder.astar;

import com.acrylic.universalnms.pathfinder.AStarPathNode;
import com.acrylic.universalnms.pathfinder.PathNode;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAStarPathfinder<N extends AStarPathNode>
        implements Pathfinder {

    private final Set<N> open, closed;

    public AbstractAStarPathfinder() {
        this(new HashSet<>(), new HashSet<>());
    }

    public AbstractAStarPathfinder(@NotNull Set<N> open, @NotNull Set<N> closed) {
        this.open = open;
        this.closed = closed;
    }

    public Set<N> getOpen() {
        return open;
    }

    public Set<N> getClosed() {
        return closed;
    }

    @Nullable
    public N getCheapestNodeFromOpen() {
        N cursor = null;
        double fCost = 0;
        for (N node : open) {
            if (cursor == null)
                cursor = node;
            else {
                double nodeFCost = node.getFCost();
                if (nodeFCost < fCost) {
                    cursor = node;
                    fCost = nodeFCost;
                }
            }
        }
        return cursor;
    }

    public void complete(N node) {

    }

    public abstract double getDistanceFromStartToEnd();

    @Override
    public abstract AStarPathNode getStartNode();

    @Override
    public abstract AStarPathNode getEndNode();
}
