package com.unoriginal.mimicfish.init;

import com.google.common.collect.Sets;
import com.unoriginal.mimicfish.Mimicfish;
import com.unoriginal.mimicfish.MimicfishClientHandler;
import com.unoriginal.mimicfish.chest_searcher.Controller;
import com.unoriginal.mimicfish.chest_searcher.Render;
import com.unoriginal.mimicfish.chest_searcher.RenderEnqueue;
import com.unoriginal.mimicfish.entities.entity.EntityLunkertooth;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

public class ModEvents {

    private static final Set<Block> CHESTS = Sets.newHashSet(Blocks.CHEST, Blocks.TRAPPED_CHEST);

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void pickupItem( BlockEvent.BreakEvent event )
    {
        RenderEnqueue.checkBlock( event.getPos(), event.getState(), false);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void placeItem( BlockEvent.EntityPlaceEvent event )
    {
        RenderEnqueue.checkBlock( event.getPos(), event.getState(), true);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void chunkLoad( ChunkEvent.Load event )
    {
        Controller.requestBlockFinder( true );
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tickEnd( TickEvent.ClientTickEvent event )
    {
        if ( event.phase == TickEvent.Phase.END )
        {
            Controller.requestBlockFinder( false );
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onWorldRenderLast( RenderWorldLastEvent event ) // Called when drawing the world.
    {
        if ( Controller.drawOres() )
        {
            float f = event.getPartialTicks();

            // this is a world pos of the player
            Render.drawOres(
                    (float) MimicfishClientHandler.mc.player.prevPosX + ( (float)  MimicfishClientHandler.mc.player.posX - (float)  MimicfishClientHandler.mc.player.prevPosX ) * f,
                    (float)  MimicfishClientHandler.mc.player.prevPosY + ( (float)  MimicfishClientHandler.mc.player.posY - (float)  MimicfishClientHandler.mc.player.prevPosY ) * f,
                    (float) MimicfishClientHandler.mc.player.prevPosZ + ( (float) MimicfishClientHandler.mc.player.posZ - (float)  MimicfishClientHandler.mc.player.prevPosZ ) * f
            );
        }
    }
    @SubscribeEvent
    public void playerFOV(FOVUpdateEvent event){
        EntityPlayer player = event.getEntity();
        if(player.isPotionActive(ModPotion.CHESTSIGHT) && player.getActivePotionEffect(ModPotion.CHESTSIGHT).getDuration() > 1){
            event.setNewfov(event.getFov() - 0.25F);
        }
    }

    @SubscribeEvent
    public void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Controller.toggleDrawOres();
        Controller.shutdownExecutor();
    }


    @SubscribeEvent
    public void onPotionRemoved(PotionEvent.PotionRemoveEvent event) {
        if (Controller.drawOres())
            Controller.toggleDrawOres();
    }


    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getEntityPlayer().getEntityWorld();
        if (CHESTS.contains(event.getEntityPlayer().world.getBlockState(event.getPos()).getBlock())) {
            //so far until the if statement it works
            TileEntity unknownTile = event.getEntityPlayer().world.getTileEntity((event.getPos()));
            if (unknownTile instanceof TileEntityChest && !world.isRemote) {
                TileEntityChest chesttile = (TileEntityChest) unknownTile;
                //ItemStack stack = new ItemStack(ModItems.ANGLERFISH_EGG);
                if (chesttile.adjacentChestXNeg == null && chesttile.adjacentChestZNeg == null && chesttile.adjacentChestXPos == null && chesttile.adjacentChestZPos == null) {
                    for (ItemStack itemstack : chesttile.chestContents) {
                        if (itemstack.getItem() == ModItems.ANGLERFISH_EGG) {
                            EntityLunkertooth lunkertooth = new EntityLunkertooth(world);
                            lunkertooth.setPosition(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
                            lunkertooth.addItemsToTable(chesttile.chestContents);
                            chesttile.chestContents.clear();
                            world.spawnEntity(lunkertooth);
                            lunkertooth.spawnExplosionCloud();
                            world.setBlockState(event.getPos(), Blocks.AIR.getDefaultState());
                            world.removeTileEntity(event.getPos());
                            break;
                        }
                    }
                }
            }
        }
    }

}
