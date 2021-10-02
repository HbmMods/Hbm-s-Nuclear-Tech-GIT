package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Tuple;
import com.hbm.util.Tuple.Triplet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityOilDrillBase extends TileEntityMachineBase implements IConsumer, IFluidSource {
	
	public int indicator = 0;
	
	public long power;
	
	public List<IFluidAcceptor> list1 = new ArrayList();
	public List<IFluidAcceptor> list2 = new ArrayList();
	public FluidTank[] tanks;

	public TileEntityOilDrillBase() {
		super(5);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.OIL, 64_000, 0);
		tanks[1] = new FluidTank(FluidType.GAS, 64_000, 1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].readFromNBT(nbt, "t" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].writeToNBT(nbt, "t" + i);
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.tanks[0].unloadTank(1, 2, slots);
			this.tanks[1].unloadTank(3, 4, slots);
			
			for(int i = 0; i < 2; i++) {
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			}
			
			power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
			
			if(this.power >= this.getPowerReq() && this.tanks[0].getFill() < this.tanks[0].getMaxFill() && this.tanks[1].getFill() < this.tanks[1].getMaxFill()) {
				
				this.power -= this.getPowerReq();
				
				if(worldObj.getTotalWorldTime() % getDelay() == 0) {
					this.indicator = 0;
					
					for(int y = yCoord - 1; y >= getDrillDepth(); y--) {
						
						if(worldObj.getBlock(xCoord, y, zCoord) != ModBlocks.oil_pipe) {
						
							if(trySuck(y)) {
								break;
							} else {
								tryDrill(y);
								break;
							}
						}
						
						if(y == getDrillDepth())
							this.indicator = 1;
					}
				}
				
			} else {
				this.indicator = 2;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("indicator", this.indicator);
			this.networkPack(data, 25);
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.indicator = nbt.getInteger("indicator");
	}
	
	public abstract int getPowerReq();
	public abstract int getDelay();
	
	public void tryDrill(int y) {
		Block b = worldObj.getBlock(xCoord, y, zCoord);
		
		if(b.getExplosionResistance(null) < 1000) {
			worldObj.setBlock(xCoord, y, zCoord, ModBlocks.oil_pipe);
		} else {
			this.indicator = 2;
		}
	}
	
	public int getDrillDepth() {
		return 5;
	}
	
	public boolean trySuck(int y) {
		
		Block b = worldObj.getBlock(xCoord, y, zCoord);
		
		if(!canSuckBlock(b))
			return false;
		
		trace.clear();
		
		return suckRec(xCoord, y, zCoord, 0);
	}
	
	public boolean canSuckBlock(Block b) {
		return b == ModBlocks.ore_oil || b == ModBlocks.ore_oil_empty;
	}
	
	protected HashSet<Tuple.Triplet<Integer, Integer, Integer>> trace = new HashSet();
	
	public boolean suckRec(int x, int y, int z, int layer) {
		
		Triplet<Integer, Integer, Integer> pos = new Triplet(x, y, z);
		
		if(trace.contains(pos))
			return false;
		
		trace.add(pos);
		
		if(layer > 64)
			return false;
		
		Block b = worldObj.getBlock(x, y, z);
		
		if(b == ModBlocks.ore_oil) {
			doSuck(x, y, z);
			return true;
		}
		
		if(b == ModBlocks.ore_oil_empty) {
			ForgeDirection[] dirs = BobMathUtil.getShuffledDirs();
			
			for(ForgeDirection dir : dirs) {
				if(suckRec(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, layer + 1))
					return true;
			}
		}
		
		return false;
	}
	
	public void doSuck(int x, int y, int z) {
		
		if(worldObj.getBlock(x, y, z) == ModBlocks.ore_oil) {
			onSuck(x, y, z);
			worldObj.setBlock(x, y, z, ModBlocks.ore_oil_empty);
		}
	}
	
	public abstract void onSuck(int x, int y, int z);

	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 2 == 0;
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();

		return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return this.list1;
		if(type.name().equals(tanks[1].getTankType().name()))
			return this.list2;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			list1.clear();
		if(type.name().equals(tanks[1].getTankType().name()))
			list2.clear();
	}

	@Override
	public void setFillstate(int fill, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if(index < 2 && tanks[index] != null)
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
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
