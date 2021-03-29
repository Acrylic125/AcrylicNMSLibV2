package com.acrylic.universalnms.factory;

import com.acrylic.universalnms.packets.types.*;

public interface PacketFactory {

    BlockCrackPacket getNewBlockCrackPacket();

    EntityDestroyPacket getNewEntityDestroyPacket();

    EntityEquipmentPackets getNewEquipmentPackets();

    EntityMetadataPacket getNewEntityMetadataPacket();

    LivingEntitySpawnPacket getNewLivingEntityDisplayPackets();

    TeleportPacket getNewTeleportPacket();

    SoundPacket getNewSoundPacket();

    TablistHeaderFooterPacket getNewTablistHeaderFooterPacket();

    PlayerInfoPacket getNewPlayerInfoPacket();

    EntityOrientationPackets getNewEntityOrientationPackets();

    EntityAnimationPackets getNewEntityAnimationPackets();

    TitlePacket getNewTitlePacket();

    ChatPacket getNewChatPacket();

}
