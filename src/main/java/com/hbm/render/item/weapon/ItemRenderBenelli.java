package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemRenderBenelli implements IItemRenderer
{
	public ItemRenderBenelli() {}
	
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
		int magSize = ItemGunBase.getMag(item);
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		GL11.glPushMatrix();
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hlr_tex);//TODO finish
		final float scale1 = 0.2F;
		final double scale2 = 0.065D;
		final double scale3 = 0.52D;
		
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] eject = HbmAnimations.getRelevantTransformation("EJECT");
		double[] reload = HbmAnimations.getRelevantTransformation("RELOAD");
		double[] pump = HbmAnimations.getRelevantTransformation("PUMP");
		switch (type)
		{
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -1F, -0.5F);
			GL11.glScalef(scale1, scale1, scale1);
//			if (player.isSneaking())
//			{
//				GL11.glTranslatef(-5.05F, 3.0F, 2.0F);
//				GL11.glRotatef(5.0F, 0.0F, -1.0F, 0.0F);
//				GL11.glRotatef(5.0F, -1.0F, 0.0F, 0.0F);
//				GL11.glRotatef(5.0F, 0.0F, 0.0F, 1.0F);
//			}
			// Move on recoil
			GL11.glTranslated(0, recoil[1], recoil[2]);
			GL11.glRotated(recoil[0], 1, 0, 0);
			// Move up for reload
			GL11.glPushMatrix();
//			GL11.glTranslated(reload[2] / 2, 0, 0);
//			GL11.glRotated(reload[0], 1, 0, 0);
//			GL11.glRotated(reload[1], 0, 0, 1);
			ResourceManager.benelli.renderPart("Body_Cube.002");
			// Pump new round if empty
			if (magSize == 0)
				GL11.glTranslated(pump[0], pump[1], pump[2]);
			ResourceManager.benelli.renderPart("Pump_Cylinder.003");
			GL11.glPopMatrix();
			// Eject spent shell
			GL11.glPushMatrix();
			GL11.glTranslated(eject[0], eject[1], eject[2]);
			ResourceManager.benelli.renderPart("Shell_Cylinder.002");
			GL11.glPopMatrix();
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.0F, -0.2F, -0.65F);
//			GL11.glTranslated(0, recoil[1], recoil[2] * 0.5);
			GL11.glRotated(recoil[0], 1, 0, 0);
			GL11.glScaled(scale2, scale2, scale2);
			
			GL11.glPushMatrix();
			GL11.glTranslated(-eject[0], eject[1], eject[2]);
			ResourceManager.benelli.renderPart("Shell_Cylinder.002");
			GL11.glPopMatrix();
			break;
		case ENTITY:// Dropped entity
			//GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
			//GL11.glTranslatef(0.0F, 0.0F, 0.0F);
			GL11.glScaled(0.125D, 0.125D, 0.125D);
			break;
		case INVENTORY:// Inventory icon
			GL11.glScaled(scale3, scale3, -scale3);
			GL11.glTranslatef(22.0F, 22.0F, 0.0F);
			GL11.glRotatef(270.0F, 10.0F, 0.0F, 0.0F);
			GL11.glRotatef(47.5F, 0.0F, 10.0F, 0.0F);
			GL11.glRotatef(270.0F, 0.0F, 0.0F, 10.0F);
		default:
			break;
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		if (type != ItemRenderType.EQUIPPED_FIRST_PERSON)
			ResourceManager.benelli.renderAll();
//		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.??);
		GL11.glPopMatrix();

	}

}
