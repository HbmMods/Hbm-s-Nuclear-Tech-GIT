package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.items.armor.JetpackModular;
import com.hbm.items.armor.JetpackPart.EnumJetpackPart;
import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ModelJetpackModular extends ModelArmorBase {
	
	public static final ModelJetpackModular INSTANCE = new ModelJetpackModular();

	public ModelJetpackModular() {
		super(1);
		this.body = new ModelRendererJetpack();
	}


	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		this.body.render(scaleFactor);
	}
	
	public static class ModelRendererJetpack extends ModelRendererObj {
		
		protected ItemStack stackCtx;

		public ModelRendererJetpack() { super(null); }
		public void setContext(ItemStack stack) { this.stackCtx = stack; }
		
		@Override
		public void renderComponents() {
			renderFromItem(stackCtx, false, 0.0625F);
		}
	}
	
	public static void renderFromItem(ItemStack stack, boolean inSlot, float scaleFactor) {

		GL11.glPushMatrix();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		if(inSlot) {
			double scale = 0.5625D;
			GL11.glRotated(180, 1, 0, 0);
			GL11.glTranslated(0, -24, 0);
			GL11.glScaled(scale, scale, scale);
		}

		bindTexture(ResourceManager.jetpack_base);
		ResourceManager.jetpack_universal.renderPart("Base");

		EnumJetpackPart thruster = JetpackModular.getInstalledThruster(stack);
		EnumJetpackPart tank = JetpackModular.getInstalledTank(stack);
		EnumJetpackPart flightModel = JetpackModular.getInstalledFlightModel(stack);

		if(thruster == EnumJetpackPart.THRUSTERS) {
			bindTexture(ResourceManager.jetpack_thruster); ResourceManager.jetpack_universal.renderPart("Thrusters");
		}
		if(thruster == EnumJetpackPart.THRUSTER_LARGE) {
			bindTexture(ResourceManager.jetpack_thruster_large); ResourceManager.jetpack_universal.renderPart("ThrusterLarge");
		}
		if(tank != null) {
			bindTexture(ResourceManager.jetpack_tank);
			ResourceManager.jetpack_universal.renderPart("Tank");
			if(JetpackModular.isPartInstalled(stack, EnumJetpackPart.EXTENSION_TANKS)) {
				ResourceManager.jetpack_universal.renderPart("ExtensionTanks");
			}
		}
		if(flightModel == EnumJetpackPart.STABILIZER) {
			bindTexture(ResourceManager.jetpack_stabilizer);
			ResourceManager.jetpack_universal.renderPart("Stabilizer");
		}
		if(flightModel == EnumJetpackPart.WINGS) {
			
			bindTexture(ResourceManager.jetpack_wings);
			double extension = inSlot ? 12 : 0;
			
			GL11.glPushMatrix();
			GL11.glTranslated(-extension, 0, 0);
			ResourceManager.jetpack_universal.renderPart("WingLeft");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslated(extension, 0, 0);
			ResourceManager.jetpack_universal.renderPart("WingRight");
			GL11.glPopMatrix();
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
