package com.unoriginal.mimicfish.chest_searcher;

import com.unoriginal.mimicfish.Mimicfish;
import com.unoriginal.mimicfish.MimicfishClientHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RenderEnqueue implements Runnable {
    private final WorldRegion box;

    public RenderEnqueue(WorldRegion region )
    {
        box = region;
    }

    public void run()
    {
        blockFinder();
    }

    /**
     * Use Controller.requestBlockFinder() to trigger a scan.
     */
    private void blockFinder() {
        HashMap<UUID, BlockData> blocks = Controller.getBlockStore().getStore();

        if (blocks.isEmpty()) {
            if (!Render.ores.isEmpty())
                Render.ores.clear();
            return;
        }
        World world =  MimicfishClientHandler.mc.world;
        List<BlockInfo> renderQueue = new ArrayList<>();
        for (int chunkX = this.box.minChunkX; chunkX <= this.box.maxChunkX; chunkX++) {
            int x = chunkX << 4;
            int lowBoundX = (x < this.box.minX) ? (this.box.minX - x) : 0;
            int highBoundX = (x + 15 > this.box.maxX) ? (this.box.maxX - x) : 15;
            for (int chunkZ = this.box.minChunkZ; chunkZ <= this.box.maxChunkZ; chunkZ++) {
                if (world.getChunkFromChunkCoords(chunkX, chunkZ).isLoaded()) {
                    Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
                    ExtendedBlockStorage[] extendsList = chunk.getBlockStorageArray();
                    int z = chunkZ << 4;
                    int lowBoundZ = (z < this.box.minZ) ? (this.box.minZ - z) : 0;
                    int highBoundZ = (z + 15 > this.box.maxZ) ? (this.box.maxZ - z) : 15;
                    for (int curExtend = this.box.minChunkY; curExtend <= this.box.maxChunkY; curExtend++) {
                        ExtendedBlockStorage ebs = extendsList[curExtend];
                        if (ebs != null) {
                            int y = curExtend << 4;
                            int lowBoundY = (y < this.box.minY) ? (this.box.minY - y) : 0;
                            int highBoundY = (y + 15 > this.box.maxY) ? (this.box.maxY - y) : 15;
                            for (int i = lowBoundX; i <= highBoundX; i++) {
                                for (int j = lowBoundY; j <= highBoundY; j++) {
                                    for (int k = lowBoundZ; k <= highBoundZ; k++) {
                                        IBlockState currentState = ebs.get(i, j, k);
                                        if (!Controller.blackList.contains(currentState.getBlock())) {
                                            ResourceLocation block = currentState.getBlock().getRegistryName();
                                            if (block != null) {
                                                BlockStore.BlockDataWithUUID dataWithUUID = Controller.getBlockStore().getStoreByReference(block.toString());
                                                if (dataWithUUID != null)
                                                    if (dataWithUUID.getBlockData() != null && dataWithUUID.getBlockData().isDrawing()) {
                                                        double alpha = Math.max(0.0D, (Controller.getRadius() -  MimicfishClientHandler.mc.player.getDistance((x + i), (y + j), (z + k))) / Controller.getRadius() * 255.0D);
                                                        renderQueue.add(new BlockInfo(x + i, y + j, z + k, alpha));
                                                    }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        BlockPos playerPos =  MimicfishClientHandler.mc.player.getPosition();
        renderQueue.sort((t, t1) -> Double.compare(t1.distanceSq(playerPos), t.distanceSq(playerPos)));
        Render.ores.clear();
        Render.ores.addAll(renderQueue);
    }

    /**
     * Single-block version of blockFinder. Can safely be called directly
     * for quick block check.
     * @param pos the BlockPos to check
     * @param state the current state of the block
     * @param add true if the block was added to world, false if it was removed
     */
    public static void checkBlock(BlockPos pos, IBlockState state, boolean add )
    {
        if ( !Controller.drawOres() || Controller.getBlockStore().getStore().isEmpty() )
            return; // just pass

        String defaultState = state.getBlock().getDefaultState().toString();

        // Let's see if the block to check is an ore we monitor
        if ( Controller.getBlockStore().getStore().containsKey(defaultState) ) // it's a block we are monitoring
        {
            if( !add )
            {
                Render.ores.remove( new BlockInfo(pos, 0.0D) );
                return;
            }

            BlockData data = null;
            if( Controller.getBlockStore().getStore().containsKey(defaultState))
                data =  Controller.getBlockStore().getStore().get(defaultState);

            if( data == null )
                return;

            double alpha = Math.max(0D  , ((Controller.getRadius() -  MimicfishClientHandler.mc.player.getDistance(pos.getX(), pos.getY(), pos.getZ())) / Controller.getRadius() ) * 255);

            // the block was added to the world, let's add it to the drawing buffer
            Render.ores.add( new BlockInfo(pos, alpha));
        }
    }
}
