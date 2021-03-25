package com.acrylic.universalnms.entityai;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface TargettableAI extends PathSeekerAI {

    @Nullable
    Entity getTarget();

    void setTarget(@Nullable Entity target);

    /**
     * Call this in the tick method to update the pathfinder location.
     */
    default void updatePathfinderLocation() {
        Entity target = getTarget();
        setTargetLocation((target == null) ? null : target.getLocation());
    }

}
