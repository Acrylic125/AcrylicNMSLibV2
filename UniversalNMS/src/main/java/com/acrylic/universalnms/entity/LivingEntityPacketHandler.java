package com.acrylic.universalnms.entity;

import com.acrylic.universalnms.packets.types.EntityEquipmentPackets;
import org.jetbrains.annotations.NotNull;

public interface LivingEntityPacketHandler extends EntityPacketHandler {

    @NotNull
    @Override
    NMSLivingEntityInstance getEntityInstance();

    @NotNull
    EntityEquipmentPackets getEquipmentPackets();


}
