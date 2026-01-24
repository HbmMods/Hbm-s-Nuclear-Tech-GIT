package com.hbm.render.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.storage.TileEntityBatteryREDD;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Clock;
import com.hbm.util.Vec3NT;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

public class RenderBatteryREDD extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		bindTexture(ResourceManager.battery_redd_tex);
		ResourceManager.battery_redd.renderPart("Base");
		
		GL11.glPushMatrix();

		GL11.glTranslated(0, 5.5, 0);
		TileEntityBatteryREDD redd = (TileEntityBatteryREDD) tile;
		float speed = redd.getSpeed();
		double rot = redd.prevRotation + (redd.rotation - redd.prevRotation) * interp;
		GL11.glRotated(rot, 1, 0, 0);
		GL11.glTranslated(0, -5.5, 0);
		
		ResourceManager.battery_redd.renderPart("Wheel");
		
		RenderArcFurnace.fullbright(true);
		ResourceManager.battery_redd.renderPart("Lights");
		
		RenderArcFurnace.fullbright(false);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 5.5, 0);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDepthMask(false);
		
		Vec3NT vec = new Vec3NT(0, 0, 4);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		
		double len = 4.25D;
		double width = 0.125D;
		double span = speed * 0.75;
		
		if(span > 0) for(int j = -1; j <= 1; j += 2) {
			for(int i = 0; i < 8; i++) {
				double xOffset = 0.8125 * j;
				vec.setComponents(0, 1, 0);
				vec.rotateAroundXDeg(i * 45D);
				
				tess.setColorRGBA_F(1F, 1F, 0F, 0.75F);
				tess.addVertex(xOffset, vec.yCoord * len - vec.yCoord * width, vec.zCoord * len - vec.zCoord * width);
				tess.addVertex(xOffset, vec.yCoord * len + vec.yCoord * width, vec.zCoord * len + vec.zCoord * width);
				vec.rotateAroundXDeg(span);
				tess.setColorRGBA_F(1F, 1F, 0F, 0.5F);
				tess.addVertex(xOffset, vec.yCoord * len + vec.yCoord * width, vec.zCoord * len + vec.zCoord * width);
				tess.addVertex(xOffset, vec.yCoord * len - vec.yCoord * width, vec.zCoord * len - vec.zCoord * width);
				
				tess.setColorRGBA_F(1F, 1F, 0F, 0.5F);
				tess.addVertex(xOffset, vec.yCoord * len - vec.yCoord * width, vec.zCoord * len - vec.zCoord * width);
				tess.addVertex(xOffset, vec.yCoord * len + vec.yCoord * width, vec.zCoord * len + vec.zCoord * width);
				vec.rotateAroundXDeg(span);
				tess.setColorRGBA_F(1F, 1F, 0F, 0.25F);
				tess.addVertex(xOffset, vec.yCoord * len + vec.yCoord * width, vec.zCoord * len + vec.zCoord * width);
				tess.addVertex(xOffset, vec.yCoord * len - vec.yCoord * width, vec.zCoord * len - vec.zCoord * width);
				
				tess.setColorRGBA_F(1F, 1F, 0F, 0.25F);
				tess.addVertex(xOffset, vec.yCoord * len - vec.yCoord * width, vec.zCoord * len - vec.zCoord * width);
				tess.addVertex(xOffset, vec.yCoord * len + vec.yCoord * width, vec.zCoord * len + vec.zCoord * width);
				vec.rotateAroundXDeg(span);
				tess.setColorRGBA_F(1F, 1F, 0F, 0F);
				tess.addVertex(xOffset, vec.yCoord * len + vec.yCoord * width, vec.zCoord * len + vec.zCoord * width);
				tess.addVertex(xOffset, vec.yCoord * len - vec.yCoord * width, vec.zCoord * len - vec.zCoord * width);
			}
		}
		tess.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glPopAttrib();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPopMatrix();
		
		renderSparkle(tile);
		
		GL11.glPopMatrix();
		
		if(speed > 0) renderZaps(tile);
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
	
	protected void renderSparkle(TileEntity tile) {
		TileEntityBatteryREDD redd = (TileEntityBatteryREDD) tile;
		
		long time = Clock.get_ms();
		float alpha = 0.45F + (float) (Math.sin(time / 1000D) * 0.15F);
		float alphaMult = redd.getSpeed() / 15F;
		float r = 1.0F;
		float g = 0.25F;
		float b = 0.75F;
		
		double mainOsc = BobMathUtil.sps(time / 1000D) % 1D;
		double sparkleSpin = time / 250D * -1 % 1D;
		double sparkleOsc = Math.sin(time / 1000D) * 0.5D % 1D;

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDepthMask(false);

		GL11.glColor4f(r, g, b, alpha * alphaMult);
		
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		bindTexture(ResourceManager.fusion_plasma_tex);
		GL11.glTranslated(0, mainOsc, 0);
		ResourceManager.battery_redd.renderPart("Plasma");
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		// cost-cutting measure, don't render extra layers from more than 100m away
		if(MainRegistry.proxy.me().getDistanceSq(tile.xCoord + 0.5, tile.yCoord + 2.5, tile.zCoord + 0.5) < 100 * 100) {

			GL11.glColor4f(r * 2, g * 2, b * 2, 0.75F * alphaMult);
			
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			bindTexture(ResourceManager.fusion_plasma_sparkle_tex);
			GL11.glTranslated(sparkleSpin, sparkleOsc, 0);
			ResourceManager.battery_redd.renderPart("Plasma");
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glPopAttrib();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	protected void renderZaps(TileEntity tile) {
		
		Random rand = new Random(tile.getWorldObj().getTotalWorldTime() / 5);
		rand.nextBoolean();
		
		if(rand.nextBoolean()) {
			GL11.glPushMatrix();
			GL11.glTranslated(3.125, 5.5, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(-1.375, -2.625, 3.75), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 15, 0.25F, 3, 0.0625F);
			BeamPronter.prontBeam(Vec3.createVectorHelper(-1.375, -2.625, 3.75), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 1, 0, 3, 0.0625F);
			GL11.glPopMatrix();
		}
		
		if(rand.nextBoolean()) {
			GL11.glPushMatrix();
			GL11.glTranslated(-3.125, 5.5, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(1.375, -2.625, 3.75), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 15, 0.25F, 3, 0.0625F);
			BeamPronter.prontBeam(Vec3.createVectorHelper(1.375, -2.625, 3.75), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 1, 0, 3, 0.0625F);
			GL11.glPopMatrix();
		}
		
		if(rand.nextBoolean()) {
			GL11.glPushMatrix();
			GL11.glTranslated(3.125, 5.5, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(-1.375, -2.625, -3.75), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 15, 0.25F, 3, 0.0625F);
			BeamPronter.prontBeam(Vec3.createVectorHelper(-1.375, -2.625, -3.75), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 1, 0, 3, 0.0625F);
			GL11.glPopMatrix();
		}
		
		if(rand.nextBoolean()) {
			GL11.glPushMatrix();
			GL11.glTranslated(-3.125, 5.5, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(1.375, -2.625, -3.75), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 15, 0.25F, 3, 0.0625F);
			BeamPronter.prontBeam(Vec3.createVectorHelper(1.375, -2.625, -3.75), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x002040, (int)(System.currentTimeMillis() % 1000) / 50, 1, 0, 3, 0.0625F);
			GL11.glPopMatrix();
		}
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_battery_redd);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommon() {
				GL11.glRotated(-90, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.battery_redd_tex);
				ResourceManager.battery_redd.renderPart("Base");
				ResourceManager.battery_redd.renderPart("Wheel");
				GL11.glDisable(GL11.GL_LIGHTING);
				ResourceManager.battery_redd.renderPart("Lights");
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}

}
