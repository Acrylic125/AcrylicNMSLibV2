package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.packets.types.EntityDestroyPacket;
import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.AbstractEntityRenderer;
import com.acrylic.universalnms.renderer.EntityRendererWorker;
import com.acrylic.universalnms.renderer.RangedEntityRenderer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface EntityPacketHandler {

    @NotNull
    NMSEntityInstance getEntityInstance();

    void setRenderer(@NotNull AbstractEntityRenderer renderer);

    AbstractEntityRenderer getRenderer();

    default RangedEntityRenderer useEntityPlayerCheckableRenderer() {
        RangedEntityRenderer renderer = new RangedEntityRenderer(getEntityInstance().getBukkitEntity());
        setRenderer(renderer);
        return renderer;
    }

    default RangedEntityRenderer useEntityPlayerCheckableRenderer(float range) {
        RangedEntityRenderer renderer = useEntityPlayerCheckableRenderer();
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

    default EntityRendererWorker getRendererWorker() {
        return NMSLib.getNMSEntities().getDefaultRendererWorker();
    }

    static void terminateCurrentRenderer(EntityPacketHandler entityPacketHandler, AbstractEntityRenderer abstractEntityRenderer) {
        if (abstractEntityRenderer == null)
            return;
        abstractEntityRenderer.unbindEntity(entityPacketHandler.getEntityInstance());
        entityPacketHandler.getRendererWorker().checkForRemoval(abstractEntityRenderer);
    }

    static void initializeRenderer(EntityPacketHandler entityPacketHandler) {
        AbstractEntityRenderer renderer = entityPacketHandler.getRenderer();
        renderer.bindEntity(entityPacketHandler.getEntityInstance(), new AbstractEntityRenderer.ActionHolder(
                entityPacketHandler::displayEntityToPlayer, entityPacketHandler::hideEntityFromPlayer
        ));
        entityPacketHandler.getRendererWorker().register(renderer);
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
