package com.hbm.render.model;

import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ModelArmorBase extends ModelBiped {

	int type;

	public ModelRendererObj head;
	public ModelRendererObj body;
	public ModelRendererObj leftArm;
	public ModelRendererObj rightArm;
	public ModelRendererObj leftLeg;
	public ModelRendererObj rightLeg;
	public ModelRendererObj leftFoot;
	public ModelRendererObj rightFoot;

	public ModelArmorBase(int type) {
		this.type = type;

		// generate null defaults to prevent major breakage from using
		// incomplete models
		head = new ModelRendererObj(null);
		body = new ModelRendererObj(null);
		leftArm = new ModelRendererObj(null).setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm = new ModelRendererObj(null).setRotationPoint(5.0F, 2.0F, 0.0F);
		leftLeg = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftFoot = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		rightFoot = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	public void setRotationAngles(float walkCycle, float walkAmplitude, float idleCycle, float headYaw, float headPitch, float scale, Entity entity) {

		head.rotateAngleY = headYaw / (180F / (float) Math.PI);
		head.rotateAngleX = headPitch / (180F / (float) Math.PI);
		rightArm.rotateAngleX = MathHelper.cos(walkCycle * 0.6662F + (float) Math.PI) * 2.0F * walkAmplitude * 0.5F;
		leftArm.rotateAngleX = MathHelper.cos(walkCycle * 0.6662F) * 2.0F * walkAmplitude * 0.5F;
		rightArm.rotateAngleZ = 0.0F;
		leftArm.rotateAngleZ = 0.0F;
		rightFoot.rotateAngleX = rightLeg.rotateAngleX = MathHelper.cos(walkCycle * 0.6662F) * 1.4F * walkAmplitude;
		leftFoot.rotateAngleX = leftLeg.rotateAngleX = MathHelper.cos(walkCycle * 0.6662F + (float) Math.PI) * 1.4F * walkAmplitude;
		rightFoot.rotateAngleY = rightLeg.rotateAngleY = 0.0F;
		leftFoot.rotateAngleY = leftLeg.rotateAngleY = 0.0F;

		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			this.aimedBow = false;

			if(player.getHeldItem() != null) {

				int hold = 1;

				if(player.getItemInUseCount() > 0) {

					EnumAction action = player.getHeldItem().getItemUseAction();

					if(action == EnumAction.block)
						hold = 3;

					if(action == EnumAction.bow)
						this.aimedBow = true;
				}

				if(player.getHeldItem().getItem() instanceof IHoldableWeapon)
					this.aimedBow = true;

				rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F) * hold;
			}
		}

		this.isSneak = entity.isSneaking();
		this.isRiding = entity.isRiding();

		if(this.isRiding) {
			rightArm.rotateAngleX += -((float) Math.PI / 5F);
			leftArm.rotateAngleX += -((float) Math.PI / 5F);
			rightFoot.rotateAngleX = rightLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			leftFoot.rotateAngleX = leftLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			rightFoot.rotateAngleY = rightLeg.rotateAngleY = ((float) Math.PI / 10F);
			leftFoot.rotateAngleY = leftLeg.rotateAngleY = -((float) Math.PI / 10F);
		}

		if(this.heldItemLeft != 0) {
			leftArm.rotateAngleX = leftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F) * (float) this.heldItemLeft;
		}

		if(this.heldItemRight != 0) {
			rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F) * (float) this.heldItemRight;
		}

		rightArm.rotateAngleY = 0.0F;
		leftArm.rotateAngleY = 0.0F;
		float f6;
		float f7;

		if(this.onGround > -9990.0F) {
			f6 = this.onGround;
			body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float) Math.PI * 2.0F) * 0.2F;
			rightArm.rotationPointZ = MathHelper.sin(body.rotateAngleY) * 5.0F;
			rightArm.rotationPointX = -MathHelper.cos(body.rotateAngleY) * 5.0F;
			leftArm.rotationPointZ = -MathHelper.sin(body.rotateAngleY) * 5.0F;
			leftArm.rotationPointX = MathHelper.cos(body.rotateAngleY) * 5.0F;
			rightArm.rotateAngleY += body.rotateAngleY;
			leftArm.rotateAngleY += body.rotateAngleY;
			leftArm.rotateAngleX += body.rotateAngleY;
			f6 = 1.0F - this.onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float) Math.PI);
			float f8 = MathHelper.sin(this.onGround * (float) Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
			rightArm.rotateAngleX = (float) ((double) rightArm.rotateAngleX - ((double) f7 * 1.2D + (double) f8));
			rightArm.rotateAngleY += body.rotateAngleY * 2.0F;
			rightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float) Math.PI) * -0.4F;
		}

		if(this.isSneak) {
			body.rotateAngleX = 0.5F;
			rightArm.rotateAngleX += 0.4F;
			leftArm.rotateAngleX += 0.4F;
			rightFoot.offsetZ = rightLeg.offsetZ = 4.0F;
			leftFoot.offsetZ = leftLeg.offsetZ = 4.0F;
			rightFoot.offsetY = rightLeg.offsetY = -3.0F;
			leftFoot.offsetY = leftLeg.offsetY = -3.0F;
			head.offsetY = 1.0F;
		} else {
			body.rotateAngleX = 0.0F;
			rightFoot.offsetZ = rightLeg.offsetZ = 0.1F;
			leftFoot.offsetZ = leftLeg.offsetZ = 0.1F;
			rightFoot.offsetY = rightLeg.offsetY = 0.0F;
			leftFoot.offsetY = leftLeg.offsetY = 0.0F;
			head.offsetY = 0.0F;
		}

		rightArm.rotateAngleZ += MathHelper.cos(idleCycle * 0.09F) * 0.05F + 0.05F;
		leftArm.rotateAngleZ -= MathHelper.cos(idleCycle * 0.09F) * 0.05F + 0.05F;
		rightArm.rotateAngleX += MathHelper.sin(idleCycle * 0.067F) * 0.05F;
		leftArm.rotateAngleX -= MathHelper.sin(idleCycle * 0.067F) * 0.05F;

		if(this.aimedBow) {
			f6 = 0.0F;
			f7 = 0.0F;
			rightArm.rotateAngleZ = 0.0F;
			leftArm.rotateAngleZ = 0.0F;
			rightArm.rotateAngleY = -(0.1F - f6 * 0.6F) + head.rotateAngleY;
			leftArm.rotateAngleY = 0.1F - f6 * 0.6F + head.rotateAngleY + 0.4F;
			rightArm.rotateAngleX = -((float) Math.PI / 2F) + head.rotateAngleX;
			leftArm.rotateAngleX = -((float) Math.PI / 2F) + head.rotateAngleX;
			rightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			leftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			rightArm.rotateAngleZ += MathHelper.cos(idleCycle * 0.09F) * 0.05F + 0.05F;
			leftArm.rotateAngleZ -= MathHelper.cos(idleCycle * 0.09F) * 0.05F + 0.05F;
			rightArm.rotateAngleX += MathHelper.sin(idleCycle * 0.067F) * 0.05F;
			leftArm.rotateAngleX -= MathHelper.sin(idleCycle * 0.067F) * 0.05F;
		}

		if(entity instanceof EntityPlayer) {
			Object o = RenderManager.instance.entityRenderMap.get(EntityPlayer.class);
			if(o instanceof RenderPlayer) {
				RenderPlayer render = (RenderPlayer) o;
				leftArm.copyRotationFrom(render.modelBipedMain.bipedLeftArm);
				rightArm.copyRotationFrom(render.modelBipedMain.bipedRightArm);
			}
		} else {
			Object o = RenderManager.instance.entityRenderMap.get(entity.getClass());
			if(o instanceof RenderBiped) {
				RenderBiped render = (RenderBiped) o;
				leftArm.copyRotationFrom(render.modelBipedMain.bipedLeftArm);
				rightArm.copyRotationFrom(render.modelBipedMain.bipedRightArm);
			}
		}
	}

	protected void bindTexture(ResourceLocation loc) {
		Minecraft.getMinecraft().renderEngine.bindTexture(loc);
	}
}
