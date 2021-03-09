package com.acrylic.universalnms.protocollib.packetlistener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PacketListenerBuilder implements AbstractPacketListenerBuilder {

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

    @Override
    public AbstractPacketListenerBuilder priority(@NotNull ListenerPriority priority) {
        this.listenerPriority = priority;
        return this;
    }

    @Override
    public ListenerPriority getPriority() {
        return listenerPriority;
    }

    @Override
    public AbstractPacketListenerBuilder handleReceivingPacket(@NotNull Consumer<PacketEvent> handle) {
        this.handleReceivingPackets = handle;
        return this;
    }

    @Override
    public Consumer<PacketEvent> getPacketReceivingHandle() {
        return handleReceivingPackets;
    }

    @Override
    public AbstractPacketListenerBuilder handleSendingPacket(@NotNull Consumer<PacketEvent> handle) {
        this.handleSendingPackets = handle;
        return this;
    }

    @Override
    public Consumer<PacketEvent> getPacketSendingHandle() {
        return handleSendingPackets;
    }

    @Override
    public AbstractPacketListenerBuilder packetTypes(PacketType... packetTypes) {
        this.packetTypes = packetTypes;
        return this;
    }

    @Override
    public PacketType[] getPacketTypes() {
        return packetTypes;
    }
}
