package com.acrylic.universalnms.entityai.strategies;

import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entityai.PathSeekerAI;
import com.acrylic.universalnms.pathfinder.Path;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import com.acrylic.universalnms.pathfinder.PathfinderGenerator;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathfinderStrategyImpl implements PathfinderStrategy {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(32);

    private Iterator<Location> focussedPath;
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
            if (focussedPath == null || state == PathfindingState.IDLE) {
                state = PathfindingState.SEARCHING;
                Scheduler.async().runTask(executorService)
                        .plugin(NMSLib.getPlugin())
                        .handleThenBuild(() -> {
                            Location iLocation = pathSeekerAI.getInstance().getBukkitEntity().getLocation();
                            Pathfinder pathfinder = pathfinderGenerator.generatePathfinder(iLocation, targetLocation);
                            pathfinder.pathfind();

                            Path path = pathfinder.generatePath(1 / speed);
                            focussedPath = path.iterator();
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
                    double x = next.getX() - iLocation.getX(),
                            y = next.getY() - iLocation.getY(),
                            z = next.getZ() - iLocation.getZ();
                    nmsEntityInstance.move(x, y, z);
                    nmsEntityInstance.setYawAndPitch((float) Math.toDegrees(Math.atan2(z, x) - 90f), (float) ((-90f * y) / Math.sqrt(x * x + y * y + z * z)));
                } else {
                    focussedPath = null;
                    state = PathfindingState.IDLE;
                    //Bukkit.broadcastMessage("Done traversing.");
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
