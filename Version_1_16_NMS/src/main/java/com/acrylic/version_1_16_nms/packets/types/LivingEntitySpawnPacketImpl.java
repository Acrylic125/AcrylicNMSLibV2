package com.acrylic.version_1_16_nms.packets.types;

import com.acrylic.universalnms.packets.types.LivingEntitySpawnPacket;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityLiving;
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
