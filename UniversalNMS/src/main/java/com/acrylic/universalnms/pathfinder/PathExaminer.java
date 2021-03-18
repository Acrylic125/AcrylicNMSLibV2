package com.acrylic.universalnms.pathfinder;

public interface PathExaminer {

    boolean isPassableAtBlock(int x, int y, int z);

    boolean isClimbableAtBlock(int x, int y, int z);



}
