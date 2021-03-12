package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universalnms.pathfinder.PathNode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Nullable;

public class JPSPathNode implements PathNode {

    private final int x, y, z;
    private final int facingX, facingZ;
    private final JPSPathNode startingNode;
    private final JPSPathfinder pathfinder;
    private PathNode[] successors;

    protected JPSPathNode(Location location, int facingX, int facingZ) {
        this(location.getBlock(), facingX, facingZ);
    }

    protected JPSPathNode(Block block, int facingX, int facingZ) {
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.facingX = facingX;
        this.facingZ = facingZ;
    }

    protected JPSPathNode(JPSPathNode parentNode, int x, int y, int z, int facingX, int facingZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.facingX = facingX;
        this.facingZ = facingZ;
        this.startingNode = parentNode.getStartNode();
        this.pathfinder = parentNode.getPathfinder();
    }

    @Nullable
    @Override
    public JPSPathNode getStartNode() {
        return startingNode;
    }

    public void setSuccessors(PathNode[] successors) {
        this.successors = successors;
    }

    @Nullable
    @Override
    public PathNode[] getSuccessors() {
        return successors;
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

    public int getFacingX() {
        return facingX;
    }

    public int getFacingZ() {
        return facingZ;
    }

    public JPSPathfinder getPathfinder() {
        return pathfinder;
    }
}

