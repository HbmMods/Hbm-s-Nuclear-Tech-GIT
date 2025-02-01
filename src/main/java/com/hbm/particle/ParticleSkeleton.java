package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.particle.helper.SkeletonCreator.EnumSkeletonType;
import com.hbm.render.loader.HFRWavefrontObject;

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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;

@SideOnly(Side.CLIENT)
public class ParticleSkeleton extends EntityFX {

	public static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/skeleton.png");
	public static final ResourceLocation texture_ext = new ResourceLocation(RefStrings.MODID + ":textures/particle/skoilet.png");
	public static final IModelCustom skeleton = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/effect/skeleton.obj"), false).asVBO();
	protected EnumSkeletonType type;
	
	private float momentumYaw;
	private float momentumPitch;
	private int initialDelay;
	
	private final TextureManager textureManager;

	public ParticleSkeleton(TextureManager textureManager, World world, double x, double y, double z, float r, float g, float b, EnumSkeletonType type) {
		super(world, x, y, z);
		this.textureManager = textureManager;
		this.type = type;

		this.particleMaxAge = 1200 + rand.nextInt(20);
		
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		this.particleGravity = 0.02F;
		this.initialDelay = 20;

		this.momentumPitch = rand.nextFloat() * 5 * (rand.nextBoolean() ? 1 : -1);
		this.momentumYaw = rand.nextFloat() * 5 * (rand.nextBoolean() ? 1 : -1);
	}
	
	@Override
	public void onUpdate() {

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;
		
		if(initialDelay-- > 0) return;
		
		if(initialDelay == -1) {
			this.motionX = rand.nextGaussian() * 0.025;
			this.motionZ = rand.nextGaussian() * 0.025;
		}

		if(this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}
		boolean wasOnGround = this.onGround;
		
		this.motionY -= this.particleGravity;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.98D;
		this.motionY *= 0.98D;
		this.motionZ *= 0.98D;
		
		if(!this.onGround) {
			this.rotationPitch += this.momentumPitch;
			this.rotationYaw += this.momentumYaw;
		} else {
			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
			
			if(!wasOnGround) {
				MainRegistry.proxy.playSoundClient(posX, posY, posZ, "mob.skeleton.hurt", 0.25F, 0.8F + rand.nextFloat() * 0.4F);
			}
		}
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.enableStandardItemLighting();

		double pX = prevPosX + (posX - prevPosX) * interp;
		double pY = prevPosY + (posY - prevPosY) * interp;
		double pZ = prevPosZ + (posZ - prevPosZ) * interp;
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;

		GL11.glTranslated(pX - dX, pY - dY, pZ - dZ);

		GL11.glRotated(this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * interp, 0, 1, 0);
		GL11.glRotated(this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * interp, 1, 0, 0);
		
		float timeLeft = this.particleMaxAge - (this.particleAge + interp);
		if(timeLeft < 40) {
			this.particleAlpha = timeLeft / 40F;
		} else {
			this.particleAlpha = 1F;
		}
		
		int brightness = worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(pX), MathHelper.floor_double(pY), MathHelper.floor_double(pZ), 0);
		int lX = brightness % 65536;
		int lY = brightness / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lX / 1.0F, (float)lY / 1.0F);
		
		GL11.glColor4f(particleRed, particleGreen, particleBlue, particleAlpha);
		GL11.glRotated(-90, 0, 1, 0); 
		
		switch(type) {
		case SKULL: 
			this.textureManager.bindTexture(texture);
			skeleton.renderPart("Skull"); break;
		case TORSO: 
			this.textureManager.bindTexture(texture);
			skeleton.renderPart("Torso"); break;
		case LIMB: 
			this.textureManager.bindTexture(texture);
			skeleton.renderPart("Limb"); break;
		case SKULL_VILLAGER: 
			this.textureManager.bindTexture(texture_ext);
			skeleton.renderPart("SkullVillager"); break;
		}
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}
}
