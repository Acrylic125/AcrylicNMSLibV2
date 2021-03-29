package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.packets.types.EntityDestroyPacket;
import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.EntityPlayerInitializableRenderer;
import com.acrylic.universalnms.renderer.PlayerInitializableRenderer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface EntityPacketHandler {

    @NotNull
    NMSEntityInstance getEntityInstance();

    void setRenderer(@NotNull PlayerInitializableRenderer renderer);

    PlayerInitializableRenderer getRenderer();

    default EntityPlayerInitializableRenderer useEntityPlayerCheckableRenderer() {
        EntityPlayerInitializableRenderer renderer = new EntityPlayerInitializableRenderer(getEntityInstance().getBukkitEntity());
        setRenderer(renderer);
        return renderer;
    }

    default EntityPlayerInitializableRenderer useEntityPlayerCheckableRenderer(double range) {
        EntityPlayerInitializableRenderer renderer = useEntityPlayerCheckableRenderer();
        renderer.setRange(range);
        return renderer;
    }

    EntityDestroyPacket getDestroyPacket();

    @NotNull
    EntitySpawnPacket getSpawnPacket();

    @NotNull
    TeleportPacket getTeleportPacket();

    void displayEntityToPlayer(Player player);

    void hideEntityFromPlayer(Player player);

    static void initializeRenderer(EntityPacketHandler entityPacketHandler) {
        PlayerInitializableRenderer renderer = entityPacketHandler.getRenderer();
        renderer.setOnInitialize(entityPacketHandler::displayEntityToPlayer);
        renderer.setOnDeinitialize(entityPacketHandler::hideEntityFromPlayer);
    }

    void updatePackets();

    default void refreshPackets() {
        getDestroyPacket().getSender().sendToAllByRenderer(getRenderer());
        updatePackets();
        resendPackets();
    }

    void resendPackets();

    static void throwNoRendererError() {
        throw new IllegalStateException("Please set a non null renderer. You may use EntityPacketHandler#useEntityPlayerCheckableRenderer() as a default renderer or set your own with EntityPacketHandler#setRenderer.");
    }

}
