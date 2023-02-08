package com.unoriginal.mimicfish.init;

import com.unoriginal.mimicfish.Mimicfish;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModSounds {

    public static final SoundEvent LUNKERTOOTH_IDLE = new SoundEvent(new ResourceLocation(Mimicfish.MODID, "lunkertooth_idle")).setRegistryName("lunkertooth_idle");
    public static final SoundEvent LUNKERTOOTH_HURT = new SoundEvent(new ResourceLocation(Mimicfish.MODID, "lunkertooth_hurt")).setRegistryName("lunkertooth_hurt");
    public static final SoundEvent LUNKERTOOTH_DEATH = new SoundEvent(new ResourceLocation(Mimicfish.MODID, "lunkertooth_death")).setRegistryName("lunkertooth_death");
    public static final SoundEvent LUNKERTOOTH_SCREAM = new SoundEvent(new ResourceLocation(Mimicfish.MODID, "lunkertooth_scream")).setRegistryName("lunkertooth_scream");
    public static final SoundEvent LUNKERTOOTH_BITE = new SoundEvent(new ResourceLocation(Mimicfish.MODID, "lunkertooth_bite")).setRegistryName("lunkertooth_bite");
    public static final SoundEvent LUNKERTOOTH_FLOP = new SoundEvent(new ResourceLocation(Mimicfish.MODID, "lunkertooth_flop")).setRegistryName("lunkertooth_flop");


    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().registerAll(LUNKERTOOTH_IDLE);
            event.getRegistry().registerAll(LUNKERTOOTH_HURT);
            event.getRegistry().registerAll(LUNKERTOOTH_DEATH);
            event.getRegistry().registerAll(LUNKERTOOTH_SCREAM);
            event.getRegistry().registerAll(LUNKERTOOTH_BITE);
            event.getRegistry().registerAll(LUNKERTOOTH_FLOP);
        }
    }
}
