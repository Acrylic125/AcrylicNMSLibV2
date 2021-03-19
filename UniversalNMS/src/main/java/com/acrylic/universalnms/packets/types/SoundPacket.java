package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.enums.SoundCategory;
import com.acrylic.universalnms.packets.SinglePacketWrapper;
import math.ProbabilityKt;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public interface SoundPacket extends SinglePacketWrapper {

    default void apply(@NotNull Object sound, @NotNull Location location, float volume, float pitch) {
        apply(sound, SoundCategory.MASTER, location, volume, pitch);
    }

    void apply(@NotNull Object sound, @NotNull SoundCategory soundCategory, @NotNull Location location, float volume, float pitch);

    default void applyBreakSound(@NotNull Block block, float volume, float pitch) {
        apply(NMSLib.getNMSUtilityFactory().getBlockBreakSound(block), SoundCategory.BLOCKS, block.getLocation(), volume, pitch);
    }

    default void applyBreakSound(@NotNull Block block, float volume) {
        applyBreakSound(block, volume, 1);
    }

    default void applyBreakSound(@NotNull Block block) {
        applyBreakSound(block, 1f, ProbabilityKt.getRandom(0.8f, 0.9f));
    }

    default void applyStepSound(@NotNull Block block, float volume, float pitch) {
        apply(NMSLib.getNMSUtilityFactory().getBlockStepSound(block), SoundCategory.PLAYERS, block.getLocation(), volume, pitch);
    }

    default void applyStepSound(@NotNull Block block, float volume) {
        applyStepSound(block, volume, 1);
    }

    default void applyStepSound(@NotNull Block block) {
        applyStepSound(block, 1);
    }

    default void applyPlaceSound(@NotNull Block block, float volume, float pitch) {
        apply(NMSLib.getNMSUtilityFactory().getBlockPlaceSound(block), SoundCategory.BLOCKS, block.getLocation(), volume, pitch);
    }

    default void applyPlaceSound(@NotNull Block block, float volume) {
        applyPlaceSound(block, volume, 1);
    }

    default void applyPlaceSound(@NotNull Block block) {
        applyPlaceSound(block, 1);
    }

}
