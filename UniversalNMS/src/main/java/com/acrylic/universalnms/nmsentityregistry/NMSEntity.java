package com.acrylic.universalnms.nmsentityregistry;

import org.bukkit.entity.EntityType;

import static com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry.*;

public @interface NMSEntity {

    EntityType bukkitEntityTYpe();

    String name();

    Class<?> entityClass();

    int id() default CHECK_ID;

}
