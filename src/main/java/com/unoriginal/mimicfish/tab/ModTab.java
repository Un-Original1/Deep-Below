package com.unoriginal.mimicfish.tab;

import com.unoriginal.mimicfish.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModTab extends CreativeTabs {

    public ModTab(String label){ super("mimictab");}

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.ESCA);
    }
}
