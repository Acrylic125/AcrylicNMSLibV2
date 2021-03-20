package com.acrylic.universalnms.pathfinder;

import com.acrylic.universalnms.pathfinder.jps.JPSPathfinderGenerator;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * The path generator holds the configuration of the
 * the pathfinder. It's main purpose is to produce a
 * pathfinder instance that will be used as the algorithm
 * to generate a path.
 *
 * @see Pathfinder
 */
public interface PathfinderGenerator {

    JPSPathfinderGenerator JPS_PATHFINDER_GENERATOR = new JPSPathfinderGenerator();

    double getMaximumSearchRange();

    @NotNull
    Pathfinder generatePathfinder(@NotNull Location start, @NotNull Location end);

    @NotNull
    PathExaminer getPathExaminer();

    float getMaximumDropHeight();

    float getMaximumHeight();

    float getMinimumHeightToTraverse();

}
