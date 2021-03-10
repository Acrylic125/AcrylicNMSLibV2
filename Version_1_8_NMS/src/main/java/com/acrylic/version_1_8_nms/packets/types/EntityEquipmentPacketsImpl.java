package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universalnms.packets.types.EntityEquipmentPackets;
import com.acrylic.version_1_8_nms.NMSUtils;
import com.acrylic.version_1_8_nms.packets.MultiPacketWrapperImpl;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityEquipmentPacketsImpl
        extends MultiPacketWrapperImpl
        implements EntityEquipmentPackets {

    private final Map<Integer, net.minecraft.server.v1_8_R3.ItemStack> itemStackMap = new HashMap<>(5);
    private ArrayList<PacketPlayOutEntityEquipment> entityEquipments;

    public void apply(@NotNull EntityLiving entity) {
        entityEquipments = new ArrayList<>(itemStackMap.size());
        int id = entity.getId();
        itemStackMap.forEach((i, item) -> entityEquipments.add(new PacketPlayOutEntityEquipment(id, i, item)));
    }

    @Override
    public void apply(@NotNull LivingEntity entity) {
        apply(NMSUtils.convertToNMSEntity(entity));
    }

    @Override
    public void setHelmet(ItemStack item) {
        itemStackMap.put(4, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setChestplate(ItemStack item) {
        itemStackMap.put(3, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setLeggings(ItemStack item) {
        itemStackMap.put(2, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setBoots(ItemStack item) {
        itemStackMap.put(1, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setItemInHand(ItemStack item) {
        itemStackMap.put(0, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setItemInOffHand(ItemStack item) {
        throw new IllegalStateException("1.8 does not support items in offhand.");
    }

    @Override
    public Collection<PacketPlayOutEntityEquipment> getPackets() {
        return entityEquipments;
    }
}
