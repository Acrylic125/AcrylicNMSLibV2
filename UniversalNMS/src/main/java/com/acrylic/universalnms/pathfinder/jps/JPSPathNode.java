package com.acrylic.universalnms.pathfinder.jps;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class JPSPathNode extends JPSBaseNode {

    private static int scanUpAndDownYValue = Integer.MIN_VALUE;
    private final int facingX, facingZ, facingY;

    protected JPSPathNode(@NotNull JPSBaseNode start, float x, float y, float z,
                          int facingX, int facingY, int facingZ,
                          boolean scanUpAndDown) {
        super(start.getPathfinder(), start, x, y, z);
        this.facingX = facingX;
        this.facingY = (scanUpAndDown) ? scanUpAndDownYValue : facingY;
        this.facingZ = facingZ;
    }

    public boolean shouldScanUpAndDown() {
        return this.facingY == scanUpAndDownYValue;
    }

    public boolean isFacingZero() {
        return facingX == 0 && facingY == 0 && facingZ == 0;
    }

    public int getFacingX() {
        return facingX;
    }

    public int getFacingY() {
        return facingY;
    }

    public int getFacingZ() {
        return facingZ;
    }

    @Override
    public Location getLocation() {
        Location location = super.getLocation();
        location.setDirection(new Vector(facingX, 0, facingZ));
        return location;
    }
}

