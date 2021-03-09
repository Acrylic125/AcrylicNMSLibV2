package com.acrylic.universalnms.entity;

import com.acrylic.universal.packet.types.EntityEquipmentPackets;
import com.acrylic.universal.packet.types.EntitySpawnPacket;
import com.acrylic.universal.packet.types.TeleportPacket;
import com.acrylic.universal.render.Renderer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface EntityDisplayer {

    @NotNull
    NMSEntityInstance getEntityInstance();

    void setRenderer(@NotNull Renderer<Player> renderer);

    @NotNull
    Renderer<Player> getRenderer();

    @NotNull
    EntitySpawnPacket getSpawnPacket();

    @NotNull
    TeleportPacket getTeleportPacket();

}
