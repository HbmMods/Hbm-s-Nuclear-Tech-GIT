package com.hbm.tileentity.network;

import com.hbm.blocks.network.IBlockFluidDuct;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.HbmKeybinds;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.fluidmk2.FluidNode;
import api.hbm.fluidmk2.IFluidPipeSingle;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluidPipeAnchor extends TileEntityPipelineBase implements IFluidPipeSingle, IFluidCopiable {

	protected FluidNode node;
	protected FluidType type = Fluids.NONE;
	protected FluidType lastType = Fluids.NONE;

	@Override
	public NetworkType getNetworkType() {
		return NetworkType.FLUID;
	}

	@Override
	public FluidNode createNode(FluidType type) {
		return this.initNode(new FluidNode(type.getNetworkProvider(), new BlockPos(xCoord, yCoord, zCoord)));
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return this.getOppositeDir() == dir && type == this.type;
	}

	@Override
	public void updateEntity() {
		if(worldObj.isRemote && lastType != type) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			lastType = type;
		}

		if(!worldObj.isRemote) {
			this.updateNode();
		}
	}

	@Override
	protected void addNodeConnection(int x, int y, int z) {
		this.updateNode();
		this.linkNode(this.node, x, y, z);
	}

	@Override
	protected void destroyNode(int x, int y, int z) {
		UniNodespace.destroyNode(worldObj, x, y, z, this.type.getNetworkProvider());
	}

	@Override
	protected int canConnect(TileEntityPipelineBase other) {
		if (!(other instanceof TileEntityFluidPipeAnchor)) return 1;
		
		TileEntityFluidPipeAnchor pipe = (TileEntityFluidPipeAnchor) other;

		// connect with NONE type anchors
		if(this.type == Fluids.NONE && pipe.type != this.type) this.setType(pipe.type);
		if(pipe.type == Fluids.NONE && this.type != pipe.type) pipe.setType(this.type);

		if(this.type != pipe.type) return 4;
		return 0;
	}
	
	@Override
	public FluidType getType() {
		return this.type;
	}

	@Override
	public void setType(FluidType type) {
		FluidType prev = this.type;
		this.type = type;
		this.markDirty();

		if(worldObj instanceof WorldServer) {
			WorldServer world = (WorldServer) worldObj;
			world.getPlayerManager().markBlockForUpdate(xCoord, yCoord, zCoord);
		}

		UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, prev.getNetworkProvider());

		if(this.node != null) {
			this.node = null;
		}
	}

	@Override
	public int[] getFluidIDToCopy() {
		return new int[]{ type.getID() };
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
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("type", this.type.getID());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.type = Fluids.fromID(nbt.getInteger("type"));
	}
	
	private void updateNode() {
		this.node = this.ensureNode(this.node, this.type.getNetworkProvider(), () -> createNode(this.type));
	}
}
