package com.acrylic.universalnms.worldexaminer;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ChunkKeyWorldBlockReader implements WorldBlockReader {

    private final Map<Chunk, ChunkExaminer> chunkExaminerMap = new HashMap<>();


    @NotNull
    @Override
    public World getWorld() {
        return null;
    }

    @NotNull
    @Override
    public Collection<ChunkExaminer> getReadChunks() {
        return null;
    }

    @NotNull
    @Override
    public ChunkExaminer getChunkExaminerByChunk(@NotNull Chunk chunk) {
        return null;
    }
}
