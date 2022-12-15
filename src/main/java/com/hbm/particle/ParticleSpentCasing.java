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
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleSpentCasing extends EntityFX
{
	private static final float dScale = 0.05f, smokeOffset = 0.025f, gravity = -0.5f;
	private static final byte smokeAccel = 1;
	
	private final List<Pair<EasyLocation, Double>> smokeNodes = new ArrayList<Pair<EasyLocation, Double>>();
	
	private final TextureManager textureManager;
	
	private final float momentumPitch, momentumYaw;
	private final SpentCasingConfig config;
	private final boolean smoke;
	
	private boolean onGroundPreviously = false;
	public ParticleSpentCasing(TextureManager textureManager, World world, double x, double y, double z, double mx, double my, double mz, float momentumPitch, float momentumYaw, SpentCasingConfig config)
	{
		super(world, x, y, z, mx, my, mz);
		this.textureManager = textureManager;
		this.momentumPitch = momentumPitch;
		this.momentumYaw = momentumYaw;
		this.config = config;

		particleMaxAge = 120;
		smoke = config.getSmokeChance() == 0 ? true
				: config.getSmokeChance() < 0 ? false
						: rand.nextInt(config.getSmokeChance()) == 0;
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
		
		if (!onGroundPreviously && onGround)
			onGroundPreviously = true;
		else if (onGroundPreviously && !onGround)
			onGroundPreviously = false;
		
		if (!config.getBounceSound().isEmpty())
		{
			if (!onGroundPreviously && onGround)
				worldObj.playSoundEffect(posX, posY, posZ, config.getBounceSound(), 1, 1);
		}
		
		if (particleAge > 90 && !smokeNodes.isEmpty())
			smokeNodes.clear();
		
		if (smoke && particleAge <= 90)
		{
			final double side = (rotationYaw - prevRotationYaw) * 0.1D;
			final Vec3 prev = Vec3.createVectorHelper(motionX, -motionY, motionZ);
			prev.rotateAroundY((float) Math.toRadians(rotationYaw));
			
			for (Pair<EasyLocation, Double> pair : smokeNodes)
			{
				final EasyLocation node = pair.getKey();
				
				node.posX += prev.xCoord * smokeAccel + rand.nextGaussian() * smokeOffset + side;
				node.posY += prev.yCoord + 1.5;
				node.posZ += prev.zCoord * smokeAccel + rand.nextGaussian() * smokeOffset;
			}
			
			final double alpha = (particleAge / 20d);
			
			smokeNodes.add(new Pair<EasyLocation, Double>(EasyLocation.getZeroLocation(), alpha));
		}
		
		prevRotationPitch = rotationPitch;
		prevRotationYaw = rotationYaw;
		
		if (motionY > gravity && !onGround)
			motionY += gravity;
		if (motionY < -0.75)
			motionY = -0.75;
		
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

		GL11.glScalef(config.getScaleX(), config.getScaleY(), config.getScaleZ());
		
//		GL11.glRotatef(prevRotationYaw + (rotationYaw - prevRotationYaw),
//				0.0F, 1.0F, 0.0F);
//		GL11.glRotatef(prevRotationPitch + (rotationPitch - prevRotationPitch),
//				0.0F, 0.0F, 1.0F);
		
		GL11.glRotatef(180 - rotationYaw, 0, 1, 0);
		GL11.glRotatef(-rotationPitch, 1, 0, 0);
		if (config.doesOverrideColor())
			GL11.glColor3f(config.getRedOverride(), config.getBlueOverride(), config.getGreenOverride());
		
		if (!smokeNodes.isEmpty())
		{
			tessellator.startDrawingQuads();
			tessellator.setNormal(0F, 1F, 0F);
			
			for (int i = 0; i < smokeNodes.size() - 1; i++)
			{
				final Pair<EasyLocation, Double> node = smokeNodes.get(i), past = smokeNodes.get(i + 1);
				final EasyLocation nodeLoc = node.getKey(), pastLoc = past.getKey();
				final float nodeAlpha = node.getValue().floatValue(), pastAlpha = past.getValue().floatValue(), scale = Math.max(config.getScaleX(), config.getScaleY());
				
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
}
