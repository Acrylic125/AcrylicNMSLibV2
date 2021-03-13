package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universalnms.entity.EntityPacketHandler;
import com.acrylic.universalnms.entity.LivingEntityPacketHandler;
import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.Renderer;
import com.acrylic.universalnms.send.BatchSender;
import com.acrylic.version_1_16_nms.packets.types.*;
import net.minecraft.server.v1_16_R3.EntityLiving;
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
    private final BatchSender displaySender = new BatchSender();
    private Renderer<Player> renderer;

    public LivingEntityPacketHandlerImpl(@NotNull NMSLivingEntityInstanceImpl entityInstance, @Nullable Renderer<Player> renderer) {
        this.entityInstance = entityInstance;
        this.renderer = renderer;
        if (renderer != null)
            EntityPacketHandler.initializeRenderer(this);
        entityDestroyPacket.apply(entityInstance.getNMSEntity());
        equipmentPackets.apply(entityInstance.getNMSEntity());
        displaySender.attachSender(entitySpawnPacket.getSender());
        displaySender.attachSender(entityMetadataPacket.getSender());
        displaySender.attachSender(equipmentPackets.getSender());
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
    public void setRenderer(@NotNull Renderer<Player> renderer) {
        this.renderer = renderer;
        EntityPacketHandler.initializeRenderer(this);
    }

    @NotNull
    @Override
    public Renderer<Player> getRenderer() {
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
        EntityLiving entityLiving = entityInstance.getNMSEntity();
        entitySpawnPacket.apply(entityLiving);
        entityMetadataPacket.apply(entityLiving);
        displaySender.sendTo(player);
    }

    @Override
    public void hideEntityFromPlayer(Player player) {
        entityDestroyPacket.getSender().sendTo(player);
    }
}