package com.acrylic.universalnms.nmsentityregistry;

import org.bukkit.entity.EntityType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NMSEntity {

    EntityType bukkitEntityTYpe();

    String name();

    Class<?> entityClass();

    int id() default CHECK_ID;

}
