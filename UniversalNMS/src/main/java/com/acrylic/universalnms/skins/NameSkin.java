package com.acrylic.universalnms.skins;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Level;

public class NameSkin implements Skin {

    @Nullable
    public static NameSkin create(@NotNull String name) {
        return create(name, Bukkit.getOfflinePlayer(name).getUniqueId());
    }

    @Nullable
    public static NameSkin create(@NotNull String name, @NotNull UUID uuid) {
        try {
            String sig = null, text = null;
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" +
                    uuid.toString() + "?unsigned=false");
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonObject property = new JsonParser().parse(reader).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            JsonElement value = property.get("value");
            JsonElement signature = property.get("signature");
            if (value != null && signature != null) {
                sig = value.getAsString();
                text = signature.getAsString();
            }
            reader.close();
            if (sig == null || text == null) {
                Bukkit.getLogger().log(Level.ALL, "Skin querying " + uuid + " failed! The skin does not have a valid texture or signature.");
            } else {
                Bukkit.getLogger().log(Level.ALL, "Skin querying " + uuid + " succeeded!");
                return new NameSkin(name, sig, text);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.getLogger().log(Level.ALL, "Skin querying " + uuid + " failed!");
        }
        return null;
    }

    private final String name;
    private final String signature, texture;

    public NameSkin(@NotNull String name, @NotNull String signature, @NotNull String texture) {
        this.name = name;
        this.signature = signature;
        this.texture = texture;
    }

    @NotNull
    @Override
    public String getTexture() {
        return texture;
    }

    @NotNull
    @Override
    public String getSignature() {
        return signature;
    }

    @NotNull
    @Override
    public String getID() {
        return name;
    }
}
