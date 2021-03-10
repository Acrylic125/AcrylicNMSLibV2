package com.acrylic.version_1_8_nms.nmsentityregistry;

import com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NMSEntityRegistryImpl extends AbstractNMSEntityRegistry {

    @Override
    public void registerEntity(int id, String name, EntityType entityType, Class<?> mainClass, Class<?> nmsEntityClass) {
        if (mainClass.isAssignableFrom(EntityInsentient.class))
            throw new IllegalArgumentException("The main class must be an instance of " + EntityInsentient.class.getName());
        if (nmsEntityClass.isAssignableFrom(EntityInsentient.class))
            throw new IllegalArgumentException("The NMS Entity class must be an instance of " + EntityInsentient.class.getName());
        try {

            List<Map<?, ?>> dataMap = new ArrayList<>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    dataMap.add((Map<?, ?>) f.get(null));
                    f.setAccessible(false);
                }
            }

            Map<?, ?> mMap = dataMap.get(2);
            if (mMap.containsKey(id)){
                dataMap.get(0).remove(name);
                mMap.remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, mainClass, name, id);
            method.setAccessible(false);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void registerDefaults() {

    }
}
