package com.acrylic.universalnms.packets.types;

import com.acrylic.universal.entity.equipment.EntityEquipmentBuilder;
import com.acrylic.universalnms.packets.MultiPacketWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.EntityEquipment;
import org.jetbrains.annotations.NotNull;

public interface EntityEquipmentPackets extends MultiPacketWrapper {

    void apply(@NotNull Entity entity, @NotNull EntityEquipment equipment);

    void apply(@NotNull Entity entity, @NotNull EntityEquipmentBuilder equipmentBuilder);

}
