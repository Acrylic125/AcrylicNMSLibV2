package com.acrylic.universalnms.skins;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

public class SkinImpl implements Skin {

    @Nullable
    public static SkinImpl create(@NotNull String name) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://api.ashcon.app/mojang/v2/user/%s", name)).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                ArrayList<String> lines = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                reader.lines().forEach(lines::add);

                String reply = String.join(" ",lines);
                int indexOfValue = reply.indexOf("\"value\": \"");
                int indexOfSignature = reply.indexOf("\"signature\": \"");
                String skin = reply.substring(indexOfValue + 10, reply.indexOf("\"", indexOfValue + 10));
                String signature = reply.substring(indexOfSignature + 14, reply.indexOf("\"", indexOfSignature + 14));

                Bukkit.getLogger().log(Level.ALL, "Skin querying " + name + " succeeded!");
                return new SkinImpl(name, signature, skin);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.getLogger().log(Level.ALL, "Skin querying " + name + " failed!");
        }
        return null;
    }

    private final String name;
    private final String signature, texture;

    public SkinImpl(@NotNull String name, @NotNull String signature, @NotNull String texture) {
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

    @Override
    public String toString() {
        return "NameSkin{" +
                "name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                ", texture='" + texture + '\'' +
                '}';
    }
}
