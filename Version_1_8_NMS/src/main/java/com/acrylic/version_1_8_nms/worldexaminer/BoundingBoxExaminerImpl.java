package com.acrylic.version_1_8_nms.worldexaminer;

import com.acrylic.universalnms.misc.BoundingBoxExaminer;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public AxisAlignedBB getBoundingBox(@NotNull Block block) {
        World world = NMSUtils.convertToNMSWorld(block.getWorld());
        net.minecraft.server.v1_8_R3.Block nmsBlock = NMSUtils.convertToNMSBlock(block);
        BlockPosition pos = NMSUtils.getBlockPosition(block);
        nmsBlock.updateShape(world, pos);
        return nmsBlock.a(world,
                pos,
                nmsBlock.getBlockData());
    }

    @NotNull
    @Override
    public AxisAlignedBB getBoundingBox(@NotNull Location location) {
        return getBoundingBox(location.getBlock());
    }

    @NotNull
    @Override
    public AxisAlignedBB getBoundingBox(@NotNull Entity entity) {
        return NMSUtils.convertToNMSEntity(entity).getBoundingBox();
    }

    private void bindWith(AxisAlignedBB bb) {
        minX = bb.a;
        minY = bb.b;
        minZ = bb.c;
        maxX = bb.d;
        maxY = bb.e;
        maxZ = bb.f;
    }

    @Override
    public String toString() {
        return "Size = {" + getX() + ", " + getY() + ", " + getZ() + "} " +
                "From = {" + minX + ", " + minY + ", " + minZ + "} " +
                "To = {" + maxX + ", " + maxY + ", " + maxZ + "}";
    }
}
