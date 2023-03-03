package com.hbm.render.entity.effect;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.particle.EntityModFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class MultiCloudRenderer extends Render {
	private Item[] field_94151_a;
	private int field_94150_f;
	public MultiCloudRenderer(Item[] p_i1259_1_, int p_i1259_2_) {
		this.field_94151_a = p_i1259_1_;
		this.field_94150_f = p_i1259_2_;
	}

	public MultiCloudRenderer(Item[] p_i1260_1_) {
		this(p_i1260_1_, 0);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void func_76986_a(T entity, double d, double d1, double d2, float f,
	 * float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		if (p_76986_1_ instanceof EntityModFX) {
			EntityModFX fx = (EntityModFX) p_76986_1_;

			Item item = field_94151_a[0];
			
			if (fx.particleAge <= fx.maxAge && fx.particleAge >= fx.maxAge / 8 * 7) {
				item = field_94151_a[7];
			}

			if (fx.particleAge < fx.maxAge / 8 * 7 && fx.particleAge >= fx.maxAge / 8 * 6) {
				item = field_94151_a[6];
			}

			if (fx.particleAge < fx.maxAge / 8 * 6 && fx.particleAge >= fx.maxAge / 8 * 5) {
				item = field_94151_a[5];
			}

			if (fx.particleAge < fx.maxAge / 8 * 5 && fx.particleAge >= fx.maxAge / 8 * 4) {
				item = field_94151_a[4];
			}

			if (fx.particleAge < fx.maxAge / 8 * 4 && fx.particleAge >= fx.maxAge / 8 * 3) {
				item = field_94151_a[3];
			}

			if (fx.particleAge < fx.maxAge / 8 * 3 && fx.particleAge >= fx.maxAge / 8 * 2) {
				item = field_94151_a[2];
			}

			if (fx.particleAge < fx.maxAge / 8 * 2 && fx.particleAge >= fx.maxAge / 8 * 1) {
				item = field_94151_a[1];
			}

			if (fx.particleAge < fx.maxAge / 8 && fx.particleAge >= 0) {
				item = field_94151_a[0];
			}

			IIcon iicon = item.getIconFromDamage(field_94150_f);

			if (iicon != null) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glScalef(7.5F, 7.5F, 7.5F);
				
				////
				Random randy = new Random(p_76986_1_.hashCode());
				////
				
				Random rand = new Random(100);
				
				for(int i = 0; i < 5; i++) {
					
					double d = randy.nextInt(10) * 0.05;
					GL11.glColor3d(1 - d, 1 - d, 1 - d);

					double dX = (rand.nextGaussian() - 1D) * 0.15D;
					double dY = (rand.nextGaussian() - 1D) * 0.15D;
					double dZ = (rand.nextGaussian() - 1D) * 0.15D;
					double size = rand.nextDouble() * 0.5D + 0.25D;
					
					GL11.glTranslatef((float) dX, (float) dY, (float) dZ);
					GL11.glScaled(size, size, size);

					GL11.glPushMatrix();
					this.bindEntityTexture(p_76986_1_);
					Tessellator tessellator = Tessellator.instance;
					this.func_77026_a(tessellator, iicon);
					GL11.glPopMatrix();

					GL11.glScaled(1/size, 1/size, 1/size);
					GL11.glTranslatef((float) -dX, (float) -dY, (float) -dZ);
				}
				
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glPopMatrix();
			}
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TextureMap.locationItemsTexture;
	}

	private void func_77026_a(Tessellator p_77026_1_, IIcon p_77026_2_) {
		float f = p_77026_2_.getMinU();
		float f1 = p_77026_2_.getMaxU();
		float f2 = p_77026_2_.getMinV();
		float f3 = p_77026_2_.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		p_77026_1_.startDrawingQuads();
		p_77026_1_.setNormal(0.0F, 1.0F, 0.0F);
		p_77026_1_.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
		p_77026_1_.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
		p_77026_1_.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2);
		p_77026_1_.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2);
		p_77026_1_.draw();
	}
}
