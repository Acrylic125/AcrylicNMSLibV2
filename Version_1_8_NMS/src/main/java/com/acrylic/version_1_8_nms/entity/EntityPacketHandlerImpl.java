package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universalnms.entity.EntityPacketHandler;
import com.acrylic.universalnms.packets.types.EntityDestroyPacket;
import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.PlayerInitializableRenderer;
import com.acrylic.version_1_8_nms.packets.types.EntityDestroyPacketImpl;
import com.acrylic.version_1_8_nms.packets.types.EntitySpawnPacketImpl;
import com.acrylic.version_1_8_nms.packets.types.TeleportPacketImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityPacketHandlerImpl implements EntityPacketHandler {

    private final NMSEntityInstanceImpl entityInstance;
    private final EntitySpawnPacketImpl entitySpawnPacket = new EntitySpawnPacketImpl();
    private final TeleportPacketImpl teleportPacket = new TeleportPacketImpl();
    private final EntityDestroyPacketImpl entityDestroyPacket = new EntityDestroyPacketImpl();
    private PlayerInitializableRenderer renderer;

    public EntityPacketHandlerImpl(@NotNull NMSEntityInstanceImpl entityInstance, @Nullable PlayerInitializableRenderer renderer) {
        this.entityInstance = entityInstance;
        this.renderer = renderer;
        if (renderer != null)
            EntityPacketHandler.initializeRenderer(this);
        this.entityDestroyPacket.apply(entityInstance.getNMSEntity());
    }

    @NotNull
    @Override
    public NMSEntityInstanceImpl getEntityInstance() {
        return entityInstance;
    }

    @Override
    public void setRenderer(@NotNull PlayerInitializableRenderer renderer) {
        this.renderer = renderer;
        EntityPacketHandler.initializeRenderer(this);
    }

    @Override
    public PlayerInitializableRenderer getRenderer() {
        if (renderer == null)
            EntityPacketHandler.throwNoRendererError();
        return renderer;
    }

    @NotNull
    @Override
    public EntitySpawnPacket getSpawnPacket() {
        return entitySpawnPacket;
    }

    @NotNull
    @Override
    public TeleportPacket getTeleportPacket() {
        return teleportPacket;
    }

    @Override
    public void displayEntityToPlayer(Player player) {
        updatePackets();
        entitySpawnPacket.getSender().sendTo(player);
    }

    @Override
    public void hideEntityFromPlayer(Player player) {
        entityDestroyPacket.getSender().sendTo(player);
    }

    @Override
    public void updatePackets() {
        entitySpawnPacket.apply(getEntityInstance().getNMSEntity());
    }

    @Override
    public void resendPackets() {
        entitySpawnPacket.getSender().sendToAllByRenderer(getRenderer());
    }

    @Override
    public EntityDestroyPacket getDestroyPacket() {
        return entityDestroyPacket;
    }
}
