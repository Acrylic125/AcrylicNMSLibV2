package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.equipment.EntityEquipmentBuilder;
import com.acrylic.universal.packet.types.EntityAnimationPackets;
import com.acrylic.universal.packet.types.EntityEquipmentPackets;
import com.acrylic.universal.packet.types.EntityMetadataPacket;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.jetbrains.annotations.NotNull;

public interface NMSLivingEntityInstance extends NMSEntityInstance, LivingEntityInstance {


    int getMaxNoDamageCooldown();

    void setMaxNoDamageCooldown(int ticks);

    void knockback(@NotNull LivingEntity attacker);

    void attack(@NotNull LivingEntity victim);

    EntityAnimationPackets getEntityAnimationPackets();

    @NotNull
    EntityEquipmentPackets getEquipmentPackets();

    EntityMetadataPacket getMetadataPacket();

    @Override
    void setEquipment(@NotNull EntityEquipmentBuilder entityEquipmentBuilder);

    @Override
    void setEquipment(@NotNull EntityEquipment entityEquipment);
}
