package com.acrylic.universalnms.pathfinder.jps;

import org.jetbrains.annotations.NotNull;

public class JPSPathNode extends JPSBaseNode {

    private final int facingX, facingZ;

    protected JPSPathNode(@NotNull JPSBaseNode start, int x, int y, int z, int facingX, int facingZ) {
        super(start.getPathfinder(), start, start.getWorld(), x, y, z);
        this.facingX = facingX;
        this.facingZ = facingZ;
    }

    public int getFacingX() {
        return facingX;
    }

    public int getFacingZ() {
        return facingZ;
    }
}

