package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderHLR implements IItemRenderer
{
	public ItemRenderHLR() {}
	
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
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		GL11.glPushMatrix();
		
		//GL11.glShadeModel(GL11.GL_SMOOTH);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hlr_tex);
		final float scale1 = 0.2F;
		final double scale2 = 0.065D;
		final double scale3 = 0.425D;
		switch (type)
		{
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -1F, -0.5F);
			GL11.glScalef(scale1, scale1, scale1);
			if (player.isSneaking())
			{
				GL11.glTranslatef(-5.05F, 3.0F, 2.0F);
				GL11.glRotatef(5.0F, 0.0F, -1.0F, 0.0F);
				GL11.glRotatef(5.0F, -1.0F, 0.0F, 0.0F);
//				GL11.glRotatef(5.0F, 0.0F, 0.0F, 1.0F);
			}
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.0F, -0.2F, -1.0F);
			GL11.glScaled(scale2 - 0.12, scale2, scale2);
			break;
		case ENTITY:// Dropped entity
			GL11.glScaled(0.125D, 0.125D, 0.125D);
			break;
		case INVENTORY:// Inventory icon
			GL11.glScaled(scale3, scale3, -scale3);
			GL11.glTranslatef(25.0F, 27.5F, 0.0F);
			GL11.glRotatef(270.0F, 10.0F, 0.0F, 0.0F);
			GL11.glRotatef(47.5F, 0.0F, 10.0F, 0.0F);
			GL11.glRotatef(270.0F, 0.0F, 0.0F, 10.0F);
		default:
			break;
		}
		ResourceManager.hlr.renderAll();
//		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.??);
		GL11.glPopMatrix();

	}

}
