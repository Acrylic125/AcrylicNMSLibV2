package com.acrylic.universalnms.worldexaminer;

import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface WorldBlockReader {

    @NotNull
    World getWorld();

    @NotNull
    Collection<ChunkExaminer> getReadChunks();

    @NotNull
    ChunkExaminer getChunkExaminerByChunk(@NotNull Chunk chunk);

    @NotNull
    default ChunkExaminer getChunkExaminerByChunk(int x, int z) {
        return getChunkExaminerByChunk(getWorld().getChunkAt(x >> 4, z >> 4));
    }

    @NotNull
    default ChunkExaminer getChunkExaminerAt(int x, int y, int z) {
        return getChunkExaminerByChunk(x, z);
    }

    @NotNull
    default ChunkExaminer getChunkExaminerAt(float x, float y, float z) {
        return getChunkExaminerAt((int) x, (int) y, (int) z);
    }

    @NotNull
    default ChunkExaminer getChunkExaminerAt(@NotNull Location location) {
        return getChunkExaminerByChunk(location.getChunk());
    }

    @NotNull
    default ChunkExaminer getChunkExaminerAt(@NotNull Block block) {
        return getChunkExaminerByChunk(block.getChunk());
    }

}
