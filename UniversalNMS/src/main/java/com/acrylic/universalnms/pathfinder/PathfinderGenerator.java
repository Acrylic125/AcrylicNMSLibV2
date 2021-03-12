package com.acrylic.universalnms.pathfinder;

import org.jetbrains.annotations.NotNull;

import javax.xml.stream.Location;

/**
 * The path generator holds the configuration of the
 * the pathfinder. It's main purpose is to produce a
 * pathfinder instance that will be used as the algorithm
 * to generate a path.
 *
 * @see Pathfinder
 */
public interface PathfinderGenerator {

    double getMaximumSearchRange();

    @NotNull
    Pathfinder generatePathfinder(@NotNull Location start, @NotNull Location end);

    float getMaximumDropHeight();

    float getMaximumHeight();

    float getMinimumHeightToTraverse();

}
