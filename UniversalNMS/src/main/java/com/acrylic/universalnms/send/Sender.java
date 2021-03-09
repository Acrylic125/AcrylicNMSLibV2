package com.acrylic.universalnms.send;

import com.acrylic.universalnms.renderer.Renderer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface Sender {

    void sendTo(@NotNull Player sendTo);

    default void sendToAll(@NotNull Collection<? extends Player> sendTo) {
        for (Player t : sendTo)
            sendTo(t);
    }

    default void sendToAll(@NotNull Player... sendTo) {
        for (Player t : sendTo)
            sendTo(t);
    }

    default void sendToAllByRenderer(@NotNull Renderer<Player> renderer) {
        renderer.runForAllRendered(this::sendTo);
    }

    default void sendToAllOnline() {
        sendToAll(Bukkit.getOnlinePlayers());
    }

}
