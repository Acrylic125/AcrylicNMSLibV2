package com.acrylic.version_1_8_nms;

import com.acrylic.universal.command.AbstractCommandBuilder;
import com.acrylic.universal.command.AbstractCommandExecuted;
import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universalnms.renderer.EntityRendererPlayer;
import com.acrylic.version_1_8_nms.entity.NMSArmorStandInstanceImpl;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Command {

    public static JavaPlugin plugin;

    public static AbstractCommandBuilder getCommand() {
        return CommandBuilder.create("1.8")
                .handle(commandExecuted -> {
                    ChatUtils.send(commandExecuted.getSender(),
                            "&6&lAcrylic NMS Lib (1.8)",
                            "&e/acrylicnms 1.8 test"
                            );
                }).arguments(new AbstractCommandBuilder[] {
                        getTestArgument()
                });
    }

    public static AbstractCommandBuilder getTestArgument() {
        return CommandBuilder.create("test")
                .filter(AbstractCommandExecuted::isPlayer)
                .handle(commandExecuted -> {
                    Player player = (Player) commandExecuted.getSender();
                    NMSArmorStandInstanceImpl armorStandInstance = new NMSArmorStandInstanceImpl(player.getLocation(), null);
                    armorStandInstance.getPacketHandler().setRenderer(new EntityRendererPlayer(armorStandInstance.getBukkitEntity()));
                    armorStandInstance.asAnimator();
                    Scheduler.sync().runRepeatingTask(1, 1)
                            .plugin(plugin)
                            .handleThenBuild(armorStandInstance::tick);
                });
    }

}
