package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityArtilleryShell;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.inventory.gui.GUITurretArty;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemAmmoArty;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretArty extends TileEntityTurretBaseArtillery implements IGUIProvider {

	public short mode = 0;
	public static final short MODE_ARTILLERY = 0;
	public static final short MODE_CANNON = 1;
	public static final short MODE_MANUAL = 2;
	private boolean didJustShoot = false;
	private boolean retracting = false;
	public double barrelPos = 0;
	public double lastBarrelPos = 0;

	@Override
	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {

		if(ammoStacks != null)
			return ammoStacks;

		ammoStacks = new ArrayList();

		List list = new ArrayList();
		ModItems.ammo_arty.getSubItems(ModItems.ammo_arty, MainRegistry.weaponTab, list);
		this.ammoStacks.addAll(list);

		return ammoStacks;
	}

	@Override
	protected List<Integer> getAmmoList() {
		return new ArrayList();
	}

	@Override
	public String getName() {
		return "container.turretArty";
	}

	@Override
	public long getMaxPower() {
		return 100000;
	}

	@Override
	public double getBarrelLength() {
		return 9D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 0;
	}

	@Override
	public double getHeightOffset() {
		return 3D;
	}

	@Override
	public double getDecetorRange() {
		return this.mode == this.MODE_CANNON ? 250D : 3000D;
	}

	@Override
	public double getDecetorGrace() {
		return this.mode == this.MODE_CANNON ? 32D : 250D;
	}

	@Override
	public double getTurretYawSpeed() {
		return 1D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 0.5D;
	}

	@Override
	public double getTurretDepression() {
		return 30D;
	}

	@Override
	public double getTurretElevation() {
		return 90D;
	}

	@Override
	public int getDecetorInterval() {
		return mode == MODE_CANNON ? 20 : 200;
	}

	@Override
	public boolean doLOSCheck() {
		return this.mode == this.MODE_CANNON;
	}

	@Override
	protected void alignTurret() {

		Vec3 pos = this.getTurretPos();

		Vec3 barrel = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
		barrel.rotateAroundZ((float) -this.rotationPitch);
		barrel.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		/*
		 * This is done to compensate for the barrel length, as this small deviation has a huge impact in both modes at longer ranges.
		 * The consequence of this is that using the >before< angle of the barrel as an approximation can lead to problems at closer range,
		 * as the math tries to properly calculate the >after< angle. This should not be a problem due to the detector grace distance being
		 * rather high, but it is still important to note.
		 */
		pos.xCoord += barrel.xCoord;
		pos.yCoord += barrel.yCoord;
		pos.zCoord += barrel.zCoord;

		Vec3 delta = Vec3.createVectorHelper(tPos.xCoord - pos.xCoord, tPos.yCoord - pos.yCoord, tPos.zCoord - pos.zCoord);
		double targetYaw = -Math.atan2(delta.xCoord, delta.zCoord);

		double x = Math.sqrt(delta.xCoord * delta.xCoord + delta.zCoord * delta.zCoord);
		double y = delta.yCoord;
		double v0 = getV0();
		double v02 = v0 * v0;
		double g = 9.81 * 0.05;
		double upperLower = mode == MODE_CANNON ? -1 : 1;
		double targetPitch = Math.atan((v02 + Math.sqrt(v02*v02 - g*(g*x*x + 2*y*v02)) * upperLower) / (g*x));

		this.turnTowardsAngle(targetPitch, targetYaw);
	}

	public double getV0() {
		return mode == MODE_CANNON ? 20D : 50D;
	}

	public ItemStack getShellLoaded() {

		for(int i = 1; i < 10; i++) {
			if(slots[i] != null) {
				if(slots[i].getItem() == ModItems.ammo_arty) {
					return slots[i];
				}
			}
		}

		return null;
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

	public void spawnShell(ItemStack type) {

		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));

		EntityArtilleryShell proj = new EntityArtilleryShell(worldObj);
		proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, 0.0F, 0.0F);
		proj.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, (float) getV0(), 0.0F);
		proj.setTarget((int) tPos.xCoord, (int) tPos.yCoord, (int) tPos.zCoord);
		proj.setType(type.getItemDamage());

		if(type.getItemDamage() == 8 && type.hasTagCompound()) {
			NBTTagCompound cargo = type.stackTagCompound.getCompoundTag("cargo");

			if(cargo != null) {
				proj.setCargo(ItemStack.loadItemStackFromNBT(cargo));
			}
		}

		if(this.mode != this.MODE_CANNON)
			proj.setWhistle(true);

		worldObj.spawnEntityInWorld(proj);

		casingDelay = this.casingDelay();
	}

	@Override
	public int casingDelay() {
		return 7;
	}

	@Override
	public void updateEntity() {

		if(worldObj.isRemote) {
			this.lastBarrelPos = this.barrelPos;

			if(this.retracting) {
				this.barrelPos += 0.5;

				if(this.barrelPos >= 1) {
					this.retracting = false;
				}

			} else {
				this.barrelPos -= 0.05;
				if(this.barrelPos < 0) {
					this.barrelPos = 0;
				}
			}
			this.lastRotationPitch = this.rotationPitch;
			this.lastRotationYaw = this.rotationYaw;
			this.rotationPitch = this.syncRotationPitch;
			this.rotationYaw = this.syncRotationYaw;
		}

		if(!worldObj.isRemote) {
			if(this.mode == this.MODE_MANUAL) {
				if(!this.targetQueue.isEmpty()) {
					this.tPos = this.targetQueue.get(0);
				}
			} else {
				this.targetQueue.clear();
			}

			this.aligned = false;

			this.updateConnections();

			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.stattrak++;
			}

			if(target != null && this.mode != this.MODE_MANUAL) {
				if(!this.entityInLOS(this.target)) {
					this.target = null;
				}
			}

			if(target != null) {
				this.tPos = this.getEntityPos(target);
			} else {
				if(this.mode != this.MODE_MANUAL) {
					this.tPos = null;
				}
			}

			if(isOn() && hasPower()) {

				if(tPos != null)
					this.alignTurret();
			} else {
				this.target = null;
				this.tPos = null;
			}

			if(!isOn()) this.targetQueue.clear();

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

					if(this.target == null && this.mode != this.MODE_MANUAL)
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

			this.didJustShoot = false;

			if(casingDelay > 0) {
				casingDelay--;
			} else {
				spawnCasing();
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

	int timer;

	@Override
	public void updateFiringTick() {

		timer++;

		int delay = mode == MODE_ARTILLERY ? 300 : 40;

		if(timer % delay == 0) {

			ItemStack conf = this.getShellLoaded();

			if(conf != null) {
				cachedCasingConfig = ItemAmmoArty.itemTypes[conf.getItemDamage()].casing;
				this.spawnShell(conf);
				this.conusmeAmmo(ModItems.ammo_arty);
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.jeremy_fire", 25.0F, 1.0F);
				Vec3 pos = this.getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				this.didJustShoot = true;

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 0F);
				data.setByte("count", (byte)5);
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			}

			if(this.mode == this.MODE_MANUAL && !this.targetQueue.isEmpty()) {
				this.targetQueue.remove(0);
				this.tPos = null;
			}
		}
	}

	protected static CasingEjector ejector = new CasingEjector().setMotion(0, 0.6, -1).setAngleRange(0.1F, 0.1F);

	@Override
	protected CasingEjector getEjector() {
		return ejector;
	}

	@Override
	protected Vec3 getCasingSpawnPos() {
		return this.getTurretPos();
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		if(meta == 5) {
			this.mode++;
			if(this.mode > 2)
				this.mode = 0;

			this.tPos = null;
			this.targetQueue.clear();

		} else{
			super.handleButtonPacket(value, meta);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeShort(this.mode);
		buf.writeBoolean(this.didJustShoot);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.mode = buf.readShort();
		this.retracting = buf.readBoolean();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.mode = nbt.getShort("mode");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setShort("mode", this.mode);
	}

	@Override
	protected void spawnCasing() {

		if(cachedCasingConfig == null) return;
		CasingEjector ej = getEjector();

		Vec3 spawn = this.getCasingSpawnPos();
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "casing");
		data.setFloat("pitch", (float) 0);
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

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretArty(player.inventory, this);
	}
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] addCoords(Context context, Arguments args) {
		this.mode = MODE_MANUAL;
		if(Math.sqrt(Math.pow(xCoord - args.checkDouble(0), 2)+Math.pow(yCoord - args.checkDouble(1), 2)+Math.pow(zCoord - args.checkDouble(2), 2)) >= this.getDecetorRange()) // check distance against range
			return new Object[] {false};
		targetQueue.add(Vec3.createVectorHelper(args.checkDouble(0), args.checkDouble(1), args.checkDouble(2)));
		return new Object[] {true};
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
				"isAligned",
				"getCurrentTarget",
				"getTargetDistance",
				"addCoords"
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
			case "getCurrentTarget":
				return getCurrentTarget(context, args);
			case "getTargetDistance":
				return getTargetDistance(context, args);
			case "addCoords":
				return addCoords(context, args);
		}
		throw new NoSuchMethodException();
	}

}
