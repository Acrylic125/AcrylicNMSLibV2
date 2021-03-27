package com.acrylic.universalnms.entityai.strategyimpl;

import com.acrylic.universalnms.entityai.TargettableAI;
import com.acrylic.universalnms.entityai.strategies.TargetSelectorStrategy;
import math.ProbabilityKt;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;

public abstract class AbstractRandomTargetSelectorStrategy implements TargetSelectorStrategy {

    private final TargettableAI targettableAI;
    private boolean locked = false;
    private float targetSelectionRangeX = 32,
                  targetSelectionRangeY = 32,
                  targetSelectionRangeZ = 32;
    private long cooldownToRetrySearch = 3_000, timeToSearch = 0;

    public AbstractRandomTargetSelectorStrategy(@NotNull TargettableAI targettableAI) {
        this.targettableAI = targettableAI;
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
        Entity currentTarget = targettableAI.getTarget();
        if (currentTarget == null) {
            if (timeToSearch > System.currentTimeMillis())
                return;
            Location location = targettableAI.getInstance().getBukkitEntity().getLocation();
            Collection<Entity> possibleTargets = location.getWorld().getNearbyEntities(location, targetSelectionRangeX, targetSelectionRangeY, targetSelectionRangeZ);
            if (!possibleTargets.isEmpty()) {
                LinkedList<Entity> options = new LinkedList<>();
                for (Entity potentialTarget : possibleTargets) {
                    if (canTarget(potentialTarget))
                        options.add(potentialTarget);
                }
                int size = options.size();
                if (size > 0)
                    setTarget(options.get(ProbabilityKt.getRandom(0, size - 1)));
                this.timeToSearch = System.currentTimeMillis() + cooldownToRetrySearch;
            }
        }
    }

    @NotNull
    @Override
    public TargettableAI getEntityAI() {
        return targettableAI;
    }

    public float getTargetSelectionRangeX() {
        return targetSelectionRangeX;
    }

    public void setTargetSelectionRangeX(float targetSelectionRangeX) {
        this.targetSelectionRangeX = targetSelectionRangeX;
    }

    public float getTargetSelectionRangeY() {
        return targetSelectionRangeY;
    }

    public void setTargetSelectionRangeY(float targetSelectionRangeY) {
        this.targetSelectionRangeY = targetSelectionRangeY;
    }

    public float getTargetSelectionRangeZ() {
        return targetSelectionRangeZ;
    }

    public void setTargetSelectionRangeZ(float targetSelectionRangeZ) {
        this.targetSelectionRangeZ = targetSelectionRangeZ;
    }

    public long getCooldownToRetrySearch() {
        return cooldownToRetrySearch;
    }

    public void setCooldownToRetrySearch(long cooldownToRetrySearch) {
        this.cooldownToRetrySearch = cooldownToRetrySearch;
    }
}
