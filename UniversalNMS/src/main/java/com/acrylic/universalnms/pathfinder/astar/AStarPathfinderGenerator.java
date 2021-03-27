package com.acrylic.universalnms.pathfinder.astar;

import com.acrylic.universalnms.pathfinder.PathExaminer;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import com.acrylic.universalnms.pathfinder.impl.PathExaminerByHeightImpl;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class AStarPathfinderGenerator implements PathfinderGenerator {

    private double maximumSearchDistance = 16;
    private int recursionMaximumHorizontal = 8, recursionMaximumDiagonal = 8;
    private float maximumDropHeight = 2,
            maximumHeight = 2,
            minimumXToTraverse = 1,
            minimumYToTraverse = 2,
            minimumZToTraverse = 1;
    private int maximumClosestChecks = 60;
    private boolean debugMode = true;

    public AStarPathfinderGenerator setMaximumSearchDistance(double maximumSearchDistance) {
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

    public AStarPathfinderGenerator setRecursionMaximumHorizontal(int recursionMaximumHorizontal) {
        this.recursionMaximumHorizontal = recursionMaximumHorizontal;
        return this;
    }

    public int getRecursionMaximumDiagonal() {
        return recursionMaximumDiagonal;
    }

    public AStarPathfinderGenerator setRecursionMaximumDiagonal(int recursionMaximumDiagonal) {
        this.recursionMaximumDiagonal = recursionMaximumDiagonal;
        return this;
    }

    public float getMaximumDropHeight() {
        return maximumDropHeight;
    }

    public AStarPathfinderGenerator setMaximumDropHeight(float maximumDropHeight) {
        this.maximumDropHeight = maximumDropHeight;
        return this;
    }

    public float getMaximumHeight() {
        return maximumHeight;
    }

    public AStarPathfinderGenerator setMinimumXToTraverse(float minimumXToTraverse) {
        this.minimumXToTraverse = minimumXToTraverse;
        return this;
    }

    @Override
    public float getMinimumXToTraverse() {
        return minimumXToTraverse;
    }

    public AStarPathfinderGenerator setMinimumYToTraverse(float minimumYToTraverse) {
        this.minimumYToTraverse = minimumYToTraverse;
        return this;
    }

    @Override
    public float getMinimumYToTraverse() {
        return minimumYToTraverse;
    }

    public AStarPathfinderGenerator setMinimumZToTraverse(float minimumZToTraverse) {
        this.minimumZToTraverse = minimumZToTraverse;
        return this;
    }

    @Override
    public float getMinimumZToTraverse() {
        return minimumZToTraverse;
    }

    public AStarPathfinderGenerator setMaximumHeight(float maximumHeight) {
        this.maximumHeight = maximumHeight;
        return this;
    }

    @NotNull
    @Override
    public AStarPathfinder generatePathfinder(@NotNull Location start, @NotNull Location end) {
        return new AStarPathfinder(this, start, end);
    }

    @NotNull
    @Override
    public PathExaminer getPathExaminer() {
        return new PathExaminerByHeightImpl();
    }

    public int getMaximumClosestChecks() {
        return maximumClosestChecks;
    }

    public AStarPathfinderGenerator setMaximumClosestChecks(int maximumClosestChecks) {
        this.maximumClosestChecks = maximumClosestChecks;
        return this;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public AStarPathfinderGenerator setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }

}
