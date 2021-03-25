package com.acrylic.universalnms.entityai;

import com.acrylic.universalnms.entityai.strategies.PathQuitterStrategy;
import com.acrylic.universalnms.entityai.strategies.PathfinderStrategy;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PathSeekerAI extends EntityAI {

    @NotNull
    PathfinderStrategy getPathfinderStrategy();

    void setTargetLocation(@Nullable Location location);

    @Nullable
    Location getTargetLocation();

    @Nullable
    PathQuitterStrategy getPathQuitterStrategy();

}
