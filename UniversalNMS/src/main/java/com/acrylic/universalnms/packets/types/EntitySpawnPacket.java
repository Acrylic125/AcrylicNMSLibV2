package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.packets.SinglePacketWrapper;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntitySpawnPacket extends SinglePacketWrapper {

    void apply(@NotNull Entity entity);

}
