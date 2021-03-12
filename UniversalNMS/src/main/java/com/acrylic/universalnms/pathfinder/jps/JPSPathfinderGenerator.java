package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.Location;

public class JPSPathfinderGenerator implements PathfinderGenerator {

    private double maximumSearchDistance = 32;
    private int recursionMaximumHorizontal = 12, recursionMaximumDiagonal = 12;
    private float maximumDropHeight = 2, maximumHeight = 2;

    public JPSPathfinderGenerator setMaximumSearchDistance(double maximumSearchDistance) {
        this.maximumSearchDistance = maximumSearchDistance;
        return this;
    }

    @Override
    public double getMaximumSearchDistance() {
        return maximumSearchDistance;
    }

    public int getRecursionMaximumHorizontal() {
        return recursionMaximumHorizontal;
    }

    public JPSPathfinderGenerator setRecursionMaximumHorizontal(int recursionMaximumHorizontal) {
        this.recursionMaximumHorizontal = recursionMaximumHorizontal;
        return this;
    }

    public int getRecursionMaximumDiagonal() {
        return recursionMaximumDiagonal;
    }

    public JPSPathfinderGenerator setRecursionMaximumDiagonal(int recursionMaximumDiagonal) {
        this.recursionMaximumDiagonal = recursionMaximumDiagonal;
        return this;
    }

    public float getMaximumDropHeight() {
        return maximumDropHeight;
    }

    public JPSPathfinderGenerator setMaximumDropHeight(float maximumDropHeight) {
        this.maximumDropHeight = maximumDropHeight;
        return this;
    }

    public float getMaximumHeight() {
        return maximumHeight;
    }

    public JPSPathfinderGenerator setMaximumHeight(float maximumHeight) {
        this.maximumHeight = maximumHeight;
        return this;
    }

    @NotNull
    @Override
    public JPSPathfinder generatePathfinder(@NotNull Location start, @NotNull Location end) {
        return null;
    }
}
