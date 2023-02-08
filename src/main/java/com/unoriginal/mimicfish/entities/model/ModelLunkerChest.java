package com.unoriginal.mimicfish.entities.model;// Made with Blockbench 4.6.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.unoriginal.mimicfish.entities.entity.EntityLunkertooth;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLunkerChest extends ModelBase {
	private final ModelRenderer angler;
	private final ModelRenderer esca;
	private final ModelRenderer chest;
	private final ModelRenderer chest_up;

	public ModelLunkerChest() {
		textureWidth = 64;
		textureHeight = 64;

		angler = new ModelRenderer(this);
		angler.setRotationPoint(0.0F, 27.0F, 50.0F);


		esca = new ModelRenderer(this);
		esca.setRotationPoint(0.0F, -10.0F, -22.0F);
		angler.addChild(esca);


		chest = new ModelRenderer(this);
		chest.setRotationPoint(0.0F, -5.0F, -25.0F);
		esca.addChild(chest);
		chest.cubeList.add(new ModelBox(chest, 0, 19, -7.0F, 2.0F, -10.0F, 14, 10, 14, 0.0F, false));

		chest_up = new ModelRenderer(this);
		chest_up.setRotationPoint(0.0F, 3.0F, 4.0F);
		chest.addChild(chest_up);
		chest_up.cubeList.add(new ModelBox(chest_up, 0, 0, -7.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F, false));
		chest_up.cubeList.add(new ModelBox(chest_up, 0, 0, -1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F, false));
	}

	public void renderAll(Entity lunkertooth, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		angler.render(scale);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		if (entityIn instanceof EntityLunkertooth) {
			EntityLunkertooth lunkertooth = (EntityLunkertooth) entityIn;
			if (lunkertooth.getChestOpenTicks() > 0) {
				int i = lunkertooth.getChestOpenTicks();
				if (i >= 90) {
					this.chest_up.rotateAngleX = (float) Math.toRadians(MathHelper.cos((float) Math.PI / 10F * (float) (100 - i)) * 26.25F - 26.25F);
					this.chest.rotateAngleX = (float) Math.toRadians(-MathHelper.cos((float) Math.PI / 10F * (float) (100 - i)) * 5F + 5F);
				} else if (i <= 5) {
					this.chest_up.rotateAngleX = (float) Math.toRadians(MathHelper.cos((float) Math.PI / 5F * (float) (5 - i)) * 26.25F - 26.25F);
					this.chest.rotateAngleX = (float) Math.toRadians(-MathHelper.cos((float) Math.PI / 5F * (float) (5 - i)) * 5F + 5F);
				} else {
					this.chest_up.rotateAngleX = 0F + (float) Math.toRadians(-52.5F);
					this.chest.rotateAngleX = 0F + (float) Math.toRadians(10F);
				}
			} else {
				this.chest_up.rotateAngleX = 0F;
				this.chest.rotateAngleX = 0F;
			}


			if (lunkertooth.isAttackingClient()) {
				this.angler.rotateAngleX = (float) Math.toRadians(MathHelper.cos((float) Math.PI / 8F * (float) (20 - lunkertooth.getAttackTick())) * 2.5F);
				this.angler.rotateAngleY = 0F;

				this.esca.rotateAngleX = (float) Math.toRadians(MathHelper.cos((float) Math.PI / 10F * (float) (20 - lunkertooth.getAttackTick())) * 5F - 5F);
				this.esca.rotateAngleZ = (float) Math.toRadians(MathHelper.cos((float) Math.PI / 5F * (float) (20 - lunkertooth.getAttackTick())) * 2.5F);
			} else {
				this.angler.rotateAngleX = 0F - (float) Math.toRadians(MathHelper.cos((float) Math.PI / 12F * ageInTicks) * 2.5F);
				this.angler.rotateAngleY = 0F - (float) Math.toRadians(MathHelper.sin((float) Math.PI / 12F * ageInTicks) * 7.5F);

				this.esca.rotateAngleX = 0F + (float) Math.toRadians(MathHelper.cos((float) Math.PI / 6F * ageInTicks) * 1.25F - 1.25);
				this.esca.rotateAngleZ = 0F;

			}
		}
	}
}