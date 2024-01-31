package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponFFColt implements IItemRenderer {
	
	ResourceLocation main;
	ResourceLocation hammer;
	ResourceLocation grip;
	
	public ItemRenderWeaponFFColt(ResourceLocation main, ResourceLocation hammer, ResourceLocation grip) {
		this.main = main;
		this.hammer = hammer;
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
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 1.5D;
			GL11.glTranslated(0.75, 0.5, 0);
			GL11.glScaled(s0, s0, s0);
			GL11.glRotated(100, 0, -1, 0);
			GL11.glRotated(25, 1, 0, 0);

			HbmAnimations.applyRelevantTransformation("Body");
			Minecraft.getMinecraft().renderEngine.bindTexture(main);
			ResourceManager.ff_python.renderPart("Body");

			GL11.glPushMatrix();
			HbmAnimations.applyRelevantTransformation("Cylinder");
			ResourceManager.ff_python.renderPart("Cylinder");
			GL11.glPopMatrix();

			Minecraft.getMinecraft().renderEngine.bindTexture(grip);
			ResourceManager.ff_python.renderPart("Grip");

			HbmAnimations.applyRelevantTransformation("Hammer");
			Minecraft.getMinecraft().renderEngine.bindTexture(hammer);
			ResourceManager.ff_python.renderPart("Hammer");
			
			GL11.glPopMatrix();
			
			return;
			
		case EQUIPPED:

			double scale = 1.5D;
			GL11.glRotated(195, 0, 1, 0);
			GL11.glRotated(-10, 0, 0, 1);
			GL11.glRotated(-10, 1, 0, 0);
			GL11.glTranslated(-0.5, 0.1, -0.25);
			GL11.glScaled(scale, scale, scale);
			
			break;
			
		case ENTITY:

			double s1 = 1D;
			GL11.glScaled(s1, s1, s1);
			GL11.glRotated(90, 0, 1, 0);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 17D;
			GL11.glTranslated(8, 8, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(main);
		ResourceManager.ff_python.renderPart("Body");
		ResourceManager.ff_python.renderPart("Cylinder");
		Minecraft.getMinecraft().renderEngine.bindTexture(grip);
		ResourceManager.ff_python.renderPart("Grip");
		Minecraft.getMinecraft().renderEngine.bindTexture(hammer);
		ResourceManager.ff_python.renderPart("Hammer");
		
		GL11.glPopMatrix();
	}
}
