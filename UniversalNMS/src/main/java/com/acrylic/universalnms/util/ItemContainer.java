package com.acrylic.universalnms.util;

import com.acrylic.universal.items.ItemUtils;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.nbt.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemContainer {

    private final ItemStack item;
    private final NBTItem nbtItem;

    public ItemContainer(@Nullable ItemStack item) {
        this(item, (ItemUtils.isAir(item)) ? null : NMSLib.getFactory().getNMSUtilsFactory().getNewNBTItem(item));
    }

    public ItemContainer(@Nullable ItemStack item, @Nullable NBTItem nbtItem) {
        this.item = item;
        this.nbtItem = nbtItem;
    }

    @Nullable
    public ItemStack getItem() {
        return item;
    }

    @Nullable
    public NBTItem getNBTItem() {
        return nbtItem;
    }
}
