package com.acrylic.universalnms.renderer;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class AdaptableRenderer<R extends Renderer<T>, T> implements Renderer<T> {

    private final R adaptFrom;

    public AdaptableRenderer(@NotNull R adaptFrom) {
        this.adaptFrom = adaptFrom;
    }

    @Override
    public void runForAllRendered(@NotNull Consumer<T> action) {
        adaptFrom.runForAllRendered(action);
    }

    @Override
    public Renderer<T> clone() {
        return new AdaptableRenderer<>(adaptFrom);
    }

    public R getAdaptFrom() {
        return adaptFrom;
    }
}
