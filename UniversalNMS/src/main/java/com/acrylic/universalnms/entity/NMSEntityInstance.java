package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.EntityInstance;
import com.acrylic.universal.utils.LocationUtils;
import com.acrylic.universal.utils.TeleportationUtils;
import com.acrylic.universalnms.entityai.EntityAI;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface NMSEntityInstance extends EntityInstance {

    Object getNMSEntity();

    int getTicksLived();

    int getFireTicks();

    void setFireTicks(int ticks);

    @Nullable
    EntityAI getAI();

    EntityPacketHandler getDisplayer();

    boolean isNoClip();

    void setNoClip(boolean noClip);

    void removeFromWorld();

    void addToWorld();

    void tick();

    @Override
    default void teleport(@NotNull Location location) {
        Entity entity = getBukkitEntity();
        LocationUtils.teleport(entity, location);
        EntityPacketHandler displayer = getDisplayer();
        TeleportPacket teleportPacket = displayer.getTeleportPacket();
        teleportPacket.apply(entity, location);
        teleportPacket.send().sendToAllByRenderer(displayer.getRenderer());
    }

}
