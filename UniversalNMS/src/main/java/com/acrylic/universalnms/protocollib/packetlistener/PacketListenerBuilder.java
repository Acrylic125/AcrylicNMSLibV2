package com.acrylic.universalnms.protocollib.packetlistener;

import com.acrylic.universal.MCLib;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class PacketListenerBuilder {

    private ListenerPriority listenerPriority = ListenerPriority.NORMAL;
    private Consumer<PacketEvent> handleReceivingPackets;
    private Consumer<PacketEvent> handleSendingPackets;
    private PacketType[] packetTypes;

    public static PacketListenerBuilder listen(@NotNull PacketType... packetTypes) {
        return new PacketListenerBuilder(packetTypes);
    }

    private PacketListenerBuilder(PacketType[] packetTypes) {
        this.packetTypes = packetTypes;
    }

    public PacketListenerBuilder priority(@NotNull ListenerPriority priority) {
        this.listenerPriority = priority;
        return this;
    }

    public ListenerPriority getPriority() {
        return listenerPriority;
    }

    public PacketListenerBuilder handleReceivingPacket(@NotNull Consumer<PacketEvent> handle) {
        this.handleReceivingPackets = handle;
        return this;
    }

    public Consumer<PacketEvent> getPacketReceivingHandle() {
        return handleReceivingPackets;
    }

    public PacketListenerBuilder handleSendingPacket(@NotNull Consumer<PacketEvent> handle) {
        this.handleSendingPackets = handle;
        return this;
    }

    public Consumer<PacketEvent> getPacketSendingHandle() {
        return handleSendingPackets;
    }

    public PacketListenerBuilder packetTypes(PacketType... packetTypes) {
        this.packetTypes = packetTypes;
        return this;
    }

    public PacketType[] getPacketTypes() {
        return packetTypes;
    }

    public void build() {
        build(MCLib.getPlugin());
    }

    public void build(@NotNull JavaPlugin plugin) {
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
