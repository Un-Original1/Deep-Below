package com.unoriginal.mimicfish;

import com.unoriginal.mimicfish.chest_searcher.*;
import com.unoriginal.mimicfish.proxy.ClientProxy;
import com.unoriginal.mimicfish.proxy.CommonProxy;
import com.unoriginal.mimicfish.tab.ModTab;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Mod(modid = Mimicfish.MODID, name = Mimicfish.NAME, version = Mimicfish.VERSION)
public class Mimicfish
{
    public static final String MODID = "mimicfish";
    public static final String NAME = "Deep Below";
    public static final String VERSION = "1.0";
    public static final CreativeTabs MimicTab = new ModTab("mimictab");

    @SidedProxy(serverSide = "com.unoriginal.mimicfish.proxy.CommonProxy", clientSide = "com.unoriginal.mimicfish.proxy.ClientProxy")
    public static CommonProxy commonProxy;

    @Mod.Instance
    public static Mimicfish instance;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        commonProxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        BlockStoreBuilder.init();
        commonProxy.init(event);
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        commonProxy.postInit(e);
    }

    @EventHandler
    public void finalize(FMLServerStoppingEvent e){
        Controller.shutdownExecutor();
    }

}
