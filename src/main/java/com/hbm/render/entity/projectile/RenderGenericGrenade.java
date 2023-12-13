package com.hbm.render.entity.projectile;

import com.hbm.entity.grenade.EntityDisperserCanister;
import net.minecraft.item.Item;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.grenade.IGenericGrenade;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class RenderGenericGrenade extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		IIcon iicon;
		if(entity instanceof EntityDisperserCanister){
			EntityDisperserCanister canister = (EntityDisperserCanister) entity;
			iicon = canister.getType().getIconFromDamage(canister.getFluid().getID());
		} else {
			IGenericGrenade grenade = (IGenericGrenade) entity;
			iicon = grenade.getGrenade().getIconFromDamage(0);
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
