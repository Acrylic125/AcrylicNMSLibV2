package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universalnms.packets.types.EntityAnimationPackets;
import com.acrylic.version_1_8_nms.NMSUtils;
import com.acrylic.version_1_8_nms.packets.MultiPacketWrapperImpl;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class EntityAnimationPacketsImpl
        extends MultiPacketWrapperImpl
        implements EntityAnimationPackets {

    private final Collection<Packet<?>> packets = new ArrayList<>();

    @Override
    public EntityAnimationPackets attachArmSwingingAnimation(@NotNull Entity entity) {
        packets.add(getNewArmSwingPacket(entity));
        return this;
    }

    @Override
    public PacketPlayOutAnimation getNewArmSwingPacket(@NotNull Entity entity) {
        return new PacketPlayOutAnimation(NMSUtils.convertToNMSEntity(entity), 0);
    }

    @Override
    public EntityAnimationPackets attachLeftArmSwingingAnimation(@NotNull Entity entity) {
        throw new UnsupportedOperationException("Left Swinging Arm animation is not available in 1.8.");
    }

    @Override
    public PacketPlayOutAnimation getNewLeftArmSwingPacket(@NotNull Entity entity) {
        throw new UnsupportedOperationException("Left Swinging Arm animation is not available in 1.8.");
    }

    @Override
    public EntityAnimationPackets attachCritAnimation(@NotNull Entity entity) {
        packets.add(getNewCritPacket(entity));
        return this;
    }

    @Override
    public PacketPlayOutAnimation getNewCritPacket(@NotNull Entity entity) {
        return new PacketPlayOutAnimation(NMSUtils.convertToNMSEntity(entity), 4);
    }

    @Override
    public EntityAnimationPackets attachMagicCritAnimation(@NotNull Entity entity) {
        packets.add(getNewMagicCritPacket(entity));
        return this;
    }

    @Override
    public PacketPlayOutAnimation getNewMagicCritPacket(@NotNull Entity entity) {
        return new PacketPlayOutAnimation(NMSUtils.convertToNMSEntity(entity), 5);
    }

    @Override
    public EntityAnimationPackets attachHurtAnimation(@NotNull Entity entity) {
        packets.add(getNewHurtPacket(entity));
        return this;
    }

    @Override
    public PacketPlayOutAnimation getNewHurtPacket(@NotNull Entity entity) {
        return new PacketPlayOutAnimation(NMSUtils.convertToNMSEntity(entity), 1);
    }

    @Override
    public EntityAnimationPackets attachSleepAnimation(@NotNull Entity entity, @NotNull Location location) {
        packets.add(getNewSleepPacket(entity, location));
        return this;
    }

    @Override
    public PacketPlayOutBed getNewSleepPacket(@NotNull Entity entity, @NotNull Location location) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = NMSUtils.convertToNMSEntity(entity);
        if (!(nmsEntity instanceof EntityHuman))
            throw new IllegalArgumentException("Sleep animation only supports EntityHuman entities.");
        return new PacketPlayOutBed((EntityHuman) nmsEntity, NMSUtils.getBlockPosition(location));
    }

    @Override
    public EntityAnimationPackets attachSleepAnimation(@NotNull Entity entity) {
        return attachSleepAnimation(entity, entity.getLocation());
    }

    @Override
    public PacketPlayOutBed getNewSleepPacket(@NotNull Entity entity) {
        return getNewSleepPacket(entity, entity.getLocation());
    }

    @Override
    public EntityAnimationPackets attachStopSleepAnimation(@NotNull Entity entity) {
        packets.add(getNewStopElytraPacket(entity));
        return this;
    }

    @Override
    public PacketPlayOutAnimation generateStopSleepPacket(@NotNull Entity entity) {
        return new PacketPlayOutAnimation(NMSUtils.convertToNMSEntity(entity), 2);
    }

    @Override
    public EntityAnimationPackets attachSneakAnimation(@NotNull Entity entity) {
        packets.add(getNewSneakPacket(entity));
        return this;
    }

    @Override
    public PacketPlayOutEntityMetadata getNewSneakPacket(@NotNull Entity entity) {
        return generateSneakPacket(NMSUtils.convertToNMSEntity(entity), true);
    }

    @Override
    public EntityAnimationPackets attachStopSneakAnimation(@NotNull Entity entity) {
        packets.add(getNewStopSneakPacket(entity));
        return this;
    }

    @Override
    public PacketPlayOutEntityMetadata getNewStopSneakPacket(@NotNull Entity entity) {
       return generateSneakPacket(NMSUtils.convertToNMSEntity(entity), false);
    }

    public PacketPlayOutEntityMetadata generateSneakPacket(@NotNull net.minecraft.server.v1_8_R3.Entity entity, boolean sneaking) {
        Entity bukkitEntity = entity.getBukkitEntity();
        if (!(bukkitEntity instanceof Player))
            throw new IllegalArgumentException("Sneak animation only supports EntityHuman entities.");
        ((Player) bukkitEntity).setSneaking(sneaking);
        EntityMetadataPacketImpl metadataPacket = new EntityMetadataPacketImpl();
        metadataPacket.apply(entity);
        return metadataPacket.getPacket();
    }

    @Override
    public EntityAnimationPackets attachStartElytraAnimation(@NotNull Entity entity) {
        throw new UnsupportedOperationException("Start Elytra animation is not available in 1.8.");
    }

    @Override
    public EntityAnimationPackets getNewStartElytraPacket(@NotNull Entity entity) {
        throw new UnsupportedOperationException("Start Elytra animation is not available in 1.8.");
    }

    @Override
    public EntityAnimationPackets attachStopElytraAnimation(@NotNull Entity entity) {
        throw new UnsupportedOperationException("Stop Elytra animation is not available in 1.8.");
    }

    @Override
    public PacketPlayOutAnimation getNewStopElytraPacket(@NotNull Entity entity) {
        throw new UnsupportedOperationException("Stop Elytra animation is not available in 1.8.");
    }

    @Override
    public Collection<Packet<?>> getPackets() {
        return packets;
    }
}
