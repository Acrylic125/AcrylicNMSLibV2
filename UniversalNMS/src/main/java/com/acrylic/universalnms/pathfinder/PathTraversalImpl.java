package com.acrylic.universalnms.pathfinder;

import com.acrylic.universal.math.MathUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PathTraversalImpl implements PathTraversal {

    private final Path path;
    private int pointsPerBlock = 1;
    private final Location cursor;
    private Vector vector = null;
    private int sectionIndex = 0, sectionMaximumIndex = 0, pathIndex = 0;

    public PathTraversalImpl(@NotNull World world, @NotNull Path path) {
        this.path = path;
        this.cursor = new Location(world, 0, 0, 0);
    }

    @NotNull
    @Override
    public Path getPath() {
        return path;
    }

    /**
     * When setting the points per block while this
     * is traversing from {@link #forEachRemaining(Consumer)}
     * or others, it will not guarantee the sectionIndex.
     *
     * This will be corrected when this moves to the next section.
     *
     * @param pointsPerBlock The amount of points per block.
     */
    @Override
    public void setPointsPerBlock(int pointsPerBlock) {
        MathUtils.validateNonZero(pointsPerBlock);
        float p = ((float) pointsPerBlock / this.pointsPerBlock);
        this.sectionIndex = (int) (this.sectionIndex * p);
        this.sectionMaximumIndex = (int) (this.sectionMaximumIndex * p);
        this.pointsPerBlock = pointsPerBlock;
        updateVector();
    }

    @Override
    public int getPointsPerBlock() {
        return pointsPerBlock;
    }

    @Override
    public boolean hasNext() {
        return pathIndex >= (path.getTotalLocations() - 1);
    }

    public boolean hasStarted() {
        return vector != null;
    }

    private void updateVector() {
        if (hasStarted())
            vector.normalize().multiply(1 / pointsPerBlock);
    }

    private void toSection(int toPathIndex) {
        this.sectionIndex = 0;
        this.pathIndex = toPathIndex - 1;
        Location pathLocation = path.getLocation(toPathIndex);
        if (pathLocation == null) {
            this.sectionMaximumIndex = 0;
            this.vector = new Vector(0, 0, 0);
        } else {
            double d = cursor.distance(pathLocation);
            this.sectionMaximumIndex = (int) d;
            this.vector = new Vector((cursor.getX() - pathLocation.getX()) / d, (cursor.getY() - pathLocation.getY()) / d, (cursor.getZ() - pathLocation.getZ()) / d);
        }
    }

    @Override
    public Location next() {
        if (!hasStarted()) {
            toSection(1);
        } else if (this.sectionIndex >= (this.sectionMaximumIndex - 1)) {
            toSection(this.pathIndex + 1);
        }
        sectionIndex++;
        return cursor.add(vector).clone();
    }
}
