package com.acrylic.universalnms.entityai.strategyimpl;

import com.acrylic.universalnms.entityai.TargettableAI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class AnyRandomTargetSelector extends AbstractRandomTargetSelectorStrategy {

    public AnyRandomTargetSelector(@NotNull TargettableAI targettableAI) {
        super(targettableAI);
    }

    @Override
    public boolean canTarget(@NotNull Entity entity) {
        return entity instanceof LivingEntity;
    }
}
