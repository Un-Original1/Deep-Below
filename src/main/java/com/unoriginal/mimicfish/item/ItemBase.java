package com.unoriginal.mimicfish.item;

import com.unoriginal.mimicfish.Mimicfish;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase(String name, int maxstacksize) {
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Mimicfish.MimicTab);
        this.setMaxStackSize(maxstacksize);
    }
}
