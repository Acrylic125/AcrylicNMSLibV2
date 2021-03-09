package com.acrylic.universalnms.entity;

import com.acrylic.universal.packet.types.EntityEquipmentPackets;
import org.jetbrains.annotations.NotNull;

public interface LivingEntityDisplayer extends EntityDisplayer {

    @NotNull
    @Override
    NMSLivingEntityInstance getEntityInstance();

    @NotNull
    EntityEquipmentPackets getEquipmentPackets();


}
