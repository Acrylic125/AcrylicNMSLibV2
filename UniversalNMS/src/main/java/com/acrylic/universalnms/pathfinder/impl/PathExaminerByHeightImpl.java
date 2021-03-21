package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.pathfinder.*;
import com.acrylic.universalnms.worldexaminer.BoundingBoxExaminer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.acrylic.universal.items.ItemUtils.isLiquid;

/**
 * A very simple and not very accurate path examiner
 * to determine if the block is traversable.
 *
 * This only use {@link PathfinderGenerator#getMinimumYToTraverse()}
 * to determine if the can traverse and not the full BB size.
 *
 * This is a lazy implementation.
 */
public class PathExaminerByHeightImpl implements PathExaminer {

    public static final PathExaminerByHeightImpl TEST = new PathExaminerByHeightImpl();

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

    public boolean isPassable(PathBlock pathBlock) {
        boolean passable = isSwimmable(pathBlock) || shouldBypass(pathBlock) || isClimbable(pathBlock);
        if (passable)
            return true;
        return pathBlock.getCollisionBoundingBoxExaminer().isEmpty();
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

    @Override
    public PathTypeResult examineTo(@NotNull PathTypeResult pathTypeResult) {
        return null;
    }

    @Nullable
    public PathType getPathTypeAt(@NotNull Pathfinder pathfinder, float x, float y, float z) {
        float height = pathfinder.getPathfinderGenerator().getMinimumYToTraverse();
        int htr = (int) Math.ceil(height) - 1; //Amount of blocks to check up to.
        PathWorldBlockReader worldBlockReader = pathfinder.getPathWorldBlockReader();
        PathBlock at = worldBlockReader.getPathBlockAt(x, y, z);
        //Check for swimming.
        if (isSwimmable(at)) {
            if (htr >= 1) {
                double maxY = iterateHeightUntilNotPassable(worldBlockReader, 1, htr, x, y, z,
                        (nX, nY, nZ, pathBlock) -> isPassable(pathBlock));
                return (maxY - y >= height) ? PathType.SWIM : null;
            } else return PathType.SWIM;
        }
        //Check for climbing.
        else if (isClimbable(at)) {
            if (htr >= 1) {
                double maxY = iterateHeightUntilNotPassable(worldBlockReader, 1, htr, x, y, z,
                        (nX, nY, nZ, pathBlock) -> isPassable(pathBlock));
                return (maxY - y >= height) ? PathType.CLIMB : null;
            } else return PathType.CLIMB;
        }
        //Check for bypass.
        else if (shouldBypass(at)) {
            if (htr > 1) {
                double maxY = iterateHeightUntilNotPassable(worldBlockReader, 1, htr, x, y, z,
                        (nX, nY, nZ, pathBlock) -> isPassable(pathBlock));
                return (maxY - y >= height) ? PathType.BYPASS : null;
            } else return PathType.BYPASS;
        }
        //Check for walking.
        else {
            boolean isPassableAt = isPassable(at);
            float reduceStartFromY = 0;
            //If the block the path finder is at is not 'passable'
            if (!isPassableAt) {
                //If the block at that location is shorter than 1.
                //For instance, slabs.
                y = (float) at.getCollisionBoundingBoxExaminer().getMaxY();
                if (at.getCollisionBoundingBoxExaminer().getY() < 1)
                    htr++;
                else
                    reduceStartFromY = 1;
            }
            double maxY;
            if (htr >= 1) {
                maxY = iterateHeightUntilNotPassable(worldBlockReader, 1, htr, x, y - reduceStartFromY, z,
                        (nX, nY, nZ, pathBlock) -> isPassable(pathBlock));
            } else {
                maxY = (!isPassableAt) ? at.getCollisionBoundingBoxExaminer().getMinY() : y + 1;
            }
            return (maxY - y >= height) ? PathType.WALK : null;
        }
    }

    @Nullable
    public Location getPathPointAt(@NotNull Pathfinder pathfinder, float x, float y, float z) {
        return null;
    }


    /**
     *
     * @param reader The reader.
     * @param addFrom The starting y addition value.
     * @param addTo The ending y addition value.
     * @param x The x location.
     * @param y The y location.
     * @param z The z location.
     * @param action The action and return if it is passable.
     */
    private double iterateHeightUntilNotPassable(PathWorldBlockReader reader, int addFrom, int addTo,
                                                  float x, float y, float z,
                                                  PathPointActionAndCheck action) {
        for (int i = addFrom; i <= addTo; i++) {
            float iY = i + y;
            PathBlock upI = reader.getPathBlockAt(x, iY, z);
            if (!action.isPassable(x, iY, z, upI)) {
                BoundingBoxExaminer collisionBox = upI.getCollisionBoundingBoxExaminer();
                return (collisionBox.isEmpty()) ? y + addTo + 1 : collisionBox.getMinY();
            }
        }
        return y + addTo + 1;
    }

    @FunctionalInterface
    private interface PathPointActionAndCheck {
        boolean isPassable(float x, float y, float z, PathBlock pathBlock);
    }

}
