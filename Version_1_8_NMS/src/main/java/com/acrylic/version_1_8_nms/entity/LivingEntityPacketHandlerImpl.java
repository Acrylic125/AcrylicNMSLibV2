package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universalnms.entity.LivingEntityPacketHandler;
import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.TeleportPacket;
import com.acrylic.universalnms.renderer.Renderer;
import com.acrylic.version_1_8_nms.packets.EntityEquipmentPacketsImpl;
import com.acrylic.version_1_8_nms.packets.EntitySpawnPacketImpl;
import com.acrylic.version_1_8_nms.packets.TeleportPacketImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LivingEntityPacketHandlerImpl implements LivingEntityPacketHandler {

    private final NMSLivingEntityInstanceImpl entityInstance;
    private final EntitySpawnPacketImpl entitySpawnPacket = new EntitySpawnPacketImpl();
    private final TeleportPacketImpl teleportPacket = new TeleportPacketImpl();
    private final EntityEquipmentPacketsImpl equipmentPackets = new EntityEquipmentPacketsImpl();
    private Renderer<Player> renderer;

    public LivingEntityPacketHandlerImpl(@NotNull NMSLivingEntityInstanceImpl entityInstance, @NotNull Renderer<Player> renderer) {
        this.entityInstance = entityInstance;
        this.renderer = renderer;
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
}
