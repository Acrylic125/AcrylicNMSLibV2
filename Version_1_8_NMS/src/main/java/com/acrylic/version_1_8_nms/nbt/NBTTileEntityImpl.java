package com.acrylic.version_1_8_nms.nbt;

import com.acrylic.universalnms.nbt.NBTTileEntity;
import com.acrylic.version_1_8_nms.NMSUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class NBTTileEntityImpl extends NBTTileEntity {

    private final TileEntity tileEntity;
    private NBTCompoundImpl compound;

    public NBTTileEntityImpl(@NotNull Block block, @NotNull NBTCompoundImpl compound) {
        super(block);
        this.tileEntity = NMSUtils.convertToNMSTileEntity(block);
        this.compound = compound;
    }

    public NBTTileEntityImpl(@NotNull Block block) {
        super(block);
        this.tileEntity = NMSUtils.convertToNMSTileEntity(block);
        NBTTagCompound compound = new NBTTagCompound();
        tileEntity.b(compound);
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
        if (tileEntity == null)
            return;
        tileEntity.a(getCompound().getTagCompound());
        tileEntity.update();
    }
}
