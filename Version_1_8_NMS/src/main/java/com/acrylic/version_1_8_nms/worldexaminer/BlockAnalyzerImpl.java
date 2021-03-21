package com.acrylic.version_1_8_nms.worldexaminer;

import com.acrylic.universalnms.worldexaminer.BlockAnalyzer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.jetbrains.annotations.Nullable;

public class BlockAnalyzerImpl implements BlockAnalyzer {

    @Override
    public boolean isDoor(@Nullable Material material) {
        return material != null && CraftMagicNumbers.getBlock(material) instanceof BlockDoor;
    }

    @Override
    public boolean isTrapdoor(@Nullable Material material) {
        return material != null && CraftMagicNumbers.getBlock(material) instanceof BlockTrapdoor;
    }

    @Override
    public boolean isFenceGate(@Nullable Material material) {
        return material != null && CraftMagicNumbers.getBlock(material) instanceof BlockFenceGate;
    }

    @Override
    public boolean isFence(@Nullable Material material) {
        return material != null && CraftMagicNumbers.getBlock(material) instanceof BlockFence;
    }

    @Override
    public boolean isCarpet(@Nullable Material material) {
        return material != null && CraftMagicNumbers.getBlock(material) instanceof BlockCarpet;
    }

    @Override
    public boolean isWool(@Nullable Material material) {
        return material == Material.WOOL;
    }

    @Override
    public boolean isClayOrTerracotta(@Nullable Material material) {
        if (material == null)
            return false;
        switch (material) {
            case HARD_CLAY:
            case STAINED_CLAY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isFlower(@Nullable Material material) {
        return material != null && CraftMagicNumbers.getBlock(material) instanceof BlockPlant;
    }
}
