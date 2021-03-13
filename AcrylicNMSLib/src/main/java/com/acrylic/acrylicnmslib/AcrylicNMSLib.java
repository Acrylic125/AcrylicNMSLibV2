package com.acrylic.acrylicnmslib;

import com.acrylic.acrylicnmslib.plugin.AcrylicNMSPlugin;
import com.acrylic.universal.Universal;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.factory.NMSAbstractFactory;
import com.acrylic.universalnms.nmsentityregistry.AbstractNMSEntityRegistry;
import com.acrylic.version_1_8_nms.factory.NMSAbstractFactoryImpl;
import com.acrylic.version_1_8_nms.nmsentityregistry.NMSEntityRegistryImpl;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class AcrylicNMSLib
        extends JavaPlugin {

    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        NMSLib.setAcrylicNMSPlugin(new NMSLib());
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
        NMSLib nmsLib = NMSLib.getNMSPlugin();
        switch (version) {
            case 8:
                nmsLib.setEntityRegistry(new NMSEntityRegistryImpl());
                nmsLib.setNMSAbstractFactory(new NMSAbstractFactoryImpl());
                break;
            case 16:
                nmsLib.setEntityRegistry(new com.acrylic.version_1_16_nms.nmsentityregistry.NMSEntityRegistryImpl());
                nmsLib.setNMSAbstractFactory(new com.acrylic.version_1_16_nms.factory.NMSAbstractFactoryImpl());
                break;
            default:
                throw new IllegalStateException("1." + version + " is not supported.");
        }
        nmsLib.getNMSEntityRegistry().registerDefaults();
    }

    @NotNull
    public static JavaPlugin getPlugin() {
        return plugin;
    }

}
