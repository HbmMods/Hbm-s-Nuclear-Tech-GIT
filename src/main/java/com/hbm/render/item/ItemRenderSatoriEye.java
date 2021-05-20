package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderSatoriEye implements IItemRenderer
{
	public ItemRenderSatoriEye() {}

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
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		if (MainRegistry.polaroidID == 11)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.satori_eye_open_tex);
		else
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.satori_eye_closed_tex);
		
		float scale1 = 0.1F;
		float scale2 = 5.0F;
		float scale3 = 4.25F;
		
		switch (type)
		{
		case ENTITY:// Dropped item
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glScalef(scale1, scale1, scale1);
			GL11.glTranslatef(5.0F, 5.0F, 0F);
			GL11.glRotatef(180F, 1, 0, 0);
			break;
		case EQUIPPED_FIRST_PERSON:// In hand POV
			GL11.glScalef(scale2, scale2, scale2);
			GL11.glTranslatef(4F, 0.75F, 2.0F);
			GL11.glRotatef(150.0F, 0, 1, 0);
			GL11.glRotatef(-10F, 0, 0, 1);
			GL11.glRotatef(-10F, 1, 0, 0);
			break;
		case INVENTORY:// Inventory icon
			GL11.glScalef(scale3, scale3, scale3);
			GL11.glTranslatef(1.9F, 1.9F, 1.0F);
			GL11.glRotatef(90.0F, 0F, 1.0F, 0F);
			GL11.glRotatef(180.0F, 1.0F, 0F, 0F);
			break;
		default:
			break;
		}
		ResourceManager.satori_eye.renderAll();
		GL11.glPopMatrix();
	}

}
