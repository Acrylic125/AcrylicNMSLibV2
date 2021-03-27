package com.acrylic.universalnms.pathfinder.astar;

import com.acrylic.universalnms.pathfinder.Pathfinder;
import com.acrylic.universalnms.pathfinder.impl.PathImpl;
import com.acrylic.universalnms.pathfinder.jps.JPSBaseNode;
import math.ProbabilityKt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    public N getCheapestNodeFromOpenAndClosed() {
        N cheapestOpen = getCheapestNodeFromOpen(), cheapestClosed = getCheapestNodeFromClosed();
        boolean openNull = cheapestOpen == null,
                closedNull = cheapestClosed == null;
        if (openNull && closedNull)
            return null;
        if (openNull)
            return cheapestClosed;
        else if (closedNull)
            return cheapestOpen;
        else
            return (cheapestClosed.getFCost() < cheapestOpen.getFCost()) ? cheapestClosed : cheapestOpen;
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
            if (node.equals(getStartNode()) || node.equals(getEndNode()))
                continue;
            if (cursor == null) {
                cursor = node;
                fCost = node.getFCost();
            } else {
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
        return getClosed().get(node.hashCode()) != null;
    }

    public N getSimilarNodeFromOpen(N node) {
        return getOpen().get(node.hashCode());
    }

    public abstract double getDistanceFromStartToEnd();

    @Override
    public abstract AStarPathNode getStartNode();

    @Override
    public abstract AStarPathNode getEndNode();

    protected abstract N getFinalNode();

    /**
     * Reverses the last node and maps it into a Location array.
     *
     * @return The path based on the last node.
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public PathImpl generatePath(float pointsPerBlock) {
        //If there is no last node, returns a 0 path.
        N finalNode = getFinalNode();
        if (finalNode == null)
            return new PathImpl(this, new Location[0], pointsPerBlock);
        int d = finalNode.getDepth() + 1;
        Location[] points = new Location[d];
        N cursor = finalNode;
        for (int i = 0; i < d; i++) {
            //This should never happen but if it does, it will throw an error.
            if (cursor == null)
                throw new IllegalStateException("Something went terribly wrong. The cursor node is null while having a for loop index of " + i + ". This should never happen?");
            Location location = cursor.getLocation().clone();
            //location.setX(location.getX() + ProbabilityKt.getPositiveNegativeRandom(minVariability, maxVariability));
            //location.setZ(location.getZ() + ProbabilityKt.getPositiveNegativeRandom(minVariability, maxVariability));
            points[d - i - 1] = location;
            cursor = (N) cursor.getParent(); //Cast
        }
        return new PathImpl(this, points, pointsPerBlock);
    }
}
