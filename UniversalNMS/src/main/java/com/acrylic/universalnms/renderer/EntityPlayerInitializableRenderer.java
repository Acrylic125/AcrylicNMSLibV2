package com.acrylic.universalnms.renderer;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

public class EntityPlayerInitializableRenderer extends RangedPlayerInitializableRenderer {

    private final Entity entity;

    public EntityPlayerInitializableRenderer(@NotNull Entity entity) {
        super();
        this.entity = entity;
    }

    public EntityPlayerInitializableRenderer(@NotNull Entity entity, @NotNull Collection<UUID> rendered) {
        super(rendered);
        this.entity = entity;
    }

    @Override
    public synchronized void doChecks() {
        setLocation(entity.getLocation());
        super.doChecks();
    }

    @Override
    public EntityPlayerInitializableRenderer clone() {
        return new EntityPlayerInitializableRenderer(entity, getRendered());
    }
}
