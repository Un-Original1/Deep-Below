package com.unoriginal.mimicfish.init;

import com.unoriginal.mimicfish.item.ItemBase;
import com.unoriginal.mimicfish.item.ItemEsca;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModItems {
    public static Item ESCA;
    public static Item ANGLERFISH_EGG;

    public static void init() {
        ESCA = new ItemEsca("esca", 16, 1, 0F, false);
        ANGLERFISH_EGG = new ItemBase("anglerfish_egg", 64);
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ESCA);
        event.getRegistry().registerAll(ANGLERFISH_EGG);
    }
    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event)
    {
        registerRender(ESCA);
        registerRender(ANGLERFISH_EGG);
    }
    public static void registerRender(Item item)
    {
        if(!item.getHasSubtypes()) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        } else {
            NonNullList<ItemStack> list = NonNullList.create();
            item.getSubItems(item.getCreativeTab(), list);
            for(ItemStack stack : list) {
                ModelLoader.setCustomModelResourceLocation(item, stack.getMetadata(), new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
    }
}
