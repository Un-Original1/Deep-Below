package com.unoriginal.mimicfish.potions;

import com.unoriginal.mimicfish.chest_searcher.BlockData;
import com.unoriginal.mimicfish.chest_searcher.BlockStore;
import com.unoriginal.mimicfish.chest_searcher.Controller;
import com.unoriginal.mimicfish.init.ModPotion;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class PotionChestSight extends PotionBase {

    public PotionChestSight(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        this.setPotionName("potion.chestsight");
        setIconIndex(0, 0);
        registerPotionAttributeModifier(SharedMonsterAttributes.LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 2.0D, 0);

    }

    public boolean isReady(int duration, int amplifier) {

        if (this == ModPotion.CHESTSIGHT && duration < 80) {

            BlockStore.BlockDataWithUUID bdUUID = Controller.getBlockStore().getStoreByReference("minecraft:chest");
            BlockData CHEST = bdUUID.getBlockData();
            if (CHEST.isDrawing()) {
                CHEST.setDrawing(false);
                if (Controller.drawOres())
                    Controller.toggleDrawOres();
                return CHEST.isDrawing();
            }
            BlockStore.BlockDataWithUUID bdUUID2 = Controller.getBlockStore().getStoreByReference("minecraft:trapped_chest");
            BlockData CHEST_TRAP = bdUUID2.getBlockData();
            if (CHEST_TRAP.isDrawing()) {
                CHEST_TRAP.setDrawing(false);
                if (Controller.drawOres())
                    Controller.toggleDrawOres();
                return CHEST_TRAP.isDrawing();
            }
        }
        return (this == ModPotion.CHESTSIGHT);
    }

    @SideOnly(Side.CLIENT)
    public void performEffect(EntityLivingBase e, int amplifier){
        if (e instanceof AbstractClientPlayer) {

            BlockStore.BlockDataWithUUID bdUUID = Controller.getBlockStore().getStoreByReference("minecraft:chest");
            BlockData CHEST = bdUUID.getBlockData();
            if (!CHEST.isDrawing())
                CHEST.setDrawing(true);

            BlockStore.BlockDataWithUUID bdUUID2 = Controller.getBlockStore().getStoreByReference("minecraft:trapped_chest");
            BlockData CHEST_TRAP = bdUUID2.getBlockData();
            if (!CHEST_TRAP.isDrawing())
                CHEST_TRAP.setDrawing(true);


            if (!Controller.drawOres())
                Controller.toggleDrawOres();
        }
        super.performEffect(e, amplifier);
    }
}
