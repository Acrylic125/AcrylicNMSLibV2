package com.acrylic.universalnms.pathfinder;

import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

public interface PathNode {

    @Nullable
    PathNode getStartNode();

    @Nullable
    PathNode[] getSuccessors();

    World getWorld();

    int getX();

    int getY();

    int getZ();

}
