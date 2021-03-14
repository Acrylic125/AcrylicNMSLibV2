package com.acrylic.universalnms.nbt;

import files.editor.Configurable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface NBTCompound extends Configurable {

    NBTCompound set(@NotNull String var, byte val);

    NBTCompound set(@NotNull String var, byte[] val);

    NBTCompound set(@NotNull String var, short val);

    NBTCompound set(@NotNull String var, int val);

    NBTCompound set(@NotNull String var, int[] val);

    NBTCompound set(@NotNull String var, long val);

    NBTCompound set(@NotNull String var, float val);

    NBTCompound set(@NotNull String var, double val);

    NBTCompound set(@NotNull String var, char val);

    NBTCompound set(@NotNull String var, boolean val);

    NBTCompound setArray(@NotNull String var, byte... val);

    NBTCompound setArray(@NotNull String var, int... val);

    NBTCompound set(@NotNull String var, @NotNull String val);

    NBTCompound set(@NotNull String var, @NotNull List<?> val);

    default NBTCompound set(@NotNull String var, @NotNull UUID uuid) {
        return set(var, uuid.toString());
    }

    String getName();

    @Nullable
    NBTCompound getParent();

    NBTCompound removeKey(@NotNull String var);

    boolean hasKey(@NotNull String var);

    boolean getBoolean(@NotNull String var);

    Set<String> getKeys();

    int[] getIntArray(@NotNull String var);

    byte[] getByteArray(@NotNull String var);

    @Nullable
    NBTCompound getCompoundIfExist(@NotNull String var);

    @NotNull
    NBTCompound getCompound(@NotNull String var);

    @NotNull
    @Override
    default NBTCompound set(@NotNull String s, @NotNull Object o) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    default Number getDecimalNumber(@NotNull String s) {
        return getDouble(s);
    }

    @NotNull
    @Override
    default Number getNumber(@NotNull String s) {
        return getDouble(s);
    }

    @NotNull
    @Override
    default Number getWholeNumber(@NotNull String s) {
        return getInt(s);
    }

    String getCompoundString();

}
