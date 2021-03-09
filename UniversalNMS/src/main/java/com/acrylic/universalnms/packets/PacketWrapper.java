package com.acrylic.universalnms.packets;

import com.acrylic.universalnms.send.Sendable;

public interface PacketWrapper extends Sendable {

    void validateUse();

}
