package com.hbm.particle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.main.ResourceManager;
import com.hbm.util.Tuple.Pair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleSpentCasing extends EntityFX {
	
	public static final Random rand = new Random();
	private static float dScale = 0.05F, smokeJitter = 0.025F, smokeAccel = 0.5F;
	private static byte maxSmokeGen = 60, maxSmokeLife = 120;

	private final List<Pair<Vec3, Double>> smokeNodes = new ArrayList();

	private final TextureManager textureManager;

	private final SpentCasing config;
	private boolean smoke;

	private float momentumPitch, momentumYaw;
	private boolean onGroundPreviously = false;
	private double maxHeight;

	public ParticleSpentCasing(TextureManager textureManager, World world, double x, double y, double z, double mx, double my, double mz, float momentumPitch, float momentumYaw, SpentCasing config) {
		super(world, x, y, z, 0, 0, 0);
		this.textureManager = textureManager;
		this.momentumPitch = momentumPitch;
		this.momentumYaw = momentumYaw;
		this.config = config;

		particleMaxAge = config.getMaxAge();
		smoke = rand.nextFloat() < config.getSmokeChance();

		motionX = mx;
		motionY = my;
		motionZ = mz;

		particleGravity = 8F;

		maxHeight = y;
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(motionY > 0 && posY > maxHeight)
			maxHeight = posY;

		if(!onGroundPreviously && onGround)
			tryPlayBounceSound();

		if(!onGroundPreviously && onGround) {
			
			onGroundPreviously = true;
			motionY = Math.log10(maxHeight - posY + 2);
			momentumPitch = (float) rand.nextGaussian() * config.getBouncePitch();
			momentumYaw = (float) rand.nextGaussian() * config.getBounceYaw();
			maxHeight = posY;
			
		} else if(onGroundPreviously && !onGround) {
			onGroundPreviously = false;
		}

		if (particleAge > maxSmokeLife && !smokeNodes.isEmpty())
			smokeNodes.clear();

		if(smoke && particleAge <= maxSmokeLife) {
			
			//motion-based smoke changes were moved to rendering (to account for interp in realtime)

			for(Pair<Vec3, Double> pair : smokeNodes) {
				final Vec3 node = pair.getKey();

				node.xCoord += rand.nextGaussian() * smokeJitter;
				node.zCoord += rand.nextGaussian() * smokeJitter;
			}

			if(particleAge < maxSmokeGen || inWater) {
				final double alpha = (particleAge / 20d);
				smokeNodes.add(new Pair<Vec3, Double>(Vec3.createVectorHelper(0, 0, 0), alpha));
			}
		}

		prevRotationPitch = rotationPitch;
		prevRotationYaw = rotationYaw;

//		if (motionY > gravity && !onGround)
//			motionY += gravity;
//		if (motionY < -0.75)
//			motionY = -0.75;

		if(onGround)
			rotationPitch = 0;
		else {
			rotationPitch += momentumPitch;
			rotationYaw += momentumYaw;
		}
	}

	@Override
	public void renderParticle(Tessellator tessellator, float interp, float x, float y, float z, float tx, float tz) {

		GL11.glPushMatrix();
		RenderHelper.enableStandardItemLighting();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		double pX = prevPosX + (posX - prevPosX) * interp;
		double pY = prevPosY + (posY - prevPosY) * interp;
		double pZ = prevPosZ + (posZ - prevPosZ) * interp;
		
		int brightness = worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(pX), MathHelper.floor_double(pY), MathHelper.floor_double(pZ), 0);
		int lX = brightness % 65536;
		int lY = brightness / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lX / 1.0F, (float)lY / 1.0F);

		textureManager.bindTexture(ResourceManager.casings_tex);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;

		GL11.glTranslated(pX - dX, pY - dY - this.height / 4, pZ - dZ);

		GL11.glScalef(dScale, dScale, dScale);

		GL11.glRotatef(180 - rotationYaw, 0, 1, 0);
		GL11.glRotatef(-rotationPitch, 1, 0, 0);

		GL11.glScalef(config.getScaleX(), config.getScaleY(), config.getScaleZ());

		int index = 0;
		for(String name : config.getType().partNames) {
			int col = this.config.getColors()[index]; //unsafe on purpose, set your colors properly or else...!
			Color color = new Color(col);
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			ResourceManager.casings.renderPart(name);
			index++;
		}
		
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);

		/*if(!smokeNodes.isEmpty()) {
			tessellator.startDrawingQuads();
			tessellator.setNormal(0F, 1F, 0F);

			for(int i = 0; i < smokeNodes.size() - 1; i++) {
				final Pair<Vec3, Double> node = smokeNodes.get(i), past = smokeNodes.get(i + 1);
				final Vec3 nodeLoc = node.getKey(), pastLoc = past.getKey();
				final float nodeAlpha = node.getValue().floatValue(), pastAlpha = past.getValue().floatValue(), scale = config.getScaleX();

				tessellator.setColorRGBA_F(1F, 1F, 1F, nodeAlpha);
				tessellator.addVertex(nodeLoc.xCoord, nodeLoc.yCoord, nodeLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(nodeLoc.xCoord + scale, nodeLoc.yCoord, nodeLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(pastLoc.xCoord + scale, pastLoc.yCoord, pastLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, pastAlpha);
				tessellator.addVertex(pastLoc.xCoord, pastLoc.yCoord, pastLoc.zCoord);

				tessellator.setColorRGBA_F(1F, 1F, 1F, nodeAlpha);
				tessellator.addVertex(nodeLoc.xCoord, nodeLoc.yCoord, nodeLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(nodeLoc.xCoord - scale, nodeLoc.yCoord, nodeLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(pastLoc.xCoord - scale, pastLoc.yCoord, pastLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, pastAlpha);
				tessellator.addVertex(pastLoc.xCoord, pastLoc.yCoord, pastLoc.zCoord);
			}

			GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			tessellator.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
		}*/

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		
		RenderHelper.disableStandardItemLighting();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posZ);

		if(this.worldObj.blockExists(i, 0, j)) {
			double d0 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66D;
			int k = MathHelper.floor_double(this.posY - (double) this.yOffset + d0);
			return this.worldObj.getLightBrightnessForSkyBlocks(i, k, j, 0);
		} else {
			return 0;
		  }
    }

	private void tryPlayBounceSound() {

		String sound = config.getSound();
		
		if(sound != null && !sound.isEmpty()) {
			worldObj.playSoundAtEntity(this, sound, 2, 1);
		}
	}
}