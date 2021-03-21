package com.acrylic.universalnms.pathfinder;

import com.acrylic.universal.utils.BitMaskUtils;
import com.acrylic.universalnms.pathfinder.impl.PathWorldBlockReaderImpl;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PathTypeResultByHeightImpl implements PathTypeResult {

    private final float heightRequirement;
    private final float x ,y, z;
    private final float minYToScan, maxYToScan;
    private final World world;
    private float passableX, passableY, passableZ;
    private final PathWorldBlockReader pathWorldBlockReader;
    private PathType pathType;

    public PathTypeResultByHeightImpl(@NotNull Pathfinder pathfinder, float x, float y, float z) {
        PathfinderGenerator generator = pathfinder.getPathfinderGenerator();
        this.heightRequirement = generator.getMinimumYToTraverse();
        this.pathWorldBlockReader = pathfinder.getPathWorldBlockReader();
        this.minYToScan = y - generator.getMaximumDropHeight();
        this.maxYToScan = y + generator.getMaximumHeight();
        this.x = x;
        this.y = y;
        this.z = z;
        this.passableX = x;
        this.passableY = y;
        this.passableZ = z;
        this.world = pathfinder.getWorld();
    }

    //Only for testing. Do not use!
    @Deprecated
    public PathTypeResultByHeightImpl(float heightRequirement, float minYToScan, float maxYToScan, World world, float x, float y, float z) {
        this.heightRequirement = heightRequirement;
        this.pathWorldBlockReader = new PathWorldBlockReaderImpl(world);
        this.minYToScan = y - minYToScan;
        this.maxYToScan = y + maxYToScan;
        this.x = x;
        this.y = y;
        this.z = z;
        this.passableX = x;
        this.passableY = y;
        this.passableZ = z;
        this.world = world;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @NotNull
    public PathWorldBlockReader getPathWorldBlockReader() {
        return pathWorldBlockReader;
    }

    /**
     * 0x01 - Bypass (HIGHEST PRIORITY)
     * 0x02 - Swim (HIGH PRIORITY)
     * 0x04 - Climb (MEDIUM PRIORITY)
     * ...
     * 0x0 - None (LOWEST PRIORITY)
     *
     * @param pathExaminer The examiner.
     */
    @Override
    public void examineWith(@NotNull PathExaminer pathExaminer) {
        int floorMinY = (int) Math.floor(minYToScan),
            ceilMaxY = (int) Math.ceil(maxYToScan),
            arrIndex = 0;
        //Array that will be used to determine which path type.
        PathType[] pathTypes = new PathType[ceilMaxY - floorMinY + 1];
        float startingY = minYToScan,
              checkpointStartY = minYToScan;
        boolean shouldSetStartingY = true, isFirstSetY = true;
        int contains = 0x0;
        //A counter to count the streak of ladders. This will be used to determine if this is a climbable path type.
        //int climbStreak = 0;
        for (int y = floorMinY; y <= ceilMaxY; y++) {
            PathBlock pathBlock = pathWorldBlockReader.getPathBlockAt(x, y, z);
            PathType pathType = pathExaminer.getPathTypeOfBlock(pathBlock);
            if (pathType == null) {
                if (shouldSetStartingY) {
                    startingY = (float) pathBlock.getCollisionBoundingBoxExaminer().getMaxY();
                    shouldSetStartingY = false;
                }
            }
            pathTypes[arrIndex] = pathType;

            arrIndex++;
        }
    }

    @Nullable
    @Override
    public PathType getPathType() {
        return pathType;
    }

    @Override
    public float getResultX() {
        return passableX;
    }

    @Override
    public float getResultY() {
        return passableY;
    }

    @Override
    public float getResultZ() {
        return passableZ;
    }

}
