package com.acrylic.universalnms.send;

import com.acrylic.universalnms.renderer.Renderer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public class BatchSender implements Sender {

    private final Collection<BatchableSender> batchableSenders;

    public BatchSender(@NotNull Collection<BatchableSender> batchableSenders) {
        this.batchableSenders = batchableSenders;
    }

    public void attachSender(@NotNull BatchableSender batchableSender) {
        batchableSenders.add(batchableSender);
    }

    @Override
    public void sendTo(@NotNull Player sendTo) {
        for (BatchableSender batchableSender : batchableSenders) {
            batchableSender.onInitialBatchCall();
            batchableSender.batchSend(sendTo);
        }
    }

    @Override
    public void sendToAll(@NotNull Collection<? extends Player> sendTo) {
        boolean initial = true;
        for (Player player : sendTo) {
            for (BatchableSender batchableSender : batchableSenders) {
                if (initial)
                    batchableSender.onInitialBatchCall();
                batchableSender.batchSend(player);
            }
            if (initial)
                initial = false;
        }
    }

    @Override
    public void sendToAll(@NotNull Player... sendTo) {
        boolean initial = true;
        for (Player player : sendTo) {
            for (BatchableSender batchableSender : batchableSenders) {
                if (initial)
                    batchableSender.onInitialBatchCall();
                batchableSender.batchSend(player);
            }
            if (initial)
                initial = false;
        }
    }

    @Override
    public void sendToAllByRenderer(@NotNull Renderer<Player> renderer) {
        final AtomicBoolean initial = new AtomicBoolean(true);
        renderer.runForAllRendered(player -> {
            boolean i = initial.get();
            for (BatchableSender batchableSender : batchableSenders) {
                if (i)
                    batchableSender.onInitialBatchCall();
                batchableSender.batchSend(player);
            }
            if (i)
                initial.set(false);
        });
    }

}
