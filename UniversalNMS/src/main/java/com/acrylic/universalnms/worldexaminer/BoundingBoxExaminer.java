package com.acrylic.universalnms.worldexaminer;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BoundingBoxExaminer {

    double getMinX();

    double getMaxX();

    default double getX() {
        return getMaxX() - getMinX();
    }

    double getMinY();

    double getMaxY();

    default double getY() {
        return getMaxY() - getMinY();
    }

    double getMaxZ();

    double getMinZ();

    default double getZ() {
        return getMaxZ() - getMinZ();
    }

    /**
     *
     * @param block The block to examine.
     * @return True if the block can be examined.
     */
    default boolean examine(@NotNull Block block) {
        return examine(block.getLocation());
    }

    /**
     *
     * @param location The location to examine.
     * @return True if the block can be examined.
     */
    boolean examine(@NotNull Location location);

    /**
     *
     * @param entity The entity to examine.
     * @return True if the entity can be examined.
     */
    boolean examine(@NotNull Entity entity);

    @Nullable
    Object getBoundingBox();

    default boolean canExamine() {
        return getBoundingBox() != null;
    }

}
