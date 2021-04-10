package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponTWR implements IItemRenderer
{
	public ItemRenderWeaponTWR() {}
	
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

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		
		//GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.twr_tex);
		float scale1 = 0.09F;
		double scale2 = 0.05D;
		double scale3 = 0.25D;
		switch (type)
		{
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -1.0F, -0.6F);
			GL11.glScalef(scale1, scale1, scale1);
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -0.6F, -1.25F);
			GL11.glScaled(scale2, scale2, scale2);
			break;
		case ENTITY:// Dropped entity
			//GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
			//GL11.glTranslatef(0.0F, 0.0F, 0.0F);
			GL11.glScaled(0.09D, 0.09D, 0.09D);
			break;
		case INVENTORY:// Inventory icon
			GL11.glScaled(scale3, scale3, -scale3);
			GL11.glTranslatef(25.0F, 35.0F, 0.0F);
			GL11.glRotatef(270.0F, 10.0F, 0.0F, 0.0F);
			GL11.glRotatef(50.0F, 0.0F, 10.0F, 0.0F);
			GL11.glRotatef(270.0F, 0.0F, 0.0F, 10.0F);
		default:
			break;
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		ResourceManager.twr.renderAll();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.twr_tex);
		GL11.glPopMatrix();
	}

}
