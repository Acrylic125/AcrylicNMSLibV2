package com.acrylic.universalnms.entityai.strategies;

import com.acrylic.universalnms.entityai.PathSeekerAI;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import org.jetbrains.annotations.NotNull;

public interface PathfinderStrategy extends AIStrategy {

    enum PathfindingState {
        /**
         * Flags that the pathfinder is still searching.
         */
        SEARCHING,
        /**
         * Flags that the pathfinder is complete and traversing.
         */
        TRAVERSING,
        IDLE
    }

    /**
     * Pathfinding can be process intensive especially
     * for extremely large and long ranged paths. This
     * supports asynchronous pathfinding by the means of
     * using a {@link PathfindingState} to determine the
     * current status of this strategy.
     *
     * However, if you choose to do it synchronously, you may
     * choose to not use this.
     *
     * @return The current state.
     */
    @NotNull
    PathfindingState getPathfindingState();

    @NotNull
    @Override
    PathSeekerAI getEntityAI();

    float getSpeed();

    void completeTraversal();

}
