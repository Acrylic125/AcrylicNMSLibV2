package com.acrylic.universalnms.pathfinder;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * A path traversal handles the movement/interpolation of the path.
 *
 * It allows for more specific manipulation of the path
 * by making use of the iterator interface. This therefore means
 * that this is intended to be a one use system.
 */
public interface PathTraversal extends Iterator<Location> {

    @NotNull
    Path getPath();

    void setPointsPerBlock(int pointsPerBlock);

    int getPointsPerBlock();

    default int getEstimatedTotalPoints() {
        return getPointsPerBlock() * (getPath().getTotalLocations() - 1);
    }

    default boolean hasEnded() {
        return !hasNext();
    }

}
