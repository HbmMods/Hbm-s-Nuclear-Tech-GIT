package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderThompsonRedux implements IItemRenderer
{
	static Minecraft mc = Minecraft.getMinecraft();

	public ItemRenderThompsonRedux() {}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		switch (type)
		{
		case FIRST_PERSON_MAP:
			return false;
		default:
			return true;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}

	static final float scale1 = 2F;
	static final float scale2 = 1F;
	static final float scale3 = 5F;
	static final float scale4 = 10F;
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		mc.renderEngine.bindTexture(ResourceManager.thompson_tex_redux);
		EntityPlayer player = mc.thePlayer;
		
		switch (type)
		{
		case ENTITY:// Dropped item
			GL11.glScalef(scale1, scale1, scale1);
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glScalef(scale2, scale2, scale2);
			GL11.glRotatef(90F, 0F, 1F, 0F);
			GL11.glRotatef(-45F, 1F, 0F, 0F);
			GL11.glTranslatef(0F, -0.2F, 0.85F);
			break;
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glScalef(scale3, scale3, scale3);
			GL11.glRotatef(-90F, 1F, 0F, 0F);
			GL11.glRotatef(60F, 0F, 1F, 0F);
			GL11.glRotatef(90F, 0F, 0F, 2F);
			GL11.glTranslatef(0.025F, -0.2F, 0F);
			break;
		case INVENTORY:
			GL11.glScalef(scale4, scale4, scale4);
			GL11.glRotatef(-90F, 1F, 0F, 0F);
			GL11.glRotatef(-125F, 0F, 1F, 0F);
			GL11.glRotatef(90F, 0F, 0F, 1F);
			GL11.glTranslatef(0F, -0.2F, -1F);
			break;
		default:
			break;
		}
		
		ResourceManager.thompson_redux.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

}
