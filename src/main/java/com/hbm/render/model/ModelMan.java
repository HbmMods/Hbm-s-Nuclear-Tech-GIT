package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;

public class ModelMan extends ModelArmorBase {

	public ModelMan() {
		super(0);

		head = new ModelRendererObj(ResourceManager.player_manly_af, "Head");
		body = new ModelRendererObj(ResourceManager.player_manly_af, "Body");
		leftArm = new ModelRendererObj(ResourceManager.player_manly_af, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm = new ModelRendererObj(ResourceManager.player_manly_af, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		leftLeg = new ModelRendererObj(ResourceManager.player_manly_af, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg = new ModelRendererObj(ResourceManager.player_manly_af, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		bindTexture(ResourceManager.player_manly_tex);
		head.render(0.0625F);
		body.render(0.0625F);
		leftArm.render(0.0625F);
		rightArm.render(0.0625F);
		leftLeg.render(0.0625F);
		rightLeg.render(0.0625F);
		GL11.glPopMatrix();
	}

	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7, RenderPlayer render) {
		this.isSneak = par1Entity.isSneaking();
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		head.copyRotationFrom(render.modelBipedMain.bipedHead);
		body.copyRotationFrom(render.modelBipedMain.bipedBody);
		leftArm.copyRotationFrom(render.modelBipedMain.bipedLeftArm);
		rightArm.copyRotationFrom(render.modelBipedMain.bipedRightArm);
		leftLeg.copyRotationFrom(render.modelBipedMain.bipedLeftLeg);
		rightLeg.copyRotationFrom(render.modelBipedMain.bipedRightLeg);
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		bindTexture(ResourceManager.player_manly_tex);
		head.render(0.0625F);
		body.render(0.0625F);
		leftArm.render(0.0625F);
		rightArm.render(0.0625F);
		leftLeg.render(0.0625F);
		rightLeg.render(0.0625F);
		GL11.glPopMatrix();
	}
}
