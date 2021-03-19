package com.acrylic.universalnms.pathfinder.astar;

import com.acrylic.universalnms.pathfinder.Pathfinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AbstractAStarPathfinder<N extends AStarPathNode>
        implements Pathfinder {

    private final Map<Integer, N> open, closed;

    public AbstractAStarPathfinder() {
        this(new HashMap<>(), new HashMap<>());
    }

    public AbstractAStarPathfinder(@NotNull Map<Integer, N> open, @NotNull Map<Integer, N> closed) {
        this.open = open;
        this.closed = closed;
    }

    public Map<Integer, N> getOpen() {
        return open;
    }

    public Map<Integer, N> getClosed() {
        return closed;
    }

    @Nullable
    public N getCheapestNodeFromOpen() {
        return getCheapestNodeFromCollection(open.values());
    }

    @Nullable
    public N getCheapestNodeFromClosed() {
        return getCheapestNodeFromCollection(closed.values());
    }

    private N getCheapestNodeFromCollection(Collection<N> collection) {
        N cursor = null;
        double fCost = 0;
        for (N node : collection) {
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

    public void addNodeToOpen(N node) {
        getOpen().put(node.hashCode(), node);
    }

    public void addNodeToClosed(N node) {
        getClosed().put(node.hashCode(), node);
    }

    public void removeNodeFromOpen(N node) {
        getOpen().remove(node.hashCode());
    }

    public void removeNodeFromClosed(N node) {
        getClosed().remove(node.hashCode());
    }

    public boolean isNodeInOpen(N node) {
        return getOpen().get(node.hashCode()) != null;
    }

    public boolean isNodeInClosed(N node) {
        return getOpen().get(node.hashCode()) != null;
    }

    public abstract double getDistanceFromStartToEnd();

    @Override
    public abstract AStarPathNode getStartNode();

    @Override
    public abstract AStarPathNode getEndNode();
}
