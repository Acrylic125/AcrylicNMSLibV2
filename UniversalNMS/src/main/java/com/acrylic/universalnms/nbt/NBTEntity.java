package com.acrylic.universalnms.nbt;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public abstract class NBTEntity implements NBT {

    private final Entity originalEntity;

    public NBTEntity(@NotNull Entity entity) {
        this.originalEntity = entity;
    }

    public boolean isLivingEntity() {
        return originalEntity instanceof LivingEntity;
    }

    public Entity getOriginalEntity() {
        return originalEntity;
    }

    public abstract void update();

    @Override
    public String toString() {
        return "AbstractNBTEntity{" +
                "originalEntity=" + originalEntity +
                ", nbtCompound=" + getCompound() +
                '}';
    }
}
