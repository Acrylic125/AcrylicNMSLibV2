package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.EntityPlayerCheckableRenderer;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import com.acrylic.universalnms.renderer.Renderer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface EntityPacketHandler {

    @NotNull
    NMSEntityInstance getEntityInstance();

    void setRenderer(@NotNull PlayerCheckableRenderer renderer);

    PlayerCheckableRenderer getRenderer();

    default void useEntityPlayerCheckableRenderer() {
        setRenderer(new EntityPlayerCheckableRenderer(getEntityInstance().getBukkitEntity()));
    }

    @NotNull
    EntitySpawnPacket getSpawnPacket();

    @NotNull
    TeleportPacket getTeleportPacket();

    void displayEntityToPlayer(Player player);

    void hideEntityFromPlayer(Player player);

    static void initializeRenderer(EntityPacketHandler entityPacketHandler) {
        PlayerCheckableRenderer renderer = entityPacketHandler.getRenderer();
        renderer.setOnInitialize(entityPacketHandler::displayEntityToPlayer);
        renderer.setOnDeinitialize(entityPacketHandler::hideEntityFromPlayer);
    }

    void updatePackets();

    void resendPackets();

    static void throwNoRendererError() {
        throw new IllegalStateException("Please set a non null renderer. You may use EntityPacketHandler#useEntityPlayerCheckableRenderer() as a default renderer or set your own with EntityPacketHandler#setRenderer.");
    }

}
