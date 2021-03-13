package com.acrylic.universalnms.particles;

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ItemParticles
        extends AbstractParticles {

    void setItem(ItemStack item);

    Object getParticleType();

    void setParticleType(@NotNull EnumWrappers.Particle particle);

}
