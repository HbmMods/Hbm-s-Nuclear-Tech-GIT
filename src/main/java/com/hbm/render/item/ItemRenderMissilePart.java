package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemCustomMissilePart.PartType;
import com.hbm.render.util.MissilePart;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderMissilePart implements IItemRenderer {
	
	MissilePart part;

	public ItemRenderMissilePart() { }
	
	public ItemRenderMissilePart(MissilePart part) { 
		this.part = part;
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
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		if(part == null)
			return;

		GL11.glPushMatrix();
		
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
			GL11.glTranslated(0.5, 0, 0);
			
		case ENTITY:
			double s = 0.4;
			GL11.glScaled(s, s, s);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(part.texture);
			part.model.renderAll();
			
			break;
			
		case INVENTORY:
			
			double height = part.guiheight;
			
			if(height == 0D)
				height = 4D;
			
			double size = 10;
			double scale = size / height;
			
			GL11.glTranslated(height / 2 * scale, 0, 0);
			GL11.glRotated(135, 0, 0, 1);
			GL11.glRotated(145, 1, 0, 0);
			
			if(part.type == PartType.WARHEAD) {
				GL11.glTranslated(0, height / 8 * scale, 0);
			}
			
			if(part.type == PartType.FUSELAGE) {
				GL11.glTranslated(0, height / 4 * scale, 0);
			}
			
			GL11.glTranslated(3.5, 14, 0);
			GL11.glScaled(-scale, -scale, -scale);

			GL11.glRotatef(System.currentTimeMillis() / 25 % 360, 0, -1, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(part.texture);
			part.model.renderAll();
			
			break;
		default: break;
		}
		
		GL11.glPopMatrix();
	}
}
