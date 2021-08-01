package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelArmorWings extends ModelArmorBase {

	ModelRendererObj wingLB;
	ModelRendererObj wingLT;
	ModelRendererObj wingRB;
	ModelRendererObj wingRT;
	
	public ModelArmorWings(int type) {
		super(type);

		wingLB = new ModelRendererObj(ResourceManager.armor_wings, "LeftBase");
		wingLT = new ModelRendererObj(ResourceManager.armor_wings, "LeftTip");
		wingRB = new ModelRendererObj(ResourceManager.armor_wings, "RightBase");
		wingRT = new ModelRendererObj(ResourceManager.armor_wings, "RightTip");

		//i should really stop doing that
		head = new ModelRendererObj(ResourceManager.anvil);
		body = new ModelRendererObj(ResourceManager.anvil);
		leftArm = new ModelRendererObj(ResourceManager.anvil).setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm = new ModelRendererObj(ResourceManager.anvil).setRotationPoint(5.0F, 2.0F, 0.0F);
		leftLeg = new ModelRendererObj(ResourceManager.anvil).setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg = new ModelRendererObj(ResourceManager.anvil).setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftFoot = new ModelRendererObj(ResourceManager.anvil).setRotationPoint(1.9F, 12.0F, 0.0F);
		rightFoot = new ModelRendererObj(ResourceManager.anvil).setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		
		setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
		//body.copyTo(wingLB);
		//body.copyTo(wingLT);
		//body.copyTo(wingRB);
		//body.copyTo(wingRT);
		
		GL11.glPushMatrix();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(this.getTexture());
		
		double px = 0.0625D;

		double rot = Math.sin((entity.ticksExisted) * 0.2D) * 20;
		double rot2 = Math.sin((entity.ticksExisted) * 0.2D - Math.PI * 0.5) * 50 + 30;
		
		int pivotSideOffset = 1;
		int pivotFrontOffset = 5;
		int pivotZOffset = 3;
		int tipSideOffset = 16;
		int tipZOffset = 2;
		double inwardAngle = 10D;
		
		GL11.glPushMatrix();

		GL11.glTranslatef(body.offsetX * (float) px, body.offsetY * (float) px, body.offsetZ * (float) px);
		GL11.glTranslatef(body.rotationPointX * (float) px, body.rotationPointY * (float) px, body.rotationPointZ * (float) px);

		if(body.rotateAngleZ != 0.0F) {
			GL11.glRotatef(body.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
		}

		if(body.rotateAngleY != 0.0F) {
			GL11.glRotatef(body.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
		}

		if(body.rotateAngleX != 0.0F) {
			GL11.glRotatef(body.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
		}
		
		if(this.type != 1 && entity.onGround) {
			rot = 20;
			rot2 = 160;
		}
		
		if(this.type == 1) {
			rot = 0;
			rot2 = 10;
			
			if(entity.onGround) {
				rot = 30;
				rot2 = -30;
			} else if(entity.motionY < -0.1) {
				rot = 0;
				rot2 = 10;
			} else {
				rot = 30;
				rot2 = 20;
			}
		}
		
		GL11.glTranslated(0, -2 * px, 0);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPushMatrix();
			
			GL11.glRotated(-inwardAngle, 0, 1, 0);
			
			GL11.glTranslated(pivotSideOffset * px, pivotFrontOffset * px, pivotZOffset * px);
			GL11.glRotated(rot * 0.5, 0, 1, 0);
			GL11.glRotated(rot + 5, 0, 0, 1);
			GL11.glRotated(45, 1, 0, 0);
			GL11.glTranslated(-pivotSideOffset * px, -pivotFrontOffset * px, -pivotZOffset * px);
			
			GL11.glTranslated(pivotSideOffset * px, pivotFrontOffset * px, pivotZOffset * px);
			GL11.glRotated(rot, 0, 0, 1);
			GL11.glTranslated(-pivotSideOffset * px, -pivotFrontOffset * px, -pivotZOffset * px);
			wingLB.render(par7);
			
			GL11.glTranslated(tipSideOffset * px, pivotFrontOffset * px, tipZOffset * px);
			GL11.glRotated(rot2, 0, 1, 0);
			if(doesRotateZ())
				GL11.glRotated(rot2 * 0.25 + 5, 0, 0, 1);
			GL11.glTranslated(-tipSideOffset * px, -pivotFrontOffset * px, -tipZOffset * px);
			wingLT.render(par7);
			
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
			
			GL11.glRotated(inwardAngle, 0, 1, 0);
			
			GL11.glTranslated(-pivotSideOffset * px, pivotFrontOffset * px, pivotZOffset * px);
			GL11.glRotated(-rot * 0.5, 0, 1, 0);
			GL11.glRotated(-rot - 5, 0, 0, 1);
			GL11.glRotated(45, 1, 0, 0);
			GL11.glTranslated(pivotSideOffset * px, -pivotFrontOffset * px, -pivotZOffset * px);
			
			GL11.glTranslated(-pivotSideOffset * px, pivotFrontOffset * px, pivotZOffset * px);
			GL11.glRotated(-rot, 0, 0, 1);
			GL11.glTranslated(pivotSideOffset * px, -pivotFrontOffset * px, -pivotZOffset * px);
			wingRB.render(par7);
			
			GL11.glTranslated(-tipSideOffset * px, pivotFrontOffset * px, tipZOffset * px);
			GL11.glRotated(-rot2, 0, 1, 0);
			if(doesRotateZ())
				GL11.glRotated(-rot2 * 0.25 - 5, 0, 0, 1);
			GL11.glTranslated(tipSideOffset * px, -pivotFrontOffset * px, -tipZOffset * px);
			wingRT.render(par7);
			
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
			
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}
	
	protected boolean doesRotateZ() {
		return true;
	}
	
	protected ResourceLocation getTexture() {
		
		if(this.type == 2)
			return ResourceManager.wings_bob;
		
		return ResourceManager.wings_murk;
	}
}
