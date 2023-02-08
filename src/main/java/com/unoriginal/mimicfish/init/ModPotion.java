package com.unoriginal.mimicfish.init;

import com.unoriginal.mimicfish.Mimicfish;
import com.unoriginal.mimicfish.potions.PotionChestSight;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Mimicfish.MODID)
public class ModPotion {
    private ModPotion(){
    }
    //public static final example potion = (bound class) new potiontype yadda yadda yadda
    public static final PotionChestSight CHESTSIGHT = (PotionChestSight) new PotionChestSight(false, 1).setRegistryName("chestsight");
    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event){
        event.getRegistry().registerAll(CHESTSIGHT);
    }
    @SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
        //I doubt I'll use this
    }
}
