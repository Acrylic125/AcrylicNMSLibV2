package com.acrylic.version_1_16_nms.packets.types;

import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.enums.ChatMessageType;
import com.acrylic.universalnms.packets.types.ChatPacket;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.jetbrains.annotations.NotNull;

public class ChatPacketImpl
        extends SinglePacketWrapperImpl
        implements ChatPacket {

    private PacketPlayOutChat packet;

    @Override
    public void apply(@NotNull String text, @NotNull ChatMessageType chatMessageType) {
        this.packet = new PacketPlayOutChat(NMSUtils.toChatComponent(text), toNMSChatMessageType(chatMessageType), null);
    }

    @Override
    public PacketPlayOutChat getPacket() {
        return packet;
    }

    private static net.minecraft.server.v1_16_R3.ChatMessageType toNMSChatMessageType(ChatMessageType chatMessageType) {
        switch (chatMessageType) {
            case CHAT: return net.minecraft.server.v1_16_R3.ChatMessageType.CHAT;
            case SYSTEM: return net.minecraft.server.v1_16_R3.ChatMessageType.SYSTEM;
            case GAME_INFO: return net.minecraft.server.v1_16_R3.ChatMessageType.GAME_INFO;
            default: return null;
        }
    }
}
