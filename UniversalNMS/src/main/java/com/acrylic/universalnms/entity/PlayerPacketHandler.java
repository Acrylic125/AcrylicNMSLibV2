package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.packets.types.EntityOrientationPackets;
import com.acrylic.universalnms.packets.types.NamedPlayerSpawnPacket;
import com.acrylic.universalnms.packets.types.PlayerInfoPacket;
import org.jetbrains.annotations.NotNull;

public interface PlayerPacketHandler extends LivingEntityPacketHandler {

    @NotNull
    @Override
    NMSPlayerInstance getEntityInstance();

    @NotNull
    @Override
    NamedPlayerSpawnPacket getSpawnPacket();

    @NotNull
    EntityOrientationPackets getEntityOrientationPackets();

    @NotNull
    PlayerInfoPacket getAddPlayerInfoPacket();

    @NotNull
    PlayerInfoPacket getRemovePlayerInfoPacket();


}
