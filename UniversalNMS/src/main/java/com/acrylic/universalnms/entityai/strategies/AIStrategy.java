package com.acrylic.universalnms.entityai.strategies;

import com.acrylic.universalnms.entityai.EntityAI;
import org.jetbrains.annotations.NotNull;

/**
 * Am AI Strategy object represents the behaviour of
 * the entity holding the {@link com.acrylic.universalnms.entityai.EntityAI}
 * of this strategy.
 */
public interface AIStrategy {

    @NotNull
    EntityAI getEntityAI();

    void tick();

}
