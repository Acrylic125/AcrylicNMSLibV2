package com.acrylic.universalnms.worldexaminer;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public abstract class BukkitBoundingBoxExaminerImpl
        implements BoundingBoxExaminer {

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

    @Override
    public void examine(World world, int x, int y, int z) {
        bindWith(getBoundingBox(world, x, y, z));
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

    @NotNull
    @Override
    public BoundingBox getBoundingBox(World world, int x, int y, int z) {
        return getBoundingBox(world.getBlockAt(x, y, z));
    }

    @NotNull
    @Override
    public BoundingBox getBoundingBox(World world, float x, float y, float z) {
        return getBoundingBox(world.getBlockAt((int) x, (int) y, (int) z));
    }

    public void bindWith(BoundingBox bb) {
        minX = bb.getMinX();
        minY = bb.getMinY();
        minZ = bb.getMinZ();
        maxX = bb.getMaxX();
        maxY = bb.getMaxY();
        maxZ = bb.getMaxZ();
    }

    public void bindWith(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    @Override
    public String toString() {
        return "Size = {" + getX() + ", " + getY() + ", " + getZ() + "} " +
                "From = {" + minX + ", " + minY + ", " + minZ + "} " +
                "To = {" + maxX + ", " + maxY + ", " + maxZ + "}";
    }

}
