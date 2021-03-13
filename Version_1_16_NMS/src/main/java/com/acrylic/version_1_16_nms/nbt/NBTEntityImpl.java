package com.acrylic.version_1_16_nms.nbt;

import com.acrylic.universalnms.nbt.NBTEntity;
import com.acrylic.version_1_16_nms.NMSUtils;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class NBTEntityImpl extends NBTEntity {

    private final net.minecraft.server.v1_16_R3.Entity nmsEntity;
    private NBTCompoundImpl compound;

    public NBTEntityImpl(@NotNull Entity entity, @NotNull NBTCompoundImpl compound) {
        super(entity);
        this.nmsEntity = NMSUtils.convertToNMSEntity(entity);
        this.compound = compound;
    }

    public NBTEntityImpl(@NotNull Entity entity) {
        super(entity);
        this.nmsEntity = NMSUtils.convertToNMSEntity(entity);
        NBTTagCompound compound = new NBTTagCompound();
        nmsEntity.save(compound);
        this.compound = new NBTCompoundImpl(compound);
    }

    public void setCompound(@NotNull NBTCompoundImpl compound) {
        this.compound = compound;
    }

    @NotNull
    @Override
    public NBTCompoundImpl getCompound() {
        return compound;
    }

    @Override
    public void update() {
        if (nmsEntity instanceof EntityLiving) {
            ((EntityLiving) nmsEntity).save(getCompound().getTagCompound());
        }
    }
}
