package com.acrylic.universalnms.entityai.aiimpl;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entityai.TargettableAI;
import com.acrylic.universalnms.entityai.strategies.PathfinderStrategy;
import com.acrylic.universalnms.entityai.strategies.TargetSelectorStrategy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

public class TargettableAIImpl
        extends PathSeekerAIImpl
        implements TargettableAI {

    private WeakReference<Entity> target;
    private TargetSelectorStrategy targetSelectorStrategy;
    private float dontPathfindRange = 3,
                  untargetRangeBetweenInstanceAndTarget = -1;

    public TargettableAIImpl(@NotNull NMSEntityInstance nmsEntityInstance) {
        super(nmsEntityInstance);
    }

    @Nullable
    @Override
    public Entity getTarget() {
        return (target == null) ? null : target.get();
    }

    @Override
    public void setTarget(@Nullable Entity target) {
        this.target = (target == null) ? null : new WeakReference<>(target);
    }

    @Override
    public void tick() {
        if (targetSelectorStrategy != null && !targetSelectorStrategy.isLocked())
            targetSelectorStrategy.tick();
        Entity target = getTarget();
        if (target != null) {
            if (isAValidTarget(target)) {
                Location location = target.getLocation();
                if (location.distanceSquared(getInstance().getBukkitEntity().getLocation()) < (dontPathfindRange * dontPathfindRange)) {
                    setTargetLocation(null);
                    if (pathfinderStrategy != null)
                        pathfinderStrategy.completeTraversal();
                } else {
                    setTargetLocation(location);
                }
            } else {
                setTargetLocation(null);
                setTarget(null);
            }
        } else {
            setTargetLocation(null);
        }
        super.tick();
    }

    public float getDontPathfindRange() {
        return dontPathfindRange;
    }

    public void setDontPathfindRange(float dontPathfindRange) {
        this.dontPathfindRange = dontPathfindRange;
    }

    public float getUntargetRangeBetweenInstanceAndTarget() {
        return untargetRangeBetweenInstanceAndTarget;
    }

    public void setUntargetRangeBetweenInstanceAndTarget(float untargetRangeBetweenInstanceAndTarget) {
        this.untargetRangeBetweenInstanceAndTarget = untargetRangeBetweenInstanceAndTarget;
    }

    public TargetSelectorStrategy getTargetSelectorStrategy() {
        return targetSelectorStrategy;
    }

    public void setTargetSelectorStrategy(@Nullable TargetSelectorStrategy targetSelectorStrategy) {
        this.targetSelectorStrategy = targetSelectorStrategy;
    }

    @Override
    public boolean isAValidTarget(Entity target) {
        return TargettableAI.super.isAValidTarget(target) &&
                (untargetRangeBetweenInstanceAndTarget == -1 || (target.getLocation().distanceSquared(getInstance().getBukkitEntity().getLocation()) <= (untargetRangeBetweenInstanceAndTarget * untargetRangeBetweenInstanceAndTarget)));
    }
}
