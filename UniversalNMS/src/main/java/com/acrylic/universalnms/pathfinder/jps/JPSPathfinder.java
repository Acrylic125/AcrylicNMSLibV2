package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universal.blocks.MCBlockData;
import com.acrylic.universal.files.configloader.ConfigLoader;
import com.acrylic.universal.gui.uiformats.MiddleGUIComponent;
import com.acrylic.universal.items.ItemUtils;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.pathfinder.PathImpl;
import com.acrylic.universalnms.pathfinder.PathNode;
import com.acrylic.universalnms.pathfinder.astar.AbstractAStarPathfinder;
import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * This implementation is designed for Minecraft Entity pathfinding. It does
 * not guarantee the most efficient path but it can speed up the AStar implementation
 * by magnitudes. This is also thread safe due to the use of chunk snapshots.
 *
 * @see ChunkSnapshot
 *
 * The following links were used in the development of this pathfinding.
 *
 * Pathfinding Visualizer:
 * http://qiao.github.io/PathFinding.js/visual/
 */
public class JPSPathfinder extends AbstractAStarPathfinder<JPSBaseNode> {

    private final JPSPathfinderGenerator pathfinderGenerator;
    private final Map<Chunk, ChunkExaminer> chunkExaminerMap = new HashMap<>();
    private final JPSBaseNode startNode, endNode;
    private JPSBaseNode lastNode;
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

