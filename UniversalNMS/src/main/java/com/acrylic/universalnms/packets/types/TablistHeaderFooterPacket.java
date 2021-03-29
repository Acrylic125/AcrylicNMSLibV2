package com.acrylic.universalnms.packets.types;

import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.packets.SinglePacketWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TablistHeaderFooterPacket extends SinglePacketWrapper {

    //Empty string.
    String EMPTY = "\"\"";

    default void applyHeader(@NotNull String header) {
        applyHeaderAndFooter(header, null);
    }

    default void applyHeader(@NotNull String... header) {
        applyHeader(toStringFromArray(header));
    }

    default void applyFooter(@NotNull String footer) {
        applyHeaderAndFooter(null, footer);
    }

    default void applyFooter(@NotNull String... footer) {
        applyFooter(toStringFromArray(footer));
    }

    static String toStringFromArray(@NotNull String[] str) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0, m = str.length;
        for (String s : str) {
            stringBuilder.append(ChatUtils.get(s));
            if (i >= m - 1)
                break;
            stringBuilder.append("\n");
            i++;
        }
        return stringBuilder.toString();
    }

    void applyHeaderAndFooter(@Nullable String header, @Nullable String footer);

    default void applyHeaderAndFooter(@Nullable String[] header, @Nullable String[] footer) {
        applyHeaderAndFooter((header == null) ? null : toStringFromArray(header),
                (footer == null) ? null : toStringFromArray(footer));
    }

}
