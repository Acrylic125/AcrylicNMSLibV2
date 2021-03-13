package com.acrylic.universalnms.nbt;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class NBTTileEntity implements NBT {

    private final Block originalBlock;
    private NBTCompound nbtCompound;

    public NBTTileEntity(@NotNull Block block) {
        this.originalBlock = block;
    }

    public void setNBTCompound(NBTCompound nbtCompound) {
        this.nbtCompound = nbtCompound;
    }

    @Override
    public NBTCompound getCompound() {
        return this.nbtCompound;
    }

    public Block getOriginalBlock() {
        return originalBlock;
    }

    public abstract void update();

}
