package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponFFMaresLeg implements IItemRenderer {
	
	ResourceLocation main;
	ResourceLocation grip;
	
	public ItemRenderWeaponFFMaresLeg(ResourceLocation main, ResourceLocation grip) {
		this.main = main;
		this.grip = grip;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		double lever = 0;
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 1.25D;
			GL11.glTranslated(0.75, 0.5, 0);
			GL11.glScaled(s0, s0, s0);
			
			if(!player.isSneaking()) {
				GL11.glRotated(100, 0, -1, 0);
				GL11.glRotated(-5, 0, 1, 0);
				GL11.glRotated(20, 1, 0, 0);
			} else {
				GL11.glRotated(97.5, 0, -1, 0);
				GL11.glTranslated(-0.71, 0.375, 0);
				GL11.glRotated(25, 1, 0, 0);
				GL11.glRotated(1, 0, 1, 0);
				GL11.glRotated(-3, 0, 0, 1);
				GL11.glRotated(0.2, 0, 1, 0);
				GL11.glTranslated(-0.006, -0.005, 0.4);
			}

			double[] recoil = HbmAnimations.getRelevantTransformation("LEVER_RECOIL");
			GL11.glTranslated(0, 0, recoil[0] * 0.5);
			
			double[] rotation = HbmAnimations.getRelevantTransformation("LEVER_ROTATE");
			lever = rotation[2];
			GL11.glTranslated(0, 0, 0.5);
			GL11.glRotated(Math.min(lever, 30), 1, 0, 0);
			GL11.glTranslated(0, 0, -0.5);
			
			break;
			
		case EQUIPPED:

			double scale = 1.25D;
			GL11.glRotated(195, 0, 1, 0);
			GL11.glRotated(-10, 0, 0, 1);
			GL11.glRotated(-10, 1, 0, 0);
			GL11.glTranslated(-0.45, 0.1, -0.5);
			GL11.glScaled(scale, scale, scale);
			
			break;
			
		case ENTITY:

			double s1 = 1D;
			GL11.glScaled(s1, s1, s1);
			GL11.glRotated(90, 0, 1, 0);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 9D;
			GL11.glTranslated(8, 9, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(grip);
		ResourceManager.ff_maresleg.renderPart("Grip");
		Minecraft.getMinecraft().renderEngine.bindTexture(main);
		ResourceManager.ff_maresleg.renderPart("Gun");
		GL11.glTranslated(0, 0.1, 0.25);
		GL11.glRotated(lever * 1.5, 1, 0, 0);
		GL11.glTranslated(0, -0.1, -0.25);
		ResourceManager.ff_maresleg.renderPart("Lever");
		
		GL11.glPopMatrix();
	}
}
