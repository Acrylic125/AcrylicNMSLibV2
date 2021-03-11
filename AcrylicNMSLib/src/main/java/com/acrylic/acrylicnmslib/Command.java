package com.acrylic.acrylicnmslib;

import com.acrylic.universal.command.AbstractCommandBuilder;
import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.text.ChatUtils;
import org.bukkit.command.CommandSender;

public class Command {

    public static void createCommand() {
        com.acrylic.version_1_8_nms.Command.plugin = AcrylicNMSLib.getPlugin();
        CommandBuilder.create("acrylicnms")
                .handle(commandExecuted -> {
                    CommandSender sender = commandExecuted.getSender();
                    ChatUtils.send(sender,
                            "&e&lAcrylic NMS Lib",
                            "&e/acrylicnms <version>"
                    );
                }).arguments(new AbstractCommandBuilder[] {
                com.acrylic.version_1_8_nms.Command.getCommand()
        }).register(AcrylicNMSLib.getPlugin());
    }

}
