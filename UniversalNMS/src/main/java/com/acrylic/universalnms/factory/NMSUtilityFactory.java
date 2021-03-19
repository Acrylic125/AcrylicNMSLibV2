package com.acrylic.universalnms.factory;

import com.acrylic.universalnms.misc.BoundingBoxExaminer;
import com.acrylic.universalnms.nbt.NBTEntity;
import com.acrylic.universalnms.nbt.NBTItem;
import com.acrylic.universalnms.nbt.NBTTileEntity;
import com.acrylic.universalnms.particles.ColorParticles;
import com.acrylic.universalnms.particles.ItemParticles;
import com.acrylic.universalnms.particles.Particles;
import com.acrylic.universalnms.worldexaminer.BlockAnalyzer;
import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface NMSUtilityFactory {

    ChunkExaminer getNewChunkExaminer(@NotNull ChunkSnapshot chunkSnapshot);

    ChunkExaminer getNewChunkExaminer(@NotNull Chunk chunk);

    default NBTItem getNewNBTItem(@NotNull ItemStack item) {
        return getNewNBTItem(item, false);
    }

    NBTItem getNewNBTItem(@NotNull ItemStack item, boolean saveTag);

    NBTEntity getNewNBTEntity(@NotNull Entity entity);

    NBTTileEntity getNewNBTTileEntity(@NotNull Block entity);

    Particles getNewParticles();

    ColorParticles getNewColorParticles();

    ItemParticles getNewItemParticles();

    BlockAnalyzer getNewBlockAnalyzer(@NotNull Block block);

    default BlockAnalyzer getNewBlockAnalyzer(@NotNull Location location) {
        return getNewBlockAnalyzer(location.getBlock());
    }

    default BoundingBoxExaminer getNewBoundingBoxExaminer(@NotNull Entity entity) {
        BoundingBoxExaminer boundingBoxExaminer = getNewBoundingBoxExaminer();
        boundingBoxExaminer.examine(entity);
        return boundingBoxExaminer;
    }

    default BoundingBoxExaminer getNewBoundingBoxExaminer(@NotNull Block block) {
        BoundingBoxExaminer boundingBoxExaminer = getNewBoundingBoxExaminer();
        boundingBoxExaminer.examine(block);
        return boundingBoxExaminer;
    }

    default BoundingBoxExaminer getNewBoundingBoxExaminer(@NotNull Location location) {
        BoundingBoxExaminer boundingBoxExaminer = getNewBoundingBoxExaminer();
        boundingBoxExaminer.examine(location);
        return boundingBoxExaminer;
    }

    default BoundingBoxExaminer getNewBoundingBoxExaminer(@NotNull World world, int x, int y, int z) {
        BoundingBoxExaminer boundingBoxExaminer = getNewBoundingBoxExaminer();
        boundingBoxExaminer.examine(world, x, y, z);
        return boundingBoxExaminer;
    }

    default BoundingBoxExaminer getNewBoundingBoxExaminer(@NotNull World world, double x, double y, double z) {
        BoundingBoxExaminer boundingBoxExaminer = getNewBoundingBoxExaminer();
        boundingBoxExaminer.examine(world, x, y, z);
        return boundingBoxExaminer;
    }

    BoundingBoxExaminer getNewBoundingBoxExaminer();

}
