package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universal.utils.keys.BlockKey;
import com.acrylic.universalnms.pathfinder.PathReader;
import com.acrylic.universalnms.pathfinder.PathBlock;
import com.acrylic.universalnms.pathfinder.PathTypeResult;
import com.acrylic.universalnms.pathfinder.Pathfinder;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PathReaderImpl implements PathReader {

    private final Pathfinder pathfinder;
    private final World world;
    private final Map<BlockKey, PathBlock> analyzerMap;
    private final Map<BlockKey, PathTypeResult> pathTypeResultMap;

    public PathReaderImpl(@NotNull Pathfinder pathfinder) {
        this(pathfinder, new HashMap<>(), new HashMap<>());
    }

    public PathReaderImpl(@NotNull Pathfinder pathfinder, @NotNull Map<BlockKey, PathBlock> analyzerMap, @NotNull Map<BlockKey, PathTypeResult> pathTypeResultMap) {
        this.pathfinder = pathfinder;
        this.world = pathfinder.getWorld();
        this.analyzerMap = analyzerMap;
        this.pathTypeResultMap = pathTypeResultMap;
    }

    @NotNull
    @Override
    public Pathfinder getPathfinder() {
        return pathfinder;
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
    public PathBlock getPathBlockAt(int x, int y, int z) {
        Block block = getBlockAt(x, y, z);
        BlockKey blockKey = new BlockKey(block);
        PathBlock blockAnalyzer = analyzerMap.get(blockKey);
        if (blockAnalyzer == null) {
            blockAnalyzer = new PathBlock(block);
            analyzerMap.put(blockKey, blockAnalyzer);
        }
        return blockAnalyzer;
    }

    @Override
    public PathTypeResult getPathTypeResultAt(int x, int y, int z) {
        BlockKey blockKey = new BlockKey(world, x, y, z);
        PathTypeResult result = pathTypeResultMap.get(blockKey);
        if (result == null) {
            result = pathfinder.getPathfinderGenerator().getPathExaminer().examineTo(pathfinder, x, y, z);
            pathTypeResultMap.put(blockKey, result);
        }
        return result;
    }
}
