package com.acrylic.version_1_16_nms.worldexaminer;

import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.worldexaminer.BlockAnalyzer;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.jetbrains.annotations.Nullable;

public class BlockAnalyzerImpl implements BlockAnalyzer {

    @Override
    public boolean isDoor(@Nullable Material material) {
        return CraftMagicNumbers.getBlock(material) instanceof BlockDoor;
    }

    @Override
    public boolean isTrapdoor(@Nullable Material material) {
        return CraftMagicNumbers.getBlock(material) instanceof BlockTrapdoor;
    }

    @Override
    public boolean isFenceGate(@Nullable Material material) {
        return CraftMagicNumbers.getBlock(material) instanceof BlockFenceGate;
    }

    @Override
    public boolean isFence(@Nullable Material material) {
        return CraftMagicNumbers.getBlock(material) instanceof BlockFence;
    }

    @Override
    public boolean isCarpet(@Nullable Material material) {
        return CraftMagicNumbers.getBlock(material) instanceof BlockCarpet;
    }

    @Override
    public boolean isWool(@Nullable Material material) {
        if (material == null)
            return false;
        switch (material) {
            case BLACK_WOOL:
            case BLUE_WOOL:
            case BROWN_WOOL:
            case CYAN_WOOL:
            case GRAY_WOOL:
            case GREEN_WOOL:
            case WHITE_WOOL:
            case LIME_WOOL:
            case MAGENTA_WOOL:
            case ORANGE_WOOL:
            case PINK_WOOL:
            case PURPLE_WOOL:
            case RED_WOOL:
            case YELLOW_WOOL:
            case LIGHT_BLUE_WOOL:
            case LIGHT_GRAY_WOOL:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isClayOrTerracotta(@Nullable Material material) {
        if (material == null)
            return false;
        switch (material) {
            case TERRACOTTA:
            case BLACK_TERRACOTTA:
            case GRAY_TERRACOTTA:
            case BLUE_TERRACOTTA:
            case CYAN_TERRACOTTA:
            case BROWN_TERRACOTTA:
            case BLACK_GLAZED_TERRACOTTA:
            case BLUE_GLAZED_TERRACOTTA:
            case BROWN_GLAZED_TERRACOTTA:
            case LIME_TERRACOTTA:
            case CYAN_GLAZED_TERRACOTTA:
            case WHITE_TERRACOTTA:
            case ORANGE_TERRACOTTA:
            case MAGENTA_TERRACOTTA:
            case LIGHT_BLUE_TERRACOTTA:
            case YELLOW_TERRACOTTA:
            case PINK_TERRACOTTA:
            case LIGHT_GRAY_TERRACOTTA:
            case PURPLE_TERRACOTTA:
            case GREEN_TERRACOTTA:
            case RED_TERRACOTTA:
            case GRAY_GLAZED_TERRACOTTA:
            case GREEN_GLAZED_TERRACOTTA:
            case LIGHT_BLUE_GLAZED_TERRACOTTA:
            case LIGHT_GRAY_GLAZED_TERRACOTTA:
            case LIME_GLAZED_TERRACOTTA:
            case MAGENTA_GLAZED_TERRACOTTA:
            case ORANGE_GLAZED_TERRACOTTA:
            case PINK_GLAZED_TERRACOTTA:
            case PURPLE_GLAZED_TERRACOTTA:
            case RED_GLAZED_TERRACOTTA:
            case WHITE_GLAZED_TERRACOTTA:
            case YELLOW_GLAZED_TERRACOTTA:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isFlower(@Nullable Material material) {
        return CraftMagicNumbers.getBlock(material) instanceof BlockPlant;
    }
}
