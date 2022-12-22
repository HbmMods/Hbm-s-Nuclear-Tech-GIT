package com.hbm.particle;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.calc.EasyLocation;
import com.hbm.main.ResourceManager;
import com.hbm.util.Tuple.Pair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleSpentCasing extends EntityFX
{
	private static final float dScale = 0.05f;//, smokeJitter = 0.025f, smokeAccel = 0.5f;
//	private static final byte maxSmokeGen = 60, maxSmokeLife = 120;
	
	private final List<Pair<EasyLocation, Double>> smokeNodes = new ArrayList<Pair<EasyLocation, Double>>();
	
	private final TextureManager textureManager;
	
	private final SpentCasingConfig config;
//	private final boolean smoke;
	
	private float momentumPitch, momentumYaw;
	private boolean onGroundPreviously = false;
	private double maxHeight;
	public ParticleSpentCasing(TextureManager textureManager, World world, double x, double y, double z, double mx, double my, double mz, float momentumPitch, float momentumYaw, SpentCasingConfig config)
	{
		super(world, x, y, z, 0, 0, 0);
		this.textureManager = textureManager;
		this.momentumPitch = momentumPitch;
		this.momentumYaw = momentumYaw;
		this.config = config;

		particleMaxAge = 240;
//		smoke = config.getSmokeChance() == 0 ? true
//				: config.getSmokeChance() < 0 ? false
//						: rand.nextInt(config.getSmokeChance()) == 0;
		
		motionX = mx;
		motionY = my;
		motionZ = mz;
		
		particleGravity = 8f;
		
		maxHeight = y;
	}

	@Override
	public int getFXLayer()
	{
		return 3;
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (motionY > 0 && posY > maxHeight)
			maxHeight = posY;

		if (!onGroundPreviously && onGround)
			tryPlayBounceSound();
		
		// TODO Bounce factor in config
		if (!onGroundPreviously && onGround)
		{
			onGroundPreviously = true;
			motionY = Math.log10(maxHeight - posY + 2);
			momentumPitch = (float) rand.nextGaussian() * config.getPitchFactor();
			momentumYaw = (float) rand.nextGaussian() * config.getYawFactor();
			
			maxHeight = posY;
		} else if (onGroundPreviously && !onGround)
			onGroundPreviously = false;
		
//		if (particleAge > maxSmokeLife && !smokeNodes.isEmpty())
//			smokeNodes.clear();
		
//		if (smoke && particleAge <= maxSmokeLife)
//		{
//			final double side = (rotationYaw - prevRotationYaw) * 0.1D;
//			final Vec3 prev = Vec3.createVectorHelper(motionX, motionY, motionZ);
//			prev.rotateAroundY((float) Math.toRadians(rotationYaw));
//			
//			for (Pair<EasyLocation, Double> pair : smokeNodes)
//			{
//				final EasyLocation node = pair.getKey();
//				
//				node.posX += prev.xCoord * smokeAccel + rand.nextGaussian() * smokeJitter + side;
//				node.posY += prev.yCoord + smokeAccel;
//				node.posZ += prev.zCoord * smokeAccel + rand.nextGaussian() * smokeJitter;
//			}
//			
//			if (particleAge < maxSmokeGen || inWater)
//			{
//				final double alpha = (particleAge / 20d);
//				smokeNodes.add(new Pair<EasyLocation, Double>(EasyLocation.getZeroLocation(), alpha));
//			}
//		}
		
		prevRotationPitch = rotationPitch;
		prevRotationYaw = rotationYaw;
		
//		if (motionY > gravity && !onGround)
//			motionY += gravity;
//		if (motionY < -0.75)
//			motionY = -0.75;
		
		if (onGround)
			rotationPitch = 0;
		else
		{
			rotationPitch += momentumPitch;
			rotationYaw += momentumYaw;
		}
	}
	
	@Override
	public void renderParticle(
			Tessellator tessellator, float interp, float x, float y, float z,
			float tx, float tz
	)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		textureManager.bindTexture(ResourceManager.casings_tex);
		
		GL11.glTranslated(
				prevPosX + (posX - prevPosX) * interp - interpPosX,
				prevPosY + (posY - prevPosY) * interp - interpPosY,
				prevPosZ + (posZ - prevPosZ) * interp - interpPosZ);
		
		GL11.glScalef(dScale, dScale, dScale);

		GL11.glRotatef(180 - rotationYaw, 0, 1, 0);
		GL11.glRotatef(-rotationPitch, 1, 0, 0);
		
		GL11.glScalef(config.getScaleX(), config.getScaleY(), config.getScaleZ());
		
		if (config.doesOverrideColor())
			GL11.glColor3b((byte) config.getRedOverride(), (byte) config.getGreenOverride(), (byte) config.getBlueOverride());
		
		if (!smokeNodes.isEmpty())
		{
			tessellator.startDrawingQuads();
			tessellator.setNormal(0F, 1F, 0F);
			
			for (int i = 0; i < smokeNodes.size() - 1; i++)
			{
				final Pair<EasyLocation, Double> node = smokeNodes.get(i), past = smokeNodes.get(i + 1);
				final EasyLocation nodeLoc = node.getKey(), pastLoc = past.getKey();
				final float nodeAlpha = node.getValue().floatValue(), pastAlpha = past.getValue().floatValue(), scale = config.getScaleX();
				
				tessellator.setColorRGBA_F(1F, 1F, 1F, nodeAlpha);
				tessellator.addVertex(nodeLoc.posX(), nodeLoc.posY(), nodeLoc.posZ());
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(nodeLoc.posX() + scale, nodeLoc.posY(), nodeLoc.posZ());
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(pastLoc.posX() + scale, pastLoc.posY(), pastLoc.posZ());
				tessellator.setColorRGBA_F(1F, 1F, 1F, pastAlpha);
				tessellator.addVertex(pastLoc.posX(), pastLoc.posY(), pastLoc.posZ());
				
				tessellator.setColorRGBA_F(1F, 1F, 1F, nodeAlpha);
				tessellator.addVertex(nodeLoc.posX(), nodeLoc.posY(), nodeLoc.posZ());
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(nodeLoc.posX() - scale, nodeLoc.posY(), nodeLoc.posZ());
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(pastLoc.posX() - scale, pastLoc.posY(), pastLoc.posZ());
				tessellator.setColorRGBA_F(1F, 1F, 1F, pastAlpha);
				tessellator.addVertex(pastLoc.posX(), pastLoc.posY(), pastLoc.posZ());
			}
			
			GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			tessellator.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
		}
		
		ResourceManager.casings.renderPart(config.getCasingType().objName);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
	
	private void tryPlayBounceSound()
	{
		if (!config.getBounceSound().isEmpty())
			worldObj.playSoundAtEntity(this, config.getBounceSound(), 2, 1);
//			playSound(config.getBounceSound(), 2, 1);
	}
	
//	private static float[] getOffset(float time)
//	{
//		final float sinVal1 = (float) ((Math.sin(time * 0.15) + Math.sin(time * 0.25 - 10) + Math.sin(time * 0.1 + 10)) / 3f),
//					sinVal2 = (float) ((Math.sin(time * 0.1) + Math.sin(time * 0.05 + 20) + Math.sin(time * 0.13 + 20)) / 3f);
//		
//		return new float[] {BobMathUtil.remap(BobMathUtil.smoothStep(sinVal1, -1, 1), 0, 1, -2, 1.5F), BobMathUtil.remap(sinVal2, -1, 1, -0.03F, 0.05F)};
//	}
//	
//	private static float[] getJitter(float time)
//	{
//		final float sinVal1 = (float) ((Math.sin(time * 0.8) + Math.sin(time * 0.6 - 10) + Math.sin(time * 0.9 + 10)) / 3f),
//					sinVal2 = (float) ((Math.sin(time * 0.3) + Math.sin(time * 0.2 + 20) + Math.sin(time * 0.1 + 20)) / 3f);
//		
//		return new float[] {BobMathUtil.remap(sinVal1, -1, 1, -3, 3), BobMathUtil.remap(sinVal2, -1, 1, -1F, 1F)};
//	}
}
