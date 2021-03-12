package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universalnms.pathfinder.Pathfinder;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.Location;

public class JPSPathfinderGenerator implements PathfinderGenerator {

    private double maximumSearchDistance = 32;

    public JPSPathfinderGenerator setMaximumSearchDistance(double maximumSearchDistance) {
        this.maximumSearchDistance = maximumSearchDistance;
        return this;
    }

    @Override
    public double getMaximumSearchDistance() {
        return maximumSearchDistance;
    }

    @NotNull
    @Override
    public JPSPathfinder generatePathfinder(@NotNull Location start, @NotNull Location end) {
        return null;
    }
}
