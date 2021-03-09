package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.LivingEntityInstance;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilder;
import com.acrylic.universalnms.packets.types.EntityEquipmentPackets;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.jetbrains.annotations.NotNull;

public interface NMSLivingEntityInstance extends NMSEntityInstance, LivingEntityInstance {

    int getMaxNoDamageCooldown();

    void setMaxNoDamageCooldown(int ticks);

    void knockback(@NotNull LivingEntity attacker);

    void damage(@NotNull LivingEntity attacker);

    void damage(@NotNull LivingEntity attacker, float damage);

    void damage(float damage);

    @Override
    void setEquipment(@NotNull EntityEquipmentBuilder entityEquipmentBuilder);

    @Override
    void setEquipment(@NotNull EntityEquipment entityEquipment);
}
