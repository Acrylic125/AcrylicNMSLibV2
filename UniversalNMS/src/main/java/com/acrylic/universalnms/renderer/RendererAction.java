package com.acrylic.universalnms.renderer;

@FunctionalInterface
public interface RendererAction<T> {

    void run(T t);

}
