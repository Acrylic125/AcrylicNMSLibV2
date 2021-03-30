package com.acrylic.universalnms.entityai.strategyimpl;

import com.acrylic.universal.animations.holograms.Hologram;
import com.acrylic.universal.animations.holograms.Holograms;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entityai.PathSeekerAI;
import com.acrylic.universalnms.entityai.strategies.PathfinderStrategy;
import com.acrylic.universalnms.pathfinder.PathType;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class DirectPathfinderStrategy implements PathfinderStrategy {

    private final PathSeekerAI pathSeekerAI;
    private boolean locked = false;
    private PathfindingState state = PathfindingState.IDLE;
    private Vector movement = new Vector();
    private float speed = 0.2f;

    public DirectPathfinderStrategy(@NotNull PathSeekerAI pathSeekerAI) {
        this.pathSeekerAI = pathSeekerAI;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void tick() {
        final Location targetLocation = pathSeekerAI.getTargetLocation();
        if (targetLocation == null) {
            state = PathfindingState.IDLE;
        } else {
            NMSEntityInstance nmsEntityInstance = pathSeekerAI.getInstance();
            if (state == PathfindingState.IDLE) {
                Location current = nmsEntityInstance.getLocation();
                pathSeekerAI.getInstance().setNoClip(true);
                double m = current.distance(targetLocation) * (1 / speed);
                movement.setX((current.getX() - targetLocation.getX()) / m)
                        .setY((current.getY() - targetLocation.getY()) / m)
                        .setZ((current.getZ() - targetLocation.getZ()) / m);
                state = PathfindingState.TRAVERSING;
            }
            if (nmsEntityInstance.getEntityConfiguration().useTeleportForPathfindingStrategy()) {
                Location current = nmsEntityInstance.getLocation();
                nmsEntityInstance.teleport(current.add(movement));
            } else {
                nmsEntityInstance.move(movement);
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

    @Override
    public void completeTraversal() {
        state = PathfindingState.IDLE;
        pathSeekerAI.getInstance().setNoClip(false);
    }
}
