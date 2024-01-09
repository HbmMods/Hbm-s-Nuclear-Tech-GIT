package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderM2 extends ItemRenderBase {
	
	public ItemRenderM2() { }
	
	static final float scale1 = 0.35F, scale2 = 2.25F, scale3 = 0.25F, scale4 = 0.5F;
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		switch (type) {
			case ENTITY:
				GL11.glTranslated(0, -0.25, 0);
				GL11.glRotatef(90, 0, 1, 0);
				GL11.glScalef(scale4, scale4, scale4); 
				break;
			case EQUIPPED:
				GL11.glScalef(scale1, scale1, -scale1);
				GL11.glRotatef(-90, 0, 1, 0);
				GL11.glTranslatef(-0.4f, 0.5f, -5);
				GL11.glRotatef(30, 1, 0, 0);
				break;
			case EQUIPPED_FIRST_PERSON:
				
				if (Minecraft.getMinecraft().thePlayer.isSneaking()) {
					GL11.glRotatef(-90, 0, 1, 0);
					GL11.glTranslatef(-0.96f, -0.9f, -2);
					GL11.glRotatef(-5.75f, 0, 1, 1);// Just of by 0.15
					
					/* vvv remove to restore original look vvv */
					GL11.glRotatef(1.9F, 0, 0, 1);
					GL11.glTranslatef(0.06F, 0, 0);
					GL11.glRotatef(-0.2F, 0, 1, 0);
					GL11.glRotatef(1F, 1, 0, 0);
					GL11.glTranslatef(0, 1.15F, -1.75F);
					
				} else {
					GL11.glRotatef(-95, 0, 1, 0);
					GL11.glTranslatef(0, -1, -3);
				}
				GL11.glRotatef(25, 1, 0, 0);
				
				double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
				GL11.glTranslated(0, 0, recoil[0] * 0.35);
				
				break;
			case INVENTORY:
				GL11.glScalef(scale2, scale2, scale2);
				GL11.glTranslated(2.75, 5, 0);
				GL11.glRotatef(90, 0, 1, 0);
				GL11.glRotatef(-45, 1, 0, 0);
				GL11.glRotatef(180, 0, 0, 1);
				break;
			default: break;
		}
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.m2_tex);
		ResourceManager.m2.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}