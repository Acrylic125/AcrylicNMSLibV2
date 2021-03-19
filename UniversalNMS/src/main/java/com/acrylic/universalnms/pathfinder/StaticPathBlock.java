package com.acrylic.universalnms.pathfinder;

import com.acrylic.universal.Universal;
import com.acrylic.universal.blocks.MCBlockData;
import com.acrylic.universalnms.NMSLib;
import com.acrylic.universalnms.worldexaminer.BoundingBoxExaminer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

/**
 * A static path block represents a block while pathfinding. It is only
 * analyzed ONCE and will NOT UPDATE. This is not entirely thread safe
 * as it still has to analyze the block at the given location while the pathfinder
 * is still pathfinding. However, it will provide a static representation of
 * the block without any chance of it being updated in the future. This instance
 * also holds useful information about the block for the pathfinder
 * like it's bounding box examiner.
 *
 * Any form of analysis that pertains to the future should therefore
 * be cached to ensure the static nature of this block representation.
 *
 * This was done to replace the use of Block Analyzers which were
 * removed upon the use of this, to allow for more specific use cases
 * without unused methods and functionality of the object.
 *
 * Do not confuse with path nodes! Path nodes are specifically for
 * pathfinding analysis and representation of paths. This is meant to
 * analyze the path block itself to determine if nodes should be
 * created at the given location.
 */
public class StaticPathBlock {

    private final MCBlockData mcBlockData;
    private final BoundingBoxExaminer boundingBoxExaminer;

    public StaticPathBlock(@NotNull World world, int x, int y, int z) {
        this(world.getBlockAt(x, y, z));
    }

    public StaticPathBlock(@NotNull Block block) {
        this.mcBlockData = Universal.getAcrylicPlugin().getBlockFactory().getBlockData(block);
        this.boundingBoxExaminer = NMSLib.getNMSUtilityFactory().getNewBoundingBoxExaminer(block);
    }

    public MCBlockData getMcBlockData() {
        return mcBlockData;
    }

    public BoundingBoxExaminer getBoundingBoxExaminer() {
        return boundingBoxExaminer;
    }
}
