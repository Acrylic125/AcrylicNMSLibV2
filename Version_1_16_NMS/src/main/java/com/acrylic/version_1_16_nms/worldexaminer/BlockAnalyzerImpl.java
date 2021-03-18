package com.acrylic.version_1_16_nms.worldexaminer;

import com.acrylic.universalnms.worldexaminer.BlockAnalyzer;
import com.acrylic.universalnms.worldexaminer.BoundingBoxExaminerImpl;
import com.acrylic.version_1_16_nms.NMSUtils;
import net.minecraft.server.v1_16_R3.SoundEffect;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class BlockAnalyzerImpl implements BlockAnalyzer {

    private final net.minecraft.server.v1_16_R3.Block nmsBlock;
    private final Block block;
    private BoundingBoxExaminerImpl boundingBoxExaminer;

    public BlockAnalyzerImpl(@NotNull Block block) {
        this.block = block;
        this.nmsBlock = NMSUtils.convertToNMSBlock(block);
    }

    @NotNull
    @Override
    public net.minecraft.server.v1_16_R3.Block getNMSBlock() {
        return this.nmsBlock;
    }

    @NotNull
    @Override
    public Block getBlock() {
        return this.block;
    }

    @NotNull
    @Override
    public BoundingBoxExaminerImpl getBoundingBox() {
        if (boundingBoxExaminer == null) {
            boundingBoxExaminer = new BoundingBoxExaminerImpl();
            boundingBoxExaminer.examine(block);
        }
        return boundingBoxExaminer;
    }

    @Override
    public SoundEffect getStepSound() {
        return nmsBlock.getStepSound(nmsBlock.getBlockData()).getStepSound();
    }

    @Override
    public SoundEffect getBreakSound() {
        return nmsBlock.getStepSound(nmsBlock.getBlockData()).breakSound;
    }

    @Override
    public SoundEffect getPlaceSound() {
        return nmsBlock.getStepSound(nmsBlock.getBlockData()).getPlaceSound();
    }

}
