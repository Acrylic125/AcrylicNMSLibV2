package com.acrylic.universalnms.factory;

import com.acrylic.universalnms.packets.types.*;

public interface PacketFactory {

    BlockCrackPacket getNewBlockCrackPacket();

    EntityDestroyPacket getNewEntityDestroyPacket();

    EntityEquipmentPackets getNewEquipmentPackets();

    //EntityHeadRotationPacket getNewEntityHeadRotationPacket();

    EntityMetadataPacket getNewEntityMetadataPacket();

    LivingEntitySpawnPacket getNewLivingEntityDisplayPackets();

    TeleportPacket getNewTeleportPacket();

    SoundPacket getSoundPacket();

    //TablistHeaderFooterPacket getNewTablistHeaderFooterPacket();


}
