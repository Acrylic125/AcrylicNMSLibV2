package com.acrylic.universalnms.entityai.strategies;

import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entityai.PathSeekerAI;
import com.acrylic.universalnms.pathfinder.PathTraversal;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class PathfinderStrategyImpl implements PathfinderStrategy {

    private PathTraversal focussedPath;
    private float speed = 0.2f;
    private long
            traverseTimeToStop = 0,
            maximumTimeToTraverse = 10000;
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
        } else {
            if (focussedPath == null || (maximumTimeToTraverse != -1 && traverseTimeToStop < System.currentTimeMillis())) {
                state = PathfindingState.SEARCHING;
                Scheduler.async().runTask()
                        .plugin(NMSLib.getPlugin())
                        .handleThenBuild(() -> {
                            Location iLocation = pathSeekerAI.getInstance().getBukkitEntity().getLocation();
                            Pathfinder pathfinder = pathfinderGenerator.generatePathfinder(iLocation, targetLocation);
                            focussedPath = pathfinder.generatePath().createTraversal();
                            state = PathfindingState.TRAVERSING;
                            traverseTimeToStop = System.currentTimeMillis() + maximumTimeToTraverse;
                        });
            } else if (state == PathfindingState.TRAVERSING) {
                if (focussedPath.hasNext()) {
                    NMSEntityInstance nmsEntityInstance = pathSeekerAI.getInstance();
                    Location next = focussedPath.next(),
                            iLocation = nmsEntityInstance.getBukkitEntity().getLocation();
                    if (next == null)
                        return;
                    double x = iLocation.getX() - next.getX(),
                            y = iLocation.getY() - next.getY(),
                            z = iLocation.getZ() - next.getZ();
                    nmsEntityInstance.setYawAndPitch((float) Math.toDegrees(Math.atan2(z, x) - 90f), (float) ((1f / Math.sqrt(x * x + y * y + z * z)) * y * -90f));
                    nmsEntityInstance.setVelocity(x, y, z);
                }
            }
        }
    }

    @NotNull
    @Override
    public PathfindingState getPathfindingState() {
        return state;
    }

    @NotNull
    @Override
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

    public void setMaximumTimeToTraverse(long maximumTimeToTraverse) {
        this.maximumTimeToTraverse = maximumTimeToTraverse;
    }

    @Override
    public long getMaximumTraverseTime() {
        return maximumTimeToTraverse;
    }

}
