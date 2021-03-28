package com.acrylic.universalnms.entityai.strategyimpl;

import com.acrylic.universal.Universal;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entityai.PathSeekerAI;
import com.acrylic.universalnms.entityai.strategies.PathQuitterStrategy;
import com.acrylic.universalnms.particles.ParticleBuilder;
import com.acrylic.universalnms.renderer.Renderer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SimplePathQuitterStrategyImpl implements PathQuitterStrategy {

    private final PathSeekerAI pathSeekerAI;
    private boolean locked = false;
    private float minimumRangeToConsiderQuitting = 3;
    private long timeToQuit, outOfRangeTimeRequiredToQuit;

    public SimplePathQuitterStrategyImpl(@NotNull PathSeekerAI pathSeekerAI) {
        this(pathSeekerAI, 10_000);
    }

    public SimplePathQuitterStrategyImpl(@NotNull PathSeekerAI pathSeekerAI, long outOfRangeTimeRequiredToQuit) {
        this.pathSeekerAI = pathSeekerAI;
        this.outOfRangeTimeRequiredToQuit = outOfRangeTimeRequiredToQuit;
        this.timeToQuit = System.currentTimeMillis() + outOfRangeTimeRequiredToQuit;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void tick() {
        Location target = pathSeekerAI.getTargetLocation();
        if (target != null) {
            if (target.distanceSquared(pathSeekerAI.getInstance().getBukkitEntity().getLocation()) <= (minimumRangeToConsiderQuitting * minimumRangeToConsiderQuitting)) {
                reset();
            } else if (timeToQuit < System.currentTimeMillis()) {
                reset();
                teleportTo(target);
            }
        } else {
            reset();
        }
    }

    public void reset() {
        timeToQuit = System.currentTimeMillis() + outOfRangeTimeRequiredToQuit;
    }

    protected void teleportTo(Location target) {
        pathSeekerAI.getInstance().teleport(target);
        Renderer<Player> renderer = pathSeekerAI.getInstance().getPacketHandler().getRenderer();
        ParticleBuilder.builder(EnumWrappers.Particle.SMOKE_LARGE)
                .speed(0.1f)
                .amount(10)
                .location(target)
                .build()
                .getSender()
                .sendToAllByRenderer(renderer);
        if (!Universal.getAcrylicPlugin().getVersionStore().isLegacyVersion())
            renderer.runForAllRendered(player -> player.playSound(target, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.6f));
    }

    @NotNull
    @Override
    public PathSeekerAI getEntityAI() {
        return pathSeekerAI;
    }

    public float getMinimumRangeToConsiderQuitting() {
        return minimumRangeToConsiderQuitting;
    }

    public void setMinimumRangeToConsiderQuitting(float minimumRangeToConsiderQuitting) {
        this.minimumRangeToConsiderQuitting = minimumRangeToConsiderQuitting;
    }

    public long getTimeToQuit() {
        return timeToQuit;
    }

    public void setTimeToQuit(long timeToQuit) {
        this.timeToQuit = timeToQuit;
    }

    public long getOutOfRangeTimeRequiredToQuit() {
        return outOfRangeTimeRequiredToQuit;
    }

    public void setOutOfRangeTimeRequiredToQuit(long outOfRangeTimeRequiredToQuit) {
        this.outOfRangeTimeRequiredToQuit = outOfRangeTimeRequiredToQuit;
    }
}
