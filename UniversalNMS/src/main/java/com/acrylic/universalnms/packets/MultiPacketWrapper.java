package com.acrylic.universalnms.packets;

import java.util.Collection;

public interface MultiPacketWrapper extends PacketWrapper {

    Collection<?> getPackets();

    default void reset() {
        getPackets().clear();
    }

}
