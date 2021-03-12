package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universalnms.pathfinder.Pathfinder;

public class JPSPathfinder implements Pathfinder {

    private final JPSPathfinderGenerator pathfinderGenerator;

    protected JPSPathfinder(JPSPathfinderGenerator pathfinderGenerator) {
        this.pathfinderGenerator = pathfinderGenerator;
    }

    @Override
    public JPSPathfinderGenerator getPathfinderGenerator() {
        return pathfinderGenerator;
    }
}
