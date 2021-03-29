package com.acrylic.universalnms.render;

import org.jetbrains.annotations.NotNull;

public interface InitializableRenderer<T> extends Renderer<T> {

    void initializeFor(@NotNull T t);

    void terminateFor(@NotNull T t);

    void reinitialize();

    void terminate();

}
