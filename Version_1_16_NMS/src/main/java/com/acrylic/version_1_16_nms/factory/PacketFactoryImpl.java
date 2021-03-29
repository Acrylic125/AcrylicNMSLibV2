package com.acrylic.version_1_16_nms.factory;

import com.acrylic.universalnms.factory.PacketFactory;
import com.acrylic.universalnms.packets.types.*;
import com.acrylic.version_1_16_nms.packets.types.*;

public class PacketFactoryImpl implements PacketFactory {

    @Override
    public BlockCrackPacket getNewBlockCrackPacket() {
        return new BlockCrackPacketImpl();
    }

    @Override
    public EntityDestroyPacket getNewEntityDestroyPacket() {
        return new EntityDestroyPacketImpl();
    }

    @Override
    public EntityEquipmentPackets getNewEquipmentPackets() {
        return new EntityEquipmentPacketsImpl();
    }

    @Override
    public EntityMetadataPacket getNewEntityMetadataPacket() {
        return new EntityMetadataPacketImpl();
    }

    @Override
    public LivingEntitySpawnPacket getNewLivingEntityDisplayPackets() {
        return new LivingEntitySpawnPacketImpl();
    }

    @Override
    public TeleportPacket getNewTeleportPacket() {
        return new TeleportPacketImpl();
    }

    @Override
    public SoundPacket getNewSoundPacket() {
        return new SoundPacketImpl();
    }

    @Override
    public TablistHeaderFooterPacket getNewTablistHeaderFooterPacket() {
        return new TablistHeaderFooterPacketImpl();
    }

    @Override
    public PlayerInfoPacket getNewPlayerInfoPacket() {
        return new PlayerInfoPacketImpl();
    }

    @Override
    public EntityOrientationPackets getNewEntityOrientationPackets() {
        return new EntityOrientationPacketsImpl();
    }

    @Override
    public EntityAnimationPackets getNewEntityAnimationPackets() {
        return new EntityAnimationPacketsImpl();
    }

    @Override
    public TitlePacket getNewTitlePacket() {
        return new TitlePacketImpl();
    }

    @Override
    public ChatPacket getNewChatPacket() {
        return new ChatPacketImpl();
    }
}
