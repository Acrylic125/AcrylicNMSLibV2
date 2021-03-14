package com.acrylic.version_1_16_nms.factory;

import com.acrylic.universalnms.factory.NMSAbstractFactory;
import com.acrylic.universalnms.factory.NMSEntityFactory;
import com.acrylic.universalnms.factory.NMSUtilityFactory;
import com.acrylic.universalnms.factory.PacketFactory;

public final class NMSAbstractFactoryImpl implements NMSAbstractFactory {

    private final NMSUtilityFactoryImpl nmsUtilityFactory = new NMSUtilityFactoryImpl();
    private final PacketFactoryImpl packetFactory = new PacketFactoryImpl();
    private final NMSEntityFactoryImpl entityFactory = new NMSEntityFactoryImpl();

    @Override
    public NMSUtilityFactory getNMSUtilsFactory() {
        return nmsUtilityFactory;
    }

    @Override
    public PacketFactory getPacketFactory() {
        return packetFactory;
    }

    @Override
    public NMSEntityFactory getNMSEntityFactory() {
        return entityFactory;
    }
}
