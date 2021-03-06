package com.acrylic.version_1_8_nms.entity;

import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NMSLivingEntityInstanceImpl
        extends NMSEntityInstanceImpl
        implements NMSLivingEntityInstance {

    @Override
    public void setName(String s) {
        super.setName(s);
        getPacketHandler().updateMetadata();
    }

    @Override
    public abstract EntityLiving getNMSEntity();

    @Override
    public int getMaxNoDamageCooldown() {
        return getNMSEntity().maxNoDamageTicks;
    }

    @Override
    public void setMaxNoDamageCooldown(int ticks) {
        getNMSEntity().maxNoDamageTicks = ticks;
    }

    public void knockback(@NotNull EntityLiving nmsLivingAttacker) {
        int knockback = 1;
        knockback += EnchantmentManager.a(nmsLivingAttacker);
        if (knockback > 0) {
            double x = (-Math.sin(Math.toRadians(nmsLivingAttacker.yaw)) * knockback * 0.5f * 0.6f),
                    y = 0.1f,
                    z = (Math.cos(Math.toRadians(nmsLivingAttacker.yaw)) * knockback * 0.5f * 0.6f);
            setVelocity(x, y, z);
        }
    }

    @Override
    public void knockback(@NotNull LivingEntity attacker) {
        knockback(NMSUtils.convertToNMSEntity(attacker));
    }

    @Override
    public void damage(@NotNull LivingEntity attacker) {
        damage(getNMSEntity(), NMSUtils.convertToNMSEntity(attacker));
    }

    private void damage(EntityLiving victim, EntityLiving attacker) {
        if (attacker instanceof EntityPlayer) {
            EntityPlayer entityLiving = (EntityPlayer) attacker;
            entityLiving.attack(victim);
        } else {
            AttributeInstance attackDamage = attacker.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
            float baseDamage = (float) (attackDamage == null ? 1.0f : attackDamage.getValue());
            baseDamage += EnchantmentManager.a(attacker.bA(), victim.getMonsterType());
            damageEntity(victim, attacker, baseDamage);
        }
    }

    @Override
    public void damage(@NotNull LivingEntity attacker, float damage) {
        damageEntity(getNMSEntity(), NMSUtils.convertToNMSEntity(attacker), damage);
    }

    @Override
    public void damage(float damage, @Nullable com.acrylic.universalnms.enums.DamageSource damageSource) {
        getNMSEntity().damageEntity(NMSUtils.convertToNMSDamageSource(damageSource), damage);
    }

    private void damageEntity(EntityLiving victim, EntityLiving nmsLivingAttacker, float baseDamage) {
        boolean flag = victim.damageEntity(DamageSource.mobAttack(nmsLivingAttacker), baseDamage);
        if (flag) {
            int fireAspectLevel = EnchantmentManager.getFireAspectEnchantmentLevel(nmsLivingAttacker);
            if (fireAspectLevel > 0)
                victim.setOnFire(fireAspectLevel * 4);
        }
    }

    @Override
    public void attack(@NotNull LivingEntity victim) {
        damage(NMSUtils.convertToNMSEntity(victim), getNMSEntity());
    }

    @Override
    public void setAI(boolean b) {
        getNMSEntity().ai = b;
    }

    @Override
    public void setVisible(boolean b) {
        getNMSEntity().setInvisible(!b);
    }

    @Override
    public void setMaxHealth(double v) {
        getNMSEntity().getAttributeInstance(GenericAttributes.maxHealth).setValue(v);
    }

    @Override
    public float getMaxHealth() {
        return getNMSEntity().getMaxHealth();
    }

    @Override
    public void setHealth(double v) {
        getNMSEntity().setHealth((float) v);
    }

}
