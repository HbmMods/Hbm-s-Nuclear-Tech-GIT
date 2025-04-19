package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ModelNo9 extends ModelArmorBase {

	public ModelRendererObj lamp;
	public ModelRendererObj insig;

	public ModelNo9(int type) {
		super(type);

		this.head = new ModelRendererObj(ResourceManager.armor_no9, "Helmet");
		this.insig = new ModelRendererObj(ResourceManager.armor_no9, "Insignia");
		this.lamp = new ModelRendererObj(ResourceManager.armor_no9, "Flame");
		this.body = new ModelRendererObj(null);
		this.leftArm = new ModelRendererObj(null).setRotationPoint(5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(null).setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		this.head.copyTo(this.insig);
		this.head.copyTo(this.lamp);

		GL11.glPushMatrix();

		if(this.type == 0) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.no9);
			this.head.render(scaleFactor);
			bindTexture(ResourceManager.no9_insignia);
			this.insig.render(scaleFactor);

			if(entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack helmet = player.getEquipmentInSlot(4);

				if(helmet != null && helmet.hasTagCompound() && helmet.getTagCompound().getBoolean("isOn")) {
					GL11.glColor3f(1F, 1F, 0.8F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
					GL11.glDisable(GL11.GL_LIGHTING);
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
					this.lamp.render(scaleFactor);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glPopAttrib();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glColor3f(1F, 1F, 1F);
					GL11.glShadeModel(GL11.GL_FLAT);
				}
			}
		}

		GL11.glPopMatrix();
	}
}
