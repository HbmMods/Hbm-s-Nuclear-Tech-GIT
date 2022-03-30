package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderObj implements IItemRenderer {
	
	public ItemRenderObj() { }

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
		switch(type) {
		case EQUIPPED_FIRST_PERSON:
			GL11.glRotatef(70F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-50F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-0.6F, -0.9F, 0.2F);
		case EQUIPPED:
		case ENTITY:
		default:
			GL11.glPushMatrix();
//				if(item.getItem() == ModItems.gun_brimstone)
//					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.brimstone_tex);
				//GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(10F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(190F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-15F, 0.0F, 0.0F, 1.0F);
				GL11.glScalef(0.75F, 0.75F, 0.75F);
				GL11.glTranslatef(-0.7F, -0.4F, -1.1F);
				GL11.glDisable(GL11.GL_CULL_FACE);
//				if(item.getItem() == ModItems.gun_brimstone)
//					ResourceManager.brimstone.renderAll();
			GL11.glPopMatrix(); break;
		}
	}
}