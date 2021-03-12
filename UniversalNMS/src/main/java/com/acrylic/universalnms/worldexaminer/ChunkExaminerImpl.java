package com.acrylic.universalnms.worldexaminer;

import com.acrylic.universal.blocks.BlockData;
import com.acrylic.universal.blocks.MCBlockData;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkExaminerImpl implements ChunkExaminer {

    private final ChunkSnapshot chunkSnapshot;

    public ChunkExaminerImpl(@NotNull ChunkSnapshot chunkSnapshot) {
        this.chunkSnapshot = chunkSnapshot;
    }

    public ChunkExaminerImpl(@NotNull Chunk chunk) {
        this(chunk.getChunkSnapshot());
    }

    @Override
    public int getChunkX() {
        return chunkSnapshot.getX();
    }

    @Override
    public int getChunkZ() {
        return chunkSnapshot.getZ();
    }

    @NotNull
    @Override
    public ChunkSnapshot getSnapshot() {
        return chunkSnapshot;
    }

    @Nullable
    @Override
    public MCBlockData getBlockDataRelativeToChunk(int relativeX, int y, int relativeZ) {
        return (y < 0 || y > 255) ? null : new BlockData(chunkSnapshot.getBlockType(relativeX, y, relativeZ));
    }
}
