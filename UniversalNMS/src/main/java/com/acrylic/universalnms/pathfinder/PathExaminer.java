package com.acrylic.universalnms.pathfinder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The path examiner determines how the path can be
 * traversed, whether it is walkable, swimmable, or
 * climbable.
 */
public interface PathExaminer {

    @Nullable
    PathType getPathTypeOfBlock(PathBlock pathBlock);

    PathTypeResult examineTo(Pathfinder pathfinder, float x, float y, float z);

}
