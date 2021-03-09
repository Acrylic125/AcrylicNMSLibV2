package com.acrylic.universalnms.nbt;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractNBTTileEntity implements AbstractNBT {

    private final Block originalBlock;
    private AbstractNBTCompound nbtCompound;

    public AbstractNBTTileEntity(@NotNull Block block) {
        this.originalBlock = block;
    }

    public void setNBTCompound(AbstractNBTCompound nbtCompound) {
        this.nbtCompound = nbtCompound;
    }

    @Override
    public AbstractNBTCompound getCompound() {
        return this.nbtCompound;
    }

    public Block getOriginalBlock() {
        return originalBlock;
    }

    public abstract void update();

}
