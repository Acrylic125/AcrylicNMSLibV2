package com.acrylic.universalnms.send;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class BatchSender implements Sender {

    private final Collection<SingleSender> singleSenders;

    public BatchSender(@NotNull Collection<SingleSender> singleSenders) {
        this.singleSenders = singleSenders;
    }

    public BatchSender attachSend(@NotNull SingleSender singleSender) {
        singleSenders.add(singleSender);
        return this;
    }

    @Override
    public void sendTo(@NotNull Player sendTo) {

    }
}
