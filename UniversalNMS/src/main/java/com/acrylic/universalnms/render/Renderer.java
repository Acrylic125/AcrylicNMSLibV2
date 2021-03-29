package com.acrylic.universalnms.render;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Renderer<T> {

    void runForAllRendered(@NotNull Consumer<T> action);

    Renderer<T> clone();
}
