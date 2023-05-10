package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderCreeperUniversal extends RenderCreeper {

	private final ResourceLocation creeperTextures;
	private final ResourceLocation armoredCreeperTextures;
	private float swellMod = 1.0F;

	public RenderCreeperUniversal(String texture, String overlay) {
		super();

		creeperTextures = new ResourceLocation(texture);
		armoredCreeperTextures = new ResourceLocation(overlay);
	}
	
	public RenderCreeperUniversal setSwellMod(float mod) {
		this.swellMod = mod;
		return this;
	}
	
	@Override
	protected void preRenderCallback(EntityCreeper creeper, float interp) {
		float swell = creeper.getCreeperFlashIntensity(interp);
		float flash = 1.0F + MathHelper.sin(swell * 100.0F) * swell * 0.01F;

		if(swell < 0.0F) {
			swell = 0.0F;
		}

		if(swell > 1.0F) {
			swell = 1.0F;
		}

		swell *= swell;
		swell *= swell;
		swell *= swellMod;
		float scaleHorizontal = (1.0F + swell * 0.4F) * flash;
		float scaleVertical = (1.0F + swell * 0.1F) / flash;
		GL11.glScalef(scaleHorizontal, scaleVertical, scaleHorizontal);
	}

	@Override
	protected int shouldRenderPass(EntityCreeper creeper, int pass, float interp) {
		if(creeper.getPowered()) {
			if(creeper.isInvisible()) {
				GL11.glDepthMask(false);
			} else {
				GL11.glDepthMask(true);
			}

			if(pass == 1) {
				float time = (float) creeper.ticksExisted + interp;
				this.bindTexture(armoredCreeperTextures);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				float x = time * 0.01F;
				float y = time * 0.01F;
				GL11.glTranslatef(x, y, 0.0F);
				this.setRenderPassModel(this.creeperModel);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_BLEND);
				float flashColor = 0.5F;
				GL11.glColor4f(flashColor, flashColor, flashColor, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				return 1;
			}

			if(pass == 2) {
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}

		return -1;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCreeper p_110775_1_) {
		return creeperTextures;
	}
}