package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.item.EntityTNTPrimedBase;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderTNTPrimedBase extends Render {
	
	private RenderBlocks blockRenderer = new RenderBlocks();

	public RenderTNTPrimedBase() {
		this.shadowSize = 0.5F;
	}
	
	public void doRender(EntityTNTPrimedBase tnt, double x, double y, double z, float f0, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(-90F, 0F, 1F, 0F);
		
		float f2;

		if((float) tnt.fuse - f1 + 1.0F < 10.0F) {
			f2 = 1.0F - ((float) tnt.fuse - f1 + 1.0F) / 10.0F;

			if(f2 < 0.0F) {
				f2 = 0.0F;
			}

			if(f2 > 1.0F) {
				f2 = 1.0F;
			}

			f2 *= f2;
			f2 *= f2;
			float scale = 1.0F + f2 * 0.3F;
			GL11.glScalef(scale, scale, scale);
		}

		f2 = (1.0F - ((float) tnt.fuse - f1 + 1.0F) / 100.0F) * 0.8F;
		this.bindEntityTexture(tnt);
		this.blockRenderer.renderBlockAsItem(tnt.getBlock(), 0, tnt.getBrightness(f1));
		
		if(tnt.fuse / 5 % 2 == 0) {
			
			GL11.glScaled(1.01, 1.01, 1.01);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f2);
			boolean prev = this.blockRenderer.useInventoryTint;
			this.blockRenderer.useInventoryTint = false;
			this.blockRenderer.renderBlockAsItem(tnt.getBlock(), 0, 1.0F);
			this.blockRenderer.useInventoryTint = prev;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}

		GL11.glPopMatrix();
	}
	
	protected ResourceLocation getEntityTexture(EntityTNTPrimedBase tnt) {
		return TextureMap.locationBlocksTexture;
	}
	
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityTNTPrimedBase) entity);
	}
	
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		this.doRender((EntityTNTPrimedBase) entity, x, y, z, f0, f1);
	}
}
