package com.acrylic.universalnms.particles;

import com.acrylic.universalnms.NMSLib;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public abstract class ParticleBuilder<B extends ParticleBuilder<B>> {

    public static NormalBuilder builder() {
        return new NormalBuilder();
    }

    public static NormalBuilder builder(@NotNull EnumWrappers.Particle particle) {
        return new NormalBuilder(particle);
    }

    public static ItemParticleBuilder itemParticleBuilder() {
        return new ItemParticleBuilder();
    }

    public static ItemParticleBuilder itemParticleBuilder(@NotNull EnumWrappers.Particle particle) {
        return new ItemParticleBuilder(particle);
    }

    public static ItemParticleBuilder itemCrackParticleBuilder() {
        return itemParticleBuilder(EnumWrappers.Particle.ITEM_CRACK);
    }

    public static ItemParticleBuilder itemTakeParticleBuilder() {
        return itemParticleBuilder(EnumWrappers.Particle.ITEM_TAKE);
    }

    public static ItemParticleBuilder blockDustParticleBuilder() {
        return itemParticleBuilder(EnumWrappers.Particle.BLOCK_DUST);
    }

    public static ItemParticleBuilder fallingDustParticleBuilder() {
        return itemParticleBuilder(EnumWrappers.Particle.FALLING_DUST);
    }

    public static ColorParticleBuilder colorBuilder(@NotNull EnumWrappers.Particle particle) {
        return new ColorParticleBuilder(particle);
    }

    public static ColorParticleBuilder redstoneBuilder() {
        return colorBuilder(EnumWrappers.Particle.REDSTONE);
    }

    public abstract Particles getParticles();

    public B amount(int amount) {
        getParticles().setAmount(amount);
        return (B) this;
    }

    public B speed(float speed) {
        getParticles().setSpeed(speed);
        return (B) this;
    }

    public B longDistance(boolean longDistance) {
        getParticles().setLongDistance(longDistance);
        return (B) this;
    }

    public B offset(float x, float y, float z) {
        getParticles().setOffset(x, y, z);
        return (B) this;
    }

    public B location(Location location) {
        getParticles().setLocation(location);
        return (B) this;
    }

    public B location(float x, float y, float z) {
        getParticles().setLocation(x, y, z);
        return (B) this;
    }

    public abstract Particles build();

    public static class NormalBuilder extends ParticleBuilder<NormalBuilder> {

        private final Particles particles;

        private NormalBuilder() {
            this(NMSLib.getFactory().getNMSUtilsFactory().getNewParticles());
        }

        private NormalBuilder(EnumWrappers.Particle particleType) {
            this(NMSLib.getFactory().getNMSUtilsFactory().getNewParticles());
            particles.setParticleType(particleType);
        }

        private NormalBuilder(Particles particles) {
            this.particles = particles;
        }

        public NormalBuilder particleType(EnumWrappers.Particle particleType) {
            this.particles.setParticleType(particleType);
            return this;
        }

        @Override
        public Particles getParticles() {
            return particles;
        }

        @Override
        public Particles build() {
            particles.build();
            return particles;
        }
    }

    public static class ItemParticleBuilder extends ParticleBuilder<ItemParticleBuilder> {

        private final ItemParticles particles;

        private ItemParticleBuilder() {
            this(NMSLib.getFactory().getNMSUtilsFactory().getNewItemParticles());
        }

        private ItemParticleBuilder(EnumWrappers.Particle particleType) {
            this(NMSLib.getFactory().getNMSUtilsFactory().getNewItemParticles());
            particles.setParticleType(particleType);
        }

        private ItemParticleBuilder(ItemParticles particles) {
            this.particles = particles;
        }

        public ItemParticleBuilder item(ItemStack item) {
            this.particles.setItem(item);
            return this;
        }

        @Override
        public ItemParticles getParticles() {
            return particles;
        }

        @Override
        public ItemParticles build() {
            particles.build();
            return particles;
        }
    }

    public static class ColorParticleBuilder extends ParticleBuilder<ColorParticleBuilder> {

        private final ColorParticles particles;

        private ColorParticleBuilder() {
            this(NMSLib.getFactory().getNMSUtilsFactory().getNewColorParticles());
        }

        private ColorParticleBuilder(EnumWrappers.Particle particleType) {
            this(NMSLib.getFactory().getNMSUtilsFactory().getNewColorParticles());
            particles.setParticleType(particleType);
        }

        private ColorParticleBuilder(ColorParticles particles) {
            this.particles = particles;
        }

        public ColorParticleBuilder rgb(RGB rgb) {
            this.particles.setRGB(rgb);
            return this;
        }

        public ColorParticleBuilder rgb(float r, float g, float b) {
            this.particles.setRGB(r, g, b);
            return this;
        }

        public ColorParticleBuilder rainbow() {
            this.particles.rainbow();
            return this;
        }

        @Override
        public ColorParticles getParticles() {
            return particles;
        }

        @Override
        public ColorParticles build() {
            particles.build();
            return particles;
        }
    }

}
