package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.DebugTeleporter;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.MissileStruct;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
import com.hbm.items.ItemVOTVdrive.DestinationType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.main.MainRegistry;

import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectableNT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMissileCustom extends EntityMissileBaseNT implements IChunkLoader, IRadarDetectable {
	private ItemStack payload;

	public void setPayload(ItemStack stack) {
		this.payload = stack.copy();
	}
	protected float fuel;
	protected float consumption;

	public EntityMissileCustom(World world) {
		super(world);
	}

	public EntityMissileCustom(World world, float x, float y, float z, int a, int b, MissileStruct template) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.setLocationAndAngles(x, y, z, 0, 0);
		startX = (int) x;
		startZ = (int) z;
		targetX = a;
		targetZ = b;
		this.motionY = 2;

		Vec3 vector = Vec3.createVectorHelper(targetX - startX, 0, targetZ - startZ);
		accelXZ = decelY = 1 / vector.lengthVector();
		decelY *= 2;
		velocity = 0;

		this.dataWatcher.updateObject(9, Item.getIdFromItem(template.warhead));
		this.dataWatcher.updateObject(10, Item.getIdFromItem(template.fuselage));
		this.dataWatcher.updateObject(12, Item.getIdFromItem(template.thruster));
		if(template.fins != null) {
			this.dataWatcher.updateObject(11, Item.getIdFromItem(template.fins));
		} else {
			this.dataWatcher.updateObject(11, Integer.valueOf(0));
		}

		ItemMissile fuselage = (ItemMissile) template.fuselage;
		ItemMissile thruster = (ItemMissile) template.thruster;

		this.fuel = (Float) fuselage.attributes[1];
		this.consumption = (Float) thruster.attributes[1];

		this.setSize(1.5F, 1.5F);
	}

	@Override
	protected void killMissile() {
		ExplosionLarge.explode(worldObj, posX, posY, posZ, 5, true, false, true);
		ExplosionLarge.spawnShrapnelShower(worldObj, posX, posY, posZ, motionX, motionY, motionZ, 15, 0.075);
	}
	
	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {
			if(this.hasPropulsion()) this.fuel -= this.consumption;
			EntityPlayer riding = (EntityPlayer) this.riddenByEntity;

				if(payload != null) {
				if(payload.getTagCompound().getBoolean("Processed") == true ) {
					
				if(posY < 7 && riding == null) {
					this.motionY = 0.01;
					this.velocity = 0.01;

					this.prevRotationPitch = 90;
					this.prevRotationYaw = 90;

				}else {
					this.motionX = 0;
					this.motionY = 1; 
					this.motionZ = 0;
					}
				}

				if(posY > 600) {
					if(riding != null) {
					switch (DestinationType.values()[payload.getItemDamage()]) {
					case MOHO:
			            DebugTeleporter.teleport(riding, SpaceConfig.mohoDimension, riding.posX, 300, riding.posZ);
						break;
					case DUNA:
			            DebugTeleporter.teleport(riding, SpaceConfig.dunaDimension, riding.posX, 300, riding.posZ);
			            break;
					case DRES:
			            DebugTeleporter.teleport(riding, SpaceConfig.dresDimension, riding.posX, 300, riding.posZ);
						break;
					case MUN:
			            DebugTeleporter.teleport(riding, SpaceConfig.moonDimension, riding.posX, 300, riding.posZ);
			            break;
					case MINMUS:
			            DebugTeleporter.teleport(riding, SpaceConfig.minmusDimension, riding.posX, 300, riding.posZ);
			            break;
					case EVE:
			            DebugTeleporter.teleport(riding, SpaceConfig.eveDimension, riding.posX, 300, riding.posZ);
			            break;
					case IKE:
			            DebugTeleporter.teleport(riding, SpaceConfig.ikeDimension, riding.posX, 300, riding.posZ);
			            break;
					default: 
						riding.dismountEntity(riding);
						break;
					}
				}
					if(riding != null) {
					riding.dismountEntity(riding);
					}

			}
				if(this.posY > 610) {
					this.setDead();
				}
				
			}

		}
		
		
		super.onUpdate();
	}

	@Override
	public boolean hasPropulsion() {
		return this.fuel > 0;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(8, Integer.valueOf(this.health));
		this.dataWatcher.addObject(9, Integer.valueOf(0));
		this.dataWatcher.addObject(10, Integer.valueOf(0));
		this.dataWatcher.addObject(11, Integer.valueOf(0));
		this.dataWatcher.addObject(12, Integer.valueOf(0));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		fuel = nbt.getFloat("fuel");
		consumption = nbt.getFloat("consumption");
		this.dataWatcher.updateObject(9, nbt.getInteger("warhead"));
		this.dataWatcher.updateObject(10, nbt.getInteger("fuselage"));
		this.dataWatcher.updateObject(11, nbt.getInteger("fins"));
		this.dataWatcher.updateObject(12, nbt.getInteger("thruster"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("fuel", fuel);
		nbt.setFloat("consumption", consumption);
		nbt.setInteger("warhead", this.dataWatcher.getWatchableObjectInt(9));
		nbt.setInteger("fuselage", this.dataWatcher.getWatchableObjectInt(10));
		nbt.setInteger("fins", this.dataWatcher.getWatchableObjectInt(11));
		nbt.setInteger("thruster", this.dataWatcher.getWatchableObjectInt(12));
	}
	
	@Override
	protected void spawnContrail() {

		Vec3 v = Vec3.createVectorHelper(motionX, motionY, motionZ).normalize();
		String smoke = "";
		ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(10));
		FuelType type = (FuelType) part.attributes[0];

		switch(type) {
		case BALEFIRE: smoke = "exBalefire"; break;
		case HYDROGEN: smoke = "exHydrogen"; break;
		case KEROSENE: smoke = "exKerosene"; break;
		case SOLID: smoke = "exSolid"; break;
		case XENON: break;
		case HYDRAZINE: break;

		}
		EntityPlayer riding = (EntityPlayer) this.riddenByEntity;

			if(!smoke.isEmpty()) {
				if(this.posY > 10) {
				for(int i = 0; i < velocity; i++) MainRegistry.proxy.spawnParticle(posX - v.xCoord * i, posY - v.yCoord * i, posZ - v.zCoord * i, smoke, null);
			}
		}
	}

	@Override
	public void onImpact() { //TODO: demolish this steaming pile of shit

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
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(worldObj, (int) strength, posX, posY, posZ));
			EntityNukeTorex.statFac(worldObj, posX, posY, posZ, strength);
			break;
		case BALEFIRE:
			EntityBalefire bf = new EntityBalefire(worldObj);
			bf.posX = this.posX;
			bf.posY = this.posY;
			bf.posZ = this.posZ;
			bf.destructionRange = (int) strength;
			worldObj.spawnEntityInWorld(bf);
			EntityNukeTorex.statFacBale(worldObj, posX, posY, posZ, strength);
			break;
		case N2:
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFacNoRad(worldObj, (int) strength, posX, posY, posZ));
			EntityNukeTorex.statFac(worldObj, posX, posY, posZ, strength);
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
				EntityBulletBaseNT blade = new EntityBulletBaseNT(worldObj, BulletConfigSyncingUtil.TURBINE);
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
	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(super.interactFirst(player)) {
			return true;
		} else if(!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player)) {
			player.mountEntity(this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public RadarTargetType getTargetType() {

		ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(10));
		PartSize top = part.top;
		PartSize bottom = part.bottom;

		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_10) return RadarTargetType.MISSILE_10;
		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_15) return RadarTargetType.MISSILE_10_15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_15) return RadarTargetType.MISSILE_15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_20) return RadarTargetType.MISSILE_15_20;
		if(top == PartSize.SIZE_20 && bottom == PartSize.SIZE_20) return RadarTargetType.MISSILE_20;

		return RadarTargetType.MISSILE_TIER1;
	}

	@Override
	public String getUnlocalizedName() {

		ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(10));
		PartSize top = part.top;
		PartSize bottom = part.bottom;

		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_10) return "radar.target.custom10";
		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_15) return "radar.target.custom1015";
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_15) return "radar.target.custom15";
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_20) return "radar.target.custom1520";
		if(top == PartSize.SIZE_20 && bottom == PartSize.SIZE_20) return "radar.target.custom20";
		
		return "radar.target.custom";
	}

	@Override
	public int getBlipLevel() {

		ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(10));
		PartSize top = part.top;
		PartSize bottom = part.bottom;

		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_10) return IRadarDetectableNT.TIER10;
		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_15) return IRadarDetectableNT.TIER10_15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_15) return IRadarDetectableNT.TIER15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_20) return IRadarDetectableNT.TIER15_20;
		if(top == PartSize.SIZE_20 && bottom == PartSize.SIZE_20) return IRadarDetectableNT.TIER20;
		
		return IRadarDetectableNT.TIER1;
	}

	@Override public List<ItemStack> getDebris() { return new ArrayList(); }
	@Override public ItemStack getDebrisRareDrop() { return null; }
}
