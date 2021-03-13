package com.acrylic.version_1_16_nms.packets;

import com.acrylic.universalnms.packets.PacketWrapper;
import com.acrylic.version_1_16_nms.NMSUtils;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.entity.Player;

public abstract class PacketWrapperImpl
        implements PacketWrapper {

    public static void sendPacket(Player player, Packet<?> packet) {
        sendPacket((NMSUtils.convertToNMSPlayer(player)), packet);
    }

    public static void sendPacket(EntityPlayer player, Packet<?> packet) {
        sendPacket(player.playerConnection, packet);
    }

    public static void sendPacket(PlayerConnection playerConnection, Packet<?> packet) {
        playerConnection.sendPacket(packet);
    }

}
