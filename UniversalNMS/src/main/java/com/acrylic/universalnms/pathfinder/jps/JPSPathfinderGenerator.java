package com.acrylic.universalnms.pathfinder.jps;

import com.acrylic.universalnms.pathfinder.PathExaminer;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import com.acrylic.universalnms.pathfinder.impl.PathExaminerByHeightImpl;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class JPSPathfinderGenerator implements PathfinderGenerator {

    private double maximumSearchDistance = 48;
    private int recursionMaximumHorizontal = 12, recursionMaximumDiagonal = 12;
    private float maximumDropHeight = 2,
            maximumHeight = 2,
            minimumXToTraverse = 1,
            minimumYToTraverse = 2,
            minimumZToTraverse = 1
    ;
    private int maximumClosestChecks = 100;
    private boolean debugMode = true;

    public JPSPathfinderGenerator setMaximumSearchDistance(double maximumSearchDistance) {
        this.maximumSearchDistance = maximumSearchDistance;
        return this;
    }

    @Override
    public double getMaximumSearchRange() {
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

    public JPSPathfinderGenerator setMinimumXToTraverse(float minimumXToTraverse) {
        this.minimumXToTraverse = minimumXToTraverse;
        return this;
    }

    @Override
    public float getMinimumXToTraverse() {
        return minimumXToTraverse;
    }

    public JPSPathfinderGenerator setMinimumYToTraverse(float minimumYToTraverse) {
        this.minimumYToTraverse = minimumYToTraverse;
        return this;
    }

    @Override
    public float getMinimumYToTraverse() {
        return minimumYToTraverse;
    }

    public JPSPathfinderGenerator setMinimumZToTraverse(float minimumZToTraverse) {
        this.minimumZToTraverse = minimumZToTraverse;
        return this;
    }

    @Override
    public float getMinimumZToTraverse() {
        return minimumZToTraverse;
    }

    public JPSPathfinderGenerator setMaximumHeight(float maximumHeight) {
        this.maximumHeight = maximumHeight;
        return this;
    }

    @NotNull
    @Override
    public JPSPathfinder generatePathfinder(@NotNull Location start, @NotNull Location end) {
        return new JPSPathfinder(this, start, end);
    }

    @NotNull
    @Override
    public PathExaminer getPathExaminer() {
        return new PathExaminerByHeightImpl();
    }

    public int getMaximumClosestChecks() {
        return maximumClosestChecks;
    }

    public JPSPathfinderGenerator setMaximumClosestChecks(int maximumClosestChecks) {
        this.maximumClosestChecks = maximumClosestChecks;
        return this;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public JPSPathfinderGenerator setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }
}
