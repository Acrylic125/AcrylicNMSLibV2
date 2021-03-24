package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.EntityPacketHandler;
import com.acrylic.universalnms.entity.PlayerPacketHandler;
import com.acrylic.universalnms.packets.types.*;
import com.acrylic.universalnms.renderer.PlayerCheckableRenderer;
import com.acrylic.universalnms.send.BatchSender;
import com.acrylic.version_1_8_nms.packets.types.*;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerPacketHandlerImpl implements PlayerPacketHandler {

    private final NMSPlayerInstanceImpl entityInstance;
    private final PlayerInfoPacketImpl
            playerAddInfoPacket = new PlayerInfoPacketImpl(),
            playerRemoveInfoPacket = new PlayerInfoPacketImpl();
    private final EntityOrientationPacketsImpl headRotationPacket = new EntityOrientationPacketsImpl();
    private final NamedPlayerSpawnPacketImpl entitySpawnPacket = new NamedPlayerSpawnPacketImpl();
    private final EntityDestroyPacketImpl entityDestroyPacket = new EntityDestroyPacketImpl();
    private final TeleportPacketImpl teleportPacket = new TeleportPacketImpl();
    private final EntityEquipmentPacketsImpl equipmentPackets = new EntityEquipmentPacketsImpl();
    private final EntityMetadataPacketImpl entityMetadataPacket = new EntityMetadataPacketImpl();
    private final BatchSender displaySender = new BatchSender();
    private PlayerCheckableRenderer renderer;

    public PlayerPacketHandlerImpl(@NotNull NMSPlayerInstanceImpl entityInstance, @Nullable PlayerCheckableRenderer renderer) {
        this.entityInstance = entityInstance;
        this.renderer = renderer;
        if (renderer != null)
            EntityPacketHandler.initializeRenderer(this);
        EntityPlayer entityPlayer = entityInstance.getNMSEntity();
        entityDestroyPacket.apply(entityPlayer);
        equipmentPackets.apply(entityPlayer);
        headRotationPacket.apply(entityPlayer, entityPlayer.yaw, entityPlayer.pitch);
        playerAddInfoPacket.apply(PlayerInfoPacket.Info.ADD_PLAYER, entityPlayer);
        playerRemoveInfoPacket.apply(PlayerInfoPacket.Info.REMOVE_PLAYER, entityPlayer);
        displaySender.attachSender(playerAddInfoPacket.getSender());
        displaySender.attachSender(entitySpawnPacket.getSender());
        displaySender.attachSender(entityMetadataPacket.getSender());
        displaySender.attachSender(equipmentPackets.getSender());
        displaySender.attachSender(headRotationPacket.getSender());
        displaySender.attachSender(teleportPacket.getSender());
    }

    @NotNull
    @Override
    public NMSPlayerInstanceImpl getEntityInstance() {
        return entityInstance;
    }

    @NotNull
    @Override
    public EntityOrientationPackets getHeadRotationPacket() {
        return headRotationPacket;
    }

    @NotNull
    @Override
    public PlayerInfoPacket getAddPlayerInfoPacket() {
        return playerAddInfoPacket;
    }

    @NotNull
    @Override
    public PlayerInfoPacket getRemovePlayerInfoPacket() {
        return playerRemoveInfoPacket;
    }

    @NotNull
    @Override
    public EntityEquipmentPacketsImpl getEquipmentPackets() {
        return equipmentPackets;
    }

    @Override
    public void setRenderer(@NotNull PlayerCheckableRenderer renderer) {
        this.renderer = renderer;
        EntityPacketHandler.initializeRenderer(this);
    }

    @Override
    public PlayerCheckableRenderer getRenderer() {
        if (renderer == null)
            EntityPacketHandler.throwNoRendererError();
        return renderer;
    }

    @NotNull
    @Override
    public NamedPlayerSpawnPacket getSpawnPacket() {
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

    @Override
    public void displayEntityToPlayer(Player player) {
        updatePackets();
        displaySender.sendTo(player);
        NMSLib.getNPCTablistRemover().add(player, playerRemoveInfoPacket);
    }

    @Override
    public void hideEntityFromPlayer(Player player) {
        entityDestroyPacket.getSender().sendTo(player);
    }

    @Override
    public void updatePackets() {
        EntityPlayer entityLiving = entityInstance.getNMSEntity();
        entitySpawnPacket.apply(entityLiving);
        entityMetadataPacket.apply(entityLiving);
        headRotationPacket.apply(entityLiving, entityLiving.yaw, entityLiving.pitch);
        teleportPacket.apply(entityLiving);
    }

    @Override
    public void resendPackets() {
        displaySender.sendToAllByRenderer(getRenderer());
    }

    @Override
    public EntityDestroyPacket getDestroyPacket() {
        return entityDestroyPacket;
    }

}
