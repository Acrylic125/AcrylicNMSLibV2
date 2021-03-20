package com.acrylic.version_1_16_nms.worldexaminer;

import com.acrylic.universalnms.worldexaminer.BukkitBoundingBoxExaminerImpl;
import com.acrylic.version_1_16_nms.NMSUtils;
import net.minecraft.server.v1_16_R3.AxisAlignedBB;
import net.minecraft.server.v1_16_R3.VoxelShape;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class BoundingBoxExaminerImpl extends BukkitBoundingBoxExaminerImpl {

    @Override
    public void examineCollisionBox(@NotNull Block block) {
        VoxelShape shape = ((CraftBlock) block).getNMS().getCollisionShape(NMSUtils.convertToNMSWorld(block.getWorld()), NMSUtils.getBlockPosition(block));
        if (shape.isEmpty())
            bindWith(0, 0, 0, 0, 0, 0);
        else {
            AxisAlignedBB aabb = shape.getBoundingBox();
            bindWith(block.getX() + aabb.minX, block.getY() + aabb.minY, block.getZ() + aabb.minZ, block.getX() + aabb.maxX, block.getY() + aabb.maxY, block.getZ() + aabb.maxZ);
        }
    }

    @Override
    public void examineCollisionBox(@NotNull Location location) {
        examineCollisionBox(location.getBlock());
    }

    @Override
    public void examineCollisionBox(@NotNull Entity entity) {
        examine(entity);
    }

    @Override
    public void examineCollisionBox(World world, int x, int y, int z) {
        CraftWorld craftWorld = NMSUtils.convertToCraftWorld(world);
        VoxelShape shape = ((CraftBlock) craftWorld.getBlockAt(x, y, z)).getNMS().getCollisionShape(NMSUtils.convertToNMSWorld(world), NMSUtils.getBlockPosition(x, y, z));
        if (shape.isEmpty())
            bindWith(0, 0, 0, 0, 0, 0);
        else {
            AxisAlignedBB aabb = shape.getBoundingBox();
            bindWith(x + aabb.minX, y + aabb.minY, z + aabb.minZ, x + aabb.maxX, y + aabb.maxY, z + aabb.maxZ);
        }
    }
}
