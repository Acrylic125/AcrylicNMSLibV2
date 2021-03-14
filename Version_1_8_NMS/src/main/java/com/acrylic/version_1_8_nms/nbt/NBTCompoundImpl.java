package com.acrylic.version_1_8_nms.nbt;

import com.acrylic.universalnms.nbt.NBTCompound;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NBTCompoundImpl implements NBTCompound {

    private final NBTTagCompound tagCompound;
    private final NBTCompoundImpl parent;
    private final String name;

    public NBTCompoundImpl() {
        this(null);
    }

    public NBTCompoundImpl(@Nullable NBTTagCompound tagCompound) {
        this(null, tagCompound, null, true);
    }

    private NBTCompoundImpl(@Nullable String name, @Nullable  NBTTagCompound tagCompound, @Nullable NBTCompoundImpl parent, boolean createNewIfNotExist) {
        if (createNewIfNotExist && (tagCompound == null || tagCompound.isEmpty())) {
            tagCompound = new NBTTagCompound();
            if (parent != null && name != null)
                parent.setTagCompound(name, tagCompound);
        }
        this.tagCompound = tagCompound;
        this.parent = parent;
        this.name = name;
    }

    public NBTTagCompound getTagCompound() {
        return tagCompound;
    }

    public NBTTagCompound getTagCompound(@NotNull String var) {
        return tagCompound.getCompound(var);
    }

    public NBTCompoundImpl setTagCompound(@NotNull String var, @NotNull NBTTagCompound nbtTagCompound) {
        return set(var, nbtTagCompound);
    }

    public NBTCompoundImpl set(@NotNull String var, @NotNull NBTCompoundImpl val) {
        return setTagCompound(var, val.getTagCompound());
    }

    @Override
    public NBTCompoundImpl set(@NotNull String var, byte val) {
        tagCompound.setByte(var, val);
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, byte[] val) {
        tagCompound.setByteArray(var, val);
        return this;
    }

    @Override
    public NBTCompoundImpl set(@NotNull String var, short val) {
        tagCompound.setShort(var, val);
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, int val) {
        tagCompound.setInt(var, val);
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, int[] val) {
        tagCompound.setIntArray(var, val);
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, long val) {
        tagCompound.setLong(var, val);
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, float val) {
        tagCompound.setFloat(var, val);
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, double val) {
        tagCompound.setDouble(var, val);
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, char val) {
        tagCompound.setString(var, val + "");
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, boolean val) {
        tagCompound.setBoolean(var, val);
        return this;
    }

    @Override
    public NBTCompound setArray(@NotNull String var, byte... val) {
        tagCompound.setByteArray(var, val);
        return this;
    }

    @Override
    public NBTCompound setArray(@NotNull String var, int... val) {
        tagCompound.setIntArray(var, val);
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, @NotNull String val) {
        tagCompound.setString(var, val);
        return this;
    }

    @Override
    public NBTCompound set(@NotNull String var, @NotNull List<?> val) {
        NBTTagList tagList = new NBTTagList();
        for (Object o : val)
            if (o instanceof NBTBase)
                tagList.add((NBTBase) o);
        return set(var, tagList);
    }

    @Override
    public String getName() {
        return name;
    }

    @Nullable
    @Override
    public NBTCompound getParent() {
        return parent;
    }

    @Override
    public NBTCompound removeKey(@NotNull String var) {
        tagCompound.remove(var);
        return this;
    }

    @Override
    public boolean hasKey(@NotNull String var) {
        return tagCompound.hasKey(var);
    }

    @Override
    public boolean getBoolean(@NotNull String var) {
        return tagCompound.getBoolean(var);
    }

    @Override
    public Set<String> getKeys() {
        return tagCompound.c();
    }

    @Override
    public int[] getIntArray(@NotNull String var) {
        return tagCompound.getIntArray(var);
    }

    @Override
    public byte[] getByteArray(@NotNull String var) {
        return tagCompound.getByteArray(var);
    }

    @Nullable
    @Override
    public NBTCompound getCompoundIfExist(@NotNull String var) {
        return hasKey(var) ? new NBTCompoundImpl(var, getTagCompound(var), this, false) : null;
    }

    @NotNull
    @Override
    public NBTCompound getCompound(@NotNull String var) {
        return new NBTCompoundImpl(var, getTagCompound(var), this, true);
    }

    @Override
    public String getCompoundString() {
        return tagCompound.toString();
    }

    @NotNull
    public NBTCompoundImpl set(@NotNull String s, @NotNull NBTBase var) {
        tagCompound.set(s, var);
        return this;
    }

    @Override
    public byte getByte(@NotNull String s) {
        return tagCompound.getByte(s);
    }

    @Override
    public char getChar(@NotNull String s) {
        return tagCompound.getString(s).toCharArray()[0];
    }

    @Override
    public double getDouble(@NotNull String s) {
        return tagCompound.getDouble(s);
    }

    @Override
    public float getFloat(@NotNull String s) {
        return tagCompound.getFloat(s);
    }

    @Override
    public int getInt(@NotNull String s) {
        return tagCompound.getInt(s);
    }

    @Nullable
    @Override
    public List<NBTBase> getList(@NotNull String s) {
        NBTTagList list = getTagList(s);
        final ArrayList<NBTBase> newList = new ArrayList();
        if (list == null)
            return newList;
        final int size = list.size();
        for (int i = 0; i < size; i++)
            newList.add(list.g(i));
        return newList;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public <T> List<T> getList(@NotNull String s, @NotNull Class<T> clazz) {
        NBTTagList list = getTagList(s);
        final ArrayList<T> newList = new ArrayList();
        if (list == null)
            return newList;
        final int size = list.size();
        for (int i = 0; i < size; i++) {
            Object o = list.g(i);
            if (clazz.isInstance(o))
                newList.add((T) o);
        }
        return newList;
    }

    @Nullable
    public NBTTagList getTagList(String key) {
        NBTBase list = tagCompound.get(key);
        return (list instanceof NBTTagList) ? (NBTTagList) list : null;
    }

    @Override
    public long getLong(@NotNull String s) {
        return tagCompound.getLong(s);
    }

    @Nullable
    @Override
    public NBTBase getObject(@NotNull String s) {
        return tagCompound.get(s);
    }

    @Override
    public short getShort(@NotNull String s) {
        return tagCompound.getShort(s);
    }

    @Nullable
    @Override
    public String getString(@NotNull String s) {
        return tagCompound.getString(s);
    }

    @Override
    public String toString() {
        return "NBTCompound{" +
                "tagCompound=" + tagCompound +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                '}';
    }

}
