package com.acrylic.version_1_8_nms.packets;

import com.acrylic.universalnms.packets.types.EntitySpawnPacket;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntitySpawnPacketImpl
        extends SinglePacketWrapperImpl
        implements EntitySpawnPacket {

    @Override
    public void apply(@NotNull Entity entity) {

    }

    @Override
    public Packet<?> getPacket() {
        return null;
    }
}
