package com.hbm.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.wiaj.WorldInAJar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ParticleDebris extends EntityFX {
	
	private RenderBlocks renderer;
	public WorldInAJar world;
	public static Random rng = new Random();

	public ParticleDebris(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public ParticleDebris(World world, double x, double y, double z, double mx, double my, double mz) {
		super(world, x, y, z);
		double mult = 3;
		this.motionX = mx * mult;
		this.motionY = my * mult;
		this.motionZ = mz * mult;
		this.particleMaxAge = 100;
		this.particleGravity = 0.15F;
		this.noClip = true;
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		if(this.particleAge > 5) this.noClip = false;
		
		rng.setSeed(this.getEntityId());
		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;
		this.rotationPitch += rng.nextFloat() * 10;
		this.rotationYaw += rng.nextFloat() * 10;

		if(this.getEntityId() % 3 == 0) {
			TextureManager man = Minecraft.getMinecraft().renderEngine;
			ParticleRocketFlame fx = new ParticleRocketFlame(man, worldObj, posX, posY, posZ).setScale(1F * Math.max(world.sizeY, 6) / 16F);
			fx.prevPosX = fx.posX;
			fx.prevPosY = fx.posY;
			fx.prevPosZ = fx.posZ;
			fx.setMaxAge(50);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
		
		this.motionY -= this.particleGravity;
		
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
		this.particleAge++;
		if(this.onGround || this.isInWeb) this.setDead();
	}

	@Override
	public void renderParticle(Tessellator tess, float interp, float fX, float fY, float fZ, float sX, float sZ) {
		
		if(world == null) return;
		
		if(renderer == null) {
			renderer = new RenderBlocks(world);
		}

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;
		
		float pX = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double) interp - dX));
		float pY = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double) interp - dY));
		float pZ = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - dZ));
		
		renderer.enableAO = true;
		world.lightlevel = worldObj.getLightBrightnessForSkyBlocks((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ), 0);

		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_BLEND);
		//OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		
		GL11.glPushMatrix();
		GL11.glTranslatef(pX, pY, pZ);
		GL11.glRotated(prevRotationPitch + (rotationPitch - prevRotationPitch) * interp, 0, 1, 0);
		GL11.glRotated(prevRotationYaw + (rotationYaw - prevRotationYaw) * interp, 0, 0, 1);
		GL11.glTranslated(-world.sizeX / 2D, -world.sizeY / 2D, -world.sizeZ / 2D);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator.instance.startDrawingQuads();
		
		for(int ix = 0; ix < world.sizeX; ix++) {
			for(int iy = 0; iy < world.sizeY; iy++) {
				for(int iz = 0; iz < world.sizeZ; iz++) {
					try { renderer.renderBlockByRenderType(world.getBlock(ix, iy, iz), ix, iy, iz); } catch(Exception ex) { }
				}
			}
		}
		
		Tessellator.instance.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
