package com.acrylic.version_1_16_nms.factory;

import com.acrylic.universalnms.factory.NMSUtilityFactory;
import com.acrylic.universalnms.json.JSONComponent;
import com.acrylic.universalnms.json.JSONComponentImpl;
import com.acrylic.universalnms.worldexaminer.BoundingBoxExaminer;
import com.acrylic.universalnms.nbt.NBTEntity;
import com.acrylic.universalnms.nbt.NBTItem;
import com.acrylic.universalnms.nbt.NBTTileEntity;
import com.acrylic.universalnms.particles.ColorParticles;
import com.acrylic.universalnms.particles.ItemParticles;
import com.acrylic.universalnms.particles.Particles;
import com.acrylic.universalnms.worldexaminer.BukkitBoundingBoxExaminerImpl;
import com.acrylic.universalnms.worldexaminer.ChunkExaminer;
import com.acrylic.universalnms.worldexaminer.ChunkExaminerImpl;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.nbt.NBTEntityImpl;
import com.acrylic.version_1_16_nms.nbt.NBTItemImpl;
import com.acrylic.version_1_16_nms.nbt.NBTTileEntityImpl;
import com.acrylic.version_1_16_nms.partivles.ColorParticlesImpl;
import com.acrylic.version_1_16_nms.partivles.ItemParticlesImpl;
import com.acrylic.version_1_16_nms.partivles.ParticlesImpl;
import com.acrylic.version_1_16_nms.worldexaminer.BoundingBoxExaminerImpl;
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
    public NBTItem getNewNBTItem(@NotNull ItemStack item, boolean saveTag) {
        return new NBTItemImpl(item, saveTag);
    }

    @Override
    public NBTEntity getNewNBTEntity(@NotNull Entity entity) {
        return new NBTEntityImpl(entity);
    }

    @Override
    public NBTTileEntity getNewNBTTileEntity(@NotNull Block entity) {
        return new NBTTileEntityImpl(entity);
    }

    @Override
    public Particles getNewParticles() {
        return new ParticlesImpl();
    }

    @Override
    public ColorParticles getNewColorParticles() {
        return new ColorParticlesImpl();
    }

    @Override
    public ItemParticles getNewItemParticles() {
        return new ItemParticlesImpl();
    }

    @Override
    public BoundingBoxExaminer getNewBoundingBoxExaminer() {
        return new BoundingBoxExaminerImpl();
    }

    @Override
    public Object getBlockStepSound(@NotNull Block block) {
        net.minecraft.server.v1_16_R3.Block nmsBlock = NMSUtils.convertToNMSBlock(block);
        return nmsBlock.getStepSound(nmsBlock.getBlockData()).getStepSound();
    }

    @Override
    public Object getBlockBreakSound(@NotNull Block block) {
        net.minecraft.server.v1_16_R3.Block nmsBlock = NMSUtils.convertToNMSBlock(block);
        return nmsBlock.getStepSound(nmsBlock.getBlockData()).breakSound;
    }

    @Override
    public Object getBlockPlaceSound(@NotNull Block block) {
        net.minecraft.server.v1_16_R3.Block nmsBlock = NMSUtils.convertToNMSBlock(block);
        return nmsBlock.getStepSound(nmsBlock.getBlockData()).getStepSound();
    }

    @Override
    public JSONComponent getNewJSONComponent(@NotNull String text) {
        return new JSONComponentImpl(text);
    }
}
