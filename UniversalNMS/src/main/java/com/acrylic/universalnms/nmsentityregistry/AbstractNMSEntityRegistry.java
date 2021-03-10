package com.acrylic.universalnms.nmsentityregistry;

import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractNMSEntityRegistry {

    public static final int
            //Base exclusion id.
            EXCLUDED_ID = -1,
            //Checks the entity id via bukkit.
            CHECK_ID = -2;

    public void registerNMSEntityClass(@NotNull Class<?> entityClass) {
        NMSEntity annotation = entityClass.getAnnotation(NMSEntity.class);
        if (annotation != null) {
            int id = annotation.id();
            EntityType entityType = annotation.bukkitEntityTYpe();
            if (id == CHECK_ID)
                id = entityType.getTypeId();
            registerEntity(id, annotation.name(), entityType, entityClass, annotation.entityClass());
        } else
            throw new IllegalArgumentException("The class does not have the @NMSEntity annotation.");
    }

    public abstract void registerEntity(int id, String name, EntityType entityType, Class<?> mainClass, Class<?> nmsEntityClass);

    public abstract void registerDefaults();

}
