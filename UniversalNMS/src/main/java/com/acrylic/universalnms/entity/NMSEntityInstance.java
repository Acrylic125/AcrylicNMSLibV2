package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.EntityInstance;
import com.acrylic.universal.utils.LocationUtils;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.entityconfiguration.EntityConfiguration;
import com.acrylic.universalnms.entity.manager.NMSEntities;
import com.acrylic.universalnms.entity.wrapper.NMSEntityWrapper;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.packets.types.EntityDestroyPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface NMSEntityInstance extends EntityInstance {

    enum TickSource {
        /**
         * Indicates that the tick source is from
         * the {@link NMSEntities#run()} method.
         */
        NMS_ENTITIES,
        /**
         * Indicates that the tick source is from
         * some other implementation.
         */
        CUSTOM,
        /**
         * Indicates that the tick source is directly from
         * this instance or it's wrapper. Currently not
         * in use.
         */
        @Deprecated
        ROOT
    }

    void setEntityConfiguration(@NotNull EntityConfiguration entityConfiguration);

    EntityConfiguration getEntityConfiguration();

    int getInstanceTicks();

    void setInstanceTicks(int ticks);

    Object getNMSEntity();

    NMSEntityWrapper getEntityWrapper();

    int getTicksLived();

    void setTicksLived(int ticks);

    default void addTicksLived() {
        setTicksLived(getTicksLived() + 1);
    }

    int getFireTicks();

    void setFireTicks(int ticks);

    @Nullable
    EntityAI getAI();

    void setAI(@Nullable EntityAI entityAI);

    EntityPacketHandler getPacketHandler();

    boolean isNoClip();

    void setNoClip(boolean noClip);

    void removeFromWorld();

    void addToWorld();

    float getYaw();

    void setYaw(float yaw);

    float getPitch();

    void setPitch(float pitch);

    void setYawAndPitch(float yaw, float pitch);

    default void tick(TickSource tickSource) {
        EntityAI ai = getAI();
        EntityPacketHandler packetHandler = getPacketHandler();
        PlayerCheckableRenderer renderer = packetHandler.getRenderer();;
        EntityConfiguration entityConfiguration = getEntityConfiguration();
        int ticks = getInstanceTicks();
        Predicate<NMSEntityInstance>
                runAIIf = entityConfiguration.getRunAIIf(),
                checkRendererIf = entityConfiguration.getCheckRendererIf();
        if (ai != null && !ai.isLocked() &&
                (runAIIf == null || runAIIf.test(this)) &&
                !(entityConfiguration.silentAIIfNoOneIsRendered() && !renderer.isInUse()) &&
                !(tickSource == TickSource.NMS_ENTITIES && !entityConfiguration.shouldRunAIByNMSEntities())
        )
            ai.tick();
        if ((ticks % entityConfiguration.getTicksToCheckRender() == 0) &&
                (checkRendererIf == null || checkRendererIf.test(this))) {
            renderer.doChecks();
        }
        setInstanceTicks(ticks + 1);
    }

    @Override
    default void teleport(@NotNull Location location) {
        Entity entity = getBukkitEntity();
        LocationUtils.teleport(entity, location);
        EntityPacketHandler display = getPacketHandler();
        TeleportPacket teleportPacket = display.getTeleportPacket();
        teleportPacket.apply(entity, location);
        teleportPacket.getSender().sendToAllByRenderer(display.getRenderer());
    }

    @Override
    default void delete() {
        Entity entity = getBukkitEntity();
        entity.remove();
        removeFromWorld();
        EntityDestroyPacket destroyPacket = getPacketHandler().getDestroyPacket();
        destroyPacket.apply(entity);
        destroyPacket.getSender().sendToAllOnline();
    }

    void setAnimationDataWatcher(int mask, boolean b);

    default void setOnFire(boolean b) {
        setAnimationDataWatcher(0x01, b);
    }

    void move(double x, double y, double z);

    default void move(Vector vector) {
        move(vector.getX(), vector.getY(), vector.getZ());
    }

    default void register() {
        register(NMSLib.getNMSEntities());
    }

    default void register(@NotNull NMSEntities nmsEntities) {
        nmsEntities.getEntityRetriever().register(this);
    }

    default void unregister() {
        unregister(NMSLib.getNMSEntities());
    }

    default void unregister(@NotNull NMSEntities nmsEntities) {
        nmsEntities.getEntityRetriever().unregister(this);
    }

}
