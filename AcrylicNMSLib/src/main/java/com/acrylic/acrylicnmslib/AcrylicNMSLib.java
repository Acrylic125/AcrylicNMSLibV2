package com.acrylic.acrylicnmslib;

import com.acrylic.acrylicnmslib.plugin.AcrylicNMSPlugin;
import com.acrylic.universal.Universal;
import com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry;
import com.acrylic.version_1_8_nms.nmsentityregistry.NMSEntityRegistryImpl;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class AcrylicNMSLib
        extends JavaPlugin
        implements AcrylicNMSPlugin {

    private static AcrylicNMSPlugin acrylicNMSPlugin;
    private static JavaPlugin plugin;

    private AbstractNMSEntityRegistry entityRegistry;

    @Override
    public void onEnable() {
        acrylicNMSPlugin = this;
        plugin = this;
        // Plugin startup logic
        Command.createCommand();
        loadByVersion();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadByVersion() {
        short version = Universal.getAcrylicPlugin().getVersionStore().getVersion();
        switch (version) {
            case 8:
                entityRegistry = new NMSEntityRegistryImpl();
                break;
            case 16:
            default:
                throw new IllegalStateException("1." + version + " is not supported.");
        }
        entityRegistry.registerDefaults();
    }

    @NotNull
    @Override
    public AbstractNMSEntityRegistry getNMSEntityRegistry() {
        return entityRegistry;
    }

    @NotNull
    public static JavaPlugin getPlugin() {
        return plugin;
    }

    @NotNull
    public static AcrylicNMSPlugin getNMSPlugin() {
        return acrylicNMSPlugin;
    }
}
