package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universal.blocks.MCBlockData;
import com.acrylic.universal.items.ItemUtils;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.pathfinder.Path;
import com.acrylic.universalnms.pathfinder.PathNode;
import com.acrylic.universalnms.pathfinder.astar.AbstractAStarPathfinder;
import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import org.bukkit.*;
import org.bukkit.entity.Player;
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
        closed.add(startNode);
        scanHorizontally(startNode, startNode.getX(), startNode.getY(), startNode.getZ(), 1, 0, 0);
        scanHorizontally(startNode, startNode.getX(), startNode.getY(), startNode.getZ(), -1, 0, 0);
        scanHorizontally(startNode, startNode.getX(), startNode.getY(), startNode.getZ(), 0, 1, 0);
        scanHorizontally(startNode, startNode.getX(), startNode.getY(), startNode.getZ(), 0, -1, 0);
        while (completed && !open.isEmpty()) {
            JPSBaseNode currentNode = getCheapestNodeFromOpen();
            if (currentNode == null) {
                completed = true;
            } else {
                open.remove(currentNode);
                closed.add(currentNode);
                if (currentNode.equals(getEndNode())) {
                    Bukkit.broadcastMessage("Done");
                } else if (currentNode instanceof JPSPathNode) {
                    JPSPathNode jpsPathNode = (JPSPathNode) currentNode;
                    scanHorizontally(currentNode, currentNode.getX(), currentNode.getY(), currentNode.getZ(), jpsPathNode.getFacingX(), jpsPathNode.getFacingZ(), 0);
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

    private void addNode(JPSBaseNode newNode, JPSBaseNode parent) {
        if (!getClosed().contains(newNode)) {
            newNode.setParent(parent);
            getOpen().add(newNode);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendBlockChange(new Location(getWorld(), newNode.getX(), newNode.getY(), newNode.getZ()), Material.GOLD_BLOCK, (byte) 0);
            }
        }
    }

    private void scanHorizontally(JPSBaseNode parent, int x, int y, int z, int facingX, int facingZ, int n) {
        x += facingX;
        z += facingZ;
        Bukkit.broadcastMessage("x= " + x + " y= " + y + "  FACING : " + facingX + ", " + facingZ);
        //Only do recursion if the block is within bounds
        //(Within range of search range and within the recursion limit.
        if (n <= pathfinderGenerator.getRecursionMaximumHorizontal() &&
                PathNode.calculateDistance2DSquared(x, z, startNode.getX(), startNode.getZ()) <= pathfinderGenerator.getMaximumSearchRange() * pathfinderGenerator.getMaximumSearchRange()) {
            MCBlockData mcBlockData = getBlockDataAt(x, y, z);
            if (canPass(mcBlockData)) {
                //Facing +-X
                if (facingX != 0) {
                    if (!canPass(getBlockDataAt(x, y, z - 1)) &&
                            canPass(getBlockDataAt(x + facingX, y, z - 1))) {
                        addNode(new JPSPathNode(getStartNode(), x, y, z, facingX, facingZ), parent);
                    } else if (!canPass(getBlockDataAt(x, y, z + 1)) &&
                            canPass(getBlockDataAt(x + facingX, y, z + 1))) {
                        addNode(new JPSPathNode(getStartNode(), x, y, z, facingX, facingZ), parent);
                    } else {
                        //Scan
                        scanHorizontally(parent, x, y, z, 0, 1, n + 1);
                        scanHorizontally(parent, x, y, z, 0, -1, n + 1);
                        scanHorizontally(parent, x, y, z, facingX, facingZ, n + 1);
                    }
                } //Facing +-Z
                else if (facingZ != 0) {
                    if (!canPass(getBlockDataAt(x - 1, y, z)) &&
                            canPass(getBlockDataAt(x - 1, y, z + facingZ))) {
                        addNode(new JPSPathNode(getStartNode(), x, y, z, facingX, facingZ), parent);
                    } else if (!canPass(getBlockDataAt(x + 1, y, z)) &&
                            canPass(getBlockDataAt(x + 1, y, z + facingZ))) {
                        addNode(new JPSPathNode(getStartNode(), x, y, z, facingX, facingZ), parent);
                    } else {
                        //Scan
                        scanHorizontally(parent, x, y, z, 1, 0, n + 1);
                        scanHorizontally(parent, x, y, z, -1, 0, n + 1);
                        scanHorizontally(parent, x, y, z, facingX, facingZ, n + 1);
                    }
                }
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
