package com.acrylic.universalnms.pathfinder.jps;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class JPSPathNode extends JPSBaseNode {

    private final int facingX, facingZ;

    protected JPSPathNode(@NotNull JPSBaseNode start, int x, int y, int z, int facingX, int facingZ) {
        super(start.getPathfinder(), start, x, y, z);
        this.facingX = facingX;
        this.facingZ = facingZ;
    }

    public int getFacingX() {
        return facingX;
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

