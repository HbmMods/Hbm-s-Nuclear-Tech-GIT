package com.hbm.particle;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.main.ResourceManager;
import com.hbm.util.Tuple.Pair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleSpentCasing extends EntityFX
{
	private static float dScale = 0.05F, smokeJitter = 0.025F, smokeAccel = 0.5F;
	private static byte maxSmokeGen = 60, maxSmokeLife = 120;

	private final List<Pair<Vec3, Double>> smokeNodes = new ArrayList();

	private final TextureManager textureManager;

	private final SpentCasingConfig config;
	private boolean smoke;

	private float momentumPitch, momentumYaw;
	private boolean onGroundPreviously = false;
	private double maxHeight;

	public ParticleSpentCasing(TextureManager textureManager, World world, double x, double y, double z, double mx, double my, double mz, float momentumPitch, float momentumYaw, SpentCasingConfig config) {
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

		// TODO Bounce factor in config
		if(!onGroundPreviously && onGround) {
			
			onGroundPreviously = true;
			motionY = Math.log10(maxHeight - posY + 2);
			momentumPitch = (float) rand.nextGaussian() * config.getPitchFactor();
			momentumYaw = (float) rand.nextGaussian() * config.getYawFactor();
			maxHeight = posY;
			
		} else if(onGroundPreviously && !onGround) {
			onGroundPreviously = false;
		}

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

		textureManager.bindTexture(ResourceManager.casings_tex);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;

		GL11.glTranslated(prevPosX + (posX - prevPosX) * interp - dX, prevPosY + (posY - prevPosY) * interp - dY - this.height / 4, prevPosZ + (posZ - prevPosZ) * interp - dZ);

		GL11.glScalef(dScale, dScale, dScale);

		GL11.glRotatef(180 - rotationYaw, 0, 1, 0);
		GL11.glRotatef(-rotationPitch, 1, 0, 0);

		GL11.glScalef(config.getScaleX(), config.getScaleY(), config.getScaleZ());

		if(config.doesOverrideColor())
			GL11.glColor3b((byte) config.getRedOverride(), (byte) config.getGreenOverride(), (byte) config.getBlueOverride());

		ResourceManager.casings.renderPart(config.getCasingType().objName);
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

		RenderHelper.disableStandardItemLighting();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	private void tryPlayBounceSound() {

		if(!config.getBounceSound().isEmpty()) {
			worldObj.playSoundAtEntity(this, config.getBounceSound(), 2, 1);
		}
	}
}