package com.unoriginal.mimicfish.chest_searcher;

import com.unoriginal.mimicfish.Mimicfish;
import com.unoriginal.mimicfish.MimicfishClientHandler;
import com.unoriginal.mimicfish.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Controller {

    // Block blackList
    public static ArrayList<Block> blackList = new ArrayList<Block>() {

    };

    private static Vec3i lastPlayerPos = null;

    /**
     * Global blockStore used for:
     * [Rendering, GUI, Configuration Handling]
     */
    private static BlockStore blockStore = new BlockStore();

    // Thread management
    private static Future task;
    private static ExecutorService executor;

    // Draw states
    private static boolean drawOres = false; // Off by default

    public static BlockStore getBlockStore() {
        return blockStore;
    }

    // Public accessors
    public static boolean drawOres() { return drawOres && MimicfishClientHandler.mc.world != null &&  MimicfishClientHandler.mc.player != null; }
    public static void toggleDrawOres()
    {
        if ( !drawOres ) // enable drawing
        {
            Render.ores.clear(); // first, clear the buffer
            executor = Executors.newSingleThreadExecutor();
            drawOres = true; // then, enable drawing
            requestBlockFinder( true ); // finally, force a refresh

        }
        else // disable drawing
        {

            shutdownExecutor();
        }
    }

    public static int getRadius() { return 96; }

    /**
     * Precondition: world and player must be not null
     * Has player moved since the last region scan?
     * This method does not update the last player location so consecutive
     * calls yield the same result.
     * @return true if the player has moved since the last blockFinder call
     */
    private static boolean playerHasMoved()
    {
        return lastPlayerPos == null
                || lastPlayerPos.getX() != MimicfishClientHandler.mc.player.getPosition().getX()
                || lastPlayerPos.getZ() !=  MimicfishClientHandler.mc.player.getPosition().getZ();
    }

    private static void updatePlayerPosition()
    {
        lastPlayerPos =  MimicfishClientHandler.mc.player.getPosition();
    }

    /**
     * Starts a region scan thread if possible, that is if:
     * - we actually want to draw ores
     * - we are not already scanning an area
     * - either the player has moved since the last call
     * - or we want to (and can) force a scan
     *
     * @param force should we force a block scan even if the player hasn't moved?
     */
    public static synchronized void requestBlockFinder( boolean force )
    {
        if ( drawOres() && (task == null || task.isDone()) && (force || playerHasMoved()) ) // world/player check done by drawOres()
        {
            updatePlayerPosition(); // since we're about to run, update the last known position
            WorldRegion region = new WorldRegion( lastPlayerPos, getRadius() ); // the region to scan for ores
            task = executor.submit( new RenderEnqueue(region) );
        }
    }

    /**
     * To be called at least when the game shuts down
     */
    public static void shutdownExecutor()
    {
        // Important. If drawOres is true when a player logs out then logs back in, the next requestBlockFinder will crash
        drawOres = false;
        try { executor.shutdownNow(); }
        catch (Throwable ignore) {}
    }
}
