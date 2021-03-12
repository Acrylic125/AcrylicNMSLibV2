package com.acrylic.version_1_8_nms.worldexaminer;

import com.acrylic.universal.blocks.MCBlockData;
import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import com.acrylic.version_1_8.blocks.BlockData;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
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
        return (y < 0 || y > 255) ? null : new BlockData(Material.getMaterial(chunkSnapshot.getBlockTypeId(relativeX, y, relativeZ)), (byte) chunkSnapshot.getBlockData(relativeX, y, relativeZ));
    }
}
