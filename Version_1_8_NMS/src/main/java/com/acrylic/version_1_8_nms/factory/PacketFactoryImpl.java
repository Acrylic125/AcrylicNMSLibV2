package com.acrylic.version_1_8_nms.factory;

import com.acrylic.universalnms.factory.PacketFactory;
import com.acrylic.universalnms.packets.types.*;
import com.acrylic.version_1_8_nms.packets.types.*;

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
    public SoundPacket getSoundPacket() {
        return new SoundPacketImpl();
    }
}
