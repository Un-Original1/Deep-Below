package com.unoriginal.mimicfish.entities.render;

import com.unoriginal.mimicfish.entities.entity.EntityLunkertooth;
import com.unoriginal.mimicfish.entities.model.ModelLunkerChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Calendar;

@SideOnly(Side.CLIENT)
public class LayerLunkertoothChest implements LayerRenderer<EntityLunkertooth> {
    private ModelLunkerChest CHEST_MODEL = new ModelLunkerChest();
    private static final ResourceLocation CHEST = new ResourceLocation("textures/entity/chest/normal.png");
    private static final ResourceLocation XMAS = new ResourceLocation("textures/entity/chest/christmas.png");
    private final RenderLunkertooth lunkertooth;
    public LayerLunkertoothChest(RenderLunkertooth renderLunkertooth){
        this.lunkertooth = renderLunkertooth;
    }
    @Override
    public void doRenderLayer(EntityLunkertooth entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {


            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -1.45F, -1.75F); //don't touch

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableRescaleNormal();
            this.lunkertooth.bindTexture(isXmas() ? XMAS : CHEST);
            CHEST_MODEL.setModelAttributes(this.lunkertooth.getMainModel());
            CHEST_MODEL.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
            CHEST_MODEL.renderAll(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            GlStateManager.popMatrix();

    }

    private boolean isXmas(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26;
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
