package com.acrylic.version_1_16_nms.packets.types;

import com.acrylic.universalnms.packets.types.EntityEquipmentPackets;
import com.acrylic.version_1_16_nms.NMSUtils;
import com.acrylic.version_1_16_nms.packets.MultiPacketWrapperImpl;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EntityEquipmentPacketsImpl
        extends MultiPacketWrapperImpl
        implements EntityEquipmentPackets {

    private final Map<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> itemStackMap = new HashMap<>(5);
    private ArrayList<PacketPlayOutEntityEquipment> entityEquipments;

    public void apply(@NotNull EntityLiving entity) {
        entityEquipments = new ArrayList<>(1);
        List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipment = new ArrayList<>(itemStackMap.size());
        int id = entity.getId();
        itemStackMap.forEach((slot, item) -> {
            equipment.add(new Pair<>(slot, item));
        });
        entityEquipments.add(new PacketPlayOutEntityEquipment(id, equipment));
    }

    @Override
    public void apply(@NotNull LivingEntity entity) {
        apply(NMSUtils.convertToNMSEntity(entity));
    }

    @Override
    public void setHelmet(ItemStack item) {
        itemStackMap.put(EnumItemSlot.HEAD, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setChestplate(ItemStack item) {
        itemStackMap.put(EnumItemSlot.CHEST, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setLeggings(ItemStack item) {
        itemStackMap.put(EnumItemSlot.LEGS, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setBoots(ItemStack item) {
        itemStackMap.put(EnumItemSlot.FEET, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setItemInHand(ItemStack item) {
        itemStackMap.put(EnumItemSlot.MAINHAND, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public void setItemInOffHand(ItemStack item) {
        itemStackMap.put(EnumItemSlot.OFFHAND, (item == null) ? null : NMSUtils.convertToNMSItem(item));
    }

    @Override
    public Collection<PacketPlayOutEntityEquipment> getPackets() {
        return entityEquipments;
    }
}
