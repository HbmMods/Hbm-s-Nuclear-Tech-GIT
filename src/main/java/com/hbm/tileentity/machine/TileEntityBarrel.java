package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBarrel extends TileEntityMachineBase implements IFluidAcceptor, IFluidSource {
	
	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();

	public TileEntityBarrel() {
		super(6);
		tank = new FluidTank(FluidType.NONE, 0, 0);
	}

	public TileEntityBarrel(int capacity) {
		super(6);
		tank = new FluidTank(FluidType.NONE, capacity, 0);
	}

	@Override
	public String getName() {
		return "container.barrel";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			tank.setType(0, 1, slots);
			tank.loadTank(2, 3, slots);
			tank.unloadTank(4, 5, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			age++;
			if(age >= 20)
				age = 0;
			
			if((mode == 1 || mode == 2) && (age == 9 || age == 19))
				fillFluidInit(tank.getTankType());
			
			if(tank.getFill() > 0) {
				
				Block b = this.getBlockType();
				
				//for when you fill antimatter into a matter tank
				if(b != ModBlocks.barrel_antimatter && tank.getTankType().isAntimatter()) {
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, true);
				}
				
				//for when you fill hot or corrosive liquids into a plastic tank
				if(b == ModBlocks.barrel_plastic && (tank.getTankType().isCorrosive() || tank.getTankType().isHot())) {
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.fizz", 1.0F, 1.0F);
				}
				
				//for when you fill corrosive liquid into an iron tank
				if(b == ModBlocks.barrel_iron && tank.getTankType().isCorrosive()) {
					ItemStack[] copy = this.slots.clone();
					this.slots = new ItemStack[6];
					worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.barrel_corroded);
					TileEntityBarrel barrel = (TileEntityBarrel)worldObj.getTileEntity(xCoord, yCoord, zCoord);
					
					if(barrel != null) {
						barrel.tank.setTankType(tank.getTankType());
						barrel.tank.setFill(Math.min(barrel.tank.getMaxFill(), tank.getFill()));
						barrel.slots = copy;
					}
					
					worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.fizz", 1.0F, 1.0F);
				}
				
				if(b == ModBlocks.barrel_corroded && worldObj.rand.nextInt(3) == 0) {
					tank.setFill(tank.getFill() - 1);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("mode", mode);
			this.networkPack(data, 50);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		
		mode = data.getShort("mode");
	}

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(mode == 2 || mode == 3)
			return 0;
		
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tank);
		
		return list;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		mode = nbt.getShort("mode");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", mode);
		tank.writeToNBT(nbt, "tank");
	}
}
