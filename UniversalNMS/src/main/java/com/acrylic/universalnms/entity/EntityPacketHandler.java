package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.Renderer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface EntityPacketHandler {

    @NotNull
    NMSEntityInstance getEntityInstance();

    void setRenderer(@NotNull Renderer<Player> renderer);

    @NotNull
    Renderer<Player> getRenderer();

    @NotNull
    EntitySpawnPacket getSpawnPacket();

    @NotNull
    TeleportPacket getTeleportPacket();

    void displayEntityToPlayer(Player player);

    void hideEntityFromPlayer(Player player);

    static void initializeRenderer(EntityPacketHandler entityPacketHandler) {
        Renderer<Player> renderer = entityPacketHandler.getRenderer();
        renderer.setOnInitialize(entityPacketHandler::displayEntityToPlayer);
        renderer.setOnDeinitialize(entityPacketHandler::hideEntityFromPlayer);
    }

}
