package com.acrylic.universalnms.pathfinder;

import org.jetbrains.annotations.NotNull;

public interface Pathfinder {

    PathfinderGenerator getPathfinderGenerator();

    PathNode getStartNode();

    PathNode getEndNode();

    void pathfind();

    boolean hasSearched();

    boolean hasCompleted();

    @NotNull
    Path generatePath();

}
