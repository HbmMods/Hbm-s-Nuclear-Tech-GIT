package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.particle.SpentCasingConfig.CasingType;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderCasingTest implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.casings_tex);
		
//		GL11.glTranslated(
//				player.prevPosX + (player.posX - player.prevPosX),
//				player.prevPosY + (player.posY - player.prevPosY),
//				player.prevPosZ + (player.posZ - player.prevPosZ));
		
//		GL11.glRotatef(player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw),
//				0.0F, 1.0F, 0.0F);
//		GL11.glRotatef(player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch),
//				0.0F, 0.0F, 1.0F);
		
		GL11.glRotatef(180 - player.rotationYaw, 0, 1, 0);
		GL11.glRotatef(-player.rotationPitch, 1, 0, 0);
		
		ResourceManager.casings.renderPart(CasingType.BRASS_BOTTLENECK.objName);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

}
