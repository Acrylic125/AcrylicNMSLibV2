package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.pathfinder.*;
import com.acrylic.universalnms.worldexaminer.BoundingBoxExaminer;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import static com.acrylic.universal.items.ItemUtils.isLiquid;

public class PathExaminerByHeightImpl implements PathExaminer {

    public boolean shouldBypass(PathBlock pathBlock) {
        Material material = pathBlock.getMCBlockData().getMaterial();
        return NMSLib.getBlockAnalyzer().isAnyDoor(material);
    }

    public boolean isSwimmable(PathBlock pathBlock) {
        return isLiquid(pathBlock.getMCBlockData().getMaterial());
    }

    public boolean isClimbable(PathBlock pathBlock) {
        Material material = pathBlock.getMCBlockData().getMaterial();
        switch (material) {
            case LADDER:
            case VINE:
                return true;
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public PathType getPathTypeOfBlock(PathBlock pathBlock) {
        if (shouldBypass(pathBlock))
            return PathType.BYPASS;
        else if (isSwimmable(pathBlock))
            return PathType.SWIM;
        else if (isClimbable(pathBlock))
            return PathType.CLIMB;
        else if (pathBlock.getCollisionBoundingBoxExaminer().isEmpty())
            return PathType.WALK;
        else
            return null;
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
     */
    @Override
    public PathTypeResult examineTo(Pathfinder pathfinder, float x, float y0, float z) {
        PathReader pathReader = pathfinder.getPathReader();
        float heightRequirement = pathfinder.getPathfinderGenerator().getMinimumYToTraverse();
        PathTypeResultImpl pathTypeResult = new PathTypeResultImpl(pathfinder, x, y0, z);
        PathfinderGenerator generator = pathfinder.getPathfinderGenerator();
        float minYToScan = y0 - generator.getMaximumDropHeight(),
                maxYToScan = y0 + generator.getMaximumHeight();
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
            PathBlock pathBlock = pathReader.getPathBlockAt(x, y, z);
            PathType pathType = getPathTypeOfBlock(pathBlock);
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
        float endToStartDiff = endingY - startingY;
        if ((endToStartDiff < heightRequirement) || (scanner & 0x01) != 0x01) {
            //Bukkit.broadcastMessage(" " + x + " " + startingY + " " + z + " " + startingY + " p " + endingY + " " + ((endingY - startingY) < heightRequirement) + " vs " + ((scanner & 0x01) != 0x01));
            return pathTypeResult;
        }
        PathType pathType;
        float floorX = (float) Math.floor(x), floorZ = (float) Math.floor(z);
        float passableX = (floorX + (floorX + 1)) / 2, passableZ = (floorZ + (floorZ + 1)) / 2, passableY = startingY;
        if ((contains & 0x01) == 0x01) {
            pathType = PathType.BYPASS;
        } else if ((contains & 0x02) == 0x02) {
            pathType = PathType.SWIM;
            if (endToStartDiff - heightRequirement >= 1)
                passableY++;
        } else if ((contains & 0x04) == 0x04) {
            pathType = PathType.CLIMB;
        } else {
            pathType = PathType.WALK;
        }
        pathTypeResult.setPassable(passableX, passableY, passableZ);
        pathTypeResult.setPathType(pathType);
        return pathTypeResult;
    }


}
