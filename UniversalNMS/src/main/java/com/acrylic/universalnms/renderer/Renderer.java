package com.acrylic.universalnms.renderer;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Renderer<T> {

    void doChecks();

    void setOnInitialize(@NotNull Consumer<T> action);

    void setOnDeinitialize(@NotNull Consumer<T> action);

    void runForAllRendered(@NotNull Consumer<T> action);

}
