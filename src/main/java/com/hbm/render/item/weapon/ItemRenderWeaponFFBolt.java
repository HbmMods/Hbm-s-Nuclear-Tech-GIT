package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponFFBolt implements IItemRenderer {
	
	ResourceLocation texture;
	
	public ItemRenderWeaponFFBolt(ResourceLocation texture) {
		this.texture = texture;
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
		GL11.glShadeModel(GL11.GL_SMOOTH);
		boolean renderBolt = true;
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.5D;
			GL11.glTranslated(0.5, 0.25, 0);
			GL11.glScaled(s0, s0, s0);
			GL11.glRotated(20, 0, -1, 0);
			GL11.glRotated(15, 0, 0, 1);
			
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
			GL11.glTranslated(recoil[0] * -0.5, 0, 0);

			double[] pull = HbmAnimations.getRelevantTransformation("LEVER_PULL");
			double[] rotate = HbmAnimations.getRelevantTransformation("LEVER_ROTATE");
			
			GL11.glPushMatrix();
			GL11.glTranslated(pull[0], 0, 0);
			double heightOffset = 0.52D;
			GL11.glTranslated(0, heightOffset, 0);
			GL11.glRotated(rotate[0] * 35, -1, 0, 0);
			GL11.glTranslated(0, -heightOffset, 0);
			ResourceManager.rem700.renderPart("Bolt");
			GL11.glPopMatrix();
			
			renderBolt = false;
			
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glRotated(-75, 0, 1, 0);
			GL11.glRotated(-10, 0, 0, 1);
			GL11.glRotated(10, 1, 0, 0);
			GL11.glTranslated(0.3, 0.15, -0.5);
			GL11.glScaled(scale, scale, scale);
			
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glScaled(s1, s1, s1);
			GL11.glRotated(90, 0, 1, 0);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 1.5D;
			GL11.glTranslated(10, 11, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		ResourceManager.rem700.renderPart("Gun");
		if(renderBolt)
			ResourceManager.rem700.renderPart("Bolt");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
