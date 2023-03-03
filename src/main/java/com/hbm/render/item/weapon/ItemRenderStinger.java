package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderStinger implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		ResourceLocation stingerTex = item.getItem() == ModItems.gun_stinger ? ResourceManager.stinger_tex : ResourceManager.sky_stinger_tex;
		Minecraft.getMinecraft().renderEngine.bindTexture(stingerTex);
		GL11.glPushMatrix();
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.25D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(-0.5, -0.7, -0.5);
			GL11.glRotated(-100, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
				
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-170, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-15F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-2F, -3F, 4.0F);
			
			break;
			
		case ENTITY:

			double s1 = 0.2D;
			GL11.glScaled(s1, s1, s1);
			GL11.glTranslatef(0F, -2.5F, 0F);
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 1.75D;
			GL11.glTranslated(4, 11, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(135, 1, 0, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;	
		}
		ResourceManager.stinger.renderAll();
		GL11.glPopMatrix();
	}
}