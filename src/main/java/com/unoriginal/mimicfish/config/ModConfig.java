package com.unoriginal.mimicfish.config;

import com.unoriginal.mimicfish.Mimicfish;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Mimicfish.MODID, name = Mimicfish.NAME)
public class ModConfig {
    @Config.Name("Lunkertooth ruins frequency")
    @Config.Comment("Rise the value to make it more common, lower it to make it very rare")
    @Config.RangeInt(min = 0, max = 10)
    @Config.RequiresMcRestart
    public static int ruinsFrequency = 7;

    @Mod.EventBusSubscriber(modid = Mimicfish.MODID)
    private static class ConfigEventHandler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
            if (e.getModID().equals(Mimicfish.MODID)) {
                ConfigManager.sync(Mimicfish.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
