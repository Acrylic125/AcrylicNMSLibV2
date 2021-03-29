package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universalnms.entity.EntityPacketHandler;
import com.acrylic.universalnms.entity.LivingEntityPacketHandler;
import com.acrylic.universalnms.packets.types.*;
import com.acrylic.universalnms.renderer.AbstractEntityRenderer;
import com.acrylic.universalnms.send.BatchSender;
import com.acrylic.version_1_8_nms.packets.types.*;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LivingEntityPacketHandlerImpl implements LivingEntityPacketHandler {

    private final NMSLivingEntityInstanceImpl entityInstance;
    private final LivingEntitySpawnPacketImpl entitySpawnPacket = new LivingEntitySpawnPacketImpl();
    private final EntityDestroyPacketImpl entityDestroyPacket = new EntityDestroyPacketImpl();
    private final TeleportPacketImpl teleportPacket = new TeleportPacketImpl();
    private final EntityEquipmentPacketsImpl equipmentPackets = new EntityEquipmentPacketsImpl();
    private final EntityMetadataPacketImpl entityMetadataPacket = new EntityMetadataPacketImpl();
    private final EntityAnimationPacketsImpl entityAnimationPackets = new EntityAnimationPacketsImpl();
    private final BatchSender displaySender = new BatchSender();
    private AbstractEntityRenderer renderer;

    public LivingEntityPacketHandlerImpl(@NotNull NMSLivingEntityInstanceImpl entityInstance, @Nullable AbstractEntityRenderer renderer) {
        this.entityInstance = entityInstance;
        this.renderer = renderer;
        if (renderer != null)
            setRenderer(renderer);
        entityDestroyPacket.apply(entityInstance.getNMSEntity());
        equipmentPackets.apply(entityInstance.getNMSEntity());
        displaySender.attachSender(entitySpawnPacket.getSender());
        displaySender.attachSender(entityMetadataPacket.getSender());
        displaySender.attachSender(equipmentPackets.getSender());
        displaySender.attachSender(entityAnimationPackets.getSender());
    }

    @NotNull
    @Override
    public NMSLivingEntityInstanceImpl getEntityInstance() {
        return entityInstance;
    }

    @NotNull
    @Override
    public EntityEquipmentPacketsImpl getEquipmentPackets() {
        return equipmentPackets;
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

    @NotNull
    @Override
    public EntityMetadataPacket getMetadataPacket() {
        return entityMetadataPacket;
    }

    @Override
    public void updateMetadata() {
        entityMetadataPacket.apply(entityInstance.getNMSEntity());
        entityMetadataPacket.getSender().sendToAllByRenderer(renderer);
    }

    @NotNull
    @Override
    public EntityAnimationPackets getAnimationPackets() {
        return entityAnimationPackets;
    }

    @Override
    public void displayEntityToPlayer(Player player) {
        updatePackets();
        displaySender.sendTo(player);
    }

    @Override
    public void hideEntityFromPlayer(Player player) {
        entityDestroyPacket.getSender().sendTo(player);
    }

    @Override
    public void updatePackets() {
        EntityLiving entityLiving = entityInstance.getNMSEntity();
        entitySpawnPacket.apply(entityLiving);
        entityMetadataPacket.apply(entityLiving);
    }

    @Override
    public void resendPackets() {
        displaySender.sendToAllByRenderer(getRenderer());
    }
}
