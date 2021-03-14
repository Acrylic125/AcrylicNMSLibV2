package com.acrylic.universalnms.worldexaminer;

import com.acrylic.universalnms.misc.BoundingBoxExaminer;
import org.bukkit.block.Block;

public interface BlockAnalyzer {

    Object getNMSBlock();

    Block getBlock();

    BoundingBoxExaminer getBoundingBox();

    Object getStepSound();

    Object getBreakSound();

    Object getPlaceSound();

}
