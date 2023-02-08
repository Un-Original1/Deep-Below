package com.unoriginal.mimicfish.chest_searcher;

import net.minecraft.util.math.Vec3i;

public class BlockInfo extends Vec3i {

    public double alpha;

    public BlockInfo(int x, int y, int z, double alpha) {
        super(x, y, z);
        this.alpha = alpha;
    }

    public BlockInfo(Vec3i pos, double alpha) {
        this(pos.getX(), pos.getY(), pos.getZ(), alpha);
    }
}
