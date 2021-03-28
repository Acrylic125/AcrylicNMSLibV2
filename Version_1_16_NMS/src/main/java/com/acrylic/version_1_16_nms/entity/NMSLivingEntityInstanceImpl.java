package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.version_1_16_nms.NMSUtils;
import net.citizensnpcs.util.NMSBridge;
import net.citizensnpcs.util.PlayerAnimation;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        //
//        EntityLiving target = getHandle(btarget);
//        if (nmsAttacker instanceof EntityPlayer) {
//            EntityPlayer humanHandle = (EntityPlayer)handle;
//            humanHandle.attack(target);
//            PlayerAnimation.ARM_SWING.play(humanHandle.getBukkitEntity());
//        } else if (handle instanceof EntityInsentient) {
//            ((EntityInsentient)handle).attackEntity(target);
//        } else {
//            AttributeModifiable attackDamage = handle.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
//            float f = (float)(attackDamage == null ? 1.0D : attackDamage.getValue());
//            int i = 0;
//            if (target instanceof EntityLiving) {
//                f += EnchantmentManager.a(handle.getItemInMainHand(), target.getMonsterType());
//                i += EnchantmentManager.a(Enchantments.KNOCKBACK, handle);
//            }
//
//            boolean flag = target.damageEntity(DamageSource.mobAttack(handle), f);
//            if (flag) {
//                if (i > 0) {
//                    target.f(-Math.sin((double)handle.yaw * 3.141592653589793D / 180.0D) * (double)i * 0.5D, 0.1D, Math.cos((double)handle.yaw * 3.141592653589793D / 180.0D) * (double)i * 0.5D);
//                    handle.setMot(handle.getMot().d(0.6D, 1.0D, 0.6D));
//                }
//
//                int fireAspectLevel = EnchantmentManager.getFireAspectEnchantmentLevel(handle);
//                if (fireAspectLevel > 0) {
//                    target.setOnFire(fireAspectLevel * 4);
//                }
//
//            }
//        }
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
