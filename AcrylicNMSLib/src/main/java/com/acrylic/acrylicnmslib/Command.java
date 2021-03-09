package com.acrylic.acrylicnmslib;

import com.acrylic.universal.command.CommandBuilder;

public class Command {

    public static void createCommand() {
        CommandBuilder.create("acrylicnms")
                .register();
    }

}
