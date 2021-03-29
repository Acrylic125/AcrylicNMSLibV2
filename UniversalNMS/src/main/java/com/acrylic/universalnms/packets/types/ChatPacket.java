package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.enums.ChatMessageType;
import com.acrylic.universalnms.packets.SinglePacketWrapper;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public interface ChatPacket extends SinglePacketWrapper {

    void apply(@NotNull String text, @NotNull ChatMessageType chatMessageType);

}
