package com.acrylic.version_1_8_nms.factory;

import com.acrylic.universalnms.factory.NMSUtilityFactory;
import com.acrylic.universalnms.nbt.NBTEntity;
import com.acrylic.universalnms.nbt.NBTItem;
import com.acrylic.universalnms.nbt.NBTTileEntity;
import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import com.acrylic.version_1_8_nms.worldexaminer.ChunkExaminerImpl;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class NMSUtilityFactoryImpl implements NMSUtilityFactory {

    @Override
    public ChunkExaminer getNewChunkExaminer(@NotNull ChunkSnapshot chunkSnapshot) {
        return new ChunkExaminerImpl(chunkSnapshot);
    }

    @Override
    public ChunkExaminer getNewChunkExaminer(@NotNull Chunk chunk) {
        return new ChunkExaminerImpl(chunk);
    }

    @Override
    public NBTItem getNBTItem(@NotNull ItemStack item) {
        return null;
    }

    @Override
    public NBTEntity getNBTEntity(@NotNull Entity entity) {
        return null;
    }

    @Override
    public NBTTileEntity getNBTTileEntity(@NotNull Block entity) {
        return null;
    }
}
