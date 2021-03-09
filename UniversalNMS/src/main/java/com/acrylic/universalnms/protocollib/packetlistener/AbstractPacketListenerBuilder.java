package com.acrylic.universalnms.protocollib.packetlistener;

import com.acrylic.universal.Universal;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface AbstractPacketListenerBuilder {

    AbstractPacketListenerBuilder priority(@NotNull ListenerPriority priority);

    ListenerPriority getPriority();

    AbstractPacketListenerBuilder handleReceivingPacket(@NotNull Consumer<PacketEvent> handle);

    Consumer<PacketEvent> getPacketReceivingHandle();

    AbstractPacketListenerBuilder handleSendingPacket(@NotNull Consumer<PacketEvent> handle);

    Consumer<PacketEvent> getPacketSendingHandle();

    AbstractPacketListenerBuilder packetTypes(PacketType... packetTypes);

    PacketType[] getPacketTypes();

    default void build() {
        build(Universal.getPlugin());
    }

    default void build(@NotNull JavaPlugin plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, getPriority(), getPacketTypes()) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Consumer<PacketEvent> action = getPacketReceivingHandle();
                if (action != null)
                    action.accept(event);
            }

            @Override
            public void onPacketSending(PacketEvent event) {
                Consumer<PacketEvent> action = getPacketSendingHandle();
                if (action != null)
                    action.accept(event);
            }
        });
    }

}
