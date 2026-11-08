package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ModelJetpackModular extends ModelArmorBase {
	
	public static final ModelJetpackModular INSTANCE = new ModelJetpackModular();

	ModelRendererObj thrusters;
	ModelRendererObj thrusterLarge;
	ModelRendererObj tank;
	ModelRendererObj extensionTanks;
	ModelRendererObj stabilizer;
	ModelRendererObj wingLeft;
	ModelRendererObj wingRight;

	public ModelJetpackModular() {
		super(1);
		this.body = new ModelRendererObj(ResourceManager.jetpack_universal, "Base");
		this.thrusters = new ModelRendererObj(ResourceManager.jetpack_universal, "Thrusters");
		this.thrusterLarge = new ModelRendererObj(ResourceManager.jetpack_universal, "ThrusterLarge");
		this.tank = new ModelRendererObj(ResourceManager.jetpack_universal, "Tank");
		this.extensionTanks = new ModelRendererObj(ResourceManager.jetpack_universal, "ExtensionTanks");
		this.stabilizer = new ModelRendererObj(ResourceManager.jetpack_universal, "Stabilizer");
		this.wingLeft = new ModelRendererObj(ResourceManager.jetpack_universal, "WingLeft");
		this.wingRight = new ModelRendererObj(ResourceManager.jetpack_universal, "WingRight");
	}


	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		body.copyTo(thrusters);
		body.copyTo(thrusterLarge);
		body.copyTo(tank);
		body.copyTo(extensionTanks);
		body.copyTo(stabilizer);
		body.copyTo(wingLeft);
		body.copyTo(wingRight);
		renderFromItem(null, false, scaleFactor);
	}
	
	public void renderFromItem(ItemStack stack, boolean inSlot, float scaleFactor) {

		GL11.glPushMatrix();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		if(!inSlot) {
			
		}

		bindTexture(ResourceManager.jetpack_base);
		this.body.render(scaleFactor);
		
		if(stack != null) {
			
		}

		bindTexture(ResourceManager.jetpack_tank);
		this.tank.render(scaleFactor);
		bindTexture(ResourceManager.jetpack_thruster);
		this.thrusters.render(scaleFactor);
		bindTexture(ResourceManager.jetpack_wings);
		this.wingLeft.render(scaleFactor);
		this.wingRight.render(scaleFactor);

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
