package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class ModelCloak extends ModelBiped
{
	  public ModelCloak()
	  {
	    textureWidth = 64;
	    textureHeight = 32;
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	  {
		  EntityPlayer player = (EntityPlayer)entity;
		  if(player.isSneaking())
		  {
			  this.isSneak = true;
		  } else {
			  this.isSneak = false;
		  }
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	  }
	  @Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	  {
		  if(par1Entity instanceof AbstractClientPlayer)
		  {
			  AbstractClientPlayer player = (AbstractClientPlayer) par1Entity;

				GL11.glPushMatrix();
	            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
	            double d3 = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * (double)par7 - (player.prevPosX + (player.posX - player.prevPosX) * (double)par7);
	            double d4 = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * (double)par7 - (player.prevPosY + (player.posY - player.prevPosY) * (double)par7);
	            double d0 = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * (double)par7 - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)par7);
	            float f4 = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * par7;
	            double d1 = (double)MathHelper.sin(f4 * (float)Math.PI / 180.0F);
	            double d2 = (double)(-MathHelper.cos(f4 * (float)Math.PI / 180.0F));
	            float f5 = (float)d4 * 10.0F;

	            if (f5 < -6.0F)
	            {
	                f5 = -6.0F;
	            }

	            if (f5 > 32.0F)
	            {
	                f5 = 32.0F;
	            }

	            float f6 = (float)(d3 * d1 + d0 * d2) * 100.0F;
	            float f7 = (float)(d3 * d2 - d0 * d1) * 100.0F;

	            if (f6 < 0.0F)
	            {
	                f6 = 0.0F;
	            }

	            float f8 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * par7;
	            f5 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * par7) * 6.0F) * 32.0F * f8;

	            if (player.isSneaking())
	            {
	                f5 += 25.0F;
	            }

	            GL11.glRotatef(6.0F + f6 / 2.0F + f5, 1.0F, 0.0F, 0.0F);
	            GL11.glRotatef(f7 / 2.0F, 0.0F, 0.0F, 1.0F);
	            GL11.glRotatef(-f7 / 2.0F, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			  this.bipedCloak.render(par7);
			  GL11.glPopMatrix();
		  }
	  }
}
