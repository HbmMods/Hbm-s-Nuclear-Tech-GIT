package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRBMKOutlet extends TileEntity implements IFluidSource {
	
	public List<IFluidAcceptor> list = new ArrayList();
	public FluidTank steam;
	
	public TileEntityRBMKOutlet() {
		steam = new FluidTank(Fluids.SUPERHOTSTEAM, 32000, 0);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(int i = 2; i < 6; i++) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
				
				if(b instanceof RBMKBase) {
					int[] pos = ((RBMKBase)b).findCore(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
					
					if(pos != null) {
						TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
						
						if(te instanceof TileEntityRBMKBase) {
							TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
							
							int prov = Math.min(steam.getMaxFill() - steam.getFill(), rbmk.steam);
							rbmk.steam -= prov;
							steam.setFill(steam.getFill() + prov);
						}
					}
				}
			}
			
			fillFluidInit(this.steam.getTankType());
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.steam.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.steam.writeToNBT(nbt, "tank");
	}

	@Override
	public void setFillForSync(int fill, int index) {
		steam.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		steam.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		steam.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		return steam.getFill();
	}

	@Override
	public void fillFluidInit(FluidType type) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			fillFluid(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	@Deprecated
	public boolean getTact() { return worldObj.getTotalWorldTime() % 2 == 0; }

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}

}
