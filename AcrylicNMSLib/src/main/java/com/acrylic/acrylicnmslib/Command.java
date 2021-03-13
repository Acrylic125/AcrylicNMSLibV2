package com.acrylic.acrylicnmslib;

import com.acrylic.universal.command.AbstractCommandBuilder;
import com.acrylic.universal.command.CommandBuilder;
import com.acrylic.universal.text.ChatUtils;
import com.acrylic.universalnms.NMSLib;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command {

    public static void createCommand() {
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
        }).register(NMSLib.getPlugin());
    }

}
