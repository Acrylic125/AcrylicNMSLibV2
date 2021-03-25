package com.acrylic.universalnms.entityai;

import com.acrylic.universal.entity.EntityInstance;
import com.acrylic.universalnms.entity.NMSEntityInstance;

/**
 * An Entity AI object represents an intermediary system
 * between AI Strategies. It also handles these strategies
 * for updating via {@link NMSEntityInstance#tick()}.
 *
 * This allows for runtime manipulation of the entity AI.
 *
 */
public interface EntityAI {

    NMSEntityInstance getInstance();

    void tick();

}
