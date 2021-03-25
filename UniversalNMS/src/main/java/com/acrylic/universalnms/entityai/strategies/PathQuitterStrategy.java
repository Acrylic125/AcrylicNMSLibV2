package com.acrylic.universalnms.entityai.strategies;

import com.acrylic.universalnms.entityai.PathSeekerAI;
import org.jetbrains.annotations.NotNull;

public interface PathQuitterStrategy extends AIStrategy {

    @NotNull
    @Override
    PathSeekerAI getEntityAI();
}
