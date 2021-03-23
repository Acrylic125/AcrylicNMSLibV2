package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.packets.types.EntityHeadRotationPacket;
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
    EntityHeadRotationPacket getHeadRotationPacket();

    @NotNull
    PlayerInfoPacket getPlayerInfoPacket();

}
