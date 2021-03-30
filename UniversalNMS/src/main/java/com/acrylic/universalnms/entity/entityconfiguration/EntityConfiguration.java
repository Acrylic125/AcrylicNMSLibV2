package com.acrylic.universalnms.entity.entityconfiguration;

import com.acrylic.universalnms.entity.NMSEntityInstance;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * The entity configuration acts as a control gateway
 * for the inaccessible features of the NMS Entities.
 * (Without overriding any instances) This can prove it's
 * use when you want to customise every bit of the entity.
 *
 * By default, a default configuration will be used
 * unless specified.
 */
public interface EntityConfiguration {

    EntityConfiguration
            DEFAULT_ENTITY = new EntityConfigurationImpl(),
            NO_AI_ENTITY = EntityConfigurationImpl.entityBuilder()
                    .runAIByNMSEntities(false)
                    .build();

    /**
     *
     * @return Should the AI run even if no one is being
     * rendered for.
     */
    boolean silentAIIfNoOneIsRendered();

    /**
     *
     * @return The condition for the AI to run.
     */
    @Nullable
    Predicate<NMSEntityInstance> getRunAIIf();

    /**
     *
     * @return If the AI should run via
     * {@link com.acrylic.universalnms.entity.manager.NMSEntities}
     */
    boolean shouldRunAIByNMSEntities();

    /**
     *
     * This will signal to any pathfinder strategy that
     * teleporting should be used (If true) instea of using
     * a different means of achieving the same goal. This
     * can improve the performance of entity traversals in the
     * expense of unnatural movements. It is highly enable this
     * if this entity is going to be spawned in swarms
     * instead of a singular (few) unit(s).
     *
     * @return Whether teleporting should be used when
     * traversing.
     *
     * @see com.acrylic.universalnms.entityai.strategyimpl.SimplePathQuitterStrategyImpl
     */
    boolean useTeleportForPathfindingStrategy();

}
