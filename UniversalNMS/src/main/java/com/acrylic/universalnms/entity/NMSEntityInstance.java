package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.EntityInstance;
import com.acrylic.universal.utils.LocationUtils;
import com.acrylic.universalnms.entity.wrapper.NMSEntityWrapper;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NMSEntityInstance extends EntityInstance {

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

    EntityPacketHandler getPacketHandler();

    boolean isNoClip();

    void setNoClip(boolean noClip);

    void removeFromWorld();

    void addToWorld();

    default void tick() {
        if (getTicksLived() % 20 == 0) {
            getPacketHandler().getRenderer().doChecks();
        }
    }

    @Override
    default void teleport(@NotNull Location location) {
        Entity entity = getBukkitEntity();
        LocationUtils.teleport(entity, location);
        EntityPacketHandler displayer = getPacketHandler();
        TeleportPacket teleportPacket = displayer.getTeleportPacket();
        teleportPacket.apply(entity, location);
        teleportPacket.getSender().sendToAllByRenderer(displayer.getRenderer());
    }

}
