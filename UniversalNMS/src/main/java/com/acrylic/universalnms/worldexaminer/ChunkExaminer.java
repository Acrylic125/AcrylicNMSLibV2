package com.acrylic.universalnms.worldexaminer;

import com.acrylic.universal.blocks.MCBlockData;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ChunkExaminer {

    int getChunkX();

    int getChunkZ();

    @NotNull
    ChunkSnapshot getSnapshot();

    @Nullable
    MCBlockData getBlockDataRelativeToChunk(int relativeX, int y, int relativeZ);

    @Nullable
    default MCBlockData getBlockDataAt(int blockX, int blockY, int blockZ) {
        return getBlockDataRelativeToChunk(toRelativeChunkXZValue(blockX), blockY, toRelativeChunkXZValue(blockZ));
    }

    @Nullable
    default MCBlockData getBlockDataAt(@NotNull Block block) {
        return getBlockDataAt(block.getX(), block.getY(), block.getZ());
    }

    @Nullable
    default MCBlockData getBlockDataAt(@NotNull Location location) {
        return getBlockDataAt((int) Math.floor(location.getX()), (int) Math.floor(location.getY()), (int) Math.floor(location.getZ()));
    }

    static int toRelativeChunkXZValue(int v) {
        return v & 0xf;
    }

}
