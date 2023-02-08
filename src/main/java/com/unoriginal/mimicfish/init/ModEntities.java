package com.unoriginal.mimicfish.init;

import com.unoriginal.mimicfish.Mimicfish;
import com.unoriginal.mimicfish.entities.entity.EntityLunkertooth;
import com.unoriginal.mimicfish.entities.render.RenderLunkertooth;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModEntities {
    public static void init() {
        int id = 0;
        EntityRegistry.registerModEntity(new ResourceLocation(Mimicfish.MODID, "lunkertooth"), EntityLunkertooth.class, "lunkertooth", id++, Mimicfish.instance, 64, 3, true, 1907253, 15173149);

    }
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityLunkertooth.class, RenderLunkertooth.FACTORY);
    }
}
