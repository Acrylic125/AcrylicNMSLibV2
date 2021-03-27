package com.acrylic.universalnms.entityai;

import com.acrylic.universalnms.entity.NMSEntityInstance;

/**
 * An Entity AI object represents an intermediary system
 * between AI Strategies. It also handles these strategies
 * for updating via {@link NMSEntityInstance#tick()}.
 *
 * This allows for runtime manipulation of the entity AI.
 *
 */
public interface EntityAI extends Lockable {

    NMSEntityInstance getInstance();

    /**
     * This method will run all it's strategies
     * and all it's intermediary functions.
     */
    void tick();

}
