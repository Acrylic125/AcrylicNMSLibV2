package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universalnms.pathfinder.AStarPathNode;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import com.acrylic.universalnms.pathfinder.astar.AbstractAStarPathfinder;
import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.*;

public class JPSPathfinder extends AbstractAStarPathfinder<JPSPathNode> {

    private final JPSPathfinderGenerator pathfinderGenerator;
    private final Map<Chunk, ChunkExaminer> chunkExaminerMap = new HashMap<>();
    private final JPSBaseNode startNode, endNode;
    private final World world;
    private final double distanceFromAndTo;

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
    public double getDistanceFromStartToEnd() {
        return distanceFromAndTo;
    }
}
