package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.enums.TitleType;
import com.acrylic.universalnms.packets.MultiPacketWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TitlePacket extends MultiPacketWrapper {

    TitlePacket attachFullTitle(@Nullable String title, @Nullable String subTitle, int fadeInTicks, int durationTicks, int fadeOutTicks);

    default void applyFullTitle(@Nullable String title, @Nullable String subTitle, int fadeInTicks, int durationTicks, int fadeOutTicks) {
        reset();
        attachFullTitle(title, subTitle, fadeInTicks, durationTicks, fadeOutTicks);
    }

    default void applyTitle(@NotNull String title, int fadeInTicks, int durationTicks, int fadeOutTicks) {
        applyFullTitle(title, null, fadeInTicks, durationTicks, fadeOutTicks);
    }

    default void applyTitle(@NotNull String title) {
        apply(title, TitleType.TITLE);
    }

    default void applySubTitle(@NotNull String subTitle, int fadeInTicks, int durationTicks, int fadeOutTicks) {
        applyFullTitle(null, subTitle, fadeInTicks, durationTicks, fadeOutTicks);
    }

    default void applySubTitle(@NotNull String subTitle) {
        apply(subTitle, TitleType.SUBTITLE);
    }

    default void applyClear() {
        apply(TitleType.CLEAR);
    }

    default void applyReset() {
        apply(TitleType.RESET);
    }

    TitlePacket attach(@Nullable String text, @NotNull TitleType titleType, int fadeInTicks, int durationTicks, int fadeOutTicks);

    TitlePacket attach(@Nullable String text, @NotNull TitleType titleType);

    default void apply(@Nullable String text, @NotNull TitleType titleType, int fadeInTicks, int durationTicks, int fadeOutTicks) {
        reset();
        attach(text, titleType, fadeInTicks, durationTicks, fadeOutTicks);
    }

    default void apply(@Nullable String text, @NotNull TitleType titleType) {
        reset();
        attach(text, titleType);
    }

    default void apply(@NotNull TitleType titleType) {
        apply(null, titleType);
    }


}
