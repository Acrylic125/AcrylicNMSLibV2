package com.acrylic.universalnms.entity;

import com.acrylic.universal.entity.LivingEntityBuilder;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.enums.Gamemode;
import com.acrylic.universalnms.packets.types.PlayerInfoPacket;
import com.acrylic.universalnms.skins.Skin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NMSPlayerInstance
        extends NMSLivingEntityInstance {

    static Builder builder(Location location, String name) {
        return new Builder(NMSLib.getEntityFactory().getNewNMSPlayerInstance(location, name));
    }

    static Builder builder(NMSPlayerInstance nmsPlayerInstance) {
        return new Builder(nmsPlayerInstance);
    }

    class Builder extends LivingEntityBuilder<Builder> {

        private final NMSPlayerInstance playerInstance;

        public Builder(NMSPlayerInstance playerInstance) {
            this.playerInstance = playerInstance;
        }

        public Builder skin(@NotNull String signature, @NotNull String texture) {
            this.playerInstance.setSkin(signature, texture);
            return this;
        }

        public Builder skin(@NotNull String skin) {
            this.playerInstance.setSkin(skin);
            return this;
        }

        public Builder skin(@NotNull Skin skin) {
            this.playerInstance.setSkin(skin);
            return this;
        }

        public Player buildEntity() {
            return this.playerInstance.getBukkitEntity();
        }

        public NMSPlayerInstance buildEntityInstance() {
            return this.playerInstance;
        }

        public NMSPlayerInstance getBuildFrom() {
            return this.playerInstance;
        }
    }

    @NotNull
    @Override
    Player getBukkitEntity();

    @Override
    PlayerPacketHandler getPacketHandler();

    void setGamemode(@NotNull Gamemode gamemode);

    void setSkinWithoutRefreshing(String signature, String texture);

    default void setSkin(String signature, String texture) {
        PlayerPacketHandler entityPacketHandler = getPacketHandler();
        setSkinWithoutRefreshing(signature, texture);
        Player player = getBukkitEntity();
        entityPacketHandler.getAddPlayerInfoPacket().apply(PlayerInfoPacket.Info.ADD_PLAYER, player);
        entityPacketHandler.getMetadataPacket().apply(player);
        entityPacketHandler.getSpawnPacket().apply(player);
        entityPacketHandler.resendPackets();
    }

    default void setSkin(@Nullable Skin skin) {
        if (skin == null)
            setSkin(null, null);
        else
            setSkin(skin.getSignature(), skin.getTexture());
    }

    default void setSkin(@Nullable String skinName) {
        if (skinName == null)
            setSkin(null, null);
        else
            NMSLib.getSkinMap().asyncGetAndActOnSkin(skinName, this::setSkin);
    }

}
