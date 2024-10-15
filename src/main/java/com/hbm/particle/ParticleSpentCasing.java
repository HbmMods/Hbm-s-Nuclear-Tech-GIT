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
	private static float dScale = 0.05F, smokeJitter = 0.001F;

	private int maxSmokeGen = 120;
	private double smokeLift = 0.5D;
	private int nodeLife = 30;

	private final List<Pair<Vec3, Double>> smokeNodes = new ArrayList();

	private final TextureManager textureManager;

	private final SpentCasing config;
	private boolean isSmoking;

	private float momentumPitch, momentumYaw;

	public ParticleSpentCasing(TextureManager textureManager, World world, double x, double y, double z, double mx, double my, double mz, float momentumPitch, float momentumYaw, SpentCasing config, boolean smoking, int smokeLife, double smokeLift, int nodeLife) {
		super(world, x, y, z, 0, 0, 0);
		this.textureManager = textureManager;
		this.momentumPitch = momentumPitch;
		this.momentumYaw = momentumYaw;
		this.config = config;

		this.particleMaxAge = config.getMaxAge();
		
		this.isSmoking = smoking;
		this.maxSmokeGen = smokeLife;
		this.smokeLift = smokeLift;
		this.nodeLife = nodeLife;
		
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;

		this.motionX = mx;
		this.motionY = my;
		this.motionZ = mz;

		particleGravity = 1F;
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

		if(this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}

		this.motionY -= 0.04D * (double) this.particleGravity;
		double prevMotionY = this.motionY;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.98D;
		this.motionY *= 0.98D;
		this.motionZ *= 0.98D;

		if(this.onGround) {
			this.motionX *= 0.7D;
			this.motionZ *= 0.7D;
		}

		if(onGround) {
			this.onGround = false;
			motionY = prevMotionY * -0.5;
			this.rotationPitch = 0;
			//momentumPitch = (float) rand.nextGaussian() * config.getBouncePitch();
			//momentumYaw = (float) rand.nextGaussian() * config.getBounceYaw();
			
		}

		if(particleAge > maxSmokeGen && !smokeNodes.isEmpty())
			smokeNodes.clear();

		if(isSmoking && particleAge <= maxSmokeGen) {

			for(Pair<Vec3, Double> pair : smokeNodes) {
				Vec3 node = pair.getKey();

				node.xCoord += rand.nextGaussian() * smokeJitter;
				node.zCoord += rand.nextGaussian() * smokeJitter;
				node.yCoord += smokeLift * dScale;
				
				pair.value = Math.max(0, pair.value - (1D / (double) nodeLife));
			}

			if(particleAge < maxSmokeGen || inWater) {
				smokeNodes.add(new Pair<Vec3, Double>(Vec3.createVectorHelper(0, 0, 0), smokeNodes.isEmpty() ? 0.0D : 1D));
			}
		}

		prevRotationPitch = rotationPitch;
		prevRotationYaw = rotationYaw;

		if(onGround) {
			rotationPitch = 0;
		} else {
			rotationPitch += momentumPitch;
			rotationYaw += momentumYaw;
		}
	}

	/** Used for frame-perfect translation of smoke */
	private boolean setupDeltas = false;
	private double prevRenderX;
	private double prevRenderY;
	private double prevRenderZ;

	@Override
	public void renderParticle(Tessellator tessellator, float interp, float x, float y, float z, float tx, float tz) {

		GL11.glPushMatrix();
		RenderHelper.enableStandardItemLighting();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glDepthMask(true);

		double pX = prevPosX + (posX - prevPosX) * interp;
		double pY = prevPosY + (posY - prevPosY) * interp;
		double pZ = prevPosZ + (posZ - prevPosZ) * interp;
		
		if(!setupDeltas) {
			prevRenderX = pX;
			prevRenderY = pY;
			prevRenderZ = pZ;
			setupDeltas = true;
		}
		
		int brightness = worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(pX), MathHelper.floor_double(pY), MathHelper.floor_double(pZ), 0);
		int lX = brightness % 65536;
		int lY = brightness / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lX / 1.0F, (float)lY / 1.0F);

		textureManager.bindTexture(ResourceManager.casings_tex);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;

		GL11.glTranslated(pX - dX, pY - dY - this.height / 4 + config.getScaleY() * 0.01, pZ - dZ);

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
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(pX - dX, pY - dY - this.height / 4, pZ - dZ);

		if(!smokeNodes.isEmpty()) {
			tessellator.startDrawingQuads();
			tessellator.setNormal(0F, 1F, 0F);
			
			float scale = config.getScaleX() * 0.5F * dScale;
			Vec3 vec = Vec3.createVectorHelper(scale, 0, 0);
			float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * interp;
			vec.rotateAroundY((float) Math.toRadians(-yaw));
			
			double deltaX = prevRenderX - pX;
			double deltaY = prevRenderY - pY;
			double deltaZ = prevRenderZ - pZ;
			
			for(Pair<Vec3, Double> pair : smokeNodes) {
				Vec3 pos = pair.getKey();
				double mult = 1D;
				pos.xCoord += deltaX * mult;
				pos.yCoord += deltaY * mult;
				pos.zCoord += deltaZ * mult;
			}

			for(int i = 0; i < smokeNodes.size() - 1; i++) {
				final Pair<Vec3, Double> node = smokeNodes.get(i), past = smokeNodes.get(i + 1);
				final Vec3 nodeLoc = node.getKey(), pastLoc = past.getKey();
				float nodeAlpha = node.getValue().floatValue();
				float pastAlpha = past.getValue().floatValue();
				
				double timeAlpha = 1D - (double) particleAge / (double) maxSmokeGen;
				nodeAlpha *= timeAlpha;
				pastAlpha *= timeAlpha;

				tessellator.setNormal(0F, 1F, 0F);
				tessellator.setColorRGBA_F(1F, 1F, 1F, nodeAlpha);
				tessellator.addVertex(nodeLoc.xCoord, nodeLoc.yCoord, nodeLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(nodeLoc.xCoord + vec.xCoord, nodeLoc.yCoord, nodeLoc.zCoord + vec.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(pastLoc.xCoord + vec.xCoord, pastLoc.yCoord, pastLoc.zCoord + vec.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, pastAlpha);
				tessellator.addVertex(pastLoc.xCoord, pastLoc.yCoord, pastLoc.zCoord);

				tessellator.setColorRGBA_F(1F, 1F, 1F, nodeAlpha);
				tessellator.addVertex(nodeLoc.xCoord, nodeLoc.yCoord, nodeLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(nodeLoc.xCoord - vec.xCoord, nodeLoc.yCoord, nodeLoc.zCoord - vec.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(pastLoc.xCoord - vec.xCoord, pastLoc.yCoord, pastLoc.zCoord - vec.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, pastAlpha);
				tessellator.addVertex(pastLoc.xCoord, pastLoc.yCoord, pastLoc.zCoord);
			}

			GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_CULL_FACE);
			tessellator.draw();
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		
		RenderHelper.disableStandardItemLighting();
		
		prevRenderX = pX;
		prevRenderY = pY;
		prevRenderZ = pZ;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		int x = MathHelper.floor_double(this.posX);
		int z = MathHelper.floor_double(this.posZ);

		if(this.worldObj.blockExists(x, 0, z)) {
			double d0 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66D;
			int y = MathHelper.floor_double(this.posY - (double) this.yOffset + d0);
			return this.worldObj.getLightBrightnessForSkyBlocks(x, y, z, 0);
		} else {
			return 0;
		}
	}
}