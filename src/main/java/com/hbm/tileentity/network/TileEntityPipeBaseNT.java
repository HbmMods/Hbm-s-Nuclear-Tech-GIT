package com.hbm.tileentity.network;

import com.hbm.blocks.network.IBlockFluidDuct;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.HbmKeybinds;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import api.hbm.fluid.IFluidConductor;
import api.hbm.fluid.IPipeNet;
import api.hbm.fluid.PipeNet;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.Compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPipeBaseNT extends TileEntityLoadedBase implements IFluidConductor, IFluidCopiable {

	protected IPipeNet network;
	protected FluidType type = Fluids.NONE;
	protected FluidType lastType = Fluids.NONE;

	@Override
	public void updateEntity() {

		if(worldObj.isRemote && lastType != type) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			lastType = type;
		}

		if(!worldObj.isRemote && shouldConnect()) {

			//we got here either because the net doesn't exist or because it's not valid, so that's safe to assume
			this.setPipeNet(type, null);

			this.connect();

			if(this.getPipeNet(type) == null) {
				this.setPipeNet(type, new PipeNet(type).joinLink(this));
			}
		}
	}

	public FluidType getType() {
		return this.type;
	}

	public void setType(FluidType type) {
		this.type = type;
		this.markDirty();

		if(worldObj instanceof WorldServer) {
			WorldServer world = (WorldServer) worldObj;
			world.getPlayerManager().markBlockForUpdate(xCoord, yCoord, zCoord);
		}

		if(this.network != null)
			this.network.destroy();
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && type == this.type;
	}

	protected void connect() {

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			TileEntity te = Compat.getTileStandard(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);

			if(te instanceof IFluidConductor) {

				IFluidConductor conductor = (IFluidConductor) te;

				if(!conductor.canConnect(type, dir.getOpposite()))
					continue;

				if(this.getPipeNet(type) == null && conductor.getPipeNet(type) != null) {
					conductor.getPipeNet(type).joinLink(this);
				}

				if(this.getPipeNet(type) != null && conductor.getPipeNet(type) != null && this.getPipeNet(type) != conductor.getPipeNet(type)) {
					conductor.getPipeNet(type).joinNetworks(this.getPipeNet(type));
				}
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.network != null) {
				this.network.destroy();
			}
		}
	}

	/**
	 * Only update until a power net is formed, in >99% of the cases it should be the first tick. Everything else is handled by neighbors and the net itself.
	 */
	public boolean shouldConnect() {
		return (this.network == null || !this.network.isValid()) && !this.isInvalid();
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {

		if(this.network == null)
			return fluid;

		return this.network.transferFluid(fluid, pressure);
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		return 0;
	}

	@Override
	public IPipeNet getPipeNet(FluidType type) {
		return type == this.type ? this.network : null;
	}

	@Override
	public void setPipeNet(FluidType type, IPipeNet network) {
		this.network = network;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.type = Fluids.fromID(nbt.getInteger("type"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("type", this.type.getID());
	}

	public boolean isLoaded = true;

	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		this.isLoaded = false;
	}

	@Override
	public int[] getFluidIDToCopy() {
		return new int[]{ type.getID() };
	}

	@Override
	public FluidTank getTankToPaste() {
		return null;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		int[] ids = nbt.getIntArray("fluidID");
		if(ids.length > 0) {
			int id;
			if (index < ids.length)
				id = ids[index];
			else
				id = 0;

			FluidType fluid = Fluids.fromID(id);

			if(HbmPlayerProps.getData(player).getKeyPressed(HbmKeybinds.EnumKeybind.TOOL_CTRL)){
				IBlockFluidDuct pipe = (IBlockFluidDuct)world.getBlock(x, y, z);
				pipe.changeTypeRecursively(world, x, y, z, getType(), fluid, 64);
			} else {
				this.setType(fluid);
			}
		}

	}
}
