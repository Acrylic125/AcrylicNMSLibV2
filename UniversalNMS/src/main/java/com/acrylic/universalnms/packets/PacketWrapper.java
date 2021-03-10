package com.acrylic.universalnms.packets;

import com.acrylic.universalnms.send.Sendable;
import com.acrylic.universalnms.send.ValidatedSingleSender;

public interface PacketWrapper extends Sendable {

    boolean validateUse();

    @Override
    ValidatedSingleSender getSender();
}
