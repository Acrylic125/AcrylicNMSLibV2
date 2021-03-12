package com.acrylic.universalnms.pathfinder;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PathNode {

    @NotNull
    Pathfinder getPathfinder();

    @Nullable
    PathNode[] getSuccessors();

    @Nullable
    PathNode getParent();

    World getWorld();

    int getX();

    int getY();

    int getZ();

    default Location getLocation() {
        return new Location(getWorld(), getX(), getY(), getZ());
    }

}
