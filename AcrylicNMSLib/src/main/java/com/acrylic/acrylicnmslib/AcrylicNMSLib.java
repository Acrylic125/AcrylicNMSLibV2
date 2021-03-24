package com.acrylic.acrylicnmslib;

import com.acrylic.universal.Universal;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.version_1_8_nms.factory.NMSAbstractFactoryImpl;
import com.acrylic.version_1_8_nms.nmsentityregistry.NMSEntityRegistryImpl;
import com.acrylic.version_1_8_nms.worldexaminer.BlockAnalyzerImpl;
import org.bukkit.plugin.java.JavaPlugin;

import static com.acrylic.universalnms.NMSLib.*;

public final class AcrylicNMSLib
        extends JavaPlugin {

    @Override
    public void onEnable() {
        NMSLib.setPlugin(this);
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
                setEntityRegistry(new NMSEntityRegistryImpl());
                setNMSAbstractFactory(new NMSAbstractFactoryImpl());
                setBlockAnalyzer(new BlockAnalyzerImpl());
                break;
            case 16:
                setEntityRegistry(new com.acrylic.version_1_16_nms.nmsentityregistry.NMSEntityRegistryImpl());
                setNMSAbstractFactory(new com.acrylic.version_1_16_nms.factory.NMSAbstractFactoryImpl());
                setBlockAnalyzer(new com.acrylic.version_1_16_nms.worldexaminer.BlockAnalyzerImpl());
                break;
            default:
                throw new IllegalStateException("1." + version + " is not supported.");
        }
        getNMSEntityRegistry().registerDefaults();
    }

}
