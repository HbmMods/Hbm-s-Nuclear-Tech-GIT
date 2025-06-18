package com.hbm.tileentity.machine.storage;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.energymk2.IEnergyConductorMK2;
import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.energymk2.Nodespace;
import api.hbm.energymk2.Nodespace.PowerNode;
import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import api.hbm.tile.IInfoProviderEC;

import com.hbm.blocks.machine.MachineBattery;
import com.hbm.handler.CompatHandler;
import com.hbm.inventory.container.ContainerMachineBattery;
import com.hbm.inventory.gui.GUIMachineBattery;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")})
public class TileEntityMachineBattery extends TileEntityMachineBase implements IEnergyConductorMK2, IEnergyProviderMK2, IEnergyReceiverMK2, IPersistentNBT, SimpleComponent, IGUIProvider, IInfoProviderEC, CompatHandler.OCComponent, IRORValueProvider, IRORInteractive {

	public long[] log = new long[20];
	public long delta = 0;
	public long power = 0;
	public long prevPowerState = 0;

	protected PowerNode node;

	//0: input only
	//1: buffer
	//2: output only
	//3: nothing
	public static final int mode_input = 0;
	public static final int mode_buffer = 1;
	public static final int mode_output = 2;
	public static final int mode_none = 3;
	public short redLow = 0;
	public short redHigh = 2;
	public ConnectionPriority priority = ConnectionPriority.LOW;

	//public boolean conducts = false;
	public byte lastRedstone = 0;

	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {0, 1};
	private static final int[] slots_side = new int[] {1};

	private String customName;

	public TileEntityMachineBattery() {
		super(2);
		slots = new ItemStack[2];
	}

	@Override
	public String getName() {
		return "container.battery";
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : getName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
		markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {

		switch(i) {
		case 0:
		case 1:
			if(stack.getItem() instanceof IBatteryItem) return true;
			break;
		}

		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		this.lastRedstone = nbt.getByte("lastRedstone");
		this.priority = ConnectionPriority.values()[nbt.getByte("priority")];

		customName = nbt.getString("name");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		nbt.setShort("redLow", redLow);
		nbt.setShort("redHigh", redHigh);
		nbt.setByte("lastRedstone", lastRedstone);
		nbt.setByte("priority", (byte)this.priority.ordinal());
		
		if (customName != null) {
			nbt.setString("name", customName);
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {

		if(itemStack.getItem() instanceof IBatteryItem) {
			if(i == 0 && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == 0) {
				return true;
			}
			if(i == 1 && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == ((IBatteryItem)itemStack.getItem()).getMaxCharge(itemStack)) {
				return true;
			}
		}

		return false;
	}

	public long getPowerRemainingScaled(long i) {
		return (power * i) / this.getMaxPower();
	}

	public byte getComparatorPower() {
		if(power == 0) return 0;
		double frac = (double) this.power / (double) this.getMaxPower() * 15D;
		return (byte) (MathHelper.clamp_int((int) frac + 1, 0, 15)); //to combat eventual rounding errors with the FEnSU's stupid maxPower
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote && worldObj.getBlock(xCoord, yCoord, zCoord) instanceof MachineBattery) {

			if(priority == null || priority.ordinal() == 0 || priority.ordinal() == 4) {
				priority = ConnectionPriority.LOW;
			}

			int mode = this.getRelevantMode(false);

			long prevPower = this.power;

			power = Library.chargeItemsFromTE(slots, 1, power, getMaxPower());

			// In buffer mode, becomes a cable block and provides power to itself
			// otherwise, acts like a regular power providing/accepting machine
			if(mode == mode_buffer) {
				if(this.node == null || this.node.expired) {

					this.node = (PowerNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, Nodespace.THE_POWER_PROVIDER);

					if(this.node == null || this.node.expired) {
						this.node = this.createNode();
						UniNodespace.createNode(worldObj, this.node);
					}
				}

				this.tryProvide(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UNKNOWN);
				if(node != null && node.hasValidNet()) node.net.addReceiver(this);
			} else {
				if(this.node != null) {
					UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, Nodespace.THE_POWER_PROVIDER);
					this.node = null;
				}

				for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
					PowerNode dirNode = (PowerNode) UniNodespace.getNode(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, Nodespace.THE_POWER_PROVIDER);

					if(mode == mode_output) {
						tryProvide(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
					} else {
						if(dirNode != null && dirNode.hasValidNet()) dirNode.net.removeProvider(this);
					}

					if(mode == mode_input) {
						if(dirNode != null && dirNode.hasValidNet()) dirNode.net.addReceiver(this);
					} else {
						if(dirNode != null && dirNode.hasValidNet()) dirNode.net.removeReceiver(this);
					}
				}
			}

			byte comp = this.getComparatorPower();
			if(comp != this.lastRedstone)
				this.markDirty();
			this.lastRedstone = comp;

			power = Library.chargeTEFromItems(slots, 0, power, getMaxPower());

			long avg = (power + prevPower) / 2;
			this.delta = avg - this.log[0];

			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}

			this.log[19] = avg;

			prevPowerState = power;

			this.networkPackNT(20);
		}
	}

	public void onNodeDestroyedCallback() {
		this.node = null;
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.node != null) {
				UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, Nodespace.THE_POWER_PROVIDER);
			}
		}
	}

	@Override public long getProviderSpeed() {
		int mode = this.getRelevantMode(true);
		return mode == mode_output || mode == mode_buffer ? this.getMaxPower() / 600 : 0;
	}

	@Override public long getReceiverSpeed() {
		int mode = this.getRelevantMode(true);
		return mode == mode_input || mode == mode_buffer ? this.getMaxPower() / 200 : 0;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeLong(power);
		buf.writeLong(delta);
		buf.writeShort(redLow);
		buf.writeShort(redHigh);
		buf.writeByte(priority.ordinal());
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		power = buf.readLong();
		delta = buf.readLong();
		redLow = buf.readShort();
		redHigh = buf.readShort();
		priority = ConnectionPriority.values()[buf.readByte()];
	}

	@Override
	public long getPower() {
		return power;
	}

	private short modeCache = 0;
	public short getRelevantMode(boolean useCache) {
		if(useCache) return this.modeCache;
		this.modeCache = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) ? this.redHigh : this.redLow;
		return this.modeCache;
	}

	private long bufferedMax;

	@Override
	public long getMaxPower() {

		if(bufferedMax == 0) {
			bufferedMax = ((MachineBattery)worldObj.getBlock(xCoord, yCoord, zCoord)).maxPower;
		}

		return bufferedMax;
	}

	@Override public boolean canConnect(ForgeDirection dir) { return true; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public ConnectionPriority getPriority() { return this.priority; }

	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_energy_storage"; //ok if someone else can figure out how to do this that'd be nice (change the component name based on the type of storage block)
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setLong("prevPowerState", prevPowerState);
		data.setShort("redLow", redLow);
		data.setShort("redHigh", redHigh);
		data.setInteger("priority", this.priority.ordinal());
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		this.power = data.getLong("power");
		this.prevPowerState = data.getLong("prevPowerState");
		this.redLow = data.getShort("redLow");
		this.redHigh = data.getShort("redHigh");
		this.priority = ConnectionPriority.values()[data.getInteger("priority")];
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineBattery(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineBattery(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setLong(CompatEnergyControl.L_DIFF_HE, (log[0] - log[19]) / 20L);
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
		if((PREFIX_VALUE + "fill").equals(name))		return "" + power;
		if((PREFIX_VALUE + "fillpercent").equals(name))	return "" + getPowerRemainingScaled(100);
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
}
