package com.acrylic.acrylicnmslib;

import com.acrylic.universal.command.AbstractCommandBuilder;
import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.text.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command {

    public static void createCommand() {
        com.acrylic.version_1_8_nms.Command.plugin = AcrylicNMSLib.getPlugin();
        CommandBuilder.create("acrylicnms")
                .handle(commandExecuted -> {
                    CommandSender sender = commandExecuted.getSender();
                    Player player = (Player) sender;

                    ChatUtils.send(sender,
                            "&e&lAcrylic NMS Lib",
                            "&e/acrylicnms <version>"
                    );
                }).arguments(new AbstractCommandBuilder[] {
                com.acrylic.version_1_8_nms.Command.getCommand(),
                com.acrylic.version_1_16_nms.Command.getCommand()
        }).register(AcrylicNMSLib.getPlugin());
    }

}
