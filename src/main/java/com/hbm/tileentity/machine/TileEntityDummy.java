package com.hbm.tileentity.machine;

import com.hbm.interfaces.IMultiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityDummy extends TileEntity implements IFluidHandler {

	public int targetX;
	public int targetY;
	public int targetZ;

    @Override
	public void updateEntity() {
    	if(!this.worldObj.isRemote) {
    		if(!(this.worldObj.getBlock(targetX, targetY, targetZ) instanceof IMultiblock)) {
    			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
    		}
    	}
    }

    @Override
	public void readFromNBT(NBTTagCompound nbt)
    {
    	super.readFromNBT(nbt);
        this.targetX = nbt.getInteger("tx");
        this.targetY = nbt.getInteger("ty");
        this.targetZ = nbt.getInteger("tz");
    }

    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
    	super.writeToNBT(nbt);
    	nbt.setInteger("tx", this.targetX);
    	nbt.setInteger("ty", this.targetY);
    	nbt.setInteger("tz", this.targetZ);
    }

    /**
     * Get the target tile entity
     * @return The target tile entity, or null if it doesn't exist or isn't a tile entity
     */
    protected TileEntity getTargetTileEntity() {
        if (worldObj == null) {
            return null;
        }
        return worldObj.getTileEntity(targetX, targetY, targetZ);
    }

    // IFluidHandler implementation

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        TileEntity target = getTargetTileEntity();
        if (target instanceof IFluidHandler) {
            return ((IFluidHandler) target).fill(from, resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        TileEntity target = getTargetTileEntity();
        if (target instanceof IFluidHandler) {
            return ((IFluidHandler) target).drain(from, resource, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        TileEntity target = getTargetTileEntity();
        if (target instanceof IFluidHandler) {
            return ((IFluidHandler) target).drain(from, maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        TileEntity target = getTargetTileEntity();
        if (target instanceof IFluidHandler) {
            return ((IFluidHandler) target).canFill(from, fluid);
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        TileEntity target = getTargetTileEntity();
        if (target instanceof IFluidHandler) {
            return ((IFluidHandler) target).canDrain(from, fluid);
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        TileEntity target = getTargetTileEntity();
        if (target instanceof IFluidHandler) {
            return ((IFluidHandler) target).getTankInfo(from);
        }
        return new FluidTankInfo[0];
    }
}
