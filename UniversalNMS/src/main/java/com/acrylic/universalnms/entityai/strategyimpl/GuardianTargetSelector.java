package com.acrylic.universalnms.entityai.strategyimpl;

import com.acrylic.universalnms.entityai.TargettableAI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GuardianTargetSelector extends AbstractRandomTargetSelectorStrategy {

    public GuardianTargetSelector(@NotNull TargettableAI targettableAI) {
        super(targettableAI);
    }

    @Override
    public boolean canTarget(@NotNull Entity entity) {
        return entity instanceof LivingEntity && !(entity instanceof Player);
    }
}
