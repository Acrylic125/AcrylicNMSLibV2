package com.acrylic.universalnms.misc;

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
     */
    void examine(@NotNull Block block);

    /**
     *
     * @param location The location to examine.
     */
    void examine(@NotNull Location location);

    /**
     *
     * @param entity The entity to examine.
     */
    void examine(@NotNull Entity entity);

    @NotNull
    Object getBoundingBox(@NotNull Block block);

    @NotNull
    Object getBoundingBox(@NotNull Location location);

    @NotNull
    Object getBoundingBox(@NotNull Entity entity);


}
