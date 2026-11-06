package com.hbm.render.world.skyfx;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SkyEffectSatExplosion extends SkyEffect {
	
	private static final ResourceLocation flashtex = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");
	private static final ResourceLocation wavetex = new ResourceLocation(RefStrings.MODID + ":textures/particle/shockwave.png");

	public SkyEffectSatExplosion(World world) {
		super(world);
	}

	@Override
	public void updateTick() {
		super.updateTick();
		
		if(this.ticksExisted >= 200) this.expire();
	}

	@Override
	public void renderTick(float interp) {
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		
		this.bindTexture(wavetex);
		Tessellator tess = Tessellator.instance;

		double x = prevPosX + (posX - prevPosX) * interp;
		double y = prevPosY + (posY - prevPosY) * interp;
		double z = prevPosZ + (posZ - prevPosZ) * interp;
		
		double waveSize = (ticksExisted + interp) / 200D * 15;
		double flashSize = 15D - (ticksExisted + interp) / 100F;
		double arcSize = (ticksExisted + interp) / 2.5D + 10;
		float waveAlpha = 1F - (ticksExisted + interp) / 200F;
		float flashAlpha = 1F - (ticksExisted + interp) / 200F;
		float arcAlpha = 1F - (ticksExisted + interp) / 40F;
		
		tess.startDrawingQuads();
		tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, waveAlpha * 0.5F);
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);
		tess.addVertexWithUV(x - waveSize, y, z - waveSize, 0, 0);
		tess.addVertexWithUV(x + waveSize, y, z - waveSize, 1, 0);
		tess.addVertexWithUV(x + waveSize, y, z + waveSize, 1, 1);
		tess.addVertexWithUV(x - waveSize, y, z + waveSize, 0, 1);
		tess.draw();

		this.bindTexture(flashtex);
		
		y -= 1;
		
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);
		tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, flashAlpha);
		tess.addVertexWithUV(x - flashSize, y, z - flashSize, 0, 0);
		tess.addVertexWithUV(x + flashSize, y, z - flashSize, 1, 0);
		tess.addVertexWithUV(x + flashSize, y, z + flashSize, 1, 1);
		tess.addVertexWithUV(x - flashSize, y, z + flashSize, 0, 1);
		tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, arcAlpha);
		tess.addVertexWithUV(x - arcSize, y, z - arcSize, 0, 0);
		tess.addVertexWithUV(x + arcSize, y, z - arcSize, 1, 0);
		tess.addVertexWithUV(x + arcSize, y, z + arcSize, 1, 1);
		tess.addVertexWithUV(x - arcSize, y, z + arcSize, 0, 1);
		tess.draw();

		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
	}
}
