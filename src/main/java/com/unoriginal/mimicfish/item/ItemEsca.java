package com.unoriginal.mimicfish.item;

import com.unoriginal.mimicfish.Mimicfish;
import com.unoriginal.mimicfish.init.ModPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemEsca extends ItemFood {
    public ItemEsca(String name, int maxstacksize, int amount, float saturation, boolean isWolfFood ) {
        super(amount, saturation, isWolfFood);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Mimicfish.MimicTab);
        this.setMaxStackSize(maxstacksize);
    }
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if(!world.isRemote) {
            player.addPotionEffect(new PotionEffect(ModPotion.CHESTSIGHT, 1200));
        }
    }
}
