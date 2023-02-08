package com.unoriginal.mimicfish.proxy;

import com.unoriginal.mimicfish.init.ModEntities;
import com.unoriginal.mimicfish.init.ModEvents;
import com.unoriginal.mimicfish.init.ModItems;
import com.unoriginal.mimicfish.world.structure.WorldGenStructures;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e)
    {
       ModEntities.init();
       ModItems.init();
       MinecraftForge.EVENT_BUS.register(new ModEvents());
       GameRegistry.registerWorldGenerator(new WorldGenStructures(), 3);
    }


    public void init(FMLInitializationEvent e) {

    }
    public void postInit(FMLPostInitializationEvent e) {

    }

}
