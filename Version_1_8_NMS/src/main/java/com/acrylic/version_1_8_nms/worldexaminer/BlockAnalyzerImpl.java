package com.acrylic.version_1_8_nms.worldexaminer;

import com.acrylic.universal.blocks.MCBlockData;
import com.acrylic.universalnms.worldexaminer.BlockAnalyzer;
import com.acrylic.version_1_8_nms.NMSUtils;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class BlockAnalyzerImpl implements BlockAnalyzer {

    private final net.minecraft.server.v1_8_R3.Block nmsBlock;
    private final Block block;
    private BoundingBoxExaminerImpl boundingBoxExaminer;

    public BlockAnalyzerImpl(@NotNull Block block) {
        this.block = block;
        this.nmsBlock = NMSUtils.convertToNMSBlock(block);
    }

    @Override
    public MCBlockData getBlockData() {
        return null;
    }

    @NotNull
    @Override
    public net.minecraft.server.v1_8_R3.Block getNMSBlock() {
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
    public String getStepSound() {
        return nmsBlock.stepSound.getStepSound();
    }

    @Override
    public String getBreakSound() {
        return nmsBlock.stepSound.getBreakSound();
    }

    @Override
    public String getPlaceSound() {
        return nmsBlock.stepSound.getPlaceSound();
    }

}
