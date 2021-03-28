package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineITER;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachinePlasmaHeater extends TileEntityMachineBase implements IFluidAcceptor, IConsumer {
	
	public long power;
	public static final long maxPower = 100000000;
	
	public FluidTank[] tanks;
	public FluidTank plasma;

	public TileEntityMachinePlasmaHeater() {
		super(5);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.DEUTERIUM, 16000, 0);
		tanks[1] = new FluidTank(FluidType.TRITIUM, 16000, 1);
		plasma = new FluidTank(FluidType.PLASMA_DT, 64000, 2);
	}

	@Override
	public String getName() {
		return "container.plasmaHeater";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			/// START Managing all the internal stuff ///
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tanks[0].setType(1, 2, slots);
			tanks[1].setType(3, 4, slots);
			
			updateType();
			
			int maxConv = 50;
			int powerReq = 10000;
			
			int convert = Math.min(tanks[0].getFill(), tanks[1].getFill());
			convert = Math.min(convert, (plasma.getMaxFill() - plasma.getFill()));
			convert = Math.min(convert, maxConv);
			convert = (int) Math.min(convert, power / powerReq);
			convert = Math.max(0, convert);
			
			if(convert > 0 && plasma.getTankType() != FluidType.NONE) {

				tanks[0].setFill(tanks[0].getFill() - convert);
				tanks[1].setFill(tanks[1].getFill() - convert);
				
				plasma.setFill(plasma.getFill() + convert * 2);
				power -= convert * powerReq;
				
				this.markDirty();
			}
			/// END Managing all the internal stuff ///

			/// START Loading plasma into the ITER ///
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
			int dist = 11;
			
			if(worldObj.getBlock(xCoord + dir.offsetX * dist, yCoord + 2, zCoord + dir.offsetZ * dist) == ModBlocks.iter) {
				int[] pos = ((MachineITER)ModBlocks.iter).findCore(worldObj, xCoord + dir.offsetX * dist, yCoord + 2, zCoord + dir.offsetZ * dist);
				
				if(pos != null) {
					TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
					
					if(te instanceof TileEntityITER) {
						TileEntityITER iter = (TileEntityITER)te;
							
						if(iter.plasma.getFill() == 0 && this.plasma.getTankType() != FluidType.NONE) {
							iter.plasma.setTankType(this.plasma.getTankType());
						}
							
							if(iter.isOn) {
							
							if(iter.plasma.getTankType() == this.plasma.getTankType()) {
								
								int toLoad = Math.min(iter.plasma.getMaxFill() - iter.plasma.getFill(), this.plasma.getFill());
								toLoad = Math.min(toLoad, 40);
								this.plasma.setFill(this.plasma.getFill() - toLoad);
								iter.plasma.setFill(iter.plasma.getFill() + toLoad);
								this.markDirty();
								iter.markDirty();
							}
						}
					}
				}
			}
			
			/// END Loading plasma into the ITER ///

			/// START Notif packets ///
			for(int i = 0; i < tanks.length; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			plasma.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			this.networkPack(data, 50);
			/// END Notif packets ///
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
	}
	
	private void updateType() {
		
		//if(plasma.getFill() > 0)
		//	return;
		
		List<FluidType> types = new ArrayList() {{ add(tanks[0].getTankType()); add(tanks[1].getTankType()); }};

		if(types.contains(FluidType.DEUTERIUM) && types.contains(FluidType.TRITIUM)) {
			plasma.setTankType(FluidType.PLASMA_DT);
			return;
		}
		if(types.contains(FluidType.DEUTERIUM) && types.contains(FluidType.HYDROGEN)) {
			plasma.setTankType(FluidType.PLASMA_HD);
			return;
		}
		if(types.contains(FluidType.HYDROGEN) && types.contains(FluidType.TRITIUM)) {
			plasma.setTankType(FluidType.PLASMA_HT);
			return;
		}
		if(types.contains(FluidType.XENON) && types.contains(FluidType.MERCURY)) {
			plasma.setTankType(FluidType.PLASMA_XM);
			return;
		}
		if(types.contains(FluidType.BALEFIRE) && types.contains(FluidType.AMAT)) {
			plasma.setTankType(FluidType.PLASMA_BF);
			return;
		}
		
		plasma.setTankType(FluidType.NONE);
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "fuel_1");
		tanks[1].readFromNBT(nbt, "fuel_2");
		plasma.readFromNBT(nbt, "plasma");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "fuel_1");
		tanks[1].writeToNBT(nbt, "fuel_2");
		plasma.writeToNBT(nbt, "plasma");
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getMaxFill();
		else if (type.name().equals(plasma.getTankType().name()))
			return plasma.getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if (type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if (type.name().equals(plasma.getTankType().name()))
			plasma.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if (type.name().equals(plasma.getTankType().name()))
			return plasma.getFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
		
		if(index == 2)
			plasma.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
		
		if(index == 2)
			plasma.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		list.add(plasma);
		
		return list;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
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
}
