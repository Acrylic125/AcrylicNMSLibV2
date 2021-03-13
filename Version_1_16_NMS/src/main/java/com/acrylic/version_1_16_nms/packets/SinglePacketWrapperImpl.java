package com.acrylic.version_1_16_nms.packets;

import com.acrylic.universalnms.packets.SinglePacketWrapper;
import com.acrylic.universalnms.send.ValidatedSingleSender;
import net.minecraft.server.v1_16_R3.Packet;

public abstract class SinglePacketWrapperImpl
        extends PacketWrapperImpl
        implements SinglePacketWrapper {

    private final ValidatedSingleSender send = ValidatedSingleSender
            .validateBuilder(player -> sendPacket(player, getPacket()))
            .validation(this::validateUse)
            .attachValidationMessage("The packet cannot be null. Ensure that the packet is setup.")
            .build();

    @Override
    public abstract Packet<?> getPacket();

    @Override
    public boolean validateUse() {
        return getPacket() != null;
    }

    @Override
    public ValidatedSingleSender getSender() {
        return send;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "send=" + send +
                '}';
    }
}
