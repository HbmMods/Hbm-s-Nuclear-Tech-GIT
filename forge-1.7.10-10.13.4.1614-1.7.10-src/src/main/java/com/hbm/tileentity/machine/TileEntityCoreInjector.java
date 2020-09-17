package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoreInjector extends TileEntityMachineBase implements IFluidAcceptor {
	
	public FluidTank[] tanks;
	public static final int range = 15;
	public int beam;

	public TileEntityCoreInjector() {
		super(4);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.DEUTERIUM, 128000, 0);
		tanks[1] = new FluidTank(FluidType.TRITIUM, 128000, 1);
	}

	@Override
	public String getName() {
		return "container.dfcInjector";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			tanks[0].setType(0, 1, slots);
			tanks[1].setType(2, 3, slots);
			
			beam = 0;
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
			for(int i = 1; i <= range; i++) {

				int x = xCoord + dir.offsetX * i;
				int y = yCoord + dir.offsetY * i;
				int z = zCoord + dir.offsetZ * i;
				
				TileEntity te = worldObj.getTileEntity(x, y, z);
				
				if(te instanceof TileEntityCore) {
					
					TileEntityCore core = (TileEntityCore)te;
					
					for(int t = 0; t < 2; t++) {
						
						if(core.tanks[t].getTankType() == tanks[t].getTankType()) {
							
							int f = Math.min(tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());

							tanks[t].setFill(tanks[t].getFill() - f);
							core.tanks[t].setFill(core.tanks[t].getFill() + f);
							core.markDirty();
							
						} else if(core.tanks[t].getFill() == 0) {
							
							core.tanks[t].setTankType(tanks[t].getTankType());
							int f = Math.min(tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());

							tanks[t].setFill(tanks[t].getFill() - f);
							core.tanks[t].setFill(core.tanks[t].getFill() + f);
							core.markDirty();
						}
					}
					
					beam = i;
					break;
				}
				
				if(worldObj.getBlock(x, y, z) != Blocks.air)
					break;
			}
			
			this.markDirty();

			tanks[0].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			tanks[1].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("beam", beam);
			this.networkPack(data, 250);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		beam = data.getInteger("beam");
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if (type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		
		return list;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		tanks[0].readFromNBT(nbt, "fuel1");
		tanks[1].readFromNBT(nbt, "fuel2");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		tanks[0].writeToNBT(nbt, "fuel1");
		tanks[1].writeToNBT(nbt, "fuel2");
	}

}
