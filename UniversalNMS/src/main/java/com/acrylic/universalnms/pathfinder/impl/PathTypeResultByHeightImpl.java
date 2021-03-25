package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universal.utils.BitMaskUtils;
import com.acrylic.universalnms.pathfinder.*;
import com.acrylic.universalnms.pathfinder.impl.PathWorldBlockReaderImpl;
import com.acrylic.universalnms.worldexaminer.BoundingBoxExaminer;
import org.bukkit.Bukkit;
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
     *
     * Contains:
     * 0x01 - Bypass (HIGHEST PRIORITY)
     * 0x02 - Swim (HIGH PRIORITY)
     * 0x04 - Climb (MEDIUM PRIORITY)
     * ...
     * 0x0 - None (LOWEST PRIORITY)
     *
     * Scanner:
     * 0x01 - Done start
     * 0x02 - Done end
     * 0x0 - None
     *
     * @param pathExaminer The examiner.
     */
    @Override
    public void examineWith(@NotNull PathExaminer pathExaminer) {
        int floorMinY = (int) Math.floor(minYToScan),
            ceilMaxY = (int) Math.ceil(maxYToScan);
        if (maxYToScan < minYToScan)
            throw new IllegalStateException("The max scan must be > than the min scan.");
        float startingY = minYToScan, endingY = maxYToScan + 1,
              checkpointStartY = minYToScan;
        int contains = 0x0, scanner = 0x0;
        //The check state determines the status of scanning.
        //0 = Bottom most block, 1 = Seeking for top most block.
        byte checkState = 0;
        //A counter to count the streak of ladders. This will be used to determine if this is a climbable path type.
        //int climbStreak = 0;
        for (int y = floorMinY; y <= ceilMaxY; y++) {
            PathBlock pathBlock = pathWorldBlockReader.getPathBlockAt(x, y, z);
            PathType pathType = pathExaminer.getPathTypeOfBlock(pathBlock);
            BoundingBoxExaminer collisionBox = pathBlock.getCollisionBoundingBoxExaminer();
            boolean isEmpty = collisionBox.isEmpty(),
                    shouldInitializeNewStart = false;
            if (pathType == null) {
                if (checkState == 0) {
                    shouldInitializeNewStart = true;
                } else {
                    if ((scanner & 0x02) == 0x02) {
                        float checkpointEndY = (isEmpty) ? y + 1 : (float) collisionBox.getMinY();
                        if ((checkpointEndY - checkpointStartY) > (endingY - startingY)) {
                            startingY = checkpointStartY;
                            endingY = checkpointEndY;
                        }
                    } else {
                        scanner |= 0x02;
                        endingY = (isEmpty) ? y + 1 : (float) collisionBox.getMinY();
                    }
                    checkState = 0;
                }
            } else {
                if ((scanner & 0x01) == 0x01)
                    checkState = 1;
                switch (pathType) {
                    case BYPASS:
                        contains |= 0x01;
                    case SWIM:
                        contains |= 0x02;
                        if (checkState == 0) {
                            checkState = 1;
                            shouldInitializeNewStart = true;
                        }
                    case CLIMB:
                        contains |= 0x04;
                        if (checkState == 0) {
                            checkState = 1;
                            shouldInitializeNewStart = true;
                        }
                }
            }
            if (shouldInitializeNewStart) {
                if ((scanner & 0x03) == 0x03) {
                    // Bukkit.broadcastMessage("Set checkpoint at y= " + y);
                    checkpointStartY = (isEmpty) ? y : (float) collisionBox.getMaxY();
                } else {
                    scanner |= 0x01;
                    // Bukkit.broadcastMessage("isEMpty " + isEmpty + " " + collisionBox.getMaxY());
                    startingY = (isEmpty) ? y : (float) collisionBox.getMaxY();
                    checkpointStartY = startingY;
                }
            }
        }
        //To check if the end to start difference meets the height requirement.
        //Also checks if there is a base by checking if the scanner found any base.
        if (((endingY - startingY) < heightRequirement) || (scanner & 0x01) != 0x01) {
            //Bukkit.broadcastMessage(" " + x + " " + startingY + " " + z + " " + startingY + " p " + endingY + " " + ((endingY - startingY) < heightRequirement) + " vs " + ((scanner & 0x01) != 0x01));
            return;
        }
        float floorX = (float) Math.floor(x), floorZ = (float) Math.floor(z);
        this.passableX = (floorX + (floorX + 1)) / 2;
        this.passableZ = (floorZ + (floorZ + 1)) / 2;
        this.passableY = startingY;
        if ((contains & 0x01) == 0x01) {
            this.pathType = PathType.BYPASS;
        } else if ((contains & 0x02) == 0x02) {
            this.pathType = PathType.SWIM;
        } else if ((contains & 0x04) == 0x04) {
            this.pathType = PathType.CLIMB;
        } else {
            this.pathType = PathType.WALK;
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

    @Override
    public String toString() {
        return "PathTypeResultByHeightImpl{" +
                "heightRequirement=" + heightRequirement +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", minYToScan=" + minYToScan +
                ", maxYToScan=" + maxYToScan +
                ", world=" + world +
                ", passableX=" + passableX +
                ", passableY=" + passableY +
                ", passableZ=" + passableZ +
                ", pathWorldBlockReader=" + pathWorldBlockReader +
                ", pathType=" + pathType +
                '}';
    }
}
