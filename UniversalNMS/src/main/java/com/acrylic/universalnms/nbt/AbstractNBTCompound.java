package com.acrylic.universalnms.nbt;

import files.editor.Configurable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AbstractNBTCompound extends Configurable {

    AbstractNBTCompound set(@NotNull String var, byte val);

    AbstractNBTCompound set(@NotNull String var, short val);

    AbstractNBTCompound set(@NotNull String var, int val);

    AbstractNBTCompound set(@NotNull String var, long val);

    AbstractNBTCompound set(@NotNull String var, float val);

    AbstractNBTCompound set(@NotNull String var, double val);

    AbstractNBTCompound set(@NotNull String var, char val);

    AbstractNBTCompound set(@NotNull String var, boolean val);

    AbstractNBTCompound setArray(@NotNull String var, byte... val);

    AbstractNBTCompound setArray(@NotNull String var, int... val);

    AbstractNBTCompound set(@NotNull String var, @NotNull String val);

    AbstractNBTCompound set(@NotNull String var, @NotNull List<?> val);

    default AbstractNBTCompound set(@NotNull String var, @NotNull UUID uuid) {
        return set(var, uuid.toString());
    }

    String getName();

    @Nullable
    AbstractNBTCompound getParent();

    AbstractNBTCompound removeKey(@NotNull String var);

    boolean hasKey(@NotNull String var);

    boolean getBoolean(@NotNull String var);

    Set<String> getKeys();

    int[] getIntArray(@NotNull String var);

    byte[] getByteArray(@NotNull String var);

    @Nullable
    AbstractNBTCompound getCompoundIfExist(@NotNull String var);

    @NotNull
    AbstractNBTCompound getCompound(@NotNull String var);

    @NotNull
    @Override
    default AbstractNBTCompound set(@NotNull String s, @NotNull Object o) {
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
