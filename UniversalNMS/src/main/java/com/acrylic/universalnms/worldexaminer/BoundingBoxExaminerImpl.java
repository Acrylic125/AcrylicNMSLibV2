package com.acrylic.universalnms.worldexaminer;

import com.acrylic.universalnms.misc.BoundingBoxExaminer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class BoundingBoxExaminerImpl implements BoundingBoxExaminer {

    private double minX = 0, minY = 0, minZ = 0,
            maxX = 0, maxY = 0, maxZ = 0;

    @Override
    public double getMinX() {
        return minX;
    }

    @Override
    public double getMinY() {
        return minY;
    }

    @Override
    public double getMinZ() {
        return minZ;
    }

    @Override
    public double getMaxX() {
        return maxX;
    }

    @Override
    public double getMaxY() {
        return maxY;
    }

    @Override
    public double getMaxZ() {
        return maxZ;
    }

    @Override
    public void examine(@NotNull Block block) {
        bindWith(getBoundingBox(block));
    }

    @Override
    public void examine(@NotNull Location location) {
        bindWith(getBoundingBox(location));
    }

    @Override
    public void examine(@NotNull Entity entity) {
        bindWith(getBoundingBox(entity));
    }

    @NotNull
    @Override
    public BoundingBox getBoundingBox(@NotNull Block block) {
        return block.getBoundingBox();
    }

    @NotNull
    @Override
    public BoundingBox getBoundingBox(@NotNull Location location) {
        return getBoundingBox(location.getBlock());
    }

    @NotNull
    @Override
    public BoundingBox getBoundingBox(@NotNull Entity entity) {
        return entity.getBoundingBox();
    }

    private void bindWith(BoundingBox bb) {
        minX = bb.getMinX();
        minY = bb.getMinY();
        minZ = bb.getMinZ();
        maxX = bb.getMaxX();
        maxY = bb.getMaxY();
        maxZ = bb.getMaxZ();
    }

    @Override
    public String toString() {
        return "Size = {" + getX() + ", " + getY() + ", " + getZ() + "} " +
                "From = {" + minX + ", " + minY + ", " + minZ + "} " +
                "To = {" + maxX + ", " + maxY + ", " + maxZ + "}";
    }

}
