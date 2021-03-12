package com.acrylic.universalnms.pathfinder;

public interface Pathfinder {

    PathfinderGenerator getPathfinderGenerator();

    PathNode getStartNode();

    PathNode getEndNode();

    void pathfind();

    boolean hasSearched();

    boolean hasCompleted();

}
