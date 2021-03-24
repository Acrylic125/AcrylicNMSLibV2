package com.acrylic.universalnms.send;

import com.acrylic.universal.interfaces.Terminable;
import com.acrylic.universal.threads.Scheduler;
import com.acrylic.universal.threads.TaskType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;

public class GlobalBatchPacketSender<T extends Sendable> implements Terminable, Runnable {

    private final Collection<Executable<T>> executables;
    private final Scheduler<? extends TaskType.RepeatingTask> scheduler;

    public GlobalBatchPacketSender(@NotNull Scheduler<? extends TaskType.RepeatingTask> scheduler) {
        this(scheduler, new HashSet<>());
    }

    public GlobalBatchPacketSender(@NotNull Scheduler<? extends TaskType.RepeatingTask> scheduler, @NotNull Collection<Executable<T>> executables) {
        this.scheduler = scheduler;
        this.executables = executables;
    }

    public void add(@NotNull Player player, @NotNull T sender) {
        this.executables.add(new Executable<>(player, sender));
    }

    public void start() {
        scheduler.handleThenBuild(this);
    }

    @Override
    public void terminate() {
        scheduler.cancel();
    }

    @Override
    public synchronized void run() {
        for (Executable<T> executable : executables)
            executable.fire();
        executables.clear();
    }

    private static class Executable<T extends Sendable> {

        private final WeakReference<Player> playerWeakReference;
        private final T sendable;

        private Executable(Player player, T sendable) {
            this.playerWeakReference = new WeakReference<>(player);
            this.sendable = sendable;
        }

        public Player getPlayer() {
            return playerWeakReference.get();
        }

        public void fire() {
            Player player = getPlayer();
            if (player != null)
                sendable.getSender().sendTo(player);
        }

    }

}
