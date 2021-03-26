package com.acrylic.universalnms.pathfinder;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public interface Pathfinder {

    World getWorld();

    PathReader getPathWorldBlockReader();

    PathfinderGenerator getPathfinderGenerator();

    PathNode getStartNode();

    PathNode getEndNode();

    void pathfind();

    boolean hasSearched();

    boolean hasCompleted();

    @NotNull
    Path generatePath();

}
