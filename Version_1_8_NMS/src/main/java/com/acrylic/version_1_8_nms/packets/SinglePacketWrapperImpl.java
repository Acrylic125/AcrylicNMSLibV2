package com.acrylic.version_1_8_nms.packets;

import com.acrylic.universalnms.packets.SinglePacketWrapper;
import com.acrylic.universalnms.send.ValidatedSingleSender;
import com.acrylic.universalnms.util.Validation;
import net.minecraft.server.v1_8_R3.Packet;

public abstract class SinglePacketWrapperImpl
        extends PacketWrapperImpl
        implements SinglePacketWrapper {

    @Override
    public abstract Packet<?> getPacket();

    @Override
    public boolean validateUse() {
        return getPacket() != null;
    }

    @Override
    public ValidatedSingleSender send() {
        return ValidatedSingleSender
                .validateBuilder(player -> sendPacket(player, getPacket()))
                .validation(this::validateUse)
                .attachValidationMessage("The packet cannot be null. Ensure that the packet is setup.")
                .build();
    }
}