    @Override
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
    public synchronized void pathfind() {
        //Do not search if it has already been started.
        if (searched)
            throw new IllegalStateException("The pathfinder has already started a searched.");
        searched = true;
        addNodeToClosed(startNode);
        int sX = startNode.getX(), sY = startNode.getY(), sZ = startNode.getZ();
        //Initial scans in all 4 orthogonal directions.
        scanHorizontally(startNode, sX, sY, sZ, 1, 0);
        scanHorizontally(startNode, sX, sY, sZ, -1, 0);
        scanHorizontally(startNode, sX, sY, sZ, 0, 1);
        scanHorizontally(startNode, sX, sY, sZ, 0, -1);
        //Iterations, i
        int i = 0;
        while (!completed && !getOpen().isEmpty() && i <= pathfinderGenerator.getMaximumClosestChecks()) {
            i++;
            JPSBaseNode currentNode = getCheapestNodeFromOpen();
            if (currentNode == null) {
                break;
            } else {
                removeNodeFromOpen(currentNode);
                addNodeToClosed(currentNode);
                if (currentNode.equals(getEndNode())) {
                    break;
                } else if (currentNode instanceof JPSPathNode) {
                    JPSPathNode jpsPathNode = (JPSPathNode) currentNode;
                    scanHorizontally(currentNode, currentNode.getX(), currentNode.getY(), currentNode.getZ(), jpsPathNode.getFacingZ(), jpsPathNode.getFacingX());
                    scanHorizontally(currentNode, currentNode.getX(), currentNode.getY(), currentNode.getZ(), jpsPathNode.getFacingZ() * -1, jpsPathNode.getFacingX());
                    scanHorizontally(currentNode, currentNode.getX(), currentNode.getY(), currentNode.getZ(), jpsPathNode.getFacingX(), jpsPathNode.getFacingZ());
                }
            }
        }
        completed = true;
        Bukkit.broadcastMessage(ChatUtils.get("&a&lJPS Pathfinding complete with " + i + " CCs of depth " + lastNode.getDepth() + "."));
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendBlockChange(new Location(getWorld(), getEndNode().getX(), getEndNode().getY() + 2, getEndNode().getZ()), Bukkit.createBlockData(Material.EMERALD_BLOCK));
        }
    }

    /**
     *
     * @param x The x coordinate of the block.
     * @param y The y coordinate of the block.
     * @param z The z coordinate of the block.
     * @return The threadsafe block data at the given location.
     */
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
        if (!isNodeInClosed(newNode)) {
            newNode.setDepth(parent.getDepth() + 1);
            newNode.setParent(parent);
            addNodeToOpen(newNode);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendBlockChange(new Location(getWorld(), newNode.getX(), newNode.getY(), newNode.getZ()), Bukkit.createBlockData(Material.DIAMOND_BLOCK));
            }
        }
    }

    private synchronized void scanHorizontally(JPSBaseNode parent, int x, int y, int z, final int facingX, final int facingZ) {
        //Ensure the facing is not 0.
        if (facingX == 0 && facingZ == 0)
            throw new IllegalStateException("The direction is 0 in both X and Z.");
        double rangeSquared = pathfinderGenerator.getMaximumSearchRange(); rangeSquared *= rangeSquared; //Squared
        int maxRecursion = pathfinderGenerator.getRecursionMaximumHorizontal(),
                n = 0;
        //Only do loop if the block is within bounds
        //(Within range of search range and within the recursion limit.
        while (!completed && n < maxRecursion &&
                PathNode.calculateDistance2DSquared(x, z, startNode.getX(), startNode.getZ()) <= rangeSquared) {
            x += facingX; z += facingZ;
            if (!canPass(x, y, z))
                return;
            //Facing in the X direction.
            boolean isFollowingCellPassable = canPass(x + facingX, y, z + facingZ);
            JPSPathNode newNode = null;
            if (facingX != 0) {
                if (isNodeInteresting_horizontalZ(x, y, z, -1) ||
                        isNodeInteresting_horizontalZ(x, y, z, 1)) {
                    newNode = new JPSPathNode(getStartNode(), x, y, z, facingX, facingZ);
                } else if (interestingCheck_horizontalX(x, y, z, facingX) &&
                        canPass(x + facingX, y, z)) {
                    newNode = new JPSPathNode(getStartNode(), x + facingX, y, z, facingX, facingZ);
                }
            } else {
                if (isNodeInteresting_horizontalX(x, y, z, -1) ||
                        isNodeInteresting_horizontalX(x, y, z, 1)) {
                    newNode = new JPSPathNode(getStartNode(), x, y, z, facingX, facingZ);
                } else if (interestingCheck_horizontalZ(x, y, z, facingZ) &&
                        canPass(x, y, z + facingZ)) {
                    newNode = new JPSPathNode(getStartNode(), x, y, z + facingZ, facingX, facingZ);
                }
            }
            //Check if there is a new node. If there is, stop this loop and set it as the lastNode.
            if (newNode != null) {
                addNode(newNode, parent);
                if (completed) {
                    endNode.setDepth(newNode.getDepth() + 1);
                    endNode.setParent(newNode);
                    this.lastNode = endNode;
                } else {
                    this.lastNode = newNode;
                }
                return;
            }
            //If the following node is not passable, stop while loop.
            if (!isFollowingCellPassable)
                return;
            n++;
        }
    }

    //Assumes that the following cell is free.
    private boolean interestingCheck_horizontalX(int x, int y, int z, int facingX) {
        int nX = x + facingX;
        return (!canPass(x, y, z - 1) && canPass(nX, y, z - 1)) ||
                (!canPass(x, y, z + 1) && canPass(nX, y, z + 1));
    }

    private boolean isNodeInteresting_horizontalX(int x, int y, int z, int facingX) {
        int n = 0;
        double rangeSquared = pathfinderGenerator.getMaximumSearchRange(); rangeSquared *= rangeSquared; //Squared
        while (n < pathfinderGenerator.getMaximumSearchRange() &&
                PathNode.calculateDistance2DSquared(x, z, startNode.getX(), startNode.getZ()) <= rangeSquared) {
            x += facingX;
            if (!canPass(x, y, z))
                return false;
            if (getEndNode().equals(x, y, z)) {
                completed = true;
                return true;
            }
            if (interestingCheck_horizontalX(x, y, z, facingX))
                return canPass(x + facingX, y, z);
            n++;
        }
        return false;
    }

    //Assumes that the following cell is free.
    private boolean interestingCheck_horizontalZ(int x, int y, int z, int facingZ) {
        int nZ = z + facingZ;
        return (!canPass(x - 1, y, z) && canPass(x - 1, y, nZ)) ||
                (!canPass(x + 1, y, z) && canPass(x + 1, y, nZ));
    }

    private boolean isNodeInteresting_horizontalZ(int x, int y, int z, final int facingZ) {
        int n = 0;
        double rangeSquared = pathfinderGenerator.getMaximumSearchRange(); rangeSquared *= rangeSquared; //Squared
        while (n < pathfinderGenerator.getMaximumSearchRange() &&
                PathNode.calculateDistance2DSquared(x, z, startNode.getX(), startNode.getZ()) <= rangeSquared) {
            z += facingZ;
            if (!canPass(x, y, z))
                return false;
            if (getEndNode().equals(x, y, z)) {
                completed = true;
                return true;
            }
            if (interestingCheck_horizontalZ(x, y, z, facingZ))
                return canPass(x, y, z + facingZ);
            n++;
        }
        return false;
    }

    private boolean canPass(int x, int y, int z) {
        return ItemUtils.isAir(getBlockDataAt(x, y, z).getMaterial());
    }

    private boolean canPass(MCBlockData mcBlockData) {
        return ItemUtils.isAir(mcBlockData.getMaterial());
    }

    @Override
    public boolean hasSearched() {
        return searched;
    }

    @Override
    public boolean hasCompleted() {
        return completed;
    }

    /**
     * Reverses the last node and maps it into a Location array.
     *
     * @return The path based on the last node.
     */
    @NotNull
    @Override
    public PathImpl generatePath() {
        //If there is no last node, returns a 0 path.
        if (lastNode == null)
            return new PathImpl(this, new Location[0]);
        int d = lastNode.getDepth() + 1;
        Location[] points = new Location[d];
        JPSBaseNode cursor = lastNode;
        for (int i = 0; i < d; i++) {
            //This should never happen but if it does, it will throw an error.
            if (cursor == null)
                throw new IllegalStateException("Something went terribly wrong. The cursor node is null while having a for loop index of " + i + ". This should never happen?");
            points[d - i - 1] = cursor.getLocation();
            cursor = cursor.getParent();
        }
        return new PathImpl(this, points);
    }

    @Override
    public double getDistanceFromStartToEnd() {
        return distanceFromAndTo;
    }
}
