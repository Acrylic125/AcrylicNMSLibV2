package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universal.items.ItemUtils;
import com.acrylic.universalnms.pathfinder.*;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

import static com.acrylic.universal.items.ItemUtils.isAir;
import static com.acrylic.universal.items.ItemUtils.isLiquid;

public class PathExaminerImpl implements PathExaminer {

    @Override
    public boolean isSwimmable(StaticPathBlock staticPathBlock) {
        return ItemUtils.isLiquid(staticPathBlock.getMCBlockData().getMaterial());
    }

    @Override
    public boolean isClimbable(StaticPathBlock staticPathBlock) {
        Material material = staticPathBlock.getMCBlockData().getMaterial();
        switch (material) {
            case LADDER:
            case VINE:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isPassable(StaticPathBlock staticPathBlock) {
        boolean passable = isSwimmable(staticPathBlock) || isClimbable(staticPathBlock);
        if (passable)
            return true;
        return staticPathBlock.getMCBlockData().getMaterial().isTransparent();
    }

    @Nullable
    @Override
    public PathType getPathTypeAt(@NotNull Pathfinder pathfinder, float x, float y, float z) {
        double height = pathfinder.getPathfinderGenerator().getMinimumHeightToTraverse();
        int htr = (int) Math.ceil(height); //Amount of blocks to check up to.
        PathWorldBlockReader worldBlockReader = pathfinder.getPathWorldBlockReader();
        StaticPathBlock at = worldBlockReader.getStaticPathBlockAt(x, y, z);
        //Check for swimming.
        if (isSwimmable(at)) {
            if (htr > 1) {
                AtomicReference<Double> maxHeight = new AtomicReference<>((double) y + 1);
                iterateHeightUntilNotPassable(worldBlockReader, 1, htr, x, y, z,
                        (nX, nY, nZ, pathBlock) -> {
                    boolean isPassable = isPassable(pathBlock);
                    if (!isPassable)
                        maxHeight.set(pathBlock.getBoundingBoxExaminer().getMinY());
                    return isPassable;
                });
                return (maxHeight.get() - y >= height) ? PathType.SWIM : null;
            } else {
                return PathType.SWIM;
            }
        } else if (isClimbable(at)) {

        }
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
    private void iterateHeightUntilNotPassable(PathWorldBlockReader reader, int addFrom, int addTo,
                                                  float x, float y, float z,
                                                  PathPointActionAndCheck action) {
        for (int i = addFrom; i <= addTo; i++) {
            float iY = i + y;
            StaticPathBlock upI = reader.getStaticPathBlockAt(x, iY, z);
            if (!action.isPassable(x, iY, z, upI))
                return;
        }
    }

    @FunctionalInterface
    private interface PathPointActionAndCheck {
        boolean isPassable(float x, float y, float z, StaticPathBlock pathBlock);
    }

}
