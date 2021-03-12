package com.acrylic.universalnms.nbt;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class NBTItem implements NBT {

    private final ItemStack originalItem;

    public NBTItem(@NotNull ItemStack item) {
        this.originalItem = item;
    }

    public ItemStack getOriginalItem() {
        return originalItem;
    }

    public abstract ItemStack getItem();

    @Override
    public String toString() {
        return "AbstractNBTItem{" +
                "originalItem=" + originalItem +
                "compound=" + getCompound() +
                '}';
    }
}
