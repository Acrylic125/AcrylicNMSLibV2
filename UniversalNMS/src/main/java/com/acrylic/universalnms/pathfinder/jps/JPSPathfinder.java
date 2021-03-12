package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universalnms.pathfinder.AStarPathNode;
import com.acrylic.universalnms.pathfinder.Path;
import com.acrylic.universalnms.pathfinder.astar.AbstractAStarPathfinder;
import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class JPSPathfinder extends AbstractAStarPathfinder<JPSBaseNode> {

    private final JPSPathfinderGenerator pathfinderGenerator;
    private final Map<Chunk, ChunkExaminer> chunkExaminerMap = new HashMap<>();
    private final JPSBaseNode startNode, endNode;
    private final World world;
    private final double distanceFromAndTo;
    private boolean searched = false, completed = false;

    protected JPSPathfinder(JPSPathfinderGenerator pathfinderGenerator, Location start, Location end) {
        this.pathfinderGenerator = pathfinderGenerator;
        this.distanceFromAndTo = AStarPathNode.calculateDistance2D(start.getX(), start.getZ(), end.getX(), end.getZ());
        this.startNode = JPSBaseNode.createStartNode(this, start);
        this.endNode = JPSBaseNode.createEndNode(this, this.startNode, end);
        this.world = start.getWorld();
    }

    @Override
    public JPSPathfinderGenerator getPathfinderGenerator() {
        return pathfinderGenerator;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public JPSBaseNode getStartNode() {
        return startNode;
    }

    @Override
    public JPSBaseNode getEndNode() {
        return endNode;
    }

    @Override
    public void pathfind() {
        if (searched)
            throw new IllegalStateException("The pathfinder has already started a searched.");
        searched = true;
        final Set<JPSBaseNode> open = getOpen(), closed = getClosed();
        while (completed && !open.isEmpty()) {
            JPSBaseNode currentNode = getCheapestNodeFromOpen();
            if (currentNode == null) {
                completed = true;
            } else {
                open.remove(currentNode);
                closed.add(currentNode);
                if (currentNode.equals(getEndNode())) {
                    //TODO: Complete
                } else {

                }
            }

        }
    }

    private List<JPSBaseNode> generateNeighboursAndIterate(JPSBaseNode node, Consumer<JPSBaseNode> action) {
        List<JPSBaseNode> nodes = new ArrayList<>();
        return nodes;
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
    public Path generatePath() {
        return null;
    }

    @Override
    public double getDistanceFromStartToEnd() {
        return distanceFromAndTo;
    }
}
