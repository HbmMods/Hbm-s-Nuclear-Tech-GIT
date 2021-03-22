package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.missile.EntityMissileBaseAdvanced;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemTurretBiometry;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.INpc;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.FakePlayer;

/**
 * More over-engineered than ever, but chopping this thing into the smallest possible pieces makes it easier for my demented brain to comprehend
 * @author hbm
 *
 */
public abstract class TileEntityTurretBaseNT extends TileEntityMachineBase implements IConsumer, IControlReceiver {

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("del")) {
			this.removeName(data.getInteger("del"));
			
		} else if(data.hasKey("name")) {
			this.addName(data.getString("name"));
		}
	}

	//this time we do all rotations in radians
	//what way are we facing?
	public double rotationYaw;
	public double rotationPitch;
	//only used by clients for interpolation
	public double lastRotationYaw;
	public double lastRotationPitch;
	//is the turret on?
	public boolean isOn = false;
	//is the turret aimed at the target?
	public boolean aligned = false;
	//how many ticks until the next check
	public int searchTimer;
	
	public long power;

	public boolean targetPlayers = false;
	public boolean targetAnimals = false;
	public boolean targetMobs = true;
	public boolean targetMachines = true;

	public Entity target;
	public Vec3 tPos;
	
	//tally marks!
	public int stattrak;
	
	/**
	 * 		 X
	 * 
	 * 		YYY
	 * 		YYY
	 * 		YYY Z
	 * 
	 * 		X -> ai slot		(0)
	 * 		Y -> ammo slots		(1 - 9)
	 * 		Z -> battery slot	(10)
	 */
	
	public TileEntityTurretBaseNT() {
		super(11);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
		this.targetPlayers = nbt.getBoolean("targetPlayers");
		this.targetAnimals = nbt.getBoolean("targetAnimals");
		this.targetMobs = nbt.getBoolean("targetMobs");
		this.targetMachines = nbt.getBoolean("targetMachines");
		this.stattrak = nbt.getInteger("stattrak");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setBoolean("isOn", this.isOn);
		nbt.setBoolean("targetPlayers", this.targetPlayers);
		nbt.setBoolean("targetAnimals", this.targetAnimals);
		nbt.setBoolean("targetMobs", this.targetMobs);
		nbt.setBoolean("targetMachines", this.targetMachines);
		nbt.setInteger("stattrak", this.stattrak);
	}
	
	public void manualSetup() { }
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			this.lastRotationPitch = this.rotationPitch;
			this.lastRotationYaw = this.rotationYaw;
		}

		this.aligned = false;
		
		if(!worldObj.isRemote) {
			
			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.stattrak++;
			}
		}
		
		if(target != null) {
			if(!this.entityInLOS(this.target)) {
				this.target = null;
			}
		}
		
		if(!worldObj.isRemote) {
			
			if(target != null) {
				this.tPos = this.getEntityPos(target);
			} else {
				this.tPos = null;
			}
		}
		
		if(this.isOn && hasPower()) {
			
			if(tPos != null)
				this.alignTurret();
		} else {

			this.target = null;
			this.tPos = null;
		}
		
		if(!worldObj.isRemote) {
			
			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.tPos = null;
				this.stattrak++;
			}
			
			if(this.isOn && hasPower()) {
				searchTimer--;
				
				this.setPower(this.getPower() - this.getConsumption());
				
				if(searchTimer <= 0) {
					searchTimer = this.getDecetorInterval();
					
					if(this.target == null)
						this.seekNewTarget();
				}
			} else {
				searchTimer = 0;
			}
			
			if(this.aligned) {
				this.updateFiringTick();
			}
			
			this.power = Library.chargeTEFromItems(slots, 10, this.power, this.getMaxPower());
			
			NBTTagCompound data = new NBTTagCompound();
			if(this.tPos != null) {
				data.setDouble("tX", this.tPos.xCoord);
				data.setDouble("tY", this.tPos.yCoord);
				data.setDouble("tZ", this.tPos.zCoord);
			}
			data.setLong("power", this.power);
			data.setBoolean("isOn", this.isOn);
			data.setBoolean("targetPlayers", this.targetPlayers);
			data.setBoolean("targetAnimals", this.targetAnimals);
			data.setBoolean("targetMobs", this.targetMobs);
			data.setBoolean("targetMachines", this.targetMachines);
			data.setInteger("stattrak", this.stattrak);
			this.networkPack(data, 250);
			
		} else {
			
			Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
			vec.rotateAroundZ((float) -this.rotationPitch);
			vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
			
			//this will fix the interpolation error when the turret crosses the 360° point
			if(Math.abs(this.lastRotationYaw - this.rotationYaw) > Math.PI) {
				
				if(this.lastRotationYaw < this.rotationYaw)
					this.lastRotationYaw += Math.PI * 2;
				else
					this.lastRotationYaw -= Math.PI * 2;
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
		this.targetPlayers = nbt.getBoolean("targetPlayers");
		this.targetAnimals = nbt.getBoolean("targetAnimals");
		this.targetMobs = nbt.getBoolean("targetMobs");
		this.targetMachines = nbt.getBoolean("targetMachines");
		this.stattrak = nbt.getInteger("stattrak");
		
		if(nbt.hasKey("tX")) {
			this.tPos = Vec3.createVectorHelper(nbt.getDouble("tX"), nbt.getDouble("tY"), nbt.getDouble("tZ"));
		} else {
			this.tPos = null;
		}
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		switch(meta) {
		case 0:this.isOn = !this.isOn; break;
		case 1:this.targetPlayers = !this.targetPlayers; break;
		case 2:this.targetAnimals = !this.targetAnimals; break;
		case 3:this.targetMobs = !this.targetMobs; break;
		case 4:this.targetMachines = !this.targetMachines; break;
		}
	}
	
	public abstract void updateFiringTick();
	
	public BulletConfiguration getFirstConfigLoaded() {
		
		List<Integer> list = getAmmoList();
		
		if(list == null || list.isEmpty())
			return null;
		
		//doing it like this will fire slots in the right order, not in the order of the configs
		//you know, the weird thing the IItemGunBase does
		for(int i = 1; i < 10; i++) {
			
			if(slots[i] != null) {
				
				for(Integer c : list) { //we can afford all this extra iteration trash on the count that a turret has at most like 4 bullet configs
					
					BulletConfiguration conf = BulletConfigSyncingUtil.pullConfig(c);
					
					if(conf.ammo == slots[i].getItem())
						return conf;
				}
			}
		}
		
		return null;
	}
	
	public void spawnBullet(BulletConfiguration bullet) {
		
		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		EntityBulletBase proj = new EntityBulletBase(worldObj, BulletConfigSyncingUtil.getKey(bullet));
		proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, 0.0F, 0.0F);
		
		proj.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, bullet.velocity, bullet.spread);
		worldObj.spawnEntityInWorld(proj);
	}
	
	public void conusmeAmmo(Item ammo) {
		
		for(int i = 1; i < 10; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ammo) {
				
				this.decrStackSize(i, 1);
				return;
			}
		}
		
		this.markDirty();
	}
	
	/**
	 * Reads the namelist from the AI chip in slot 0
	 * @return null if there is either no chip to be found or if the name list is empty, otherwise it just reads the strings from the chip's NBT
	 */
	public List<String> getWhitelist() {
		
		if(slots[0] != null && slots[0].getItem() == ModItems.turret_chip) {
			
			String[] array = ItemTurretBiometry.getNames(slots[0]);
			
			if(array == null)
				return null;
			
			return Arrays.asList(ItemTurretBiometry.getNames(slots[0]));
		}
		
		return null;
	}
	
	/**
	 * Appends a new name to the chip
	 * @param name
	 */
	public void addName(String name) {
		
		if(slots[0] != null && slots[0].getItem() == ModItems.turret_chip) {
			ItemTurretBiometry.addName(slots[0], name);
		}
	}
	
	/**
	 * Removes the chip's entry at a given 
	 * @param index
	 */
	public void removeName(int index) {
		
		if(slots[0] != null && slots[0].getItem() == ModItems.turret_chip) {
			
			String[] array = ItemTurretBiometry.getNames(slots[0]);
			
			if(array == null)
				return;
			
			List<String> names = new ArrayList(Arrays.asList(array));
			ItemTurretBiometry.clearNames(slots[0]);
			
			names.remove(index);
			
			for(String name : names)
				ItemTurretBiometry.addName(slots[0], name);
		}
	}
	
	/**
	 * Finds the nearest acceptable target within range and in line of sight
	 */
	protected void seekNewTarget() {
		
		Vec3 pos = this.getTurretPos();
		double range = this.getDecetorRange();
		List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(pos.xCoord, pos.yCoord, pos.zCoord, pos.xCoord, pos.yCoord, pos.zCoord).expand(range, range, range));
		
		Entity target = null;
		double closest = range;
		
		for(Entity entity : entities) {

			Vec3 ent = this.getEntityPos(entity);
			Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);
			
			double dist = delta.lengthVector();
			
			//check if it's in range
			if(dist > range)
				continue;
			
			//check if we should even fire at this entity
			if(!entityAcceptableTarget(entity))
				continue;
			
			//check for visibility
			if(!entityInLOS(entity))
				continue;
			
			//replace current target if this one is closer
			if(dist < closest) {
				closest = dist;
				target = entity;
			}
		}
		
		this.target = target;
		
		if(target != null)
			this.tPos = this.getEntityPos(this.target);
	}
	
	/**
	 * Turns the turret by a specific amount of degrees towards the target
	 * Assumes that the target is not null
	 */
	protected void alignTurret() {
		this.turnTowards(tPos);
	}
	
	/**
	 * Turns the turret towards the specified position
	 */
	public void turnTowards(Vec3 ent) {
		
		double turnYaw = Math.toRadians(this.getTurretYawSpeed());
		double turnPitch = Math.toRadians(this.getTurretPitchSpeed());
		double pi2 = Math.PI * 2;

		Vec3 pos = this.getTurretPos();
		Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);
		
		double targetPitch = Math.asin(delta.yCoord / delta.lengthVector());
		double targetYaw = -Math.atan2(delta.xCoord, delta.zCoord);
		
		//if we are about to overshoot the target by turning, just snap to the correct rotation
		if(Math.abs(this.rotationPitch - targetPitch) < turnPitch || Math.abs(this.rotationPitch - targetPitch) > pi2 - turnPitch) {
			this.rotationPitch = targetPitch;
		} else {
			
			if(targetPitch > this.rotationPitch)
				this.rotationPitch += turnPitch;
			else
				this.rotationPitch -= turnPitch;
		}
		
		double deltaYaw = (targetYaw - this.rotationYaw) % pi2;
		
		//determines what direction the turret should turn
		//used to prevent situations where the turret would do almost a full turn when
		//the target is only a couple degrees off while being on the other side of the 360° line
		int dir = 0;

		if(deltaYaw < -Math.PI)
			dir = 1;
		else if(deltaYaw < 0)
			dir = -1;
		else if(deltaYaw > Math.PI)
			dir = -1;
		else if(deltaYaw > 0)
			dir = 1;
		
		if(Math.abs(this.rotationYaw - targetYaw) < turnYaw || Math.abs(this.rotationYaw - targetYaw) > pi2 - turnYaw) {
			this.rotationYaw = targetYaw;
		} else {
			this.rotationYaw += turnYaw * dir;
		}
		
		double deltaPitch = targetPitch - this.rotationPitch;
		deltaYaw = targetYaw - this.rotationYaw;
		
		double deltaAngle = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);

		this.rotationYaw = this.rotationYaw % pi2;
		this.rotationPitch = this.rotationPitch % pi2;
		
		if(deltaAngle <= Math.toRadians(this.getAcceptableInaccuracy())) {
			this.aligned = true;
		}
	}
	
	/**
	 * Checks line of sight to the passed entity along with whether the angle falls within swivel range
	 * @return
	 */
	public boolean entityInLOS(Entity e) {
		
		if(e.isDead || !e.isEntityAlive())
			return false;
		
		Vec3 pos = this.getTurretPos();
		Vec3 ent = this.getEntityPos(e);
		Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);
		double length = delta.lengthVector();
		
		if(length < this.getDecetorGrace())
			return false;
		
		delta = delta.normalize();
		double pitch = Math.asin(delta.yCoord / delta.lengthVector());
		double pitchDeg = Math.toDegrees(pitch);
		
		//check if the entity is within swivel range
		if(pitchDeg < -this.getTurretDepression() || pitchDeg > this.getTurretElevation())
			return false;
		
		/*for(double i = 0; i < length; i += 0.25D) {

			double x = pos.xCoord + delta.xCoord * i;
			double y = pos.yCoord + delta.yCoord * i;
			double z = pos.zCoord + delta.zCoord * i;
			
			worldObj.spawnParticle("reddust", x, y, z, 0, 0, 0);
		}
		
		worldObj.spawnParticle("cloud", pos.xCoord, pos.yCoord, pos.zCoord, 0, 0.1, 0);
		worldObj.spawnParticle("flame", ent.xCoord, ent.yCoord, ent.zCoord, 0, 0.1, 0);*/
		
		return !Library.isObstructed(worldObj, ent.xCoord, ent.yCoord, ent.zCoord, pos.xCoord, pos.yCoord, pos.zCoord);
	}
	
	/**
	 * Returns true if the entity is considered for targeting
	 * @return
	 */
	public boolean entityAcceptableTarget(Entity e) {
		
		if(e.isDead || !e.isEntityAlive())
			return false;
		
		if(targetAnimals) {
			
			if(e instanceof IAnimals)
				return true;
			if(e instanceof INpc)
				return true;
		}
		
		if(targetMobs && e instanceof IMob)
			return true;
		
		if(targetMachines) {

			if(e instanceof EntityMissileBaseAdvanced)
				return true;
			if(e instanceof EntityMissileCustom)
				return true;
			if(e instanceof EntityMinecart)
				return true;
			if(e instanceof EntityBomber)
				return true;
		}
		
		if(targetPlayers && e instanceof EntityPlayer) {
			
			if(e instanceof FakePlayer)
				return false;
			
			List<String> wl = getWhitelist();
			
			if(wl == null || wl.isEmpty())
				return true;
			
			return !wl.contains(((EntityPlayer)e).getDisplayName());
		}
		
		return false;
	}
	
	/**
	 * How many degrees the turret can deviate from the target to be acceptable to fire at
	 * @return
	 */
	public double getAcceptableInaccuracy() {
		return 5;
	}
	
	/**
	 * How many degrees the turret can rotate per tick (4.5°/t = 90°/s or a half turn in two seconds)
	 * @return
	 */
	public double getTurretYawSpeed() {
		return 4.5D;
	}
	
	/**
	 * How many degrees the turret can lift per tick (3°/t = 60°/s or roughly the lowest to the highest point of an average turret in one second)
	 * @return
	 */
	public double getTurretPitchSpeed() {
		return 3D;
	}

	/**
	 * Makes turrets sad :'(
	 * @return
	 */
	public double getTurretDepression() {
		return 30D;
	}

	/**
	 * Makes turrets feel privileged
	 * @return
	 */
	public double getTurretElevation() {
		return 30D;
	}
	
	/**
	 * How many ticks until a target rescan is required
	 * @return
	 */
	public int getDecetorInterval() {
		return 10;
	}
	
	/**
	 * How far away an entity can be to be picked up
	 * @return
	 */
	public double getDecetorRange() {
		return 32D;
	}
	
	/**
	 * How far away an entity needs to be to be picked up
	 * @return
	 */
	public double getDecetorGrace() {
		return 3D;
	}
	
	/**
	 * The pivot point of the turret, larger models have a default of 1.5
	 * @return
	 */
	public double getHeightOffset() {
		return 1.5D;
	}
	
	/**
	 * Horizontal offset for the spawn point of bullets
	 * @return
	 */
	public double getBarrelLength() {
		return 1.0D;
	}
	
	/**
	 * The pivot point of the turret, this position is used for LOS calculation and more
	 * @return
	 */
	public Vec3 getTurretPos() {
		Vec3 offset = getHorizontalOffset();
		return Vec3.createVectorHelper(xCoord + offset.xCoord, yCoord + getHeightOffset(), zCoord + offset.zCoord);
	}
	
	/**
	 * The XZ offset for a standard 2x2 turret base
	 * @return
	 */
	public Vec3 getHorizontalOffset() {
		int meta = this.getBlockMetadata() - BlockDummyable.offset;

		if(meta == 2)
			return Vec3.createVectorHelper(1, 0, 1);
		if(meta == 4)
			return Vec3.createVectorHelper(1, 0, 0);
		if(meta == 5)
			return Vec3.createVectorHelper(0, 0, 1);
		
		return Vec3.createVectorHelper(0, 0, 0);
	}
	
	/**
	 * The pivot point of the turret, this position is used for LOS calculation and more
	 * @return
	 */
	public Vec3 getEntityPos(Entity e) {
		return Vec3.createVectorHelper(e.posX, e.posY + e.height * 0.5 - e.getYOffset(), e.posZ);
	}
	
	/**
	 * Yes, new turrets fire BulletNTs.
	 * @return
	 */
	protected abstract List<Integer> getAmmoList();

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}

	public boolean hasPower() {
		return this.getPower() >= this.getConsumption();
	}
	
	public void setPower(long i) {
		this.power = i;
	}
	
	public long getPower() {
		return this.power;
	}
	
	public int getPowerScaled(int scale) {
		return (int)(power * scale / this.getMaxPower());
	}
	
	public long getConsumption() {
		return 100;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
