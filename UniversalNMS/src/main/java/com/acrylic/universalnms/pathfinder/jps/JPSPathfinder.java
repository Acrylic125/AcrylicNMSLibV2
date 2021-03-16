package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universal.blocks.MCBlockData;
import com.acrylic.universal.items.ItemUtils;
import com.acrylic.universal.text.ChatUtils;
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
        addNodeToClosed(startNode);
        int sX = startNode.getX(), sY = startNode.getY(), sZ = startNode.getZ();
        scanHorizontally(startNode, sX, sY, sZ, 1, 0);
        scanHorizontally(startNode, sX, sY, sZ, -1, 0);
        scanHorizontally(startNode, sX, sY, sZ, 0, 1);
        scanHorizontally(startNode, sX, sY, sZ, 0, -1);
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
        if (pathfinderGenerator.isDebugMode()) {
            Bukkit.broadcastMessage(ChatUtils.get("&a&lJPS Pathfinding complete with " + i + " CCs."));
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendBlockChange(new Location(getWorld(), getEndNode().getX(), getEndNode().getY() + 2, getEndNode().getZ()), Bukkit.createBlockData(Material.EMERALD_BLOCK));
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

    private void addNode(JPSBaseNode newNode, JPSBaseNode parent, boolean test) {
        if (!isNodeInClosed(newNode)) {
            newNode.setParent(parent);
            addNodeToOpen(newNode);
            if (pathfinderGenerator.isDebugMode()) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendBlockChange(new Location(getWorld(), newNode.getX(), newNode.getY(), newNode.getZ()), (test) ? Bukkit.createBlockData(Material.DIAMOND_BLOCK) : Bukkit.createBlockData(Material.GOLD_BLOCK));
                }
            }
        }
    }

    private void scanHorizontally(JPSBaseNode parent, int x, int y, int z, final int facingX, final int facingZ) {
        if (completed)
            return;
        //Ensure the facing is not 0.
        if (facingX == 0 && facingZ == 0)
            throw new IllegalStateException("The direction is 0.");
        double rangeSquared = pathfinderGenerator.getMaximumSearchRange(); rangeSquared *= rangeSquared; //Squared
        int maxRecursion = pathfinderGenerator.getRecursionMaximumHorizontal(),
                n = 0;
        //Only do loop if the block is within bounds
        //(Within range of search range and within the recursion limit.
        while (n < maxRecursion &&
                PathNode.calculateDistance2DSquared(x, z, startNode.getX(), startNode.getZ()) <= rangeSquared) {
            x += facingX; z += facingZ;
            if (!canPass(x, y, z))
                return;
            //Facing in the X direction.
            boolean isFollowingCellPassable = canPass(x + facingX, y, z + facingZ);
            if (facingX != 0) {
                if (isNodeInteresting_horizontalZ(x, y, z, -1) ||
                        isNodeInteresting_horizontalZ(x, y, z, 1)) {
                    addNode(new JPSPathNode(getStartNode(), x, y, z, facingX, facingZ), parent, true);
                    return;
                }
            } else {
                if (isNodeInteresting_horizontalX(x, y, z, -1) ||
                        isNodeInteresting_horizontalX(x, y, z, 1)) {
                    addNode(new JPSPathNode(getStartNode(), x, y, z, facingX, facingZ), parent, false);
                    return;
                }
            }
            //If the following node is not passable, stop while loop.
            if (!isFollowingCellPassable)
                return;
            n++;
        }
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
            int nX = x + facingX;
            if ((!canPass(x, y, z - 1) && canPass(nX, y, z - 1)) ||
                    (!canPass(x, y, z + 1) && canPass(nX, y, z + 1)))
                return (canPass(nX, y, z));
            n++;
        }
        return false;
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
            int nZ = z + facingZ;
            if (!canPass(x, y, nZ))
                return false;
            if ((!canPass(x - 1, y, z) && canPass(x - 1, y, nZ)) ||
                    (!canPass(x + 1, y, z) && canPass(x + 1, y, nZ)))
                return true;
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
