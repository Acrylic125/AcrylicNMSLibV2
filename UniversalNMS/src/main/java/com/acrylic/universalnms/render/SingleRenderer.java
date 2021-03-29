package com.acrylic.universalnms.render;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SingleRenderer<T> implements Renderer<T> {

    private final T obj;

    public SingleRenderer(@NotNull T obj) {
        this.obj = obj;
    }

    public T getObject() {
        return obj;
    }

    @Override
    public void runForAllRendered(@NotNull Consumer<T> action) {
        action.accept(obj);
    }

    @Override
    public Renderer<T> clone() {
        return new SingleRenderer<>(obj);
    }
}
