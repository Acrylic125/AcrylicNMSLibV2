package com.acrylic.version_1_8_nms.worldexaminer;

import com.acrylic.universalnms.misc.BoundingBoxExaminer;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoundingBoxExaminerImpl implements BoundingBoxExaminer {

    private AxisAlignedBB bb;

    @Override
    public double getMinX() {
        return bb.a;
    }

    @Override
    public double getMinY() {
        return bb.b;
    }

    @Override
    public double getMinZ() {
        return bb.c;
    }

    @Override
    public double getMaxX() {
        return bb.d;
    }

    @Override
    public double getMaxY() {
        return bb.e;
    }

    @Override
    public double getMaxZ() {
        return bb.f;
    }

    @Override
    public boolean examine(@NotNull Location location) {
        World world = NMSUtils.convertToNMSWorld(location.getWorld());
        net.minecraft.server.v1_8_R3.Block nmsBlock = NMSUtils.convertToNMSBlock(location);
        BlockPosition pos = NMSUtils.getBlockPosition(location);
        nmsBlock.updateShape(world, pos);
        bb = nmsBlock.a(world,
                pos,
                nmsBlock.getBlockData());
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
