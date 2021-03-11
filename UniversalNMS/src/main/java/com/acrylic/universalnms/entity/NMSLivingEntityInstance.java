package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.LivingEntityInstance;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilder;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.packets.types.EntityEquipmentPackets;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.jetbrains.annotations.NotNull;

public interface NMSLivingEntityInstance extends NMSEntityInstance, LivingEntityInstance {

    @Override
    LivingEntityPacketHandler getPacketHandler();

    @Override
    NMSLivingEntityWrapper getEntityWrapper();

    int getMaxNoDamageCooldown();

    void setMaxNoDamageCooldown(int ticks);

    void knockback(@NotNull LivingEntity attacker);

    void damage(@NotNull LivingEntity attacker);

    void damage(@NotNull LivingEntity attacker, float damage);

    void damage(float damage);

    @Override
    default void setEquipment(@NotNull EntityEquipmentBuilder entityEquipmentBuilder) {
        entityEquipmentBuilder.apply(getBukkitEntity());
        EntityEquipmentPackets equipmentPackets = getPacketHandler().getEquipmentPackets();
        equipmentPackets.setEquipment(entityEquipmentBuilder);
        equipmentPackets.apply(getBukkitEntity());
    }

    @Override
    default void setEquipment(@NotNull EntityEquipment entityEquipment) {
        EntityEquipmentBuilder.cloneEquipment(getBukkitEntity().getEquipment(), entityEquipment);
        EntityEquipmentPackets equipmentPackets = getPacketHandler().getEquipmentPackets();
        equipmentPackets.setEquipment(entityEquipment);
        equipmentPackets.apply(getBukkitEntity());
    }
}
