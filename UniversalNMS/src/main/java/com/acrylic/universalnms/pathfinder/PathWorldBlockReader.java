package com.acrylic.universalnms.pathfinder;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public interface PathWorldBlockReader {

    @NotNull
    World getWorld();

    @NotNull
    Block getBlockAt(int x, int y, int z);

    default Block getBlockAt(double x, double y, double z) {
        return getBlockAt((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    default Block getBlockAt(@NotNull Location location) {
        return getBlockAt(location.getX(), location.getY(), location.getZ());
    }

    default Block getBlockAt(@NotNull Block block) {
        return getBlockAt(block.getX(), block.getY(), block.getZ());
    }

    BlockAnalyzer getBlockAnalyzerAt(int x, int y, int z);

    default BlockAnalyzer getBlockAnalyzerAt(double x, double y, double z) {
        return getBlockAnalyzerAt((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    default BlockAnalyzer getBlockAnalyzerAt(@NotNull Location location) {
        return getBlockAnalyzerAt(location.getX(), location.getY(), location.getZ());
    }

    default BlockAnalyzer getBlockAnalyzerAt(@NotNull Block block) {
        return getBlockAnalyzerAt(block.getX(), block.getY(), block.getZ());
    }

}
