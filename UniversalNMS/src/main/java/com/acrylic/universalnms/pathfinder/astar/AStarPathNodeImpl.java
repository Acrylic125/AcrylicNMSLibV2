package com.acrylic.universalnms.pathfinder.astar;

import com.acrylic.universalnms.pathfinder.PathNode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AStarPathNodeImpl implements AStarPathNode {

    protected static AStarPathNodeImpl createStartNode(@NotNull AStarPathfinder jpsPathfinder, @NotNull Location location) {
        return createStartNode(jpsPathfinder, location.getBlock());
    }

    protected static AStarPathNodeImpl createStartNode(@NotNull AStarPathfinder jpsPathfinder, @NotNull Block block) {
        return new AStarPathNodeImpl(jpsPathfinder, null, block.getX() + 0.5f, block.getY() + 0.5f, block.getZ() + 0.5f, 0, jpsPathfinder.getDistanceFromStartToEnd(), 0);
    }

    protected static AStarPathNodeImpl createEndNode(@NotNull AStarPathfinder jpsPathfinder, @NotNull AStarPathNodeImpl parent, @NotNull Location location) {
        return createEndNode(jpsPathfinder, parent, location.getBlock());
    }

    protected static AStarPathNodeImpl createEndNode(@NotNull AStarPathfinder jpsPathfinder, @NotNull AStarPathNodeImpl parent, @NotNull Block block) {
        return new AStarPathNodeImpl(jpsPathfinder, parent, block.getX() + 0.5f, block.getY() + 0.5f, block.getZ() + 0.5f, jpsPathfinder.getDistanceFromStartToEnd(), 0, -1);
    }

    private final AStarPathfinder pathfinder;
    private AStarPathNodeImpl parent;
    private final float x, y, z;
    private final double gCost, hCost;
    private final int facingX, facingZ;
    //The depth of the node is how layered this node is in base on it's parent(s).
    //The depth of the first node (start) is 0.
    private final int depth;

    protected AStarPathNodeImpl(AStarPathfinder pathfinder, @Nullable AStarPathNodeImpl parent, float x, float y, float z, double gCost, double hCost, int depth) {
        this.pathfinder = pathfinder;
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gCost = gCost;
        this.hCost = hCost;
        this.depth = depth;
        this.facingX = 0;
        this.facingZ = 0;
    }

    protected AStarPathNodeImpl(AStarPathfinder pathfinder, @Nullable AStarPathNodeImpl parent, float x, float y, float z, int facingX, int facingZ) {
        this.pathfinder = pathfinder;
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.z = z;
        if (parent != null) {
            this.depth = parent.depth + 1;
            this.gCost = 0;//parent.getGCost() + PathNode.calculateDistance2DSquared(this, parent);
        } else {
            this.depth = 0;
            this.gCost = 0;
        }
        this.hCost = PathNode.calculateDistance2DSquared(this, pathfinder.getEndNode());
        this.facingX = facingX;
        this.facingZ = facingZ;
    }

    public boolean isFacingZero() {
        return facingX == 0 && facingZ == 0;
    }

    public void iterateSuccessors(PathNodeAction action) {
        boolean shouldCheckAllNeighbours = isFacingZero(),
                isDiagonal = (facingX != 0 && facingZ != 0);
        action.run(this, x + 1, y, z, 0, 0);
        action.run(this, x - 1, y, z, 0, 0);
        action.run(this, x + 1, y, z + 1, 0, 0);
        action.run(this, x - 1, y, z - 1, 0, 0);
        action.run(this, x + 1, y, z - 1, 0, 0);
        action.run(this, x - 1, y, z + 1, 0, 0);
        action.run(this, x, y, z + 1, 0, 0);
        action.run(this, x, y, z - 1, 0, 0);

//        if (shouldCheckAllNeighbours || isDiagonal) {
//            boolean posX = facingX == 1, posZ = facingZ == 1;
//            float nX = x + facingX, nZ = z + facingZ;
//            if (shouldCheckAllNeighbours || (posX && posZ)) {
//                action.run(this, nX, y, nZ - 1, 0, -1);
//                action.run(this, nX, y, nZ, facingX, facingZ);
//                action.run(this, nX - 1, y, nZ, -1, 0);
//            }
//            if (shouldCheckAllNeighbours || (posX && !posZ)) {
//                action.run(this, nX - 1, y, nZ, -1, 0);
//                action.run(this, nX, y, nZ, facingX, facingZ);
//                action.run(this, nX, y, nZ + 1, 0, 1);
//            }
//            if (shouldCheckAllNeighbours || (!posX && posZ)) {
//                action.run(this, nX + 1, y, nZ, 1, 0);
//                action.run(this, nX, y, nZ, facingX, facingZ);
//                action.run(this, nX, y, nZ - 1, 0, -1);
//            }
//            if (shouldCheckAllNeighbours || (!posX && !posZ)) {
//                action.run(this, nX + 1, y, nZ, 1, 0);
//                action.run(this, nX, y, nZ, facingX, facingZ);
//                action.run(this, nX, y, nZ + 1, 0, 1);
//            }
//        }
//        if (shouldCheckAllNeighbours || !isDiagonal) {
//            if (facingX != 0) {
//                float nX = x + facingX;
//                action.run(this, nX, y, z + 1, facingX, 1);
//                action.run(this, nX, y, z, facingX, 0);
//                action.run(this, nX, y, z - 1, facingX, -1);
//            } else if (facingZ != 0) {
//                float nZ = x + facingZ;
//                action.run(this, x + 1, y, nZ, 1, facingZ);
//                action.run(this, x, y, nZ, 0, facingZ);
//                action.run(this, x - 1, y, nZ, -1, facingZ);
//            }
//        }
    }

    public int getFacingX() {
        return facingX;
    }

    public int getFacingZ() {
        return facingZ;
    }

    @Nullable
    @Override
    public AStarPathNodeImpl getParent() {
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

    public int getDepth() {
        return depth;
    }

    @NotNull
    @Override
    public AStarPathfinder getPathfinder() {
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

    public boolean equalsWithEstimatedBounds(float x, float z, float bounds) {
        return (Math.abs(x - this.x) <= bounds && Math.abs(z - this.z) <= bounds);
    }

    public boolean equals(float x, float z) {
        return this.x == x && this.z == z;
    }

    public boolean equals(float x, float y, float z) {
        return this.x == x && this.y == y && this.z == z;
    }

    public boolean equals(AStarPathNodeImpl aStarPathNode, boolean includeY) {
        if (includeY && aStarPathNode.getY() != y)
            return false;
        return aStarPathNode.getX() == x && aStarPathNode.getZ() == z;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AStarPathNodeImpl))
            return false;
        AStarPathNodeImpl aStarPathNode = (AStarPathNodeImpl) obj;
        return equals(aStarPathNode, true);
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

    public interface PathNodeAction {

        void run(AStarPathNodeImpl parent, float x, float y, float z, int facingX, int facingZ);

    }

}
