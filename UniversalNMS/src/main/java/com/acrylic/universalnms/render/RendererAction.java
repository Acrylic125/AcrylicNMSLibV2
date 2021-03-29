package com.acrylic.universalnms.render;

@FunctionalInterface
public interface RendererAction<T> {

    void run(T t);

}
