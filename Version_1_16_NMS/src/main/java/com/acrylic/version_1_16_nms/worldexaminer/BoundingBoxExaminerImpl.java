package com.acrylic.version_1_16_nms.worldexaminer;

import com.acrylic.universalnms.misc.BoundingBoxExaminer;
import com.acrylic.version_1_16_nms.NMSUtils;
import net.minecraft.server.v1_16_R3.AxisAlignedBB;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoundingBoxExaminerImpl implements BoundingBoxExaminer {

    private AxisAlignedBB bb;

    @Override
    public double getMinX() {
        return bb.minX;
    }

    @Override
    public double getMinY() {
        return bb.minY;
    }

    @Override
    public double getMinZ() {
        return bb.minZ;
    }

    @Override
    public double getMaxX() {
        return bb.maxX;
    }

    @Override
    public double getMaxY() {
        return bb.maxY;
    }

    @Override
    public double getMaxZ() {
        return bb.maxZ;
    }

    @Override
    public boolean examine(@NotNull Location location) {
        World world = NMSUtils.convertToNMSWorld(location.getWorld());
        BlockPosition pos = NMSUtils.getBlockPosition(location);
        IBlockData blockData = (world.getType(pos));
        bb = blockData.getShape(world, pos).getBoundingBox();
        return bb != null;
    }

    @Override
    public boolean examine(@NotNull Entity entity) {
        bb = NMSUtils.convertToNMSEntity(entity).getBoundingBox();
        return bb != null;
    }

    @Nullable
    @Override
    public AxisAlignedBB getNMSBoundingBox() {
        return bb;
    }

    @Override
    public String toString() {
        return "BoundingBoxExaminer{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", z=" + getZ() +
                " [min: " +
                "x=" + getMinX() +
                ", y=" + getMinY() +
                ", z=" + getMinZ() +
                "], [max: " +
                "x=" + getMaxX() +
                ", y=" + getMaxY() +
                ", z=" + getMaxZ() +
                "]}";
    }

}