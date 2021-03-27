package com.acrylic.universalnms.entityai;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

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
}
