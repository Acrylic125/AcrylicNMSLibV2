package com.acrylic.universalnms.packets.types;

import com.acrylic.universalnms.packets.SinglePacketWrapper;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface TeleportPacket extends SinglePacketWrapper {

    void apply(@NotNull Entity entity, @NotNull Location location);

    void apply(@NotNull Entity entity);

}
