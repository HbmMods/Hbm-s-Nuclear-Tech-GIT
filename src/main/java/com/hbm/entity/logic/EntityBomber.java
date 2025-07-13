package com.hbm.entity.logic;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.interfaces.NotableComments;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@NotableComments
public class EntityBomber extends EntityPlaneBase {
	
	/* This was probably the dumbest fucking way that I could have handled this. Not gonna change it now, be glad I made a superclass at all. */
	int bombStart = 75;
	int bombStop = 125;
	int bombRate = 3;
	int type = 0;
	
	protected AudioWrapper audio;

	public EntityBomber(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.setSize(8.0F, 4.0F);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	/** This sucks balls. Too bad! */
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(worldObj.isRemote) {
			if(this.getDataWatcher().getWatchableObjectFloat(17) > 0) {
				if(audio == null || !audio.isPlaying()) {
					int bomberType = this.dataWatcher.getWatchableObjectByte(16);
					audio = MainRegistry.proxy.getLoopedSound(bomberType <= 4 ? "hbm:entity.bomberSmallLoop" : "hbm:entity.bomberLoop", (float) posX, (float) posY, (float) posZ, 2F, 250F, 1F, 20);
					audio.startSound();
				}
				audio.keepAlive();
				audio.updatePosition((float) posX, (float) posY, (float) posZ);
			} else {
				if(audio != null && audio.isPlaying()) {
					audio.stopSound();
					audio = null;
				}
			}
		}
		
		if(!worldObj.isRemote && this.health > 0 && this.ticksExisted > bombStart && this.ticksExisted < bombStop && this.ticksExisted % bombRate == 0) {
			
			if(type == 3) {
				worldObj.playSoundEffect((double) (posX + 0.5F), (double) (posY + 0.5F), (double) (posZ + 0.5F), "random.fizz", 5.0F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
				ExplosionChaos.spawnPoisonCloud(worldObj, this.posX, this.posY - 1F, this.posZ, 10, 0.5, 3);

			} else if(type == 5) {

			} else if(type == 6) {
				worldObj.playSoundEffect((double) (posX + 0.5F), (double) (posY + 0.5F), (double) (posZ + 0.5F), "hbm:weapon.missileTakeOff", 10.0F, 0.9F + rand.nextFloat() * 0.2F);
				EntityBoxcar rocket = new EntityBoxcar(worldObj);
				rocket.posX = posX + rand.nextDouble() - 0.5;
				rocket.posY = posY - rand.nextDouble();
				rocket.posZ = posZ + rand.nextDouble() - 0.5;
				worldObj.spawnEntityInWorld(rocket);

			} else if(type == 7) {
				worldObj.playSoundEffect((double) (posX + 0.5F), (double) (posY + 0.5F), (double) (posZ + 0.5F), "random.fizz", 5.0F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
				ExplosionChaos.spawnPoisonCloud(worldObj, this.posX, worldObj.getHeightValue((int) this.posX, (int) this.posZ) + 2, this.posZ, 10, 1, 2);
			
			} else {
				worldObj.playSoundEffect((double) (posX + 0.5F), (double) (posY + 0.5F), (double) (posZ + 0.5F), "hbm:entity.bombWhistle", 10.0F, 0.9F + rand.nextFloat() * 0.2F);
				EntityBombletZeta zeta = new EntityBombletZeta(worldObj);
				zeta.rotation();
				zeta.type = type;
				zeta.posX = posX + rand.nextDouble() - 0.5;
				zeta.posY = posY - rand.nextDouble();
				zeta.posZ = posZ + rand.nextDouble() - 0.5;
				if(type == 0) {
					zeta.motionX = motionX + rand.nextGaussian() * 0.15; zeta.motionZ = motionZ + rand.nextGaussian() * 0.15;
				} else {
					zeta.motionX = motionX; zeta.motionZ = motionZ;
				}
				worldObj.spawnEntityInWorld(zeta);
			}
		}
	}

	public void fac(World world, double x, double y, double z) {

		Vec3 vector = Vec3.createVectorHelper(world.rand.nextDouble() - 0.5, 0, world.rand.nextDouble() - 0.5);
		vector = vector.normalize();
		vector.xCoord *= GeneralConfig.enableBomberShortMode ? 1 : 2;
		vector.zCoord *= GeneralConfig.enableBomberShortMode ? 1 : 2;

		this.setLocationAndAngles(x - vector.xCoord * 100, y + 50, z - vector.zCoord * 100, 0.0F, 0.0F);
		this.loadNeighboringChunks((int) (x / 16), (int) (z / 16));

		this.motionX = vector.xCoord;
		this.motionZ = vector.zCoord;
		this.motionY = 0.0D;

		this.rotation();

		int i = 1;

		int rand = world.rand.nextInt(7);

		switch(rand) {
		case 0: case 1: i = 1; break;
		case 2: case 3: i = 2; break;
		case 4: i = 5; break;
		case 5: i = 6; break;
		case 6: i = 7; break;
		}

		if(world.rand.nextInt(100) == 0) {
			rand = world.rand.nextInt(4);
			switch(rand) {
			case 0: i = 0; break;
			case 1: i = 3; break;
			case 2: i = 4; break;
			case 3: i = 8; break;
			}
		}

		this.getDataWatcher().updateObject(16, (byte) i);
		this.setSize(8.0F, 4.0F);
	}

	public static EntityBomber statFacCarpet(World world, double x, double y, double z) {
		EntityBomber bomber = new EntityBomber(world);
		bomber.timer = 200;
		bomber.bombStart = 50;
		bomber.bombStop = 100;
		bomber.bombRate = 2;
		bomber.fac(world, x, y, z);
		bomber.type = 0;
		return bomber;
	}

	public static EntityBomber statFacNapalm(World world, double x, double y, double z) {
		EntityBomber bomber = new EntityBomber(world);
		bomber.timer = 200;
		bomber.bombStart = 50;
		bomber.bombStop = 100;
		bomber.bombRate = 5;
		bomber.fac(world, x, y, z);
		bomber.type = 1;
		return bomber;
	}

	public static EntityBomber statFacChlorine(World world, double x, double y, double z) {
		EntityBomber bomber = new EntityBomber(world);
		bomber.timer = 200;
		bomber.bombStart = 50;
		bomber.bombStop = 100;
		bomber.bombRate = 4;
		bomber.fac(world, x, y, z);
		bomber.type = 2;
		return bomber;
	}

	public static EntityBomber statFacOrange(World world, double x, double y, double z) {
		EntityBomber bomber = new EntityBomber(world);
		bomber.timer = 200;
		bomber.bombStart = 75;
		bomber.bombStop = 125;
		bomber.bombRate = 1;
		bomber.fac(world, x, y, z);
		bomber.type = 3;
		return bomber;
	}

	public static EntityBomber statFacABomb(World world, double x, double y, double z) {
		EntityBomber bomber = new EntityBomber(world);
		bomber.timer = 200;
		bomber.bombStart = 60;
		bomber.bombStop = 70;
		bomber.bombRate = 65;
		bomber.fac(world, x, y, z);
		int i = 1;

		int rand = world.rand.nextInt(3);

		switch(rand) {
		case 0: i = 5; break;
		case 1: i = 6; break;
		case 2: i = 7; break;
		}
		if(world.rand.nextInt(100) == 0) i = 8;

		bomber.getDataWatcher().updateObject(16, (byte) i);
		bomber.type = 4;
		return bomber;
	}

	public static EntityBomber statFacStinger(World world, double x, double y, double z) {
		EntityBomber bomber = new EntityBomber(world);
		bomber.timer = 200;
		bomber.bombStart = 50;
		bomber.bombStop = 150;
		bomber.bombRate = 10;
		bomber.fac(world, x, y, z);
		bomber.getDataWatcher().updateObject(16, (byte) 4);
		bomber.type = 5;
		return bomber;
	}

	public static EntityBomber statFacBoxcar(World world, double x, double y, double z) {
		EntityBomber bomber = new EntityBomber(world);
		bomber.timer = 200;
		bomber.bombStart = 50;
		bomber.bombStop = 150;
		bomber.bombRate = 10;
		bomber.fac(world, x, y, z);
		bomber.getDataWatcher().updateObject(16, (byte) 6);
		bomber.type = 6;
		return bomber;
	}

	public static EntityBomber statFacPC(World world, double x, double y, double z) {
		EntityBomber bomber = new EntityBomber(world);
		bomber.timer = 200;
		bomber.bombStart = 75;
		bomber.bombStop = 125;
		bomber.bombRate = 1;
		bomber.fac(world, x, y, z);
		bomber.getDataWatcher().updateObject(16, (byte) 6);
		bomber.type = 7;
		return bomber;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		bombStart = nbt.getInteger("bombStart");
		bombStop = nbt.getInteger("bombStop");
		bombRate = nbt.getInteger("bombRate");
		type = nbt.getInteger("type");
		this.getDataWatcher().updateObject(16, nbt.getByte("style"));
		this.setSize(8.0F, 4.0F);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("bombStart", bombStart);
		nbt.setInteger("bombStop", bombStop);
		nbt.setInteger("bombRate", bombRate);
		nbt.setInteger("type", type);
		nbt.setByte("style", this.getDataWatcher().getWatchableObjectByte(16));
	}
}
