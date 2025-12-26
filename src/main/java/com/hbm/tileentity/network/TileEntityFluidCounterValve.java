package com.hbm.tileentity.network;

import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import com.hbm.handler.CompatHandler;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.uninos.UniNodespace;
import cpw.mods.fml.common.Optional;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityFluidCounterValve extends TileEntityPipeBaseNT implements SimpleComponent, CompatHandler.OCComponent, IRORValueProvider, IRORInteractive {
	private long counter;

	@Override
	public void updateEntity() {
		super.updateEntity();

		if(!worldObj.isRemote) {
			if(node != null && node.net != null && getType() != Fluids.NONE) {
				counter += node.net.fluidTracker;
			}

			networkPackNT(25);
		}
	}

	@Override
	public boolean shouldCreateNode() {
		return this.getBlockMetadata() == 1;
	}

	public void updateState() {
		this.blockMetadata = -1; // delete cache

		if(this.getBlockMetadata() == 0 && this.node != null) {
			UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, this.getType().getNetworkProvider());
			this.node = null;
		}
	}

	@Override
	public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
		return oldBlock != newBlock;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		counter = nbt.getLong("counter");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("counter", counter);
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeLong(counter);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.counter = Math.max(buf.readLong(), 0);
	}

	private void setState(int state) {
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, state, 2);
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:block.reactorStart", 1.0F, 1.0F);
		updateState();
	}

	public long getCounter() {
		return counter;
	}

	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_fluid_counter_valve";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluid(Context context, Arguments args) {
		return new Object[] {getType().getName()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCounter(Context context, Arguments args) {
		return new Object[] {counter};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] resetCounter(Context context, Arguments args) {
		counter = 0;
		markDirty();
		return new Object[] {};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getState(Context context, Arguments args) {
		return new Object[] {getBlockMetadata() == 1 ? 1 : 0};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setState(Context context, Arguments args) {
		final int state = args.checkInteger(0);
		if(state != 0 && state != 1) {
			throw new IllegalArgumentException();
		}
		setState(state);
		return new Object[] {};
	}

	@Override
	public String provideRORValue(String name) {
		if((PREFIX_VALUE + "value").equals(name))
			return String.valueOf(counter);
		if((PREFIX_VALUE + "state").equals(name))
			return String.valueOf(getBlockMetadata() == 1 ? 1 : 0);
		return null;
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
			PREFIX_VALUE + "value",
			PREFIX_VALUE + "state",
			PREFIX_FUNCTION + "reset",
			PREFIX_FUNCTION + "setState" + NAME_SEPARATOR + "state",
		};
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		if(name.equals(PREFIX_FUNCTION + "reset")) {
			counter = 0;
			markDirty();
		} else if(name.equals(PREFIX_FUNCTION + "setState")) {
			setState(IRORInteractive.parseInt(params[0], 0, 1));
		}
		return null;
	}
}
