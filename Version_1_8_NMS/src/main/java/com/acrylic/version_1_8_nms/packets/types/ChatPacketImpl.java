package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universalnms.enums.ChatMessageType;
import com.acrylic.universalnms.packets.types.ChatPacket;
import com.acrylic.version_1_8_nms.NMSUtils;
import com.acrylic.version_1_8_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.jetbrains.annotations.NotNull;

public class ChatPacketImpl
        extends SinglePacketWrapperImpl
        implements ChatPacket {

    private PacketPlayOutChat packet;

    @Override
    public void apply(@NotNull String text, @NotNull ChatMessageType chatMessageType) {
        this.packet = new PacketPlayOutChat(NMSUtils.toChatComponent(text), chatMessageType.getID());
    }

    @Override
    public PacketPlayOutChat getPacket() {
        return packet;
    }

}
