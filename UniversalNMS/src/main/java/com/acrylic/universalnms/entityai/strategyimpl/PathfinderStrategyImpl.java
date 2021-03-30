package com.acrylic.universalnms.entityai.strategyimpl;

import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entity.NMSPlayerInstance;
import com.acrylic.universalnms.entityai.PathSeekerAI;
import com.acrylic.universalnms.entityai.strategies.PathfinderStrategy;
import com.acrylic.universalnms.pathfinder.*;
import math.ProbabilityKt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathfinderStrategyImpl implements PathfinderStrategy {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(12);

    private Iterator<ComputedPathPoint> focussedPath;
    private float speed = 0.6f;
    private boolean locked = false;
    private long
            traverseTimeToStop = 0,
            maximumTimeToTraverse = 10000;
    private float minEndGoalVariation = 0, maxEndGoalVariation = 1.25f;
    private final PathSeekerAI pathSeekerAI;
    private volatile PathfindingState state = PathfindingState.IDLE;
    private final PathfinderGenerator pathfinderGenerator;

    public PathfinderStrategyImpl(@NotNull PathSeekerAI pathSeekerAI, @NotNull PathfinderGenerator pathfinderGenerator) {
        this.pathSeekerAI = pathSeekerAI;
        this.pathfinderGenerator = pathfinderGenerator;
    }

    @Override
    public void tick() {
        final Location targetLocation = pathSeekerAI.getTargetLocation();
        if (targetLocation == null) {
            state = PathfindingState.IDLE;
            disableSprint();
        } else {
            if (state == PathfindingState.IDLE) {
                state = PathfindingState.SEARCHING;
                search(targetLocation);
            } else if (state == PathfindingState.TRAVERSING) {
                if (focussedPath.hasNext() && (maximumTimeToTraverse == -1 || traverseTimeToStop < System.currentTimeMillis())) {
                    NMSEntityInstance nmsEntityInstance = pathSeekerAI.getInstance();
                    ComputedPathPoint next = focussedPath.next();
                    Location iLocation = nmsEntityInstance.getBukkitEntity().getLocation();
                    if (next == null)
                        return;
                    double x = next.getX() - iLocation.getX(),
                            y = next.getY() - iLocation.getY(),
                            z = next.getZ() - iLocation.getZ();
                    if (nmsEntityInstance.getEntityConfiguration().useTeleportForPathfindingStrategy()) {
                        nmsEntityInstance.teleport(next.getLocation(iLocation.getWorld()));
                    } else {
                        nmsEntityInstance.setNoClip(next.getPathType() == PathType.BYPASS);
                        nmsEntityInstance.move(x, y, z);
                    }
                    nmsEntityInstance.setYawAndPitch((float) Math.toDegrees(Math.atan2(z, x) - 90f), (float) ((-90f * y) / Math.sqrt(x * x + y * y + z * z)));
                } else {
                    completeTraversal();
                }
            }
        }
    }

    private void search(Location targetLocation) {
        Scheduler.async().runTask(executorService)
                .plugin(NMSLib.getPlugin())
                .handleThenBuild(() -> {
                    Location iLocation = pathSeekerAI.getInstance().getLocation();
                    Pathfinder pathfinder = pathfinderGenerator.generatePathfinder(iLocation, targetLocation.clone()
                            .add(ProbabilityKt.getPositiveNegativeRandom(minEndGoalVariation, maxEndGoalVariation), 0, ProbabilityKt.getPositiveNegativeRandom(minEndGoalVariation, maxEndGoalVariation)));
                    pathfinder.pathfind();

                    Path path = pathfinder.generatePath(1 / speed);
                    NMSEntityInstance entityInstance = pathSeekerAI.getInstance();
                    if (entityInstance instanceof NMSPlayerInstance) {
                        NMSPlayerInstance playerInstance = (NMSPlayerInstance) entityInstance;
                        playerInstance.setSprinting(speed > 0.4);
                    }
                    focussedPath = path.iterator();
                    state = PathfindingState.TRAVERSING;
                    traverseTimeToStop = System.currentTimeMillis() + maximumTimeToTraverse;
                });
    }

    @NotNull
    @Override
    public PathfindingState getPathfindingState() {
        return state;
    }

    @NotNull
    public PathfinderGenerator getPathfinderGenerator() {
        return pathfinderGenerator;
    }

    @NotNull
    @Override
    public PathSeekerAI getEntityAI() {
        return pathSeekerAI;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    public void setUndefinedMaximumTimeToTraverse() {
        this.maximumTimeToTraverse = -1;
    }

    public void setMaximumTimeToTraverse(long maximumTimeToTraverse) {
        this.maximumTimeToTraverse = maximumTimeToTraverse;
    }

    public long getMaximumTraverseTime() {
        return maximumTimeToTraverse;
    }

    @Override
    public void completeTraversal() {
        focussedPath = null;
        state = PathfindingState.IDLE;
        disableSprint();
    }

    private void disableSprint() {
        NMSEntityInstance entityInstance = pathSeekerAI.getInstance();
        if (entityInstance instanceof NMSPlayerInstance) {
            NMSPlayerInstance playerInstance = (NMSPlayerInstance) entityInstance;
            playerInstance.setSprinting(false);
        }
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public float getMinEndGoalVariation() {
        return minEndGoalVariation;
    }

    public void setMinEndGoalVariation(float minEndGoalVariation) {
        this.minEndGoalVariation = minEndGoalVariation;
    }

    public float getMaxEndGoalVariation() {
        return maxEndGoalVariation;
    }

    public void setMaxEndGoalVariation(float maxEndGoalVariation) {
        this.maxEndGoalVariation = maxEndGoalVariation;
    }
}
