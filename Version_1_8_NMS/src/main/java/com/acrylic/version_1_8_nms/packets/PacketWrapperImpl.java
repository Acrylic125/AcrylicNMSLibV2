package com.acrylic.version_1_8_nms.packets;

import com.acrylic.universalnms.packets.PacketWrapper;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
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
        Bukkit.broadcastMessage(packet + "");
        playerConnection.sendPacket(packet);
    }

}
