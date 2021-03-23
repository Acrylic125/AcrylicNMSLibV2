package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universalnms.packets.types.PlayerInfoPacket;
import com.acrylic.version_1_8_nms.NMSUtils;
import com.acrylic.version_1_8_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerInfoPacketImpl
        extends SinglePacketWrapperImpl
        implements PlayerInfoPacket {

    private PacketPlayOutPlayerInfo packetPlayOutPlayerInfo;

    public void apply(@NotNull Info info, @NotNull net.minecraft.server.v1_8_R3.EntityPlayer player) {
        packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo(toNMSInfo(info), player);
    }

    public void apply(@NotNull Info info, @NotNull net.minecraft.server.v1_8_R3.EntityPlayer... players) {
        packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo(toNMSInfo(info), players);
    }

    @Override
    public void apply(@NotNull Info info, @NotNull Player player) {
        apply(info, NMSUtils.convertToNMSPlayer(player));
    }

    @Override
    public void apply(@NotNull Info info, @NotNull Player... players) {
        EntityPlayer[] nmsPlayers = new EntityPlayer[players.length];
        for (int i = 0; i < players.length; i++) {
            nmsPlayers[i] = NMSUtils.convertToNMSPlayer(players[i]);
        }
        apply(info, nmsPlayers);
    }

    @Override
    public PacketPlayOutPlayerInfo getPacket() {
        return packetPlayOutPlayerInfo;
    }

    @Nullable
    public static PacketPlayOutPlayerInfo.EnumPlayerInfoAction toNMSInfo(Info info) {
        switch (info) {
            case ADD_PLAYER: return PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER;
            case REMOVE_PLAYER: return PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER;
            case UPDATE_LATENCY: return PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME;
            case UPDATE_GAME_MODE: return PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY;
            case UPDATE_DISPLAY_NAME: return PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE;
        }
        return null;
    }

}
