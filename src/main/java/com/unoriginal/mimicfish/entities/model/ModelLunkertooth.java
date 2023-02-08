package com.unoriginal.mimicfish.entities.model;// Made with Blockbench 4.6.1
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
public class ModelLunkertooth extends ModelBase {
	private final ModelRenderer angler;
	private final ModelRenderer jaw;
	private final ModelRenderer tail;
	public final ModelRenderer esca;
	private final ModelRenderer chest;
	private final ModelRenderer chest_up;
	private final ModelRenderer left_fin;
	private final ModelRenderer right_fin;

	public ModelLunkertooth() {
		textureWidth = 256;
		textureHeight = 256;

		angler = new ModelRenderer(this);
		angler.setRotationPoint(0.0F, 4.0F, 22.0F);
		angler.cubeList.add(new ModelBox(angler, 1, 83, 0.0F, -19.0F, -18.0F, 0, 14, 21, 0.0F, false));
		angler.cubeList.add(new ModelBox(angler, 64, 79, 0.0F, 13.0F, -18.0F, 0, 15, 22, 0.0F, false));
		angler.cubeList.add(new ModelBox(angler, 80, 0, -14.0F, -6.0F, -30.0F, 28, 14, 6, 0.0F, false));
		angler.cubeList.add(new ModelBox(angler, 0, 0, -14.0F, -10.0F, -24.0F, 28, 30, 24, 0.01F, false));

		jaw = new ModelRenderer(this);
		jaw.setRotationPoint(0.0F, 16.0F, -24.0F);
		angler.addChild(jaw);
		jaw.cubeList.add(new ModelBox(jaw, 0, 54, -14.0F, -16.0F, -20.0F, 28, 12, 20, 0.0F, false));
		jaw.cubeList.add(new ModelBox(jaw, 76, 66, -14.0F, -4.0F, -20.0F, 28, 8, 20, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 0.0F, 0.0F);
		angler.addChild(tail);
		tail.cubeList.add(new ModelBox(tail, 0, 139, -4.0F, -6.0F, -5.0F, 8, 12, 14, 0.0F, false));
		tail.cubeList.add(new ModelBox(tail, 43, 102, 0.0F, -10.0F, 6.0F, 0, 22, 15, 0.0F, false));

		esca = new ModelRenderer(this);
		esca.setRotationPoint(0.0F, -10.0F, -22.0F);
		angler.addChild(esca);
		esca.cubeList.add(new ModelBox(esca, 0, 14, -2.5F, -5.0F, -31.0F, 5, 5, 5, 0.0F, false));
		esca.cubeList.add(new ModelBox(esca, 1, 56, 0.0F, -16.0F, -27.0F, 0, 18, 30, 0.0F, false));

		chest = new ModelRenderer(this);
		chest.setRotationPoint(0.0F, -5.0F, -25.0F);
		esca.addChild(chest);
		//chest.cubeList.add(new ModelBox(chest, 90, 40, -7.0F, 2.0F, -10.0F, 14, 10, 14, 0.0F, false));

		chest_up = new ModelRenderer(this);
		chest_up.setRotationPoint(0.0F, 3.0F, 4.0F);
		chest.addChild(chest_up);
		//chest_up.cubeList.add(new ModelBox(chest_up, 94, 102, -7.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F, false));
		//chest_up.cubeList.add(new ModelBox(chest_up, 0, 0, -1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F, false));

		left_fin = new ModelRenderer(this);
		left_fin.setRotationPoint(14.1F, 15.0F, -10.0F);
		angler.addChild(left_fin);
		left_fin.cubeList.add(new ModelBox(left_fin, 74, 116, 0.0F, -3.0F, -1.0F, 0, 9, 14, 0.0F, false));

		right_fin = new ModelRenderer(this);
		right_fin.setRotationPoint(-14.1F, 15.0F, -10.0F);
		angler.addChild(right_fin);
		right_fin.cubeList.add(new ModelBox(right_fin, 74, 116, 0.0F, -3.0F, -1.0F, 0, 9, 14, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		angler.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		if(entityIn instanceof EntityLunkertooth){
			EntityLunkertooth lunkertooth = (EntityLunkertooth) entityIn;

			if(lunkertooth.isAttackingClient()){
				this.left_fin.rotateAngleX = (float) Math.toRadians(-MathHelper.cos(0.48F * (float)(20 - lunkertooth.getAttackTick())) * 6.25F + 6.25F);
				this.left_fin.rotateAngleY = (float) Math.toRadians(-MathHelper.cos(0.48F * (float)(20 - lunkertooth.getAttackTick())) * 16.25F + 16.25F);
				this.left_fin.rotateAngleZ = (float) Math.toRadians(MathHelper.cos(0.48F * (float)(20 - lunkertooth.getAttackTick())) * 12.5F - 12.5F);

				this.right_fin.rotateAngleX = this.left_fin.rotateAngleX;
				this.right_fin.rotateAngleY = -this.left_fin.rotateAngleY;
				this.right_fin.rotateAngleZ = -this.left_fin.rotateAngleZ;

				this.angler.rotateAngleX = (float) Math.toRadians(MathHelper.cos((float) Math.PI / 8F * (float)(20 - lunkertooth.getAttackTick())) * 2.5F);
				this.angler.rotateAngleY = 0F;

				this.jaw.rotateAngleX = (float) Math.toRadians(MathHelper.sin((float) Math.PI / 10F * (float)(20 - lunkertooth.getAttackTick()) + 0.15F) * 15F - 2.5F);

				this.esca.rotateAngleX = (float) Math.toRadians(MathHelper.cos((float) Math.PI / 10F * (float)(20 - lunkertooth.getAttackTick())) * 5F - 5F);
				this.esca.rotateAngleZ = (float) Math.toRadians(MathHelper.cos((float) Math.PI / 5F * (float)(20 - lunkertooth.getAttackTick())) * 2.5F);

				this.tail.rotateAngleX = (float) Math.toRadians(MathHelper.cos((float) Math.PI / 5F * (float)(20 - lunkertooth.getAttackTick())) * 2.5F - 2.5F);
				this.tail.rotateAngleY = 0F;
				this.tail.rotateAngleZ = (float) Math.toRadians(MathHelper.sin((float) Math.PI / 10F * (float)(20 - lunkertooth.getAttackTick())) * 27.5F);
			} else {
				this.left_fin.rotateAngleX = 0F + (float) Math.toRadians(MathHelper.cos((float) Math.PI / 6F * ageInTicks) * 2.5F + 2.5F) ;
				this.left_fin.rotateAngleY = 0F + (float) Math.toRadians(MathHelper.cos((float) Math.PI / 6F * ageInTicks) * 3.75F + 3.75F);
				this.left_fin.rotateAngleZ = 0F ;

				this.right_fin.rotateAngleX = this.left_fin.rotateAngleX;
				this.right_fin.rotateAngleY = -this.left_fin.rotateAngleY;
				this.right_fin.rotateAngleZ = -this.left_fin.rotateAngleZ;

				this.angler.rotateAngleX = 0F - (float) Math.toRadians(MathHelper.cos((float) Math.PI / 12F * ageInTicks) * 2.5F) ;
				this.angler.rotateAngleY = 0F - (float) Math.toRadians(MathHelper.sin((float) Math.PI / 12F * ageInTicks) * 7.5F);

				this.jaw.rotateAngleX = 0F;

				this.esca.rotateAngleX = 0F + (float) Math.toRadians(MathHelper.cos((float) Math.PI / 6F * ageInTicks) * 1.25F - 1.25) ;
				this.esca.rotateAngleZ = 0F;

				this.tail.rotateAngleX = 0F + (float) Math.toRadians(MathHelper.cos((float) Math.PI / 6F * ageInTicks) * 2.5F - 2.5) ;
				this.tail.rotateAngleY = 0F + (float) Math.toRadians(MathHelper.sin((float) Math.PI / 12F * ageInTicks) * 27.5);
				this.tail.rotateAngleZ = 0F;
			}
		}
	}
}