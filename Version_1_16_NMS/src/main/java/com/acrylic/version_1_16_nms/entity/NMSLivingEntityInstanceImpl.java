package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.version_1_16_nms.NMSUtils;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public abstract class NMSLivingEntityInstanceImpl
        extends NMSEntityInstanceImpl
        implements NMSLivingEntityInstance {

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
        EntityLiving nmsAttacker = NMSUtils.convertToNMSEntity(attacker);
        EntityLiving victim = getNMSEntity();
        if (nmsAttacker instanceof EntityPlayer) {
            EntityPlayer entityLiving = (EntityPlayer) nmsAttacker;
            entityLiving.attack(victim);
        } else {
            AttributeModifiable attackDamage = nmsAttacker.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
            float baseDamage = (float) (attackDamage == null ? 1.0f : attackDamage.getValue());
            //baseDamage += EnchantmentManager.a(nmsAttacker.bA(), victim.getMonsterType());
            damageEntity(victim, nmsAttacker, baseDamage);
        }
    }

    @Override
    public void damage(@NotNull LivingEntity attacker, float damage) {
        damageEntity(getNMSEntity(), NMSUtils.convertToNMSEntity(attacker), damage);
    }

    @Override
    public void damage(float damage) {
        getNMSEntity().damageEntity(DamageSource.GENERIC, damage);
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
    public void setAI(boolean b) {
        getNMSEntity().ai = b;
    }

    @Override
    public void setVisible(boolean b) {
        getNMSEntity().setInvisible(!b);
    }

    @Override
    public void setMaxHealth(double v) {
        getNMSEntity().getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(v);
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
