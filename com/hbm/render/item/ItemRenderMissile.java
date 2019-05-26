package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePart;
import com.hbm.render.misc.MissilePronter;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemRenderMissile implements IItemRenderer {

	public ItemRenderMissile() { }
	
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

		MissileMultipart missile = MissileMultipart.loadFromStruct(ItemCustomMissile.getStruct(item));
		
		GL11.glPushMatrix();
		
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			
			double s = 0.2;
			GL11.glScaled(s, s, s);
			
			GL11.glTranslated(2, 0, 0);
			
			MissilePronter.prontMissile(missile, Minecraft.getMinecraft().renderEngine);
			
			break;
			
		case INVENTORY:
			
			double height = missile.getHeight();
			
			if(height == 0D)
				height = 4D;
			
			double size = 20;
			double scale = size / height;
			
			GL11.glTranslated(height / 2 * scale, 0, 0);

			GL11.glRotated(135, 0, 0, 1);
			GL11.glRotated(215, 1, 0, 0);
			
			GL11.glTranslated(7, 14, 0);
			
			GL11.glScaled(-scale, -scale, -scale);
			
			/*if(part.type.name().equals(PartType.FINS.name())) {
				GL11.glTranslated(0, 0, 0);
				//GL11.glRotated(-45, 1, 0, 0);
			}*/

			GL11.glRotatef(System.currentTimeMillis() / 25 % 360, 0, -1, 0);
			MissilePronter.prontMissile(missile, Minecraft.getMinecraft().renderEngine);
			
			break;
		default: break;
		}
		
		GL11.glPopMatrix();
	}
}
