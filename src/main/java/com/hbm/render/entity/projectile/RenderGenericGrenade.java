package com.hbm.render.entity.projectile;

import com.hbm.entity.grenade.EntityDisperserCanister;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.grenade.IGenericGrenade;
import com.hbm.inventory.fluid.FluidType;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class RenderGenericGrenade extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		
		boolean disperser = entity instanceof EntityDisperserCanister;
		
		for(int i = 0; i < (disperser ? 2 : 1); i++) {
			
			IIcon iicon;
			if(disperser){
				EntityDisperserCanister canister = (EntityDisperserCanister) entity;
				FluidType fluid = canister.getFluid();
				iicon = canister.getType().getIconFromDamageForRenderPass(fluid.getID(), i);
				
				if(i == 1) {
					int hex = fluid.getColor();
					int r = (hex & 0xFF0000) >> 16;
					int g = (hex & 0xFF00) >> 8;
					int b = (hex & 0xFF);
					GL11.glColor3b((byte) (r / 2), (byte) (g / 2), (byte) (b / 2));
				}
				
			} else {
				IGenericGrenade grenade = (IGenericGrenade) entity;
				iicon = grenade.getGrenade().getIconFromDamage(i);
			}
	
			if(iicon != null) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x, (float) y, (float) z);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				this.bindEntityTexture(entity);
				Tessellator tessellator = Tessellator.instance;
	
				this.renderItem(tessellator, iicon);
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glPopMatrix();
			}
			
			GL11.glColor3f(1F, 1F, 1F);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}

	private void renderItem(Tessellator tess, IIcon icon) {
		float minU = icon.getMinU();
		float maxU = icon.getMaxU();
		float minV = icon.getMinV();
		float maxV = icon.getMaxV();
		float max = 1.0F;
		float offX = 0.5F;
		float offY = 0.25F;
		
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.addVertexWithUV((double) (0.0F - offX), (double) (0.0F - offY), 0.0D, (double) minU, (double) maxV);
		tess.addVertexWithUV((double) (max - offX), (double) (0.0F - offY), 0.0D, (double) maxU, (double) maxV);
		tess.addVertexWithUV((double) (max - offX), (double) (max - offY), 0.0D, (double) maxU, (double) minV);
		tess.addVertexWithUV((double) (0.0F - offX), (double) (max - offY), 0.0D, (double) minU, (double) minV);
		tess.draw();
	}
}
