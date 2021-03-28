package com.acrylic.universalnms.entityai.aiimpl;

import com.acrylic.universal.entity.EntityInstance;
import com.acrylic.universalnms.entity.NMSEntityInstance;
import com.acrylic.universalnms.entity.NMSLivingEntityInstance;
import com.acrylic.universalnms.enums.EntityAnimationEnum;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class AggressiveAI extends TargettableAIImpl {

    private float attackRange = 3;
    private long attackCooldown = 500,
                 attackTime = 0;

    public AggressiveAI(@NotNull NMSEntityInstance nmsEntityInstance) {
        super(nmsEntityInstance);
    }

    @Override
    public void tick() {
        super.tick();
        Entity target = getTarget();
        EntityInstance instance = getInstance();
        if (target instanceof LivingEntity && instance instanceof NMSLivingEntityInstance) {
            NMSLivingEntityInstance livingEntityInstance = (NMSLivingEntityInstance) instance;
            long sysTime = System.currentTimeMillis();
            if (attackTime < sysTime && target.getLocation().distanceSquared(instance.getBukkitEntity().getLocation()) <= (attackRange * attackRange)) {
                livingEntityInstance.attack((LivingEntity) target);
                livingEntityInstance.animateOnce(EntityAnimationEnum.ARM_SWING);
                attackTime = sysTime + attackCooldown;
            }
        }
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public long getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(long attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

}
