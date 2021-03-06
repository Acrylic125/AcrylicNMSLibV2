package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universalnms.entity.EntityPacketHandler;
import com.acrylic.universalnms.packets.types.EntityDestroyPacket;
import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.AbstractEntityRenderer;
import com.acrylic.version_1_16_nms.packets.types.EntityDestroyPacketImpl;
import com.acrylic.version_1_16_nms.packets.types.EntitySpawnPacketImpl;
import com.acrylic.version_1_16_nms.packets.types.TeleportPacketImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityPacketHandlerImpl implements EntityPacketHandler {

    private final NMSEntityInstanceImpl entityInstance;
    private final EntitySpawnPacketImpl entitySpawnPacket = new EntitySpawnPacketImpl();
    private final TeleportPacketImpl teleportPacket = new TeleportPacketImpl();
    private final EntityDestroyPacketImpl entityDestroyPacket = new EntityDestroyPacketImpl();
    private AbstractEntityRenderer renderer;

    public EntityPacketHandlerImpl(@NotNull NMSEntityInstanceImpl entityInstance, @Nullable AbstractEntityRenderer renderer) {
        this.entityInstance = entityInstance;
        this.renderer = renderer;
        if (renderer != null)
            setRenderer(renderer);
        this.entityDestroyPacket.apply(entityInstance.getNMSEntity());
    }

    @NotNull
    @Override
    public NMSEntityInstanceImpl getEntityInstance() {
        return entityInstance;
    }

    @Override
    public void setRenderer(@NotNull AbstractEntityRenderer renderer) {
        EntityPacketHandler.terminateCurrentRenderer(this, this.renderer);
        this.renderer = renderer;
        EntityPacketHandler.initializeRenderer(this);
    }

    @Override
    public AbstractEntityRenderer getRenderer() {
        if (renderer == null)
            EntityPacketHandler.throwNoRendererError();
        return renderer;
    }

    @Nullable
    @Override
    public AbstractEntityRenderer getUnvalidatedRenderer() {
        return renderer;
    }

    @Override
    public EntityDestroyPacket getDestroyPacket() {
        return entityDestroyPacket;
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
}
