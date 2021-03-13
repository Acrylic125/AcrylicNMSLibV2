package com.acrylic.version_1_8_nms.nbt;

import com.acrylic.universal.items.ItemUtils;
import com.acrylic.universalnms.nbt.NBTItem;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NBTItemImpl extends NBTItem {
    private final net.minecraft.server.v1_8_R3.ItemStack nmsItem;
    private NBTCompoundImpl compound;

    public NBTItemImpl(@NotNull ItemStack item, @NotNull NBTCompoundImpl compound) {
        super(item);
        this.compound = compound;
        this.nmsItem = (ItemUtils.isAir(item)) ? null : NMSUtils.convertToNMSItem(item);
    }

    public NBTItemImpl(@NotNull ItemStack item, boolean saveTag) {
        super(item);
        if (ItemUtils.isAir(item)) {
            this.nmsItem = null;
            this.compound = new NBTCompoundImpl();
        } else {
            this.nmsItem = NMSUtils.convertToNMSItem(item);
            NBTTagCompound nbtTagCompound = nmsItem.getTag();
            if (nbtTagCompound == null && saveTag) {
                nbtTagCompound = new NBTTagCompound();
                nmsItem.save(nbtTagCompound);
            }
            this.compound = new NBTCompoundImpl(nbtTagCompound);
        }
    }

    @Override
    public ItemStack getItem() {
        if (nmsItem == null)
            return getOriginalItem();
        nmsItem.setTag(compound.getTagCompound());
        return NMSUtils.convertToBukkitItem(nmsItem);
    }

    public void setCompound(NBTCompoundImpl compound) {
        this.compound = compound;
    }

    @NotNull
    @Override
    public NBTCompoundImpl getCompound() {
        return compound;
    }
}
