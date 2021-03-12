package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universalnms.pathfinder.AStarPathNode;
import com.acrylic.universalnms.pathfinder.PathNode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JPSBaseNode implements AStarPathNode {

    private final JPSPathfinder pathfinder;
    private JPSBaseNode parent;
    private final int x, y, z;
    private final double gCost, hCost;
    private JPSPathNode[] pathNodes;

    protected static JPSBaseNode createStartNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull Location location) {
        return createStartNode(jpsPathfinder, location.getBlock());
    }

    protected static JPSBaseNode createStartNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull Block block) {
        return new JPSBaseNode(jpsPathfinder, null, block.getX(), block.getY(), block.getZ(), 0, jpsPathfinder.getDistanceFromStartToEnd());
    }

    protected static JPSBaseNode createEndNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull JPSBaseNode parent, @NotNull Location location) {
        return createEndNode(jpsPathfinder, parent, location.getBlock());
    }

    protected static JPSBaseNode createEndNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull JPSBaseNode parent, @NotNull Block block) {
        return new JPSBaseNode(jpsPathfinder, parent, block.getX(), block.getY(), block.getZ(), jpsPathfinder.getDistanceFromStartToEnd(), 0);
    }

    protected JPSBaseNode(JPSPathfinder jpsPathfinder, @Nullable JPSBaseNode parent, int x, int y, int z, double gCost, double hCost) {
        this.pathfinder = jpsPathfinder;
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gCost = gCost;
        this.hCost = hCost;
    }

    protected JPSBaseNode(JPSPathfinder jpsPathfinder, @Nullable JPSBaseNode parent, int x, int y, int z) {
        this.pathfinder = jpsPathfinder;
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gCost = AStarPathNode.calculateDistance2D(this, jpsPathfinder.getStartNode());
        this.hCost = AStarPathNode.calculateDistance2D(this, jpsPathfinder.getStartNode());
    }

    @NotNull
    public JPSPathfinder getPathfinder() {
        return pathfinder;
    }

    @Override
    public double getGCost() {
        return gCost;
    }

    @Override
    public double getHCost() {
        return hCost;
    }

    public void setParent(JPSBaseNode jpsBaseNode) {
        this.parent = jpsBaseNode;
    }

    @Nullable
    @Override
    public JPSBaseNode getParent() {
        return parent;
    }

    public void setSuccessors(JPSPathNode[] pathNodes) {
        this.pathNodes = pathNodes;
    }

    @Nullable
    @Override
    public JPSPathNode[] getSuccessors() {
        return pathNodes;
    }

    @Override
    public World getWorld() {
        return pathfinder.getWorld();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JPSBaseNode))
            return false;
        JPSBaseNode jpsBaseNode = (JPSBaseNode) obj;
        return jpsBaseNode.getX() == x && jpsBaseNode.getY() == y && jpsBaseNode.getZ() == z;
    }
}
