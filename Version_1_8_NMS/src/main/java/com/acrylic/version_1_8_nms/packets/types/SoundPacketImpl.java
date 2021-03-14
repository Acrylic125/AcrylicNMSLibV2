package com.acrylic.version_1_8_nms.packets.types;

import com.acrylic.universalnms.enums.SoundCategory;
import com.acrylic.universalnms.packets.types.SoundPacket;
import com.acrylic.version_1_8_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoundPacketImpl
        extends SinglePacketWrapperImpl
        implements SoundPacket {

    private PacketPlayOutNamedSoundEffect packet;

    @Override
    public void apply(@NotNull Object sound, @Nullable SoundCategory soundCategory, @NotNull Location location, float volume, float pitch) {
        packet = new PacketPlayOutNamedSoundEffect((sound instanceof String) ? (String) sound : sound.toString(), location.getX(), location.getY(), location.getZ(), volume, pitch);
    }

    @Override
    public PacketPlayOutNamedSoundEffect getPacket() {
        return packet;
    }

}
