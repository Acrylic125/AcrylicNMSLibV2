package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import com.acrylic.universalnms.renderer.Renderer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface EntityPacketHandler {

    @NotNull
    NMSEntityInstance getEntityInstance();

    void setRenderer(@NotNull PlayerCheckableRenderer renderer);

    @NotNull
    PlayerCheckableRenderer getRenderer();

    @NotNull
    EntitySpawnPacket getSpawnPacket();

    @NotNull
    TeleportPacket getTeleportPacket();

    void displayEntityToPlayer(Player player);

    void hideEntityFromPlayer(Player player);

    static void initializeRenderer(EntityPacketHandler entityPacketHandler) {
        PlayerCheckableRenderer renderer = entityPacketHandler.getRenderer();
        renderer.setOnInitialize(player -> entityPacketHandler.displayEntityToPlayer(player));
        renderer.setOnDeinitialize(entityPacketHandler::hideEntityFromPlayer);
    }

    void updatePackets();

    void resendPackets();

}
