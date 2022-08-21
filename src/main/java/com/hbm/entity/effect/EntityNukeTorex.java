package com.hbm.entity.effect;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/*
 * Toroidial Convection Simulation Explosion Effect
 * Tor                             Ex
 */
public class EntityNukeTorex extends Entity {

	public double coreHeight = 3;
	public double convectionHeight = 3;
	public double torusWidth = 3;
	public double rollerSize = 1;
	public double heat = 1;
	public ArrayList<Cloudlet> cloudlets = new ArrayList();
	public static int cloudletLife = 200;

	public EntityNukeTorex(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void entityInit() { }

	@Override
	public void onUpdate() {
		this.ticksExisted++;
		
		int maxAge = 90 * 20;
		
		if(worldObj.isRemote) {
			
			double range = (torusWidth - rollerSize) * 0.25;
			
			if(this.ticksExisted + cloudletLife * 2 < maxAge) {
				
				int toSpawn = (int) Math.ceil(10 * getSimulationSpeed());
				
				for(int i = 0; i < toSpawn; i++) {
					double y = posY + rand.nextGaussian() - 3; //this.ticksExisted < 60 ? this.posY + this.coreHeight : posY + rand.nextGaussian() - 3;
					Cloudlet cloud = new Cloudlet(posX + rand.nextGaussian() * range, y, posZ + rand.nextGaussian() * range, (float)(rand.nextDouble() * 2D * Math.PI), 0);
					cloud.setScale(1F + this.ticksExisted * 0.001F, 5F);
					cloudlets.add(cloud);
				}
			}
			
			int cloudCount = ticksExisted * 3;
			if(ticksExisted < 200) {
				for(int i = 0; i < cloudCount; i++) {
					Vec3 vec = Vec3.createVectorHelper((ticksExisted + rand.nextDouble()) * 2, 0, 0);
					float rot = (float) (Math.PI * 2 * rand.nextDouble());
					vec.rotateAroundY(rot);
					this.cloudlets.add(new Cloudlet(vec.xCoord + posX, worldObj.getHeightValue((int) (vec.xCoord + posX) + 1, (int) (vec.zCoord + posZ)), vec.zCoord + posZ, rot, 0)
							.setScale(5F, 2F)
							.setMotion(0));
				}
			}
			
			for(Cloudlet cloud : cloudlets) {
				cloud.update();
			}
	
			coreHeight += 0.15;
			torusWidth += 0.05;
			rollerSize = torusWidth * 0.35;
			convectionHeight = coreHeight + rollerSize;
			
			int maxHeat = 50;
			heat = maxHeat - Math.pow((maxHeat * this.ticksExisted) / maxAge, 1);
			
			cloudlets.removeIf(x -> x.isDead);
		}
		
		if(!worldObj.isRemote && this.ticksExisted > maxAge) {
			this.setDead();
		}
	}
	
	public double getSimulationSpeed() {
		
		if(EntityNukeTorex.this.ticksExisted > 45 * 20) {
			int timeLeft = 1600 - EntityNukeTorex.this.ticksExisted;
			return MathHelper.clamp_double((double) timeLeft / 900D, 0, 1);
		}
		
		return 1.0D;
	}

	public class Cloudlet {

		public double posX;
		public double posY;
		public double posZ;
		public double prevPosX;
		public double prevPosY;
		public double prevPosZ;
		public double motionX;
		public double motionY;
		public double motionZ;
		public int age;
		public float angle;
		public boolean isDead = false;
		float rangeMod = 1.0F;
		public float colorMod = 1.0F;
		public Vec3 color;
		public Vec3 prevColor;

		public Cloudlet(double posX, double posY, double posZ, float angle, int age) {
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			this.age = age;
			this.angle = angle;
			this.rangeMod = 0.3F + rand.nextFloat() * 0.7F;
			this.colorMod = 0.8F + rand.nextFloat() * 0.2F;
			
			this.updateColor();
		}
		
		private void update() {
			
			age++;
			
			if(age > cloudletLife) {
				this.isDead = true;
			}

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			
			Vec3 simPos = Vec3.createVectorHelper(EntityNukeTorex.this.posX - this.posX, 0, EntityNukeTorex.this.posZ - this.posZ);
			double simPosX = EntityNukeTorex.this.posX + simPos.lengthVector();
			double simPosZ = EntityNukeTorex.this.posZ + 0D;

			Vec3 convection = getConvectionMotion(simPosX, simPosZ);
			Vec3 lift = getLiftMotion(simPosX, simPosZ);
			
			double factor = MathHelper.clamp_double((this.posY - EntityNukeTorex.this.posY) / EntityNukeTorex.this.coreHeight, 0, 1);
			this.motionX = convection.xCoord * factor + lift.xCoord * (1D - factor);
			this.motionY = convection.yCoord * factor + lift.yCoord * (1D - factor);
			this.motionZ = convection.zCoord * factor + lift.zCoord * (1D - factor);
			
			double mult = this.motionMult * getSimulationSpeed();
			
			this.posX += this.motionX * mult;
			this.posY += this.motionY * mult;
			this.posZ += this.motionZ * mult;
			
			this.updateColor();
		}
		
		/* simulated on a 2D-plane along the X/Y axis */
		private Vec3 getConvectionMotion(double simPosX, double simPosZ) {
			
			if(simPosX > EntityNukeTorex.this.posX + torusWidth * 2)
				return Vec3.createVectorHelper(0, 0, 0);
			
			/* the position of the torus' outer ring center */
			Vec3 torusPos = Vec3.createVectorHelper(
					(EntityNukeTorex.this.posX + torusWidth),
					(EntityNukeTorex.this.posY + coreHeight),
					EntityNukeTorex.this.posZ);
			
			/* the difference between the cloudlet and the torus' ring center */
			Vec3 delta = Vec3.createVectorHelper(torusPos.xCoord - simPosX, torusPos.yCoord - this.posY, torusPos.zCoord - simPosZ);
			
			
			/* the distance this cloudlet wants to achieve to the torus' ring center */
			double roller = EntityNukeTorex.this.rollerSize * this.rangeMod;
			/* the distance between this cloudlet and the torus' outer ring perimeter */
			double dist = delta.lengthVector() / roller - 1D;
			
			/* euler function based on how far the cloudlet is away from the perimeter */
			double func = 1D - Math.pow(Math.E, -dist); // [0;1]
			/* just an approximation, but it's good enough */
			float angle = (float) (func * Math.PI * 0.5D); // [0;90°]
			
			/* vector going from the ring center in the direction of the cloudlet, stopping at the perimeter */
			Vec3 rot = Vec3.createVectorHelper(-delta.xCoord / dist, -delta.yCoord / dist, -delta.zCoord / dist);
			/* rotate by the approximate angle */
			rot.rotateAroundZ(angle);
			
			/* the direction from the cloudlet to the target position on the perimeter */
			Vec3 motion = Vec3.createVectorHelper(
					torusPos.xCoord + rot.xCoord - simPosX,
					torusPos.yCoord + rot.yCoord - this.posY,
					torusPos.zCoord + rot.zCoord - simPosZ);
			
			motion = motion.normalize();
			motion.rotateAroundY(this.angle);
			
			return motion;
		}
		
		private Vec3 getLiftMotion(double simPosX, double simPosZ) {
			double scale = MathHelper.clamp_double(1D - (simPosX - (EntityNukeTorex.this.posX + torusWidth)), 0, 1);
			
			Vec3 motion = Vec3.createVectorHelper(EntityNukeTorex.this.posX - this.posX, (EntityNukeTorex.this.posY + convectionHeight) - this.posY, EntityNukeTorex.this.posZ - this.posZ);
			
			motion = motion.normalize();
			motion.xCoord *= scale;
			motion.yCoord *= scale;
			motion.zCoord *= scale;
			
			return motion;
		}
		
		private void updateColor() {
			this.prevColor = this.color;

			double exX = EntityNukeTorex.this.posX;
			double exY = EntityNukeTorex.this.posY + EntityNukeTorex.this.coreHeight;
			double exZ = EntityNukeTorex.this.posZ;

			double distX = exX - posX;
			double distY = exY - posY;
			double distZ = exZ - posZ;
			
			double distSq = distX * distX + distY * distY + distZ * distZ;
			distSq /= EntityNukeTorex.this.heat;
			double dist = Math.sqrt(distSq);
			
			dist = Math.max(dist, 1);
			double col = 2D / dist;
			
			this.color = Vec3.createVectorHelper(
					Math.max(col * 2, 0.25),
					Math.max(col * 1.5, 0.25),
					Math.max(col * 0.5, 0.25)
					);
		}
		
		public Vec3 getInterpPos(float interp) {
			return Vec3.createVectorHelper(
					prevPosX + (posX - prevPosX) * interp,
					prevPosY + (posY - prevPosY) * interp,
					prevPosZ + (posZ - prevPosZ) * interp);
		}
		
		public Vec3 getInterpColor(float interp) {
			return Vec3.createVectorHelper(
					prevColor.xCoord + (color.xCoord - prevColor.xCoord) * interp,
					prevColor.yCoord + (color.yCoord - prevColor.yCoord) * interp,
					prevColor.zCoord + (color.zCoord - prevColor.zCoord) * interp);
		}
		
		public float getAlpha() {
			return 1F - ((float)age / (float)cloudletLife);
		}
		
		private float startingScale = 1;
		private float growingScale = 5F;
		
		public float getScale() {
			return startingScale + ((float)age / (float)cloudletLife) * growingScale;
		}
		
		public Cloudlet setScale(float start, float grow) {
			this.startingScale = start;
			this.growingScale = grow;
			return this;
		}
		
		private double motionMult = 1F;
		
		public Cloudlet setMotion(double mult) {
			this.motionMult = mult;
			return this;
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
	}
}
