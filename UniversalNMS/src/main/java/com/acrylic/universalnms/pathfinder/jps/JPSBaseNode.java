package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universalnms.pathfinder.PathNode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JPSBaseNode implements PathNode {

    private final JPSPathfinder pathfinder;
    private final JPSBaseNode start;
    private final int x, y, z;
    private final World world;
    private JPSPathNode[] pathNodes;

    protected static JPSBaseNode createStartNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull Location location) {
        return createStartNode(jpsPathfinder, location.getBlock());
    }

    protected static JPSBaseNode createStartNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull Block block) {
        return new JPSBaseNode(jpsPathfinder, null, block.getWorld(), block.getX(), block.getY(), block.getZ());
    }

    protected static JPSBaseNode createEndNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull JPSBaseNode startNode, @NotNull Location location) {
        return createEndNode(jpsPathfinder, startNode, location.getBlock());
    }

    protected static JPSBaseNode createEndNode(@NotNull JPSPathfinder jpsPathfinder, @NotNull JPSBaseNode startNode, @NotNull Block block) {
        return new JPSBaseNode(jpsPathfinder, startNode, block.getWorld(), block.getX(), block.getY(), block.getZ());
    }

    protected JPSBaseNode(JPSPathfinder jpsPathfinder, @Nullable JPSBaseNode start, World world, int x, int y, int z) {
        this.pathfinder = jpsPathfinder;
        this.start = start;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public JPSPathfinder getPathfinder() {
        return pathfinder;
    }

    @Nullable
    @Override
    public JPSBaseNode getStartNode() {
        return start;
    }

    public void setSuccessors(JPSPathNode[] pathNodes) {
        this.pathNodes = pathNodes;
    }

    @Nullable
    @Override
    public PathNode[] getSuccessors() {
        return pathNodes;
    }

    @Override
    public World getWorld() {
        return world;
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



}
