package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.MissileStruct;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.main.MainRegistry;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityMissileCustom extends Entity implements IChunkLoader, IRadarDetectable {

	int startX;
	int startZ;
	int targetX;
	int targetZ;
	double velocity;
	double decelY;
	double accelXZ;
	float fuel;
	float consumption;
	private Ticket loaderTicket;
	public int health = 50;
	MissileStruct template;

	public EntityMissileCustom(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		startX = (int) posX;
		startZ = (int) posZ;
		targetX = (int) posX;
		targetZ = (int) posZ;
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		if(this.isEntityInvulnerable()) {
			return false;
		} else {
			if(!this.isDead && !this.worldObj.isRemote) {
				health -= p_70097_2_;

				if(this.health <= 0) {
					this.setDead();
					this.killMissile();
				}
			}

			return true;
		}
	}

	private void killMissile() {
		ExplosionLarge.explode(worldObj, posX, posY, posZ, 5, true, false, true);
		ExplosionLarge.spawnShrapnelShower(worldObj, posX, posY, posZ, motionX, motionY, motionZ, 15, 0.075);
	}

	public EntityMissileCustom(World world, float x, float y, float z, int a, int b, MissileStruct template) {
		super(world);
		this.ignoreFrustumCheck = true;
		/*
		 * this.posX = x; this.posY = y; this.posZ = z;
		 */
		this.setLocationAndAngles(x, y, z, 0, 0);
		startX = (int) x;
		startZ = (int) z;
		targetX = a;
		targetZ = b;
		this.motionY = 2;

		this.template = template;

		this.dataWatcher.updateObject(9, Item.getIdFromItem(template.warhead));
		this.dataWatcher.updateObject(10, Item.getIdFromItem(template.fuselage));
		if(template.fins != null)
			this.dataWatcher.updateObject(11, Item.getIdFromItem(template.fins));
		else
			this.dataWatcher.updateObject(11, Integer.valueOf(0));
		this.dataWatcher.updateObject(12, Item.getIdFromItem(template.thruster));

		Vec3 vector = Vec3.createVectorHelper(targetX - startX, 0, targetZ - startZ);
		accelXZ = decelY = 1 / vector.lengthVector();
		decelY *= 2;

		velocity = 0.0;

		ItemMissile fuselage = (ItemMissile) template.fuselage;
		ItemMissile thruster = (ItemMissile) template.thruster;

		this.fuel = (Float) fuselage.attributes[1];
		this.consumption = (Float) thruster.attributes[1];

		this.setSize(1.5F, 1.5F);
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
		this.dataWatcher.addObject(8, Integer.valueOf(this.health));

		if(template != null) {
			this.dataWatcher.addObject(9, Integer.valueOf(Item.getIdFromItem(template.warhead)));
			this.dataWatcher.addObject(10, Integer.valueOf(Item.getIdFromItem(template.fuselage)));

			if(template.fins != null)
				this.dataWatcher.addObject(11, Integer.valueOf(Item.getIdFromItem(template.fins)));
			else
				this.dataWatcher.addObject(11, Integer.valueOf(0));

			this.dataWatcher.addObject(12, Integer.valueOf(Item.getIdFromItem(template.thruster)));
		} else {
			this.dataWatcher.addObject(9, Integer.valueOf(0));
			this.dataWatcher.addObject(10, Integer.valueOf(0));
			this.dataWatcher.addObject(11, Integer.valueOf(0));
			this.dataWatcher.addObject(12, Integer.valueOf(0));
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		motionX = nbt.getDouble("moX");
		motionY = nbt.getDouble("moY");
		motionZ = nbt.getDouble("moZ");
		posX = nbt.getDouble("poX");
		posY = nbt.getDouble("poY");
		posZ = nbt.getDouble("poZ");
		decelY = nbt.getDouble("decel");
		accelXZ = nbt.getDouble("accel");
		targetX = nbt.getInteger("tX");
		targetZ = nbt.getInteger("tZ");
		startX = nbt.getInteger("sX");
		startZ = nbt.getInteger("sZ");
		velocity = nbt.getInteger("veloc");
		fuel = nbt.getFloat("fuel");
		consumption = nbt.getFloat("consumption");
		this.dataWatcher.updateObject(9, nbt.getInteger("warhead"));
		this.dataWatcher.updateObject(10, nbt.getInteger("fuselage"));
		this.dataWatcher.updateObject(11, nbt.getInteger("fins"));
		this.dataWatcher.updateObject(12, nbt.getInteger("thruster"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("moX", motionX);
		nbt.setDouble("moY", motionY);
		nbt.setDouble("moZ", motionZ);
		nbt.setDouble("poX", posX);
		nbt.setDouble("poY", posY);
		nbt.setDouble("poZ", posZ);
		nbt.setDouble("decel", decelY);
		nbt.setDouble("accel", accelXZ);
		nbt.setInteger("tX", targetX);
		nbt.setInteger("tZ", targetZ);
		nbt.setInteger("sX", startX);
		nbt.setInteger("sZ", startZ);
		nbt.setDouble("veloc", velocity);
		nbt.setFloat("fuel", fuel);
		nbt.setFloat("consumption", consumption);
		nbt.setInteger("warhead", this.dataWatcher.getWatchableObjectInt(9));
		nbt.setInteger("fuselage", this.dataWatcher.getWatchableObjectInt(10));
		nbt.setInteger("fins", this.dataWatcher.getWatchableObjectInt(11));
		nbt.setInteger("thruster", this.dataWatcher.getWatchableObjectInt(12));
	}

	protected void rotation() {
		float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
	}

	@Override
	public void onUpdate() {
		this.dataWatcher.updateObject(8, Integer.valueOf(this.health));

		this.setLocationAndAngles(posX + this.motionX * velocity, posY + this.motionY * velocity, posZ + this.motionZ * velocity, 0, 0);

		this.rotation();

		if(fuel > 0 || worldObj.isRemote) {

			fuel -= consumption;

			this.motionY -= decelY * velocity;

			Vec3 vector = Vec3.createVectorHelper(targetX - startX, 0, targetZ - startZ);
			vector = vector.normalize();
			vector.xCoord *= accelXZ * velocity;
			vector.zCoord *= accelXZ * velocity;

			if(motionY > 0) {
				motionX += vector.xCoord;
				motionZ += vector.zCoord;
			}

			if(motionY < 0) {
				motionX -= vector.xCoord;
				motionZ -= vector.zCoord;
			}

			if(velocity < 5)
				velocity += 0.01;
		} else {

			motionX *= 0.99;
			motionZ *= 0.99;

			if(motionY > -1.5)
				motionY -= 0.05;
		}

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air && this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.water && this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.flowing_water) {

			if(!this.worldObj.isRemote) {
				onImpact();
			}
			this.setDead();
			return;
		}

		if(this.worldObj.isRemote) {

			Vec3 v = Vec3.createVectorHelper(motionX, motionY, motionZ);
			v = v.normalize();

			String smoke = "";

			ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(10));
			FuelType type = (FuelType) part.attributes[0];

			switch(type) {
			case BALEFIRE:
				smoke = "exBalefire";
				break;
			case HYDROGEN:
				smoke = "exHydrogen";
				break;
			case KEROSENE:
				smoke = "exKerosene";
				break;
			case SOLID:
				smoke = "exSolid";
				break;
			case XENON:
				break;
			}

			for(int i = 0; i < velocity; i++)
				MainRegistry.proxy.spawnParticle(posX - v.xCoord * i, posY - v.yCoord * i, posZ - v.zCoord * i, smoke, null);
		}

		loadNeighboringChunks((int) (posX / 16), (int) (posZ / 16));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 2500000;
	}

	public void onImpact() {

		ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(9));

		WarheadType type = (WarheadType) part.attributes[0];
		float strength = (Float) part.attributes[1];

		switch(type) {
		case HE:
			ExplosionLarge.explode(worldObj, posX, posY, posZ, strength, true, false, true);
			ExplosionLarge.jolt(worldObj, posX, posY, posZ, strength, (int) (strength * 50), 0.25);
			break;
		case INC:
			ExplosionLarge.explodeFire(worldObj, posX, posY, posZ, strength, true, false, true);
			ExplosionLarge.jolt(worldObj, posX, posY, posZ, strength * 1.5, (int) (strength * 50), 0.25);
			break;
		case CLUSTER:
			break;
		case BUSTER:
			ExplosionLarge.buster(worldObj, posX, posY, posZ, Vec3.createVectorHelper(motionX, motionY, motionZ), strength, strength * 4);
			break;
		case NUCLEAR:
		case TX:
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(worldObj, (int) strength, posX, posY, posZ));
			EntityNukeCloudSmall nuke = new EntityNukeCloudSmall(worldObj, 1000, strength * 0.005F);
			nuke.posX = posX;
			nuke.posY = posY;
			nuke.posZ = posZ;
			worldObj.spawnEntityInWorld(nuke);
			break;
		case BALEFIRE:
			EntityBalefire bf = new EntityBalefire(worldObj);
			bf.posX = this.posX;
			bf.posY = this.posY;
			bf.posZ = this.posZ;
			bf.destructionRange = (int) strength;
			worldObj.spawnEntityInWorld(bf);
			worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFacBale(worldObj, posX, posY + 5, posZ, strength * 1.5F, 1000));
			break;
		case N2:
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFacNoRad(worldObj, (int) strength, posX, posY, posZ));
			EntityNukeCloudSmall n2 = new EntityNukeCloudSmall(worldObj, 1000, strength * 0.005F);
			n2.posX = posX;
			n2.posY = posY;
			n2.posZ = posZ;
			worldObj.spawnEntityInWorld(n2);
			break;
		case TAINT:
			int r = (int) strength;
			for(int i = 0; i < r * 10; i++) {
				int a = rand.nextInt(r) + (int) posX - (r / 2 - 1);
				int b = rand.nextInt(r) + (int) posY - (r / 2 - 1);
				int c = rand.nextInt(r) + (int) posZ - (r / 2 - 1);
				if(worldObj.getBlock(a, b, c).isReplaceable(worldObj, a, b, c) && BlockTaint.hasPosNeightbour(worldObj, a, b, c)) {
					worldObj.setBlock(a, b, c, ModBlocks.taint, rand.nextInt(3) + 4, 2);
				}
			}
			break;
		case CLOUD:
			this.worldObj.playAuxSFX(2002, (int) Math.round(this.posX), (int) Math.round(this.posY), (int) Math.round(this.posZ), 0);
			ExplosionChaos.spawnChlorine(worldObj, posX - motionX, posY - motionY, posZ - motionZ, 750, 2.5, 2);
			break;
		case TURBINE:
			ExplosionLarge.explode(worldObj, posX, posY, posZ, 10, true, false, true);
			int count = (int) strength;
			Vec3 vec = Vec3.createVectorHelper(0.5, 0, 0);

			for(int i = 0; i < count; i++) {
				EntityBulletBase blade = new EntityBulletBase(worldObj, BulletConfigSyncingUtil.TURBINE);
				blade.setPositionAndRotation(this.posX - this.motionX, this.posY - this.motionY + rand.nextGaussian(), this.posZ - this.motionZ, 0, 0);
				blade.motionX = vec.xCoord;
				blade.motionZ = vec.zCoord;
				worldObj.spawnEntityInWorld(blade);
				vec.rotateAroundY((float) (Math.PI * 2F / (float) count));
			}

			break;
		default:
			break;

		}
	}

	public void init(Ticket ticket) {
		if(!worldObj.isRemote) {

			if(ticket != null) {

				if(loaderTicket == null) {

					loaderTicket = ticket;
					loaderTicket.bindEntity(this);
					loaderTicket.getModData();
				}

				ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
			}
		}
	}

	List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!worldObj.isRemote && loaderTicket != null) {
			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}

			loadedChunks.clear();
			loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
			loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ + 1));
			loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ - 1));
			loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ - 1));
			loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ + 1));
			loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ));
			loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ + 1));
			loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ));
			loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ - 1));

			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.forceChunk(loaderTicket, chunk);
			}
		}
	}

	@Override
	public RadarTargetType getTargetType() {

		ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(10));

		PartSize top = part.top;
		PartSize bottom = part.bottom;

		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_10)
			return RadarTargetType.MISSILE_10;
		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_15)
			return RadarTargetType.MISSILE_10_15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_15)
			return RadarTargetType.MISSILE_15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_20)
			return RadarTargetType.MISSILE_15_20;
		if(top == PartSize.SIZE_20 && bottom == PartSize.SIZE_20)
			return RadarTargetType.MISSILE_20;

		return RadarTargetType.PLAYER;
	}
}
