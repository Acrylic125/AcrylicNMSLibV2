package com.acrylic.version_1_16_nms.packets.types;

import com.acrylic.universalnms.enums.SoundCategory;
import com.acrylic.universalnms.packets.types.SoundPacket;
import com.acrylic.version_1_16_nms.packets.SinglePacketWrapperImpl;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_16_R3.SoundEffect;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoundPacketImpl
        extends SinglePacketWrapperImpl
        implements SoundPacket {

    private PacketPlayOutNamedSoundEffect packet;

    @Override
    public void apply(@NotNull Object sound, @NotNull SoundCategory soundCategory, @NotNull Location location, float volume, float pitch) {
        net.minecraft.server.v1_16_R3.SoundCategory category = convertToNMSSoundCategory(soundCategory);
        if (sound instanceof SoundEffect) {
            packet = new PacketPlayOutNamedSoundEffect((SoundEffect) sound, category, location.getX(), location.getY(), location.getZ(), volume, pitch);
        } else {
            throw new IllegalArgumentException(sound + " is not a valid SoundEffect.");
        }
    }

    @Override
    public PacketPlayOutNamedSoundEffect getPacket() {
        return packet;
    }

    @Nullable
    public static net.minecraft.server.v1_16_R3.SoundCategory convertToNMSSoundCategory(SoundCategory soundCategory) {
        switch (soundCategory) {
            case MUSIC:
                return net.minecraft.server.v1_16_R3.SoundCategory.MUSIC;
            case VOICE:
                return net.minecraft.server.v1_16_R3.SoundCategory.VOICE;
            case BLOCKS:
                return net.minecraft.server.v1_16_R3.SoundCategory.BLOCKS;
            case MASTER:
                return net.minecraft.server.v1_16_R3.SoundCategory.MASTER;
            case AMBIENT:
                return net.minecraft.server.v1_16_R3.SoundCategory.AMBIENT;
            case HOSTILE:
                return net.minecraft.server.v1_16_R3.SoundCategory.HOSTILE;
            case NEUTRAL:
                return net.minecraft.server.v1_16_R3.SoundCategory.NEUTRAL;
            case PLAYERS:
                return net.minecraft.server.v1_16_R3.SoundCategory.PLAYERS;
            case RECORDS:
                return net.minecraft.server.v1_16_R3.SoundCategory.RECORDS;
            case WEATHER:
                return net.minecraft.server.v1_16_R3.SoundCategory.WEATHER;
            default:
                return null;
        }
    }

}
