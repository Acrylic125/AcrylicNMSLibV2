package com.acrylic.universalnms.packets.types;

import com.acrylic.universal.Universal;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilder;
import com.acrylic.universalnms.packets.MultiPacketWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface EntityEquipmentPackets extends MultiPacketWrapper {

    void apply(@NotNull LivingEntity entity);

    default void setEquipment(@NotNull LivingEntity entity) {
        setEquipment(entity.getEquipment());
    }

    default void setEquipment(@NotNull EntityEquipment equipment) {
        setHelmet(equipment.getHelmet());
        setChestplate(equipment.getChestplate());
        setLeggings(equipment.getLeggings());
        setBoots(equipment.getBoots());
        if (Universal.getAcrylicPlugin().getVersionStore().isLegacyVersion()) {
            setItemInHand(equipment.getItemInHand());
        } else {
            setItemInHand(equipment.getItemInMainHand());
            setItemInOffHand(equipment.getItemInOffHand());
        }
    }

    default void setEquipment(@NotNull EntityEquipmentBuilder equipmentBuilder) {
        setHelmet(equipmentBuilder.getHelmet());
        setChestplate(equipmentBuilder.getChestplate());
        setLeggings(equipmentBuilder.getLeggings());
        setBoots(equipmentBuilder.getBoots());
        setItemInHand(equipmentBuilder.getItemInHand());
        if (!Universal.getAcrylicPlugin().getVersionStore().isLegacyVersion()) {
            setItemInOffHand(equipmentBuilder.getItemInOffHand());
        }
    }

    void setHelmet(ItemStack item);

    void setChestplate(ItemStack item);

    void setLeggings(ItemStack item);

    void setBoots(ItemStack item);

    void setItemInHand(ItemStack item);

    void setItemInOffHand(ItemStack item);


}
