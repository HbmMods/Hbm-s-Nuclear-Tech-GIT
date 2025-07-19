package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import com.hbm.blocks.BlockDummyable;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.missile.EntityMissileBaseNT;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.train.EntityRailCarBase;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.CompatHandler;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemTurretBiometry;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.lib.Library;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.particle.SpentCasing;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.CompatExternal;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.entity.IRadarDetectableNT;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * More over-engineered than ever, but chopping this thing into the smallest possible pieces makes it easier for my demented brain to comprehend
 * @author hbm
 *
 */
@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public abstract class TileEntityTurretBaseNT extends TileEntityMachineBase implements IEnergyReceiverMK2, IControlReceiver, IGUIProvider, SimpleComponent, CompatHandler.OCComponent {

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
	//only used by clients for approach
	public double syncRotationYaw;
	public double syncRotationPitch;
	protected int turnProgress;
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
	public int casingDelay;
	protected SpentCasing cachedCasingConfig = null;

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
			this.rotationPitch = this.syncRotationPitch;
			this.rotationYaw = this.syncRotationYaw;
		}

		if(!worldObj.isRemote) {

			this.aligned = false;
			this.updateConnections();

			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.stattrak++;
			}

			if(target != null) {
				if(!this.entityInLOS(this.target)) {
					this.target = null;
				}
			}

			if(target != null) {
				this.tPos = this.getEntityPos(target);
			} else {
				this.tPos = null;
			}

			if(isOn() && hasPower()) {

				if(tPos != null)
					this.alignTurret();
			} else {

				this.target = null;
				this.tPos = null;
			}

			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.tPos = null;
				this.stattrak++;
			}

			if(isOn() && hasPower()) {
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

			this.networkPackNT(250);

			if(usesCasings() && this.casingDelay() > 0) {
				if(casingDelay > 0) {
					casingDelay--;
				} else {
					spawnCasing();
				}
			}

		} else {

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
	public void serialize(ByteBuf buf) {
		BufferUtil.writeVec3(buf, this.tPos);
		buf.writeDouble(this.rotationPitch);
		buf.writeDouble(this.rotationYaw);
		buf.writeLong(this.power);
		buf.writeBoolean(this.isOn);
		buf.writeBoolean(this.targetPlayers);
		buf.writeBoolean(this.targetAnimals);
		buf.writeBoolean(this.targetMobs);
		buf.writeBoolean(this.targetMachines);
		buf.writeInt(this.stattrak);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.turnProgress = 2;
		this.tPos = BufferUtil.readVec3(buf);
		this.syncRotationPitch = buf.readDouble();
		this.syncRotationYaw = buf.readDouble();
		this.power = buf.readLong();
		this.isOn = buf.readBoolean();
		this.targetPlayers = buf.readBoolean();
		this.targetAnimals = buf.readBoolean();
		this.targetMobs = buf.readBoolean();
		this.targetMachines = buf.readBoolean();
		this.stattrak = buf.readInt();
	}

	protected void updateConnections() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		//how did i even make this? what???
		this.trySubscribe(worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * 0, dir.getOpposite());
		this.trySubscribe(worldObj, xCoord + dir.offsetX * -1 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * -1 + rot.offsetZ * -1, dir.getOpposite());

		this.trySubscribe(worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * -2, rot.getOpposite());
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * -2, rot.getOpposite());

		this.trySubscribe(worldObj, xCoord + dir.offsetX * 0 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 0 + rot.offsetZ * 1, rot);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 1 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * 1, rot);

		this.trySubscribe(worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 0, dir);
		this.trySubscribe(worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * -1, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * -1, dir);
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

	public boolean usesCasings() { return false; }
	public int casingDelay() { return 0; }

	public BulletConfig getFirstConfigLoaded() {

		List<Integer> list = getAmmoList();

		if(list == null || list.isEmpty())
			return null;

		//doing it like this will fire slots in the right order, not in the order of the configs
		//you know, the weird thing the IItemGunBase does
		for(int i = 1; i < 10; i++) {

			if(slots[i] != null) {

				for(Integer c : list) { //we can afford all this extra iteration trash on the count that a turret has at most like 4 bullet configs

					BulletConfig conf = BulletConfig.configs.get(c);
					if(conf.ammo != null && conf.ammo.matchesRecipe(slots[i], true)) return conf;
				}
			}
		}

		return null;
	}

	public void spawnBullet(BulletConfig bullet, float baseDamage) {

		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));

		EntityBulletBaseMK4 proj = new EntityBulletBaseMK4(worldObj, bullet, baseDamage, bullet.spread, (float) rotationYaw, (float) rotationPitch);
		proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, proj.rotationYaw, proj.rotationPitch);
		worldObj.spawnEntityInWorld(proj);

		if(usesCasings()) {
			if(this.casingDelay() == 0) {
				spawnCasing();
			} else {
				casingDelay = this.casingDelay();
			}
		}
	}

	public void conusmeAmmo(ComparableStack ammo) {

		for(int i = 1; i < 10; i++) {

			if(slots[i] != null && ammo.matchesRecipe(slots[i], true)) {

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

		Vec3 pos = this.getTurretPos();
		Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);

		double targetPitch = Math.asin(delta.yCoord / delta.lengthVector());
		double targetYaw = -Math.atan2(delta.xCoord, delta.zCoord);

		this.turnTowardsAngle(targetPitch, targetYaw);
	}

	public void turnTowardsAngle(double targetPitch, double targetYaw) {

		double turnYaw = Math.toRadians(this.getTurretYawSpeed());
		double turnPitch = Math.toRadians(this.getTurretPitchSpeed());
		double pi2 = Math.PI * 2;

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

		if(!hasThermalVision() && e instanceof EntityLivingBase && ((EntityLivingBase)e).isPotionActive(Potion.invisibility))
			return false;

		Vec3 pos = this.getTurretPos();
		Vec3 ent = this.getEntityPos(e);
		Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);
		double length = delta.lengthVector();

		if(length < this.getDecetorGrace() || length > this.getDecetorRange() * 1.1) //the latter statement is only relevant for entities that have already been detected
			return false;

		delta = delta.normalize();
		double pitch = Math.asin(delta.yCoord / delta.lengthVector());
		double pitchDeg = Math.toDegrees(pitch);

		//check if the entity is within swivel range
		if(pitchDeg < -this.getTurretDepression() || pitchDeg > this.getTurretElevation())
			return false;

		return !Library.isObstructedOpaque(worldObj, ent.xCoord, ent.yCoord, ent.zCoord, pos.xCoord, pos.yCoord, pos.zCoord);
	}

	/**
	 * Returns true if the entity is considered for targeting
	 * @return
	 */
	public boolean entityAcceptableTarget(Entity e) {

		if(e.isDead || !e.isEntityAlive())
			return false;

		for(Class c : CompatExternal.turretTargetBlacklist) if(c.isAssignableFrom(e.getClass())) return false;

		for(Class c : CompatExternal.turretTargetCondition.keySet()) {
			if(c.isAssignableFrom(e.getClass())) {
				BiFunction<Entity, Object, Integer> lambda = CompatExternal.turretTargetCondition.get(c);
				if(lambda != null) {
					int result = lambda.apply(e, this);
					if(result == -1) return false;
					if(result == 1) return true;
				}
			}
		}

		List<String> wl = getWhitelist();

		if(wl != null) {

			if(e instanceof EntityPlayer) {
				if(wl.contains(((EntityPlayer)e).getDisplayName())) {
					return false;
				}
			} else if(e instanceof EntityLiving) {
				if(wl.contains(((EntityLiving)e).getCustomNameTag())) {
					return false;
				}
			}
		}

		if(targetAnimals) {

			if(e instanceof IAnimals) return true;
			if(e instanceof INpc) return true;
			for(Class c : CompatExternal.turretTargetFriendly) if(c.isAssignableFrom(e.getClass())) return true;
		}

		if(targetMobs) {

			//never target the ender dragon directly
			if(e instanceof EntityDragon) return false;
			if(e instanceof EntityDragonPart) return true;
			if(e instanceof IMob) return true;
			for(Class c : CompatExternal.turretTargetHostile) if(c.isAssignableFrom(e.getClass())) return true;
		}

		if(targetMachines) {

			if(e instanceof IRadarDetectableNT && !((IRadarDetectableNT)e).canBeSeenBy(this)) return false;
			if(e instanceof EntityMissileBaseNT) return e.motionY < 0;
			if(e instanceof EntityMissileCustom) return e.motionY < 0;
			if(e instanceof EntityMinecart) return true;
			if(e instanceof EntityRailCarBase) return true;
			if(e instanceof EntityBomber) return true;
			for(Class c : CompatExternal.turretTargetMachine) if(c.isAssignableFrom(e.getClass())) return true;
		}

		if(targetPlayers ) {

			if(e instanceof FakePlayer) return false;
			if(e instanceof EntityPlayer) return true;
			for(Class c : CompatExternal.turretTargetPlayer) if(c.isAssignableFrom(e.getClass())) return true;
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
	 * Whether the turret can detect invisible targets or not
	 * @return
	 */
	public boolean hasThermalVision() {
		return true;
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

	@SideOnly(Side.CLIENT)
	protected List<ItemStack> ammoStacks;

	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {

		if(ammoStacks != null)
			return ammoStacks;

		ammoStacks = new ArrayList();

		for(Integer i : getAmmoList()) {
			BulletConfig config = BulletConfig.configs.get(i);

			if(config != null && config.ammo != null) {
				ammoStacks.add(config.ammo.toStack());
			}
		}

		return ammoStacks;
	}

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

	public boolean isOn() {
		return this.isOn;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
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
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void openInventory() {
		this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.openC", 1.0F, 1.0F);
	}

	@Override
	public void closeInventory() {
		this.worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:block.closeC", 1.0F, 1.0F);
	}

	protected Vec3 getCasingSpawnPos() {
		return this.getTurretPos();
	}

	protected CasingEjector getEjector() {
		return null;
	}

	protected void spawnCasing() {

		if(cachedCasingConfig == null) return;
		CasingEjector ej = getEjector();

		Vec3 spawn = this.getCasingSpawnPos();
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "casing");
		data.setFloat("pitch", (float) -rotationPitch);
		data.setFloat("yaw", (float) rotationYaw);
		data.setBoolean("crouched", false);
		data.setString("name", cachedCasingConfig.getName());
		if(ej != null) data.setInteger("ej", ej.getId());
		PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, spawn.xCoord, spawn.yCoord, spawn.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));

		cachedCasingConfig = null;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTurretBase(player.inventory, this);
	}

	// OC stuff
	// This is a large compat, so I have to leave comments to know what I'm doing

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_turret";
	}

	// On/Off
	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setActive(Context context, Arguments args) {
		this.isOn = args.checkBoolean(0);
		return new Object[] {};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] isActive(Context context, Arguments args) {
		return new Object[] {this.isOn};
	}

	// Energy information
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {this.getPower(), this.getMaxPower()};
	}

	///////////////////////
	// Whitelist Control //
	///////////////////////
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getWhitelisted(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() == ModItems.turret_chip) {
			String[] array = ItemTurretBiometry.getNames(slots[0]);
			return new Object[] {array};
		}
		return new Object[] {};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] addWhitelist(Context context, Arguments args) {
		if(this.getWhitelist() != null) {
			List<String> names = this.getWhitelist();
			if (names.contains(args.checkString(0)))
				return new Object[]{false};
		}
		this.addName(args.checkString(0));
		return new Object[]{true};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] removeWhitelist(Context context, Arguments args) {
		List<String> names = this.getWhitelist();
		if(!names.contains(args.checkString(0)))
			return new Object[] {false};
		this.removeName(names.indexOf(args.checkString(0)));
		return new Object[] {true};
	}

	///////////////////////
	// Targeting Control //
	///////////////////////
	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setTargeting(Context context, Arguments args) {
		this.targetPlayers = args.checkBoolean(0);
		this.targetAnimals = args.checkBoolean(1);
		this.targetMobs = args.checkBoolean(2);
		this.targetMachines = args.checkBoolean(3);
		return new Object[] {};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTargeting(Context context, Arguments args) {
		return new Object[] {this.targetPlayers, this.targetAnimals, this.targetMobs, this.targetMachines};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] hasTarget(Context context, Arguments args) {
		return new Object[] {this.target != null};
	}

	///////////////////
	// Angle Control //
	///////////////////

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getAngle(Context context, Arguments args) {
		return new Object[] {Math.toDegrees(this.rotationPitch), Math.toDegrees(this.rotationYaw)};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] isAligned(Context context, Arguments args) {
		return new Object[] {this.aligned};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public boolean canConnectNode(ForgeDirection side) {
		return side == ForgeDirection.DOWN;
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() { // :vomit:
		return new String[] {
				"setActive",
				"isActive",
				"getEnergyInfo",
				"getWhitelisted",
				"addWhitelist",
				"removeWhitelist",
				"setTargeting",
				"getTargeting",
				"hasTarget",
				"getAngle",
				"isAligned"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch (method) {
			case "setActive":
				return setActive(context, args);
			case "isActive":
				return isActive(context, args);
			case "getEnergyInfo":
				return getEnergyInfo(context, args);
			case "getWhitelisted":
				return getWhitelisted(context, args);
			case "addWhitelist":
				return addWhitelist(context, args);
			case "removeWhitelist":
				return removeWhitelist(context, args);
			case "setTargeting":
				return setTargeting(context, args);
			case "getTargeting":
				return getTargeting(context, args);
			case "hasTarget":
				return hasTarget(context, args);
			case "getAngle":
				return getAngle(context, args);
			case "isAligned":
				return isAligned(context, args);
		}
		throw new NoSuchMethodException();
	}
}
