package com.acrylic.universalnms.entityai.strategyimpl;

import com.acrylic.universalnms.entityai.TargettableAI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerRandomTargetSelector extends AbstractRandomTargetSelectorStrategy{

    public PlayerRandomTargetSelector(@NotNull TargettableAI targettableAI) {
        super(targettableAI);
    }

    @Override
    public boolean canTarget(@NotNull Entity entity) {
        return getEntityAI().isAValidTarget(entity) && entity instanceof Player;
    }
}
