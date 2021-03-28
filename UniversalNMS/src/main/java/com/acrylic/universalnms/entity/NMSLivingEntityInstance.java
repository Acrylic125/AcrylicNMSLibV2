package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.LivingEntityInstance;
import com.acrylic.universal.entity.equipment.EntityEquipmentBuilder;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.entityconfiguration.EntityConfiguration;
import com.acrylic.universalnms.entity.entityconfiguration.LivingEntityConfiguration;
import com.acrylic.universalnms.entity.wrapper.NMSLivingEntityWrapper;
import com.acrylic.universalnms.enums.DamageSource;
import com.acrylic.universalnms.enums.EntityAnimationEnum;
import com.acrylic.universalnms.packets.types.EntityAnimationPackets;
import com.acrylic.universalnms.packets.types.EntityEquipmentPackets;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NMSLivingEntityInstance extends NMSEntityInstance, LivingEntityInstance {

    @Override
    LivingEntityConfiguration getEntityConfiguration();

    @Override
    LivingEntityPacketHandler getPacketHandler();

    @Override
    NMSLivingEntityWrapper getEntityWrapper();

    int getMaxNoDamageCooldown();

    void setMaxNoDamageCooldown(int ticks);

    void knockback(@NotNull LivingEntity attacker);

    void damage(@NotNull LivingEntity attacker);

    void damage(@NotNull LivingEntity attacker, float damage);

    void damage(float damage, @Nullable DamageSource damageSource);

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

    default void setCrouching(boolean b) {
        setAnimationDataWatcher(0x02, b);
    }

    @Deprecated
    default void setRiding(boolean b) {
        setAnimationDataWatcher(0x04, b);
    }

    default void setSprinting(boolean b) {
        setAnimationDataWatcher(0x08, b);
    }

    default void setSwimming(boolean b) {
        setAnimationDataWatcher(0x10, b);
    }

    default void setGlowing(boolean b) {
        setAnimationDataWatcher(0x40, b);
    }

    default void setUsingElytra(boolean b) {
        setAnimationDataWatcher(0x80, b);
    }

    @Override
    default void tick(TickSource tickSource) {
        NMSEntityInstance.super.tick(tickSource);
        if (getFireTicks() > 0 && !getBukkitEntity().isInvulnerable())
            damage(0.5f, DamageSource.BURN);
    }

    default void animateOnce(EntityAnimationEnum animation) {
        EntityAnimationPackets entityAnimationPackets = NMSLib.getPacketFactory().getNewEntityAnimationPackets();
        entityAnimationPackets.attach(getBukkitEntity(), animation);
        entityAnimationPackets.getSender().sendToAllByRenderer(getPacketHandler().getRenderer());
    }

    default void animateManyOnce(EntityAnimationEnum... animations) {
        EntityAnimationPackets entityAnimationPackets = NMSLib.getPacketFactory().getNewEntityAnimationPackets();
        entityAnimationPackets.attach(getBukkitEntity(), animations);
        entityAnimationPackets.getSender().sendToAllByRenderer(getPacketHandler().getRenderer());
    }

    default void addAnimation(EntityAnimationEnum animation) {
        LivingEntityPacketHandler packetHandler = getPacketHandler();
        EntityAnimationPackets entityAnimationPackets = packetHandler.getAnimationPackets();
        entityAnimationPackets.attach(getBukkitEntity(), animation);
        entityAnimationPackets.getSender().sendToAllByRenderer(packetHandler.getRenderer());
    }

    default void addAnimations(EntityAnimationEnum... animations) {
        LivingEntityPacketHandler packetHandler = getPacketHandler();
        EntityAnimationPackets entityAnimationPackets = packetHandler.getAnimationPackets();
        entityAnimationPackets.attach(getBukkitEntity(), animations);
        entityAnimationPackets.getSender().sendToAllByRenderer(packetHandler.getRenderer());
    }

    default void setAnimation(EntityAnimationEnum animation) {
        LivingEntityPacketHandler packetHandler = getPacketHandler();
        EntityAnimationPackets entityAnimationPackets = packetHandler.getAnimationPackets();
        entityAnimationPackets.set(getBukkitEntity(), animation);
        entityAnimationPackets.getSender().sendToAllByRenderer(packetHandler.getRenderer());
    }

    default void setAnimations(EntityAnimationEnum... animations) {
        LivingEntityPacketHandler packetHandler = getPacketHandler();
        EntityAnimationPackets entityAnimationPackets = packetHandler.getAnimationPackets();
        entityAnimationPackets.set(getBukkitEntity(), animations);
        entityAnimationPackets.getSender().sendToAllByRenderer(packetHandler.getRenderer());
    }

}
