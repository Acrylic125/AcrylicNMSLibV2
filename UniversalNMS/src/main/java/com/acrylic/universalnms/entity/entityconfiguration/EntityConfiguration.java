package com.acrylic.universalnms.entity.entityconfiguration;

import com.acrylic.universal.entity.EntityInstance;
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

    EntityConfiguration DEFAULT_ENTITY = new EntityConfigurationImpl();

    /**
     * The amount of ticks to recheck the renderer.
     * By default, it is every 20 ticks. It is not recommended
     * to repeatedly check the renderer as it may be performance
     * heavy.
     *
     * @return The amount of ticks.
     */
    int getTicksToCheckRender();

    /**
     *
     * @return The condition for the renderer to check.
     */
    @Nullable
    Predicate<NMSEntityInstance> getCheckRendererIf();

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
