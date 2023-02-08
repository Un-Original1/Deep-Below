package com.unoriginal.mimicfish.entities.render;

import com.unoriginal.mimicfish.Mimicfish;
import com.unoriginal.mimicfish.entities.entity.EntityLunkertooth;
import com.unoriginal.mimicfish.entities.model.ModelLunkertooth;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderLunkertooth extends RenderLiving<EntityLunkertooth> {
    public static final Factory FACTORY = new Factory();
    public static final ResourceLocation TEXTURE = new ResourceLocation(Mimicfish.MODID, "textures/entity/anglerfish.png");
    public static final ResourceLocation EMISSIVE = new ResourceLocation(Mimicfish.MODID, "textures/entity/anglerfish_glow.png");
    public RenderLunkertooth(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelLunkertooth(), 1.5F);
        this.addLayer( new LayerLunkertoothChest(this));
        this.addLayer(new LayerGlowGeneric(this, EMISSIVE ));
    }

    protected void applyRotations(EntityLunkertooth lunkertooth, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.applyRotations(lunkertooth, ageInTicks, rotationYaw, partialTicks);
        if(lunkertooth.getAir() <= 780){

            GlStateManager.translate(0.1F, 0.1F, -0.1F);
            GlStateManager.rotate(90F, 0F,0F,1.0F);
        //unrelated to the lunkertooth chest bug, already tested
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLunkertooth entity) {
        return TEXTURE;
    }
    public static class Factory implements IRenderFactory<EntityLunkertooth> {
        @Override
        public RenderLiving<? super EntityLunkertooth> createRenderFor(RenderManager manager) {
            return new RenderLunkertooth(manager);
        }
    }
}
