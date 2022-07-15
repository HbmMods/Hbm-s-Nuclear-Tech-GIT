package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderTWR implements IItemRenderer
{
	public ItemRenderTWR() {}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		switch (type)
		{
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	static final float scale1 = 0.09f;
	static final float scale2 = 0.05f;
	static final float scale3 = 0.25f;

	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		
		//GL11.glShadeModel(GL11.GL_SMOOTH);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.twr_tex);
		switch (type)
		{
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -1.0F, -1.0F);
			GL11.glScalef(scale1, scale1, scale1);
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -0.6F, -1.25F);
			GL11.glScalef(scale2, scale2, scale2);
			break;
		case ENTITY:// Dropped entity
			GL11.glScalef(0.1f, 0.1f, 0.1f);
			break;
		case INVENTORY:// Inventory icon
			GL11.glScalef(scale3, scale3, -scale3);
			GL11.glTranslatef(25.0F, 35.0F, 0.0F);
			GL11.glRotatef(270.0F, 10.0F, 0.0F, 0.0F);
			GL11.glRotatef(50.0F, 0.0F, 10.0F, 0.0F);
			GL11.glRotatef(270.0F, 0.0F, 0.0F, 10.0F);
		default:
			break;
		}
		ResourceManager.twr.renderAll();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.twr_tex);
		GL11.glPopMatrix();
	}

}
