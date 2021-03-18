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
    private final float x, y, z;
    private final double gCost, hCost;
    //The depth of the node is how layered this node is in base on it's parent(s).
    //The depth of the first node (start) is 0.
    private int depth;

    protected static JPSBaseNode createStartNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull Location location) {
        return createStartNode(jpsPathfinder, location.getBlock());
    }

    protected static JPSBaseNode createStartNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull Block block) {
        return new JPSBaseNode(jpsPathfinder, null, block.getX(), block.getY(), block.getZ(), 0, jpsPathfinder.getDistanceFromStartToEnd(), 0);
    }

    protected static JPSBaseNode createEndNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull JPSBaseNode parent, @NotNull Location location) {
        return createEndNode(jpsPathfinder, parent, location.getBlock());
    }

    protected static JPSBaseNode createEndNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull JPSBaseNode parent, @NotNull Block block) {
        return new JPSBaseNode(jpsPathfinder, parent, block.getX(), block.getY(), block.getZ(), jpsPathfinder.getDistanceFromStartToEnd(), 0, -1);
    }

    protected JPSBaseNode(JPSPathfinder jpsPathfinder, @Nullable JPSBaseNode parent, float x, float y, float z, double gCost, double hCost, int depth) {
        this.pathfinder = jpsPathfinder;
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gCost = gCost;
        this.hCost = hCost;
        this.depth = depth;
    }

    protected JPSBaseNode(JPSPathfinder jpsPathfinder, @Nullable JPSBaseNode parent, float x, float y, float z) {
        this.pathfinder = jpsPathfinder;
        this.parent = parent;
       this.x = x;
        this.y = y;
        this.z = z;
        if (parent != null) {
            this.depth = parent.depth + 1;
            this.gCost = parent.getGCost() + PathNode.calculateDistance2D(this, parent);
        } else {
            this.depth = 0;
            this.gCost = 0;
        }
        this.hCost = PathNode.calculateDistance2D(this, jpsPathfinder.getEndNode());
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

    @Override
    public World getWorld() {
        return pathfinder.getWorld();
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    public boolean equals(float x, float y, float z) {
        return this.x == x && this.y == y && this.z == z;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JPSBaseNode))
            return false;
        JPSBaseNode jpsBaseNode = (JPSBaseNode) obj;
        return jpsBaseNode.getX() == x && jpsBaseNode.getY() == y && jpsBaseNode.getZ() == z;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "JPSBaseNode{" +
                "parent=" + parent +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", gCost=" + gCost +
                ", hCost=" + hCost +
                '}';
    }

    public int getDepth() {
        return depth;
    }

    protected void setDepth(int depth) {
        this.depth = depth;
    }
}
