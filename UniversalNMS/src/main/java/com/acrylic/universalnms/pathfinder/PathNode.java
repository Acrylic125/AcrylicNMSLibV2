package com.acrylic.universalnms.pathfinder;

import org.jetbrains.annotations.Nullable;

public interface PathNode {

    @Nullable
    PathNode getStartNode();

    @Nullable
    PathNode[] getSuccessors();

    int getX();

    int getY();

    int getZ();

}
