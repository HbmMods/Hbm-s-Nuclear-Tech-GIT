package com.hbm.tileentity.machine.storage;

import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.Compat;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.energymk2.IEnergyConductorMK2;
import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.energymk2.Nodespace;
import api.hbm.energymk2.Nodespace.PowerNode;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")})
public abstract class TileEntityBatteryBase extends TileEntityMachineBase implements IEnergyConductorMK2, IEnergyProviderMK2, IEnergyReceiverMK2, IControlReceiver, IGUIProvider, SimpleComponent, CompatHandler.OCComponent {

	public byte lastRedstone = 0;
	public long prevPowerState = 0;

	public static final int mode_input = 0;
	public static final int mode_buffer = 1;
	public static final int mode_output = 2;
	public static final int mode_none = 3;
	public short redLow = 0;
	public short redHigh = 2;
	public ConnectionPriority priority = ConnectionPriority.LOW;

	protected PowerNode node;

	public TileEntityBatteryBase(int slotCount) {
		super(slotCount);
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(priority == null || priority.ordinal() == 0 || priority.ordinal() == 4) {
				priority = ConnectionPriority.LOW;
			}

			if(this.node == null || this.node.expired) {
				this.node = (PowerNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, Nodespace.THE_POWER_PROVIDER);

				if(this.node == null || this.node.expired) {
					this.node = this.createNode();
					UniNodespace.createNode(worldObj, this.node);
				}
			}

			if(this.node != null && this.node.hasValidNet()) switch(this.getRelevantMode(false)) {
			case mode_input: this.node.net.removeProvider(this); this.node.net.addReceiver(this); break;
			case mode_output: this.node.net.addProvider(this); this.node.net.removeReceiver(this); break;
			case mode_buffer: this.node.net.addProvider(this); this.node.net.addReceiver(this); break;
			case mode_none: this.node.net.removeProvider(this); this.node.net.removeReceiver(this); break;
			}

			byte comp = this.getComparatorPower();
			if(comp != this.lastRedstone) {
				for(BlockPos port : this.getPortPos()) {
					TileEntity tile = Compat.getTileStandard(worldObj, port.getX(), port.getY(), port.getZ());
					if(tile != null) tile.markDirty();
				}
			}
			this.lastRedstone = comp;

			prevPowerState = this.getPower();

			this.networkPackNT(100);
		}
	}

	public byte getComparatorPower() {
		double frac = (double) this.getPower() / (double) Math.max(this.getMaxPower(), 1) * 15D;
		return (byte) (MathHelper.clamp_int((int) Math.round(frac), 0, 15)); //to combat eventual rounding errors with the FEnSU's stupid maxPower
	}

	@Override
	public PowerNode createNode() {
		return new PowerNode(this.getPortPos()).setConnections(this.getConPos());
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

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.getItem() instanceof IBatteryItem;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeShort(redLow);
		buf.writeShort(redHigh);
		buf.writeByte(priority.ordinal());
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		redLow = buf.readShort();
		redHigh = buf.readShort();
		priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, buf.readByte());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		this.lastRedstone = nbt.getByte("lastRedstone");
		this.priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, nbt.getByte("priority"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setShort("redLow", redLow);
		nbt.setShort("redHigh", redHigh);
		nbt.setByte("lastRedstone", lastRedstone);
		nbt.setByte("priority", (byte) this.priority.ordinal());
	}

	@Override public boolean allowDirectProvision() { return false; }
	@Override public ConnectionPriority getPriority() { return this.priority; }

	public abstract BlockPos[] getPortPos();
	public abstract DirPos[] getConPos();

	private short modeCache = 0;

	public short getRelevantMode(boolean useCache) {
		if(useCache) return this.modeCache;
		boolean powered = false;
		for(BlockPos pos : getPortPos()) if(worldObj.isBlockIndirectlyGettingPowered(pos.getX(), pos.getY(), pos.getZ())) { powered = true; break; }
		this.modeCache = powered ? this.redHigh : this.redLow;
		return this.modeCache;
	}

	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("low")) {
			this.redLow++;
			if(this.redLow > 3) this.redLow = 0;
		}
		if(data.hasKey("high")) {
			this.redHigh++;
			if(this.redHigh > 3) this.redHigh = 0;
		}
		if(data.hasKey("priority")) {
			int ordinal = this.priority.ordinal();
			ordinal++;
			if(ordinal > ConnectionPriority.HIGH.ordinal()) ordinal = ConnectionPriority.LOW.ordinal();
			this.priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, ordinal);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_energy_storage";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getModeInfo(Context context, Arguments args) {
		return new Object[] {redLow, redHigh, getPriority().ordinal()-1};
	}
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setModeLow(Context context, Arguments args) {
		short newMode = (short) args.checkInteger(0);
		if (newMode >= mode_input && newMode <= mode_none) {
			redLow = newMode;
			return new Object[] {};
		} else {
			return new Object[] {"Invalid mode"};
		}
	}
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setModeHigh(Context context, Arguments args) {
		short newMode = (short) args.checkInteger(0);
		if (newMode >= mode_input && newMode <= mode_none) {
			redHigh = newMode;
			return new Object[] {};
		} else {
			return new Object[] {"Invalid mode"};
		}
	}
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setPriority(Context context, Arguments args) {
		int newPriority = args.checkInteger(0);
		if (newPriority >= 0 && newPriority <= 2) {
			priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, newPriority+1);
			return new Object[] {};
		} else {
			return new Object[] {"Invalid mode"};
		}
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower(), redLow, redHigh, getPriority().ordinal()-1};
	}
}
