package com.hbm.entity.effect;

import java.awt.Color;
import java.util.ArrayList;

import com.hbm.main.MainRegistry;
import com.hbm.util.BobMathUtil;
import com.hbm.util.TrackerUtil;

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
	public double lastSpawnY = - 1;
	public ArrayList<Cloudlet> cloudlets = new ArrayList();
	//public static int cloudletLife = 200;

	public boolean didPlaySound = false;
	public boolean didShake = false;

	public EntityNukeTorex(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.setSize(1F, 50F);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(10, new Float(1));
		this.dataWatcher.addObject(11, new Integer(0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}

	@Override
	public void onUpdate() {
		
		double s = 1.5; //this.getScale();
		double cs = 1.5;
		int maxAge = this.getMaxAge();
		
		if(worldObj.isRemote) {
			
			if(ticksExisted == 1) this.setScale((float) s);
			
			if(lastSpawnY == -1) {
				lastSpawnY = posY - 3;
			}
			
			if(ticksExisted < 100) this.worldObj.lastLightningBolt = 2;
			
			int spawnTarget = Math.max(worldObj.getHeightValue((int) Math.floor(posX), (int) Math.floor(posZ)) - 3, 1);
			double moveSpeed = 0.5D;
			
			if(Math.abs(spawnTarget - lastSpawnY) < moveSpeed) {
				lastSpawnY = spawnTarget;
			} else {
				lastSpawnY += moveSpeed * Math.signum(spawnTarget - lastSpawnY);
			}
			
			// spawn mush clouds
			double range = (torusWidth - rollerSize) * 0.25;
			double simSpeed = getSimulationSpeed();
			int toSpawn = (int) Math.ceil(10 * simSpeed * simSpeed);
			int lifetime = Math.min((ticksExisted * ticksExisted) + 200, maxAge - ticksExisted + 200);
				
			for(int i = 0; i < toSpawn; i++) {
				double x = posX + rand.nextGaussian() * range;
				double z = posZ + rand.nextGaussian() * range;
				Cloudlet cloud = new Cloudlet(x, lastSpawnY, z, (float)(rand.nextDouble() * 2D * Math.PI), 0, lifetime);
				cloud.setScale(1F + this.ticksExisted * 0.005F * (float) cs, 5F * (float) cs);
				cloudlets.add(cloud);
			}
			
			// spawn shock clouds
			if(ticksExisted < 150) {
				
				int cloudCount = ticksExisted * 5;
				int shockLife = Math.max(300 - ticksExisted * 20, 50);
				
				for(int i = 0; i < cloudCount; i++) {
					Vec3 vec = Vec3.createVectorHelper((ticksExisted * 1.5 + rand.nextDouble()) * 1.5, 0, 0);
					float rot = (float) (Math.PI * 2 * rand.nextDouble());
					vec.rotateAroundY(rot);
					this.cloudlets.add(new Cloudlet(vec.xCoord + posX, worldObj.getHeightValue((int) (vec.xCoord + posX) + 1, (int) (vec.zCoord + posZ)), vec.zCoord + posZ, rot, 0, shockLife, TorexType.SHOCK)
							.setScale(7F, 2F)
							.setMotion(ticksExisted > 15 ? 0.75 : 0));
				}
				
				if(!didPlaySound) {
					if(MainRegistry.proxy.me() != null && MainRegistry.proxy.me().getDistanceToEntity(this) < (ticksExisted * 1.5 + 1) * 1.5) {
						MainRegistry.proxy.playSoundClient(posX, posY, posZ, "hbm:weapon.nuclearExplosion", 10_000F, 1F);
						didPlaySound = true;
					}
				}
			}
			
			// spawn ring clouds
			if(ticksExisted < 130 * s) {
				lifetime *= s;
				for(int i = 0; i < 2; i++) {
					Cloudlet cloud = new Cloudlet(posX, posY + coreHeight, posZ, (float)(rand.nextDouble() * 2D * Math.PI), 0, lifetime, TorexType.RING);
					cloud.setScale(1F + this.ticksExisted * 0.0025F * (float) (cs * cs), 3F * (float) (cs * cs));
					cloudlets.add(cloud);
				}
			}
			
			// spawn condensation clouds
			if(ticksExisted > 130 * s && ticksExisted < 600 * s) {
				
				for(int i = 0; i < 20; i++) {
					for(int j = 0; j < 4; j++) {
						float angle = (float) (Math.PI * 2 * rand.nextDouble());
						Vec3 vec = Vec3.createVectorHelper(torusWidth + rollerSize * (5 + rand.nextDouble()), 0, 0);
						vec.rotateAroundZ((float) (Math.PI / 45 * j));
						vec.rotateAroundY(angle);
						Cloudlet cloud = new Cloudlet(posX + vec.xCoord, posY + coreHeight - 5 + j * s, posZ + vec.zCoord, angle, 0, (int) ((20 + ticksExisted / 10) * (1 + rand.nextDouble() * 0.1)), TorexType.CONDENSATION);
						cloud.setScale(0.125F * (float) (cs), 3F * (float) (cs));
						cloudlets.add(cloud);
					}
				}
			}
			if(ticksExisted > 200 * s && ticksExisted < 600 * s) {
				
				for(int i = 0; i < 20; i++) {
					for(int j = 0; j < 4; j++) {
						float angle = (float) (Math.PI * 2 * rand.nextDouble());
						Vec3 vec = Vec3.createVectorHelper(torusWidth + rollerSize * (3 + rand.nextDouble() * 0.5), 0, 0);
						vec.rotateAroundZ((float) (Math.PI / 45 * j));
						vec.rotateAroundY(angle);
						Cloudlet cloud = new Cloudlet(posX + vec.xCoord, posY + coreHeight + 25 + j * cs, posZ + vec.zCoord, angle, 0, (int) ((20 + ticksExisted / 10) * (1 + rand.nextDouble() * 0.1)), TorexType.CONDENSATION);
						cloud.setScale(0.125F * (float) (cs), 3F * (float) (cs));
						cloudlets.add(cloud);
					}
				}
			}
			
			for(Cloudlet cloud : cloudlets) {
				cloud.update();
			}
			coreHeight += 0.15 / s;
			torusWidth += 0.05 / s;
			rollerSize = torusWidth * 0.35;
			convectionHeight = coreHeight + rollerSize;
			
			int maxHeat = (int) (50 * cs);
			heat = maxHeat - Math.pow((maxHeat * this.ticksExisted) / maxAge, 1);
			
			cloudlets.removeIf(x -> x.isDead);
		}
		
		if(!worldObj.isRemote && this.ticksExisted > maxAge) {
			this.setDead();
		}
	}
	
	public EntityNukeTorex setScale(float scale) {
		if(!worldObj.isRemote) getDataWatcher().updateObject(10, scale);
		this.coreHeight = this.coreHeight / 1.5D * scale;
		this.convectionHeight = this.convectionHeight / 1.5D * scale;
		this.torusWidth = this.torusWidth / 1.5D * scale;
		this.rollerSize = this.rollerSize / 1.5D * scale;
		return this;
	}
	
	public EntityNukeTorex setType(int type) {
		this.dataWatcher.updateObject(11, type);
		return this;
	}
	
	public double getSimulationSpeed() {
		
		int lifetime = getMaxAge();
		int simSlow = lifetime / 4;
		int simStop = lifetime / 2;
		int life = EntityNukeTorex.this.ticksExisted;
		
		if(life > simStop) {
			return 0D;
		}
		
		if(life > simSlow) {
			return 1D - ((double)(life - simSlow) / (double)(simStop - simSlow));
		}
		
		return 1.0D;
	}
	
	public double getScale() {
		return this.dataWatcher.getWatchableObjectFloat(10);
	}
	
	public double getSaturation() {
		double d = (double) this.ticksExisted / (double) this.getMaxAge();
		return 1D - (d * d * d * d);
	}
	
	public double getGreying() {
		
		int lifetime = getMaxAge();
		int greying = lifetime * 3 / 4;
		
		if(ticksExisted > greying) {
			return 1 + ((double)(ticksExisted - greying) / (double)(lifetime - greying));
		}
		
		return 1D;
	}
	
	public float getAlpha() {
		
		int lifetime = getMaxAge();
		int fadeOut = lifetime * 3 / 4;
		int life = EntityNukeTorex.this.ticksExisted;
		
		if(life > fadeOut) {
			float fac = (float)(life - fadeOut) / (float)(lifetime - fadeOut);
			return 1F - fac;
		}
		
		return 1.0F;
	}
	
	public int getMaxAge() {
		double s = this.getScale();
		return (int) (45 * 20 * s);
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
		public int cloudletLife;
		public float angle;
		public boolean isDead = false;
		float rangeMod = 1.0F;
		public float colorMod = 1.0F;
		public Vec3 color;
		public Vec3 prevColor;
		public TorexType type;

		public Cloudlet(double posX, double posY, double posZ, float angle, int age, int maxAge) {
			this(posX, posY, posZ, angle, age, maxAge, TorexType.STANDARD);
		}

		public Cloudlet(double posX, double posY, double posZ, float angle, int age, int maxAge, TorexType type) {
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			this.age = age;
			this.cloudletLife = maxAge;
			this.angle = angle;
			this.rangeMod = 0.3F + rand.nextFloat() * 0.7F;
			this.colorMod = 0.8F + rand.nextFloat() * 0.2F;
			this.type = type;
			
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
			
			if(this.type == TorexType.STANDARD) {
				Vec3 convection = getConvectionMotion(simPosX, simPosZ);
				Vec3 lift = getLiftMotion(simPosX, simPosZ);
				
				double factor = MathHelper.clamp_double((this.posY - EntityNukeTorex.this.posY) / EntityNukeTorex.this.coreHeight, 0, 1);
				this.motionX = convection.xCoord * factor + lift.xCoord * (1D - factor);
				this.motionY = convection.yCoord * factor + lift.yCoord * (1D - factor);
				this.motionZ = convection.zCoord * factor + lift.zCoord * (1D - factor);
			} else if(this.type == TorexType.SHOCK) {
				
				double factor = MathHelper.clamp_double((this.posY - EntityNukeTorex.this.posY) / EntityNukeTorex.this.coreHeight, 0, 1);
				Vec3 motion = Vec3.createVectorHelper(1, 0, 0);
				motion.rotateAroundY(this.angle);
				this.motionX = motion.xCoord * factor;
				this.motionY = motion.yCoord * factor;
				this.motionZ = motion.zCoord * factor;
			} else if(this.type == TorexType.RING) {
				Vec3 motion = getRingMotion(simPosX, simPosZ);
				this.motionX = motion.xCoord;
				this.motionY = motion.yCoord;
				this.motionZ = motion.zCoord;
			} else if(this.type == TorexType.CONDENSATION) {
				Vec3 motion = getCondensationMotion();
				this.motionX = motion.xCoord;
				this.motionY = motion.yCoord;
				this.motionZ = motion.zCoord;
			}
			
			double mult = this.motionMult * getSimulationSpeed();
			
			this.posX += this.motionX * mult;
			this.posY += this.motionY * mult;
			this.posZ += this.motionZ * mult;
			
			this.updateColor();
		}
		
		private Vec3 getCondensationMotion() {
			Vec3 delta = Vec3.createVectorHelper(posX - EntityNukeTorex.this.posX, 0, posZ - EntityNukeTorex.this.posZ);
			double speed = 0.00002 * EntityNukeTorex.this.ticksExisted;
			delta.xCoord *= speed;
			delta.zCoord *= speed;
			return delta;
		}
		
		private Vec3 getRingMotion(double simPosX, double simPosZ) {
			
			if(simPosX > EntityNukeTorex.this.posX + torusWidth * 2)
				return Vec3.createVectorHelper(0, 0, 0);
			
			/* the position of the torus' outer ring center */
			Vec3 torusPos = Vec3.createVectorHelper(
					(EntityNukeTorex.this.posX + torusWidth),
					(EntityNukeTorex.this.posY + coreHeight * 0.5),
					EntityNukeTorex.this.posZ);
			
			/* the difference between the cloudlet and the torus' ring center */
			Vec3 delta = Vec3.createVectorHelper(torusPos.xCoord - simPosX, torusPos.yCoord - this.posY, torusPos.zCoord - simPosZ);
			
			/* the distance this cloudlet wants to achieve to the torus' ring center */
			double roller = EntityNukeTorex.this.rollerSize * this.rangeMod * 0.25;
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
			
			double speed = 0.001D;
			motion.xCoord *= speed;
			motion.yCoord *= speed;
			motion.zCoord *= speed;
			
			motion = motion.normalize();
			motion.rotateAroundY(this.angle);
			
			return motion;
		}
		
		/* simulated on a 2D-plane along the X/Y axis */
		private Vec3 getConvectionMotion(double simPosX, double simPosZ) {
			
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
			
			int type = EntityNukeTorex.this.dataWatcher.getWatchableObjectInt(11);
			
			if(type == 1) {
				this.color = Vec3.createVectorHelper(
						Math.max(col * 1, 0.25),
						Math.max(col * 2, 0.25),
						Math.max(col * 0.5, 0.25)
						);
			} else if(type == 2) {
				Color color = Color.getHSBColor(this.angle / 2F / (float) Math.PI, 1F, 1F);
				if(this.type == TorexType.RING) {
					this.color = Vec3.createVectorHelper(
							Math.max(col * 1, 0.25),
							Math.max(col * 1, 0.25),
							Math.max(col * 1, 0.25)
							);
				} else {
					this.color = Vec3.createVectorHelper(color.getRed() / 255D, color.getGreen() / 255D, color.getBlue() / 255D);
				}
			} else {
				this.color = Vec3.createVectorHelper(
						Math.max(col * 2, 0.25),
						Math.max(col * 1.5, 0.25),
						Math.max(col * 0.5, 0.25)
						);
			}
		}
		
		public Vec3 getInterpPos(float interp) {
			float scale = (float) EntityNukeTorex.this.getScale();
			Vec3 base = Vec3.createVectorHelper(
					prevPosX + (posX - prevPosX) * interp,
					prevPosY + (posY - prevPosY) * interp,
					prevPosZ + (posZ - prevPosZ) * interp);

			if(this.type != TorexType.SHOCK) { //no rescale for the shockwave as this messes with the positions
				base.xCoord = ((base.xCoord) - EntityNukeTorex.this.posX) * scale + EntityNukeTorex.this.posX;
				base.yCoord = ((base.yCoord) - EntityNukeTorex.this.posY) * scale + EntityNukeTorex.this.posY;
				base.zCoord = ((base.zCoord) - EntityNukeTorex.this.posZ) * scale + EntityNukeTorex.this.posZ;
			}
			
			return base;
		}
		
		public Vec3 getInterpColor(float interp) {
			
			if(this.type == TorexType.CONDENSATION) {
				return Vec3.createVectorHelper(1F, 1F, 1F);
			}
			
			double greying = EntityNukeTorex.this.getGreying();
			
			if(this.type == TorexType.RING) {
				greying += 1;
			}
			
			return Vec3.createVectorHelper(
					(prevColor.xCoord + (color.xCoord - prevColor.xCoord) * interp) * greying,
					(prevColor.yCoord + (color.yCoord - prevColor.yCoord) * interp) * greying,
					(prevColor.zCoord + (color.zCoord - prevColor.zCoord) * interp) * greying);
		}
		
		public float getAlpha() {
			float alpha = (1F - ((float)age / (float)cloudletLife)) * EntityNukeTorex.this.getAlpha();
			if(this.type == TorexType.CONDENSATION) alpha *= 0.25;
			return alpha;
		}
		
		private float startingScale = 1;
		private float growingScale = 5F;
		
		public float getScale() {
			float base = startingScale + ((float)age / (float)cloudletLife) * growingScale;
			if(this.type != TorexType.SHOCK) base *= (float) EntityNukeTorex.this.getScale();
			return base;
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
	
	public static enum TorexType {
		STANDARD,
		SHOCK,
		RING,
		CONDENSATION
	}

	@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }
	@Override public boolean writeToNBTOptional(NBTTagCompound nbt) { return false; }
	@Override public void readEntityFromNBT(NBTTagCompound nbt) { this.setDead(); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}
	
	public static void statFac(World world, double x, double y, double z, float scale) {
		EntityNukeTorex torex = new EntityNukeTorex(world).setScale(MathHelper.clamp_float((float) BobMathUtil.squirt(scale * 0.01) * 1.5F, 0.5F, 5F));
		torex.setPosition(x, y, z);
		torex.forceSpawn = true;
		world.spawnEntityInWorld(torex);
		TrackerUtil.setTrackingRange(world, torex, 1000);
	}
	
	public static void statFacBale(World world, double x, double y, double z, float scale) {
		EntityNukeTorex torex = new EntityNukeTorex(world).setScale(MathHelper.clamp_float((float) BobMathUtil.squirt(scale * 0.01) * 1.5F, 0.5F, 5F)).setType(1);
		torex.setPosition(x, y, z);
		torex.forceSpawn = true;
		world.spawnEntityInWorld(torex);
		TrackerUtil.setTrackingRange(world, torex, 1000);
	}
}
