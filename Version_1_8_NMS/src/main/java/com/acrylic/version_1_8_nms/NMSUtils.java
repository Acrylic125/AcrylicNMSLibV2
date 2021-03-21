package com.acrylic.version_1_8_nms;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;
import org.jetbrains.annotations.NotNull;

public final class NMSUtils {

    public static MinecraftServer getMCServer() {
        return getMCServer(Bukkit.getServer());
    }

    public static MinecraftServer getMCServer(@NotNull Server server) {
        return ((CraftServer) server).getServer();
    }

    public static ItemStack convertToBukkitItem(net.minecraft.server.v1_8_R3.ItemStack item) {
        return CraftItemStack.asBukkitCopy(item);
    }

    public static net.minecraft.server.v1_8_R3.ItemStack convertToNMSItem(ItemStack item) {
        return CraftItemStack.asNMSCopy(item);
    }

    public static Entity convertToBukkitEntity(CraftEntity entity) {
        return entity.getHandle().getBukkitEntity();
    }

    public static net.minecraft.server.v1_8_R3.Entity convertToNMSEntity(@NotNull Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    public static EntityLiving convertToNMSEntity(@NotNull LivingEntity entity) {
        return (EntityLiving) convertToNMSEntity((Entity) entity);
    }

    public static TileEntity convertToNMSTileEntity(Block block) {
        return convertToCraftWorld(block.getWorld()).getTileEntityAt(block.getX(), block.getY(), block.getZ());
    }

    public static WorldServer convertToWorldServer(CraftWorld world) {
        return world.getHandle();
    }

    public static WorldServer convertToWorldServer(@NotNull World world) {
        return convertToWorldServer(convertToCraftWorld(world));
    }

    public static World convertToBukkitWorld(CraftWorld world) {
        return (World) convertToWorldServer(world);
    }

    public static CraftWorld convertToCraftWorld(World world) {
        return (CraftWorld) world;
    }

    public static net.minecraft.server.v1_8_R3.World convertToNMSWorld(World world) {
        return convertToCraftWorld(world).getHandle();
    }

    public static Player convertToBukkitPlayer(CraftPlayer player) {
        return player.getPlayer();
    }

    public static EntityPlayer convertToNMSPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static net.minecraft.server.v1_8_R3.Block convertToNMSBlock(@NotNull org.bukkit.Material material) {
        return CraftMagicNumbers.getBlock(material);
    }

    public static net.minecraft.server.v1_8_R3.Block convertToNMSBlock(@NotNull Block block) {
        return CraftMagicNumbers.getBlock(block);
    }

    public static net.minecraft.server.v1_8_R3.Block convertToNMSBlock(@NotNull Location location) {
        return convertToNMSBlock(location.getBlock());
    }

    public static BlockPosition getBlockPosition(@NotNull Location location) {
        return new BlockPosition(location.getX(), location.getY(), location.getZ());
    }

    public static BlockPosition getBlockPosition(@NotNull Block block) {
        return new BlockPosition(block.getX(), block.getY(), block.getZ());
    }

    public static BlockPosition getBlockPosition(int x, int y, int z) {
        return new BlockPosition(x, y, z);
    }

    public static BlockPosition getBlockPosition(float x, float y, float z) {
        return new BlockPosition(x, y, z);
    }

}
