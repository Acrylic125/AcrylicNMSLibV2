package com.acrylic.acrylicnmslib;

import com.acrylic.time.Time;
import com.acrylic.universal.Universal;
import com.acrylic.universal.files.bukkit.Configuration;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.entity.manager.NMSEntities;
import com.acrylic.universalnms.entityai.processors.AsyncProcessor;
import com.acrylic.universalnms.send.GlobalBatchPacketSender;
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
        NMSLib.setNMSEntities(new NMSEntities(this));
        NMSLib.setNPCTablistRemover(
                new GlobalBatchPacketSender<>(
                        Scheduler.sync().
                                runRepeatingTask(10, Time.SECONDS, 10, Time.SECONDS)
                                .plugin(this)
                )
        );
        AsyncProcessor.TEMP = new AsyncProcessor<>(this);
        Configuration config = new Configuration("skins.yml", this);
        config.load();
        NMSLib.getSkinMap().loadSkinMapFromConfig(config);
        // Plugin startup logic
        Command.createCommand();
        loadByVersion();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Configuration config = new Configuration("skins.yml", this);
        config.load();
        NMSLib.getNMSEntities().terminate();
        NMSLib.getSkinMap().saveTo(config);
        NMSLib.getNPCTablistRemover().terminate();
        AsyncProcessor.TEMP.terminate();
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
