package com.acrylic.version_1_8_nms.packets;

import com.acrylic.universalnms.packets.MultiPacketWrapper;
import com.acrylic.universalnms.send.ValidatedSingleSender;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import java.util.Collection;

public abstract class MultiPacketWrapperImpl
        extends PacketWrapperImpl
        implements MultiPacketWrapper {

    @Override
    public boolean validateUse() {
        return getPackets() != null;
    }

    @Override
    public abstract Collection<? extends Packet<?>> getPackets();

    @Override
    public ValidatedSingleSender send() {
        return ValidatedSingleSender.validateBuilder(player -> {
            PlayerConnection playerConnection = (NMSUtils.convertToNMSPlayer(player)).playerConnection;
            for (Packet<?> packet : getPackets())
                sendPacket(playerConnection, packet);
        }).validation(this::validateUse)
                .attachValidationMessage("The packets cannot be null. Ensure that the packets are setup.")
                .build();
    }

}
