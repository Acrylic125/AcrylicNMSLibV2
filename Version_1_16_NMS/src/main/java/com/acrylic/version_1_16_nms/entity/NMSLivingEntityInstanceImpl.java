package com.acrylic.version_1_16_nms.entity;

import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.version_1_16_nms.NMSUtils;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftVector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

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

    @Override
    public void damage(@NotNull LivingEntity attacker) {
        attack(getNMSEntity(), NMSUtils.convertToNMSEntity(attacker));
    }

    @Override
    public void damage(@NotNull LivingEntity attacker, float damage) {
        damageEntity(getNMSEntity(), NMSUtils.convertToNMSEntity(attacker), damage);
    }

    @Override
    public void damage(float damage, @Nullable com.acrylic.universalnms.enums.DamageSource damageSource) {
        getNMSEntity().damageEntity(NMSUtils.convertToNMSDamageSource(damageSource), damage);
    }

    private void attack(EntityLiving target, EntityLiving attacker) {
        if (attacker instanceof EntityPlayer) {
            //Modified version of #EntityPlayer#attack(Entity)
            EntityPlayer attackerEntityPlayer = (EntityPlayer) attacker;
            if (target.bL() && !target.t(attackerEntityPlayer)) {
                float baseDamage = (float) attackerEntityPlayer.b(GenericAttributes.ATTACK_DAMAGE),
                      enchantDamageFactor = EnchantmentManager.a(attackerEntityPlayer.getItemInMainHand(), target.getMonsterType()),
                      attackSpeedFactor = 1;
                baseDamage *= 0.2F + attackSpeedFactor * attackSpeedFactor * 0.8F;
                enchantDamageFactor *= attackSpeedFactor;
                if (baseDamage > 0.0F || enchantDamageFactor > 0.0F) {
                    boolean flag1 = false;
                    byte b0 = 0;
                    int knockback = b0 + EnchantmentManager.b(attackerEntityPlayer);
                    if (attackerEntityPlayer.isSprinting()) {
                        attackerEntityPlayer.world.playSound(null, attackerEntityPlayer.locX(), attackerEntityPlayer.locY(), attackerEntityPlayer.locZ(), SoundEffects.ENTITY_PLAYER_ATTACK_KNOCKBACK, attackerEntityPlayer.getSoundCategory(), 1.0F, 1.0F);
                        knockback++;
                        flag1 = true;
                    }
                    //Crit check
                    boolean crit = attackerEntityPlayer.fallDistance > 0.0F &&
                            !attackerEntityPlayer.isOnGround() &&
                            !attackerEntityPlayer.isClimbing() &&
                            !attackerEntityPlayer.isInWater() &&
                            !attackerEntityPlayer.hasEffect(MobEffects.BLINDNESS) &&
                            !attackerEntityPlayer.isPassenger() &&
                            !attackerEntityPlayer.isSprinting();
                    if (crit)
                        baseDamage *= 1.5F;
                    baseDamage += enchantDamageFactor;
                    boolean swordAction = false;
                    double d0 = attackerEntityPlayer.A - attackerEntityPlayer.z;
                    if (!crit && !flag1 && attackerEntityPlayer.isOnGround() && d0 < (double)attackerEntityPlayer.dN()) {
                        ItemStack itemstack = attackerEntityPlayer.b(EnumHand.MAIN_HAND);
                        if (itemstack.getItem() instanceof ItemSword)
                            swordAction = true;
                    }

                    float oldHealth = target.getHealth();
                    boolean combustion = false;
                    int j = EnchantmentManager.getFireAspectEnchantmentLevel(attackerEntityPlayer);
                    if (j > 0 && !target.isBurning()) {
                        EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(attackerEntityPlayer.getBukkitEntity(), target.getBukkitEntity(), 1);
                        Bukkit.getPluginManager().callEvent(combustEvent);
                        if (!combustEvent.isCancelled()) {
                            combustion = true;
                            target.setOnFire(combustEvent.getDuration(), false);
                        }
                    }

                    Vec3D vec3d = target.getMot();
                    boolean flag5 = target.damageEntity(DamageSource.playerAttack(attackerEntityPlayer), baseDamage);
                    if (flag5) {
                        if (knockback > 0) {
                            target.a((float)knockback * 0.5F, MathHelper.sin(attackerEntityPlayer.yaw * 0.017453292F), -MathHelper.cos(attackerEntityPlayer.yaw * 0.017453292F));

                            attackerEntityPlayer.setMot(attackerEntityPlayer.getMot().d(0.6D, 1.0D, 0.6D));
                            attackerEntityPlayer.setSprinting(false);
                        }

                        if (swordAction) {
                            float f4 = 1.0F + EnchantmentManager.a(attackerEntityPlayer) * baseDamage;
                            List<EntityLiving> list = attackerEntityPlayer.world.a(EntityLiving.class, target.getBoundingBox().grow(1.0D, 0.25D, 1.0D));
                            Iterator<EntityLiving> iterator = list.iterator();

                            sweep: while(true) {
                                EntityLiving entityliving;
                                do {
                                    do {
                                        do {
                                            do {
                                                if (!iterator.hasNext()) {
                                                    attackerEntityPlayer.world.playSound(null, attackerEntityPlayer.locX(), attackerEntityPlayer.locY(), attackerEntityPlayer.locZ(), SoundEffects.ENTITY_PLAYER_ATTACK_SWEEP, attackerEntityPlayer.getSoundCategory(), 1.0F, 1.0F);
                                                    attackerEntityPlayer.ex();
                                                    break sweep;
                                                }

                                                entityliving = iterator.next();
                                            } while(entityliving == attackerEntityPlayer);
                                        } while(entityliving == target);
                                    } while(attackerEntityPlayer.r(entityliving));
                                } while(entityliving instanceof EntityArmorStand && ((EntityArmorStand)entityliving).isMarker());

                                if (attackerEntityPlayer.h(entityliving) < 9.0D && entityliving.damageEntity(DamageSource.playerAttack(attackerEntityPlayer).sweep(), f4)) {
                                    entityliving.a(0.4F, MathHelper.sin(attackerEntityPlayer.yaw * 0.017453292F), -MathHelper.cos(attackerEntityPlayer.yaw * 0.017453292F));
                                }
                            }
                        }

                        if (target instanceof EntityPlayer && target.velocityChanged) {
                            boolean cancelled = false;
                            Player player = (Player) target.getBukkitEntity();
                            Vector velocity = CraftVector.toBukkit(vec3d);
                            PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity.clone());
                            attackerEntityPlayer.world.getServer().getPluginManager().callEvent(event);
                            if (event.isCancelled()) {
                                cancelled = true;
                            } else if (!velocity.equals(event.getVelocity())) {
                                player.setVelocity(event.getVelocity());
                            }

                            if (!cancelled) {
                                ((EntityPlayer) target).playerConnection.sendPacket(new PacketPlayOutEntityVelocity(target));
                                target.velocityChanged = false;
                                target.setMot(vec3d);
                            }
                        }

                        if (crit) {
                            attackerEntityPlayer.world.playSound(null, attackerEntityPlayer.locX(), attackerEntityPlayer.locY(), attackerEntityPlayer.locZ(), SoundEffects.ENTITY_PLAYER_ATTACK_CRIT, attackerEntityPlayer.getSoundCategory(), 1.0F, 1.0F);
                            attackerEntityPlayer.a(target);
                        }

                        if (!crit && !swordAction) {
                            attackerEntityPlayer.world.playSound(null, attackerEntityPlayer.locX(), attackerEntityPlayer.locY(), attackerEntityPlayer.locZ(), SoundEffects.ENTITY_PLAYER_ATTACK_STRONG, attackerEntityPlayer.getSoundCategory(), 1.0F, 1.0F);
//                            Sound based on attack speed.
//                            if (flag) {
//                                attackerEntityPlayer.world.playSound((EntityHuman)null, attackerEntityPlayer.locX(), attackerEntityPlayer.locY(), attackerEntityPlayer.locZ(), SoundEffects.ENTITY_PLAYER_ATTACK_STRONG, attackerEntityPlayer.getSoundCategory(), 1.0F, 1.0F);
//                            } else {
//                                attackerEntityPlayer.world.playSound((EntityHuman)null, attackerEntityPlayer.locX(), attackerEntityPlayer.locY(), attackerEntityPlayer.locZ(), SoundEffects.ENTITY_PLAYER_ATTACK_WEAK, attackerEntityPlayer.getSoundCategory(), 1.0F, 1.0F);
//                            }
                        }

                        if (enchantDamageFactor > 0.0F) {
                            attackerEntityPlayer.b(target);
                        }

                        attackerEntityPlayer.z(target);
                        EnchantmentManager.a(target, attackerEntityPlayer);

                        EnchantmentManager.b(attackerEntityPlayer, target);
                        ItemStack weapon = attackerEntityPlayer.getItemInMainHand();
                        if (!attackerEntityPlayer.world.isClientSide && !weapon.isEmpty()) {
                            weapon.a(target, attackerEntityPlayer);
                            if (weapon.isEmpty()) {
                                attackerEntityPlayer.a(EnumHand.MAIN_HAND, ItemStack.b);
                            }
                        }

                        float f5 = oldHealth - target.getHealth();
                        attackerEntityPlayer.a(StatisticList.DAMAGE_DEALT, Math.round(f5 * 10.0F));
                        if (j > 0) {
                            EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(attackerEntityPlayer.getBukkitEntity(), target.getBukkitEntity(), j * 4);
                            Bukkit.getPluginManager().callEvent(combustEvent);
                            if (!combustEvent.isCancelled()) {
                                target.setOnFire(combustEvent.getDuration(), false);
                            }
                        }

                        if (attackerEntityPlayer.world instanceof WorldServer && f5 > 2.0F) {
                            int k = (int)((double)f5 * 0.5D);
                            ((WorldServer)attackerEntityPlayer.world).a(Particles.DAMAGE_INDICATOR, target.locX(), target.e(0.5D), target.locZ(), k, 0.1D, 0.0D, 0.1D, 0.2D);
                        }

                        attackerEntityPlayer.applyExhaustion(0.1F);
                    } else {
                        attackerEntityPlayer.world.playSound(null, attackerEntityPlayer.locX(), attackerEntityPlayer.locY(), attackerEntityPlayer.locZ(), SoundEffects.ENTITY_PLAYER_ATTACK_NODAMAGE, attackerEntityPlayer.getSoundCategory(), 1.0F, 1.0F);
                        if (combustion)
                            target.extinguish();
                    }
                }
            }
            return;
        }
        if (attacker instanceof EntityInsentient) {
            attacker.attackEntity(target);
            return;
        }
        AttributeModifiable attackDamage = attacker.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
        damageEntity(getNMSEntity(), attacker, attackDamage == null ? 1 : (float) attackDamage.getValue());
    }

    private void damageEntity(EntityLiving victim, EntityLiving attacker, float baseDamage) {
        if (victim != null) {
            baseDamage += EnchantmentManager.a(attacker.getItemInMainHand(), victim.getMonsterType());
        }
        assert victim != null;
        if (victim.damageEntity(DamageSource.mobAttack(attacker), baseDamage)) {
            knockback(victim, attacker);
            int fireAspectLevel = EnchantmentManager.getFireAspectEnchantmentLevel(attacker);
            if (fireAspectLevel > 0)
                victim.setOnFire(fireAspectLevel * 4);
        }
    }

    private void knockback(@NotNull EntityLiving victim, @NotNull EntityLiving attacker) {
        int knockback = EnchantmentManager.a(Enchantments.KNOCKBACK, attacker);
        if (knockback > 0) {
            victim.f(-Math.sin(attacker.yaw * Math.PI / 180.0F) * knockback * 0.5F, 0.1D,
                    Math.cos(attacker.yaw * Math.PI / 180.0F) * knockback * 0.5F);
            attacker.setMot(attacker.getMot().d(0.6, 1, 0.6));
        }
    }

    @Override
    public void knockback(@NotNull LivingEntity attacker) {
        knockback(getNMSEntity(), NMSUtils.convertToNMSEntity(attacker));
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

    @Override
    public void attack(@NotNull LivingEntity victim) {
        attack(NMSUtils.convertToNMSEntity(victim), getNMSEntity());
    }

}
