package com.acrylic.universalnms.entityai.impl;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entityai.TargettableAI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TargettableAIImpl
        extends PathSeekerAIImpl
        implements TargettableAI {

    private Entity target;
    private float dontPathfindRange = 3;

    public TargettableAIImpl(@NotNull NMSEntityInstance nmsEntityInstance) {
        super(nmsEntityInstance);
    }

    @Nullable
    @Override
    public Entity getTarget() {
        return target;
    }

    @Override
    public void setTarget(@Nullable Entity target) {
        this.target = target;
    }

    @Override
    public void tick() {
        Entity target = getTarget();
        if (target != null) {
            Location location = target.getLocation();
            setTargetLocation(
                    (location.distanceSquared(getInstance().getBukkitEntity().getLocation()) > (dontPathfindRange * dontPathfindRange))
                            ? location : null
            );
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
}
