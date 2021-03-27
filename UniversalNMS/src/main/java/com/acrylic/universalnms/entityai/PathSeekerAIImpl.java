package com.acrylic.universalnms.entityai;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entityai.strategies.PathQuitterStrategy;
import com.acrylic.universalnms.entityai.strategies.PathfinderStrategy;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathSeekerAIImpl implements PathSeekerAI {

    private final NMSEntityInstance nmsEntityInstance;
    private PathfinderStrategy pathfinderStrategy;
    private PathQuitterStrategy pathQuitterStrategy;
    private Location targetLocation;

    public PathSeekerAIImpl(@NotNull NMSEntityInstance nmsEntityInstance) {
        this.nmsEntityInstance = nmsEntityInstance;
    }

    @Override
    public NMSEntityInstance getInstance() {
        return nmsEntityInstance;
    }

    @Override
    public void tick() {
        if (pathfinderStrategy != null)
            pathfinderStrategy.tick();
        if (pathfinderStrategy != null)
            pathfinderStrategy.tick();
    }

    @Override
    public void setTargetLocation(@Nullable Location location) {
        this.targetLocation = location;
    }

    @Nullable
    @Override
    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setPathfinderStrategy(@Nullable PathfinderStrategy pathfinderStrategy) {
        this.pathfinderStrategy = pathfinderStrategy;
    }

    @Nullable
    @Override
    public PathfinderStrategy getPathfinderStrategy() {
        return pathfinderStrategy;
    }

    public void setPathQuitterStrategy(@Nullable PathQuitterStrategy pathQuitterStrategy) {
        this.pathQuitterStrategy = pathQuitterStrategy;
    }

    @Nullable
    @Override
    public PathQuitterStrategy getPathQuitterStrategy() {
        return pathQuitterStrategy;
    }
}
