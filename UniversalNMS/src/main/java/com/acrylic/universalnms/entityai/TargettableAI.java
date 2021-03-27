package com.acrylic.universalnms.entityai;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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

    default boolean hasAValidTarget() {
        return isAValidTarget(getTarget());
    }

    default boolean isAValidTarget(Entity target) {
        if (target == null || !target.isValid())
            return false;
        if (target instanceof Player) {
            Player player = (Player) target;
            return player.isOnline();
        }
        return true;
    }

}
