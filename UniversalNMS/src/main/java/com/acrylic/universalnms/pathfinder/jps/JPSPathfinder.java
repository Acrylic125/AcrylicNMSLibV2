package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universal.blocks.MCBlockData;
import com.acrylic.universal.items.ItemUtils;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.pathfinder.Path;
import com.acrylic.universalnms.pathfinder.PathNode;
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
        this.distanceFromAndTo = PathNode.calculateDistance2D(start.getX(), start.getZ(), end.getX(), end.getZ());
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

    private MCBlockData getBlockDataAt(int x, int y, int z) {
        Chunk chunk = getWorld().getChunkAt(x >> 4, z >> 4);
        ChunkExaminer chunkExaminer = chunkExaminerMap.get(chunk);
        if (chunkExaminer == null) {
            chunkExaminer = NMSLib.getFactory().getNMSUtilsFactory().getNewChunkExaminer(chunk);
            chunkExaminerMap.put(chunk, chunkExaminer);
        }
        return chunkExaminer.getBlockDataAt(x, y, z);
    }

    private void scanHorizontally(int x, int y, int z, int facingX, int facingZ, int n) {
        x += facingX;
        z += facingZ;
        //Only do recursion if the block is within bounds
        //(Within range of search range and within the recursion limit.
        if (n >= pathfinderGenerator.getRecursionMaximumHorizontal() &&
                PathNode.calculateDistance2D(x, z, startNode.getX(), startNode.getZ()) <= pathfinderGenerator.getMaximumSearchRange()) {
            MCBlockData mcBlockData = getBlockDataAt(x, y, z);
            if (canPass(mcBlockData)) {
                //Facing +-X
                if (facingX != 0) {
                    if (!canPass(getBlockDataAt(x, y, z - 1)) &&
                            canPass(getBlockDataAt(x + facingX, y, z - 1)) &&
                            canPass(getBlockDataAt(x + facingX, y, z - 1))) {

                    } else if (!canPass(getBlockDataAt(x, y, z + 1)) &&
                            canPass(getBlockDataAt(x + facingX, y, z + 1)) &&
                            canPass(getBlockDataAt(x + facingX, y, z + 1))) {

                    }

                    //Scan
                    scanHorizontally(x, y, z, facingX, 1, n + 1);
                    scanHorizontally(x, y, z, facingX, -1, n + 1);

                } //Facing +-Z
                else if (facingZ != 0) {
                    scanHorizontally(x, y, z, 1, facingZ, n + 1);
                    scanHorizontally(x, y, z, -1, facingZ, n + 1);
                }
                scanHorizontally(x, y, z, facingX, facingZ, n + 1);
            }
        }
    }

    private boolean canPass(MCBlockData mcBlockData) {
        return ItemUtils.isAir(mcBlockData.getMaterial());
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
