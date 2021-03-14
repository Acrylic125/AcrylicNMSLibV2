package com.acrylic.universalnms.factory;

public interface NMSAbstractFactory {

    NMSUtilityFactory getNMSUtilsFactory();

    PacketFactory getPacketFactory();

}
