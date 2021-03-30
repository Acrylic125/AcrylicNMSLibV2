package com.acrylic.version_1_8_nms.entity.wrapper;

import net.minecraft.server.v1_8_R3.*;

public class EmptyPlayerConnection extends PlayerConnection {

    public EmptyPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
    }

    @Override
    public void sendPacket(Packet packet) {
        super.sendPacket(packet);
    }
}
