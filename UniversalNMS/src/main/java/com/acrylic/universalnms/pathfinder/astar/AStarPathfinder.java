package com.acrylic.universalnms.pathfinder.astar;

import com.acrylic.universalnms.pathfinder.*;
import com.acrylic.universalnms.pathfinder.impl.PathImpl;
import com.acrylic.universalnms.pathfinder.impl.PathReaderImpl;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class AStarPathfinder extends AbstractAStarPathfinder<AStarPathNodeImpl> {

    private final AStarPathfinderGenerator pathfinderGenerator;
    private final AStarPathNodeImpl startNode, endNode;
    private AStarPathNodeImpl finalNode;
    private final World world;
    private final double distanceFromAndTo;
    private boolean searched = false, completed = false;
    private final PathReader pathReader;

    protected AStarPathfinder(AStarPathfinderGenerator pathfinderGenerator, Location start, Location end) {
        this.pathfinderGenerator = pathfinderGenerator;
        this.distanceFromAndTo = PathNode.calculateDistance2D(start.getX(), start.getZ(), end.getX(), end.getZ());
        this.startNode = AStarPathNodeImpl.createStartNode(this, start);
        this.endNode = AStarPathNodeImpl.createEndNode(this, this.startNode, end);
        this.world = start.getWorld();
        if (this.world == null)
            throw new NullPointerException("World cannot be null.");
        this.pathReader = new PathReaderImpl(this);
    }


    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public PathReader getPathReader() {
        return pathReader;
    }

    @Override
    public PathfinderGenerator getPathfinderGenerator() {
        return pathfinderGenerator;
    }

    @Override
    public void pathfind() {
        //Do not search if it has already been started.
        if (searched)
            throw new IllegalStateException("The pathfinder has already started a searched.");
        searched = true;
        boolean completeWithEndNode = false, hasStarted = false;
        addNodeToOpen(startNode);
        int i = 0;
        while (!completed && !getOpen().isEmpty() && i <= pathfinderGenerator.getMaximumClosestChecks()) {
            i++;
            AStarPathNodeImpl currentNode = (!hasStarted) ? startNode : getCheapestNodeFromOpen();
            hasStarted = true;
            if (currentNode == null) {
                break;
            } else {
                removeNodeFromOpen(currentNode);
                addNodeToClosed(currentNode);
                if (currentNode.equalsWithEstimatedBounds(endNode.getX(), endNode.getZ(), 1)) {
                    completeWithEndNode = true;
                    endNode.setDepth(currentNode.getDepth() + 1);
                    endNode.setParent(currentNode);
                    finalNode = endNode;
                    break;
                }
                currentNode.iterateSuccessors((parent, x, y, z, fX, fZ) -> {
                    PathTypeResult pathTypeResult = pathReader.getPathTypeResultAt(x, y, z);
                    if (pathTypeResult.isPassable()) {
//                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//                            onlinePlayer.sendBlockChange(new Location(getWorld(), x, y + 1, z), Bukkit.createBlockData(Material.YELLOW_STAINED_GLASS));
//                        }
                        AStarPathNodeImpl aStarPathNode = new AStarPathNodeImpl(this, parent, pathTypeResult.getResultX(), pathTypeResult.getResultY(), pathTypeResult.getResultZ(), fX, fZ),
                                similar = getSimilarNodeFromOpen(aStarPathNode);
                        if (similar != null) {
                            if (aStarPathNode.getGCost() <= similar.getGCost()) {
                                aStarPathNode.setDepth(parent.getDepth() + 1);
                                addNodeToOpen(aStarPathNode);
                            }
                        }
                        else if (!isNodeInClosed(aStarPathNode)) {
                            aStarPathNode.setDepth(parent.getDepth() + 1);
                            addNodeToOpen(aStarPathNode);
                        }
                        return true;
                    }
                    return false;
                });
            }
        }
        if (!completeWithEndNode) {
            finalNode = getCheapestNodeFromOpenAndClosed();
        }
        completed = true;
    }

    @Override
    public boolean hasSearched() {
        return searched;
    }

    @Override
    public boolean hasCompleted() {
        return completed;
    }

    @NotNull
    @Override
    public Path generatePath(float pointsPerBlock) {
        //If there is no last node, returns a 0 path.
        if (finalNode == null)
            return new PathImpl(this, new Location[0], pointsPerBlock);
        int d = finalNode.getDepth() + 1;
        Location[] points = new Location[d];
        AStarPathNodeImpl cursor = finalNode;
        for (int i = 0; i < d; i++) {
            //This should never happen but if it does, it will throw an error.
            if (cursor == null)
                throw new IllegalStateException("Something went terribly wrong. The cursor node is null while having a for loop index of " + i + ". This should never happen?");
            points[d - i - 1] = cursor.getLocation();
            cursor = cursor.getParent();
        }
        return new PathImpl(this, points, pointsPerBlock);
    }

    @Override
    public double getDistanceFromStartToEnd() {
        return distanceFromAndTo;
    }

    @Override
    public AStarPathNodeImpl getStartNode() {
        return startNode;
    }

    @Override
    public AStarPathNodeImpl getEndNode() {
        return endNode;
    }
}
