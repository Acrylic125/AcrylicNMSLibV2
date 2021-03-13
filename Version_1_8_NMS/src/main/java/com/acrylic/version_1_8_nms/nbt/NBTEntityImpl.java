package com.acrylic.version_1_8_nms.nbt;

import com.acrylic.universalnms.nbt.NBTCompound;
import com.acrylic.universalnms.nbt.NBTEntity;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NBTEntityImpl extends NBTEntity {

    private final net.minecraft.server.v1_8_R3.Entity nmsEntity;
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
        nmsEntity.e(compound);
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
            ((EntityLiving) nmsEntity).a(getCompound().getTagCompound());
        }
    }
}
