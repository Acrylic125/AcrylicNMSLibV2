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

    PathBlock getPathBlockAt(int x, int y, int z);

    default PathBlock getPathBlockAt(double x, double y, double z) {
        return getPathBlockAt((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    default PathBlock getPathBlockAt(@NotNull Location location) {
        return getPathBlockAt(location.getX(), location.getY(), location.getZ());
    }

    default PathBlock getPathBlockAt(@NotNull Block block) {
        return getPathBlockAt(block.getX(), block.getY(), block.getZ());
    }

}
