package com.acrylic.version_1_16_nms;

import com.acrylic.universal.text.ChatUtils;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class NMSUtils {

    public static MinecraftServer getMCServer() {
        return getMCServer(Bukkit.getServer());
    }

    public static MinecraftServer getMCServer(@NotNull Server server) {
        return ((CraftServer) server).getServer();
    }

    public static ItemStack convertToBukkitItem(net.minecraft.server.v1_16_R3.ItemStack item) {
        return CraftItemStack.asBukkitCopy(item);
    }

    public static net.minecraft.server.v1_16_R3.ItemStack convertToNMSItem(ItemStack item) {
        return CraftItemStack.asNMSCopy(item);
    }

    public static Entity convertToBukkitEntity(CraftEntity entity) {
        return entity.getHandle().getBukkitEntity();
    }

    public static net.minecraft.server.v1_16_R3.Entity convertToNMSEntity(@NotNull Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    public static EntityLiving convertToNMSEntity(@NotNull LivingEntity entity) {
        return (EntityLiving) convertToNMSEntity((Entity) entity);
    }

    public static TileEntity convertToNMSTileEntity(Block block) {
        return convertToNMSWorld(block.getWorld())
                .getChunkAt(block.getX() >> 4, block.getZ() >> 4)
                .getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
    }

    public static WorldServer convertToWorldServer(CraftWorld world) {
        return world.getHandle();
    }

    public static WorldServer convertToWorldServer(World world) {
        return convertToWorldServer(convertToCraftWorld(world));
    }

    public static World convertToBukkitWorld(CraftWorld world) {
        return (World) convertToWorldServer(world);
    }

    public static CraftWorld convertToCraftWorld(World world) {
        return (CraftWorld) world;
    }

    public static net.minecraft.server.v1_16_R3.World convertToNMSWorld(World world) {
        return convertToCraftWorld(world).getHandle();
    }

    public static Player convertToBukkitPlayer(CraftPlayer player) {
        return player.getPlayer();
    }

    public static EntityPlayer convertToNMSPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static net.minecraft.server.v1_16_R3.Block convertToNMSBlock(@NotNull org.bukkit.Material material) {
        return CraftMagicNumbers.getBlock(material);
    }

    public static net.minecraft.server.v1_16_R3.Block convertToNMSBlock(@NotNull Block block) {
        return CraftMagicNumbers.getBlock(block.getType());
    }

    public static net.minecraft.server.v1_16_R3.Block convertToNMSBlock(@NotNull Location location) {
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

    public static byte getByteAngle(float angle) {
        return (byte) (int) (angle * 256.0F / 360.0F);
    }

    public static byte getByteAngle(double angle) {
        return getByteAngle((float) angle);
    }

    public static DamageSource convertToNMSDamageSource(com.acrylic.universalnms.enums.DamageSource damageSource) {
        if (damageSource == null)
            return null;
        switch (damageSource) {
            case DRAGON_BREATH: return DamageSource.DRAGON_BREATH;
            case LAVA: return DamageSource.LAVA;
            case BURN: return DamageSource.BURN;
            case FALL: return DamageSource.FALL;
            case FIRE: return DamageSource.FIRE;
            case ANVIL: return DamageSource.ANVIL;
            case DROWN: return DamageSource.DROWN;
            case MAGIC: return DamageSource.MAGIC;
            case STUCK: return DamageSource.STUCK;
            case CACTUS: return DamageSource.CACTUS;
            case DRYOUT: return DamageSource.DRYOUT;
            case STARVE: return DamageSource.STARVE;
            case WITHER: return DamageSource.WITHER;
            case CRAMMING: return DamageSource.CRAMMING;
            case HOT_FLOOR: return DamageSource.HOT_FLOOR;
            case LIGHTNING: return DamageSource.LIGHTNING;
            case OUT_OF_WORLD: return DamageSource.OUT_OF_WORLD;
            case FALLING_BLOCK: return DamageSource.FALLING_BLOCK;
            case FLY_INTO_WALL: return DamageSource.FLY_INTO_WALL;
            case SWEET_BERRY_BUSH: return DamageSource.SWEET_BERRY_BUSH;
            default: return null;
        }
    }

    public static IChatBaseComponent toChatComponent(String text) {
        return IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatUtils.get(text) + "\"}");
    }
}
