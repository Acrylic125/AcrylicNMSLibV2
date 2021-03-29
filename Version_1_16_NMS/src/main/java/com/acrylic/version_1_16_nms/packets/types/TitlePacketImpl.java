package com.acrylic.version_1_16_nms.packets.types;

import com.acrylic.universalnms.enums.TitleType;
import com.acrylic.universalnms.packets.types.TitlePacket;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.packets.MultiPacketWrapperImpl;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class TitlePacketImpl
        extends MultiPacketWrapperImpl
        implements TitlePacket {

    private final Collection<PacketPlayOutTitle> titles = new ArrayList<>();

    @Override
    public TitlePacket attachFullTitle(@Nullable String title, @Nullable String subTitle, int fadeInTicks, int durationTicks, int fadeOutTicks) {
        if (title != null)
            titles.add(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, NMSUtils.toChatComponent(title), fadeInTicks, durationTicks, fadeOutTicks));
        if (subTitle != null)
            titles.add(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, NMSUtils.toChatComponent(subTitle), fadeInTicks, durationTicks, fadeOutTicks));
        return this;
    }

    @Override
    public TitlePacket attach(@Nullable String text, @NotNull TitleType titleType, int fadeInTicks, int durationTicks, int fadeOutTicks) {
        titles.add(new PacketPlayOutTitle(toNMSTitleType(titleType), (text == null) ? null : NMSUtils.toChatComponent(text), fadeInTicks, durationTicks, fadeOutTicks));
        return this;
    }

    @Override
    public TitlePacket attach(@Nullable String text, @NotNull TitleType titleType) {
        titles.add(new PacketPlayOutTitle(toNMSTitleType(titleType), (text == null) ? null : NMSUtils.toChatComponent(text)));
        return this;
    }

    @Override
    public Collection<? extends Packet<?>> getPackets() {
        return titles;
    }

    private static PacketPlayOutTitle.EnumTitleAction toNMSTitleType(TitleType titleType) {
        switch (titleType) {
            case CLEAR: return PacketPlayOutTitle.EnumTitleAction.CLEAR;
            case TITLE: return PacketPlayOutTitle.EnumTitleAction.TITLE;
            case RESET: return PacketPlayOutTitle.EnumTitleAction.RESET;
            case TIMES: return PacketPlayOutTitle.EnumTitleAction.TIMES;
            case SUBTITLE: return PacketPlayOutTitle.EnumTitleAction.SUBTITLE;
            case ACTIONBAR: return PacketPlayOutTitle.EnumTitleAction.ACTIONBAR;
            default: return null;
        }
    }
}
