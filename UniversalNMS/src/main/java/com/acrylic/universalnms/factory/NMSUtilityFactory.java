package com.acrylic.universalnms.factory;

import com.acrylic.universalnms.nbt.NBTEntity;
import com.acrylic.universalnms.nbt.NBTItem;
import com.acrylic.universalnms.nbt.NBTTileEntity;
import com.acrylic.universalnms.particles.ColorParticles;
import com.acrylic.universalnms.particles.ItemParticles;
import com.acrylic.universalnms.particles.Particles;
import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface NMSUtilityFactory {

    ChunkExaminer getNewChunkExaminer(@NotNull ChunkSnapshot chunkSnapshot);

    ChunkExaminer getNewChunkExaminer(@NotNull Chunk chunk);

    NBTItem getNBTItem(@NotNull ItemStack item);

    NBTEntity getNBTEntity(@NotNull Entity entity);

    NBTTileEntity getNBTTileEntity(@NotNull Block entity);

    Particles getNewParticles();

    ColorParticles getNewColorParticles();

    ItemParticles getNewItemParticles();

}
