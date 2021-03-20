package com.acrylic.universalnms.pathfinder.impl;

import com.acrylic.universalnms.pathfinder.*;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

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

    @Override
    public boolean isSwimmable(StaticPathBlock staticPathBlock) {
        return isLiquid(staticPathBlock.getMCBlockData().getMaterial());
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
        Material material = staticPathBlock.getMCBlockData().getMaterial();
        switch (material) {
            case AIR:
            case SCAFFOLDING:
            case ACACIA_SIGN:
            case ACACIA_WALL_SIGN:
            case BIRCH_SIGN:
            case BIRCH_WALL_SIGN:
            case CRIMSON_SIGN:
            case CRIMSON_WALL_SIGN:
            case DARK_OAK_SIGN:
            case DARK_OAK_WALL_SIGN:
            case JUNGLE_SIGN:
            case JUNGLE_WALL_SIGN:
            case OAK_SIGN:
            case OAK_WALL_SIGN:
            case SPRUCE_SIGN:
            case SPRUCE_WALL_SIGN:
            case WARPED_SIGN:
            case WARPED_WALL_SIGN:
            case TORCH:
            case SOUL_TORCH:
            case SOUL_WALL_TORCH:
            case REDSTONE_TORCH:
            case REDSTONE_WALL_TORCH:
            case LEVER:
            case ACACIA_BUTTON:
            case BIRCH_BUTTON:
            case CRIMSON_BUTTON:
            case DARK_OAK_BUTTON:
            case JUNGLE_BUTTON:
            case OAK_BUTTON:
            case POLISHED_BLACKSTONE_BUTTON:
            case SPRUCE_BUTTON:
            case STONE_BUTTON:
            case WARPED_BUTTON:
            case ACACIA_PRESSURE_PLATE:
            case BIRCH_PRESSURE_PLATE:
            case CRIMSON_PRESSURE_PLATE:
            case DARK_OAK_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case JUNGLE_PRESSURE_PLATE:
            case OAK_PRESSURE_PLATE:
            case POLISHED_BLACKSTONE_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case SPRUCE_PRESSURE_PLATE:
            case STONE_PRESSURE_PLATE:
            case WARPED_PRESSURE_PLATE:
            case COMPARATOR:
            case REPEATER:
            case REDSTONE:
            case REDSTONE_WIRE:
            case TRIPWIRE_HOOK:
            case TRIPWIRE:
            case GRASS:
            case FERN:
            case DEAD_BUSH:
            case SEAGRASS:
            case SEA_PICKLE:
            case OAK_SAPLING:
            case ACACIA_SAPLING:
            case BAMBOO_SAPLING:
            case BIRCH_SAPLING:
            case DARK_OAK_SAPLING:
            case JUNGLE_SAPLING:
            case SPRUCE_SAPLING:
            case DANDELION:
            case POPPY:
            case BLUE_ORCHID:
            case ALLIUM:
            case AZURE_BLUET:
            case RED_TULIP:
            case ORANGE_TULIP:
            case WHITE_TULIP:
            case PINK_TULIP:
            case OXEYE_DAISY:
            case CORNFLOWER:
            case LILY_OF_THE_VALLEY:
            case WITHER_ROSE:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case CRIMSON_FUNGUS:
            case WARPED_FUNGUS:
            case CRIMSON_ROOTS:
            case WARPED_ROOTS:
            case BEETROOTS:
            case KELP_PLANT:
            case MELON_STEM:
            case PUMPKIN_STEM:
            case ATTACHED_PUMPKIN_STEM:
            case ATTACHED_MELON_STEM:
            case COBWEB:
            case SUGAR_CANE:
            case SUNFLOWER:
            case PEONY:
            case ROSE_BUSH:
            case LILAC:
            case TALL_GRASS:
            case TALL_SEAGRASS:
            case RAIL:
            case DETECTOR_RAIL:
            case POWERED_RAIL:
            case ACTIVATOR_RAIL:
            case BRAIN_CORAL:
            case BUBBLE_CORAL:
            case DEAD_BRAIN_CORAL:
            case DEAD_BUBBLE_CORAL:
            case DEAD_FIRE_CORAL:
            case DEAD_HORN_CORAL:
            case DEAD_TUBE_CORAL:
            case FIRE_CORAL:
            case HORN_CORAL:
            case BRAIN_CORAL_WALL_FAN:
            case BUBBLE_CORAL_FAN:
            case DEAD_BRAIN_CORAL_FAN:
            case DEAD_BUBBLE_CORAL_FAN:
            case DEAD_FIRE_CORAL_FAN:
            case DEAD_HORN_CORAL_FAN:
            case DEAD_TUBE_CORAL_FAN:
            case FIRE_CORAL_FAN:
            case HORN_CORAL_FAN:
            case TUBE_CORAL_FAN:
            case BUBBLE_CORAL_WALL_FAN:
            case DEAD_BRAIN_CORAL_WALL_FAN:
            case DEAD_BUBBLE_CORAL_WALL_FAN:
            case DEAD_FIRE_CORAL_WALL_FAN:
            case DEAD_HORN_CORAL_WALL_FAN:
            case DEAD_TUBE_CORAL_WALL_FAN:
            case FIRE_CORAL_WALL_FAN:
            case HORN_CORAL_WALL_FAN:
            case TUBE_CORAL_WALL_FAN:
            case BRAIN_CORAL_FAN:
            case TUBE_CORAL:
                return true;
        }
        return staticPathBlock.getMCBlockData().getMaterial().isTransparent();
    }

    @Nullable
    @Override
    public PathType getPathTypeAt(@NotNull Pathfinder pathfinder, float x, float y, float z) {
        double height = pathfinder.getPathfinderGenerator().getMinimumYToTraverse();
        int htr = (int) Math.ceil(height); //Amount of blocks to check up to.
        PathWorldBlockReader worldBlockReader = pathfinder.getPathWorldBlockReader();
        StaticPathBlock at = worldBlockReader.getStaticPathBlockAt(x, y, z);
        //Check for swimming.
        boolean isHTRGreaterThan1 = htr > 1;
        if (isSwimmable(at)) {
            if (isHTRGreaterThan1) {
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
        }
        //Check for climbing.
        else if (isClimbable(at)) {
            if (isHTRGreaterThan1) {
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
                return PathType.CLIMB;
            }
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
