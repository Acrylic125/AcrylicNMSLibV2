package com.acrylic.version_1_16_nms.nmsentityregistry;

import com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry;
import com.acrylic.version_1_16_nms.entity.wrapper.ArmorStandWrapper;
import com.acrylic.version_1_16_nms.entity.wrapper.GiantWrapper;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NMSEntityRegistryImpl extends AbstractNMSEntityRegistry {

    @Override
    public void registerEntity(int id, String name, EntityType entityType, Class<?> mainClass, Class<?> nmsEntityClass) {
        if (mainClass.isAssignableFrom(EntityInsentient.class))
            throw new IllegalArgumentException("The main class must be an instance of " + EntityInsentient.class.getName());
        if (nmsEntityClass.isAssignableFrom(EntityInsentient.class))
            throw new IllegalArgumentException("The NMS Entity class must be an instance of " + EntityInsentient.class.getName());
         // a is a class field of type EntityTypes.a<EntityLiving>
        name = name.toLowerCase(Locale.ROOT);
        EntityTypes.Builder<Entity> a = EntityTypes.Builder.a(EntityTypes::a, EnumCreatureType.CREATURE);
        IRegistry.a(IRegistry.ENTITY_TYPE, name, a.a(name));
    }

    private static Object getPrivateField(String fieldName, Class<?> clazz, Object object) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void registerDefaults() {
        registerNMSEntityClass(ArmorStandWrapper.class);
        registerNMSEntityClass(GiantWrapper.class);
    }
}
