package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.packets.types.TablistHeaderFooterPacket;
import com.acrylic.version_1_8_nms.packets.SinglePacketWrapperImpl;
import io.netty.buffer.ByteBufAllocator;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class TablistHeaderFooterPacketImpl
        extends SinglePacketWrapperImpl
        implements TablistHeaderFooterPacket {

    private PacketPlayOutPlayerListHeaderFooter packet;

    @Override
    public void applyHeaderAndFooter(@Nullable String header, @Nullable String footer) {
        try {
            PacketDataSerializer data = new PacketDataSerializer(ByteBufAllocator.DEFAULT.buffer());
            data.a(serializeString(header));
            data.a(serializeString(footer));
            packet = new PacketPlayOutPlayerListHeaderFooter();
            packet.a(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public PacketPlayOutPlayerListHeaderFooter getPacket() {
        return packet;
    }

    private static String serializeString(@Nullable String str) {
        return (str == null) ? EMPTY : ComponentSerializer.toString(new TextComponent(ChatUtils.get(str)));
    }
}
