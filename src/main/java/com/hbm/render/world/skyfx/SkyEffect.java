package com.hbm.render.world.skyfx;

import com.hbm.util.Vec3NT;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class SkyEffect {

	public final World world;
	
	// not actually the in-world coordinates, these are just relative to the camera
	public double posX;
	public double posY;
	public double posZ;
	public double prevPosX;
	public double prevPosY;
	public double prevPosZ;
	protected double motionX;
	protected double motionY;
	protected double motionZ;
	protected float yaw;
	protected float pitch;
	protected float prevYaw;
	protected float prevPitch;
	
	protected TextureManager texman;
	
	protected int ticksExisted = 0;
	protected boolean isExpired = false;
	
	public SkyEffect(World world) {
		this.world = world;
		this.texman = Minecraft.getMinecraft().renderEngine;
	}
	
	public SkyEffect withPos(double x, double y, double z) {
		this.prevPosX = this.posX = x;
		this.prevPosY = this.posY = y;
		this.prevPosZ = this.posZ = z;
		return this;
	}
	
	public SkyEffect withMotion(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		return this;
	}
	
	public SkyEffect withAngleMotion(float yaw, float pitch, double speed) {
		this.prevYaw = this.yaw = yaw;
		this.prevPitch = this.pitch = pitch;
		
		Vec3NT vec = new Vec3NT(0, -speed, 0).rotateAroundZDeg(pitch).rotateAroundYDeg(yaw);
		return this.withMotion(vec.xCoord, vec.yCoord, vec.zCoord);
	}

	public void updateTick() {
		this.ticksExisted++;
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
	}
	
	public abstract void renderTick(float interp);

	public boolean getExpired() { return this.isExpired; }
	public void expire() { this.isExpired = true; }

	public void bindTexture(ResourceLocation tex) {
		this.texman.bindTexture(tex);
	}
}
