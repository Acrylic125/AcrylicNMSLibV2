package com.acrylic.universalnms.worldexaminer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BlockAnalyzer {

    boolean isDoor(@Nullable Material material);

    default boolean isDoor(@NotNull Block block) {
        return isDoor(block.getType());
    }

    boolean isTrapdoor(@Nullable Material material);

    default boolean isTrapdoor(@NotNull Block block) {
        return isTrapdoor(block.getType());
    }

    boolean isFenceGate(@Nullable Material material);

    default boolean isFenceGate(@NotNull Block block) {
        return isFenceGate(block.getType());
    }

    default boolean isAnyDoor(@Nullable Material material) {
        return isDoor(material) || isTrapdoor(material) || isFenceGate(material);
    }

    default boolean isAnyDoor(@NotNull Block block) {
        return isAnyDoor(block.getType());
    }

    boolean isFence(@Nullable Material material);

    default boolean isFence(@NotNull Block block) {
        return isFence(block.getType());
    }

    boolean isCarpet(@Nullable Material material);

    default boolean isCarpet(@NotNull Block block) {
        return isCarpet(block.getType());
    }

    boolean isWool(@Nullable Material material);

    default boolean isWool(@NotNull Block block) {
        return isCarpet(block.getType());
    }

    boolean isClayOrTerracotta(@Nullable Material material);

    default boolean isClayOrTerracotta(@NotNull Block block) {
        return isClayOrTerracotta(block.getType());
    }

    boolean isFlower(@Nullable Material material);

    default boolean isFlower(@NotNull Block block) {
        return isFlower(block.getType());
    }

}
