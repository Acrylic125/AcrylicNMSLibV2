package com.acrylic.version_1_8_nms.factory;

import com.acrylic.universalnms.factory.NMSAbstractFactory;
import com.acrylic.universalnms.factory.NMSUtilityFactory;
import com.acrylic.universalnms.factory.PacketFactory;

public final class NMSAbstractFactoryImpl implements NMSAbstractFactory {

    private final NMSUtilityFactoryImpl nmsUtilityFactory = new NMSUtilityFactoryImpl();
    private final PacketFactoryImpl packetFactory = new PacketFactoryImpl();

    @Override
    public NMSUtilityFactory getNMSUtilsFactory() {
        return nmsUtilityFactory;
    }

    @Override
    public PacketFactory getPacketFactory() {
        return packetFactory;
    }
}
