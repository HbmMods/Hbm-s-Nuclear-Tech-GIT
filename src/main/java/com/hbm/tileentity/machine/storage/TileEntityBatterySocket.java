package com.hbm.tileentity.machine.storage;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import com.hbm.entity.projectile.EntityBulletBeamBase;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.inventory.container.ContainerBatterySocket;
import com.hbm.inventory.gui.GUIBatterySocket;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBatterySC.EnumBatterySC;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.util.BobMathUtil;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.EnumUtil;
import com.hbm.util.Vec3NT;
import com.hbm.util.DamageResistanceHandler.DamageClass;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBatterySocket extends TileEntityBatteryBase implements IRORValueProvider, IRORInteractive, IInfoProviderEC {
	
	public static BulletConfig discharge;
	public static BiConsumer<EntityBulletBeamBase, MovingObjectPosition> BEAM_DISCHARGE_HIT = (beam, mop) -> {
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			beam.worldObj.func_147480_a(mop.blockX, mop.blockY, mop.blockZ, false);
			explodeDischarge(beam.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		}

		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			explodeDischarge(beam.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		}
	};
	
	public static void explodeDischarge(World world, double x, double y, double z) {
		ExplosionVNT vnt = new ExplosionVNT(world, x, y, z, 5F);
		vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 20).setDamageClass(DamageClass.ELECTRIC));
		vnt.setPlayerProcessor(new PlayerProcessorStandard());
		vnt.setSFX(new ExplosionEffectStandard());
		vnt.explode();
		world.playSoundEffect(x, y, z, "hbm:entity.ufoBlast", 5.0F, 0.9F + world.rand.nextFloat() * 0.2F);
	}
	
	static {
		discharge = new BulletConfig().setupDamageClass(DamageClass.ELECTRIC).setBeam().setSpread(0.0F).setLife(3).setThresholdNegation(20F).setArmorPiercing(0.5F).setRenderRotations(false).setDoesPenetrate(true)
				.setOnBeamImpact(BEAM_DISCHARGE_HIT);
	}

	public long[] log = new long[20];
	public long delta = 0;

	public long syncPower = 0;
	public long syncMaxPower = 0;
	public ItemStack syncStack;
	public int damageTimer;
	public int damageTarget;
	public double scPowerMult = 1D;
	
	public TileEntityBatterySocket() {
		super(1);
	}

	@Override public String getName() { return "container.batterySocket"; }

	@Override
	public void updateEntity() {
		long prevPower = this.getPower();

		super.updateEntity();

		if(!worldObj.isRemote) {
			
			if(hasSCLoaded()) {
				if(this.damageTarget == 0) pickNewSCTarget();
				this.damageTimer++;
				if(this.damageTimer >= this.damageTarget) discharge();
				fluctuate();
			}

			long avg = (this.getPower() + prevPower) / 2;
			this.delta = avg - this.log[0];

			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}

			this.log[19] = avg;
		}
	}
	
	protected boolean hasSCLoaded() {
		return slots[0] != null && slots[0].getItem() == ModItems.battery_sc && slots[0].getItemDamage() != EnumBatterySC.EMPTY.ordinal();
	}
	
	protected void pickNewSCTarget() {
		this.damageTimer = 0;
		//this.damageTarget = 100;
		this.damageTarget = 1200 + worldObj.rand.nextInt(2400); // 1-3 minutes;
		this.markChanged();
	}
	
	protected void discharge() {
		pickNewSCTarget();
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		double x = xCoord + 0.5 - dir.offsetX * 0.5 + rot.offsetX * 0.5;
		double y = yCoord + 1;
		double z = zCoord + 0.5 - dir.offsetZ * 0.5 + rot.offsetX * 0.5;

		double range = 15;
		List<EntityLivingBase> potentialTargets = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(range, range, range));
		Collections.shuffle(potentialTargets);

		for(EntityLivingBase target : potentialTargets) {

			Vec3NT initialDelta = new Vec3NT(target.posX - x, target.posY + target.height / 2 - y, target.posZ - z);
			if(initialDelta.lengthVector() > range) continue;
			EntityBulletBeamBase sub = new EntityBulletBeamBase(worldObj, discharge, 50F);
			initialDelta.normalizeSelf();
			double dominantAxis = BobMathUtil.max(Math.abs(initialDelta.xCoord), Math.abs(initialDelta.yCoord), Math.abs(initialDelta.zCoord));
			initialDelta.multiply(1.125D / dominantAxis); // move 1.125 blocks outwards
			sub.setPosition(xCoord + initialDelta.xCoord, yCoord + initialDelta.yCoord, zCoord + initialDelta.zCoord);
			Vec3NT actualDelta = new Vec3NT(target.posX - sub.posX, target.posY + target.height / 2 - sub.posY, target.posZ - sub.posZ);
			
			sub.setRotationsFromVector(actualDelta);
			sub.performHitscanExternal(actualDelta.lengthVector());
			worldObj.spawnEntityInWorld(sub);
		}
		
		explodeDischarge(worldObj, x + worldObj.rand.nextGaussian() * 0.5, y + worldObj.rand.nextGaussian() * 0.5, z + worldObj.rand.nextGaussian() * 0.5);
	}
	
	protected void fluctuate() {
		double steppy = 1D / 100D;
		this.scPowerMult += (steppy * (worldObj.rand.nextDouble() * 2 - 1));
		this.scPowerMult = MathHelper.clamp_double(scPowerMult, 0.1D, 1D);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(delta);
		buf.writeLong(this.getPower());
		buf.writeLong(this.getMaxPower());
		if(this.slots[0] != null) {
			buf.writeInt(Item.getIdFromItem(slots[0].getItem()));
			buf.writeShort(slots[0].getItemDamage());
		} else {
			buf.writeInt(-1);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		delta = buf.readLong();
		syncPower = buf.readLong();
		syncMaxPower = buf.readLong();
		int itemId = buf.readInt();
		if(itemId != -1) this.syncStack = new ItemStack(Item.getItemById(itemId), 1, buf.readShort());
		else this.syncStack = null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.damageTimer = nbt.getInteger("damageTimer");
		this.damageTarget = nbt.getInteger("damageTarget");
		this.scPowerMult = nbt.getDouble("scPowerMult");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("damageTimer", damageTimer);
		nbt.setInteger("damageTarget", damageTarget);
		nbt.setDouble("scPowerMult", scPowerMult);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(stack.getItem() instanceof IBatteryItem) {
			if(i == mode_input && ((IBatteryItem)stack.getItem()).getCharge(stack) == 0) return true;
			if(i == mode_output && ((IBatteryItem)stack.getItem()).getCharge(stack) == ((IBatteryItem)stack.getItem()).getMaxCharge(stack)) return true;
		}
		return false;
	}

	@Override public int[] getAccessibleSlotsFromSide(int side) { return new int[] {0}; }

	@Override public long getPower() {
		long power = powerFromStack(slots[0]);
		if(this.hasSCLoaded()) power *= this.scPowerMult;
		return power;
	}
	@Override public long getMaxPower() { return maxPowerFromStack(slots[0]); }
	
	@Override
	public void setPower(long power) {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return;
		((IBatteryItem) slots[0].getItem()).setCharge(slots[0], power);
	}
	
	public static long powerFromStack(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof IBatteryItem)) return 0;
		return ((IBatteryItem) stack.getItem()).getCharge(stack);
	}
	
	public static long maxPowerFromStack(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof IBatteryItem)) return 0;
		return ((IBatteryItem) stack.getItem()).getMaxCharge(stack);
	}

	@Override public long getProviderSpeed() {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return 0;
		int mode = this.getRelevantMode(true);
		return mode == mode_output || mode == mode_buffer ? ((IBatteryItem) slots[0].getItem()).getDischargeRate(slots[0]) : 0;
	}

	@Override public long getReceiverSpeed() {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return 0;
		int mode = this.getRelevantMode(true);
		return mode == mode_input || mode == mode_buffer ? ((IBatteryItem) slots[0].getItem()).getChargeRate(slots[0]) : 0;
	}

	@Override
	public BlockPos[] getPortPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new BlockPos[] {
				new BlockPos(xCoord, yCoord, zCoord),
				new BlockPos(xCoord - dir.offsetX, yCoord, zCoord - dir.offsetZ),
				new BlockPos(xCoord + rot.offsetX, yCoord, zCoord + rot.offsetZ),
				new BlockPos(xCoord - dir.offsetX + rot.offsetX, yCoord, zCoord - dir.offsetZ + rot.offsetZ)
		};
	}

	@Override
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX + rot.offsetX, yCoord, zCoord + dir.offsetZ + rot.offsetZ, dir),

				new DirPos(xCoord - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),

				new DirPos(xCoord + rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2, rot),
				new DirPos(xCoord + rot.offsetX * 2 - dir.offsetX, yCoord, zCoord + rot.offsetZ * 2 - dir.offsetZ, rot),

				new DirPos(xCoord - rot.offsetX, yCoord, zCoord - rot.offsetZ, rot.getOpposite()),
				new DirPos(xCoord - rot.offsetX - dir.offsetX, yCoord, zCoord - rot.offsetZ - dir.offsetZ, rot.getOpposite())
		};
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerBatterySocket(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIBatterySocket(player.inventory, this); }

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
					);
		}

		return bb;
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_VALUE + "fill",
				PREFIX_VALUE + "fillpercent",
				PREFIX_VALUE + "delta",
				PREFIX_FUNCTION + "setmode" + NAME_SEPARATOR + "mode",
				PREFIX_FUNCTION + "setmode" + NAME_SEPARATOR + "mode" + PARAM_SEPARATOR + "fallback",
				PREFIX_FUNCTION + "setredmode" + NAME_SEPARATOR + "mode",
				PREFIX_FUNCTION + "setredmode" + NAME_SEPARATOR + "mode" + PARAM_SEPARATOR + "fallback",
				PREFIX_FUNCTION + "setpriority" + NAME_SEPARATOR + "priority",
		};
	}

	@Override
	public String provideRORValue(String name) {
		if((PREFIX_VALUE + "fill").equals(name))		return "" + this.getPower();
		if((PREFIX_VALUE + "fillpercent").equals(name))	return "" + this.getPower() * 100 / (Math.max(this.getMaxPower(), 1));
		if((PREFIX_VALUE + "delta").equals(name))		return "" + delta;
		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {

		if((PREFIX_FUNCTION + "setmode").equals(name) && params.length > 0) {
			int mode = IRORInteractive.parseInt(params[0], 0, 3);

			if(mode != this.redLow) {
				this.redLow = (short) mode;
				this.markChanged();
				return null;
			} else if(params.length > 1) {
				int altmode = IRORInteractive.parseInt(params[1], 0, 3);
				this.redLow = (short) altmode;
				this.markChanged();
				return null;
			}
			return null;
		}

		if((PREFIX_FUNCTION + "setredmode").equals(name) && params.length > 0) {
			int mode = IRORInteractive.parseInt(params[0], 0, 3);

			if(mode != this.redHigh) {
				this.redHigh = (short) mode;
				this.markChanged();
				return null;
			} else if(params.length > 1) {
				int altmode = IRORInteractive.parseInt(params[1], 0, 3);
				this.redHigh = (short) altmode;
				this.markChanged();
				return null;
			}
			return null;
		}

		if((PREFIX_FUNCTION + "setpriority").equals(name) && params.length > 0) {
			int priority = IRORInteractive.parseInt(params[0], 0, 2) + 1;
			ConnectionPriority p = EnumUtil.grabEnumSafely(ConnectionPriority.class, priority);
			this.priority = p;
			this.markChanged();
			return null;
		}
		return null;
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setLong(CompatEnergyControl.L_DIFF_HE, (log[0] - log[19]) / 20L);
	}

	// do some opencomputer stuff
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower(), this.delta};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPackInfo(Context context, Arguments args) {
		if(slots[0] == null || !(slots[0].getItem() instanceof IBatteryItem)) return new Object[] {"", 0, 0};
		IBatteryItem bat = ((IBatteryItem) slots[0].getItem());
		return new Object[] {slots[0].getUnlocalizedName(), bat.getChargeRate(slots[0]), bat.getDischargeRate(slots[0])};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		Object[] energyInfo = getEnergyInfo(context, args);
		Object[] packInfo = getPackInfo(context, args);
		Object[] modeInfo = getModeInfo(context, args);
		return new Object[] {energyInfo[0], energyInfo[1], energyInfo[2], modeInfo[0], modeInfo[1], modeInfo[2], packInfo[0], packInfo[1], packInfo[2]};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
			"getEnergyInfo",
			"getPackInfo",
			"getModeInfo",
			"setModeLow",
			"setModeHigh",
			"setPriority",
			"getInfo"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch (method) {
			case "getEnergyInfo": return getEnergyInfo(context, args);
			case "getPackInfo": return getPackInfo(context, args);
			case "getModeInfo": return getModeInfo(context, args);
			case "setModeLow": return setModeLow(context, args);
			case "setModeHigh": return setModeHigh(context, args);
			case "setPriority": return setPriority(context, args);
			case "getInfo": return getInfo(context, args);
		}
		throw new NoSuchMethodException();
	}
}
