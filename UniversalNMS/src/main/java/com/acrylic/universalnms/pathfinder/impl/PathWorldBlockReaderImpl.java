package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universal.utils.keys.BlockKey;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.pathfinder.PathWorldBlockReader;
import com.acrylic.universalnms.pathfinder.StaticPathBlock;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PathWorldBlockReaderImpl implements PathWorldBlockReader {

    private final World world;
    private final Map<BlockKey, StaticPathBlock> analyzerMap;

    public PathWorldBlockReaderImpl(@NotNull World world) {
        this(world, new HashMap<>());
    }

    public PathWorldBlockReaderImpl(@NotNull World world, @NotNull Map<BlockKey, StaticPathBlock> analyzerMap) {
        this.world = world;
        this.analyzerMap = analyzerMap;
    }

    @NotNull
    @Override
    public World getWorld() {
        return world;
    }

    @NotNull
    @Override
    public Block getBlockAt(int x, int y, int z) {
        return world.getBlockAt(x, y, z);
    }

    @Override
    public StaticPathBlock getStaticPathBlockAt(int x, int y, int z) {
        Block block = getBlockAt(x, y, z);
        BlockKey blockKey = new BlockKey(block);
        StaticPathBlock blockAnalyzer = analyzerMap.get(blockKey);
        if (blockAnalyzer == null) {
            blockAnalyzer = new StaticPathBlock(block);
            analyzerMap.put(blockKey, blockAnalyzer);
        }
        return blockAnalyzer;
    }
}
