package com.acrylic.universalnms.entityai.strategies;

import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.entityai.TargettableAI;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TargetSelectorStrategy
        extends AIStrategy {

    @NotNull
    @Override
    TargettableAI getEntityAI();

    boolean canTarget(@NotNull Entity entity);

    default void setTarget(@Nullable Entity target) {
        getEntityAI().setTarget(target);
    }

}
