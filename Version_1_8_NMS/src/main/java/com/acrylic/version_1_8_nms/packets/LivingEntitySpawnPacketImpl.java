package com.acrylic.version_1_8_nms.packets;

import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import com.acrylic.universalnms.packets.types.LivingEntitySpawnPacket;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class LivingEntitySpawnPacketImpl
        extends SinglePacketWrapperImpl
        implements LivingEntitySpawnPacket {

    private PacketPlayOutSpawnEntityLiving packetPlayOutSpawnEntity;

    public void apply(EntityLiving entity) {
        packetPlayOutSpawnEntity = new PacketPlayOutSpawnEntityLiving(entity);
    }

    @Override
    public void apply(@NotNull Entity entity) {
        if (entity instanceof LivingEntity)
            apply(NMSUtils.convertToNMSEntity((LivingEntity) entity));
        else
            throw new IllegalArgumentException("The entity provided must be a living entity.");
    }

    @Override
    public PacketPlayOutSpawnEntityLiving getPacket() {
        return packetPlayOutSpawnEntity;
    }
}
