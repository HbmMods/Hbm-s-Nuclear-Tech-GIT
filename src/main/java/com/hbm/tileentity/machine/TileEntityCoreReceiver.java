package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.block.ILaserable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoreReceiver extends TileEntityMachineBase implements ISource, IFluidAcceptor, ILaserable {
	
	public long power;
	public long joules;
	public FluidTank tank;
	public List<IConsumer> list = new ArrayList();
	public int age = 0;

	public TileEntityCoreReceiver() {
		super(0);
		tank = new FluidTank(FluidType.CRYOGEL, 64000, 0);
	}

	@Override
	public String getName() {
		return "container.dfcReceiver";
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			power += joules * 5000;
			
			if(joules > 0) {

				if(tank.getFill() >= 20) {
					tank.setFill(tank.getFill() - 20);
				} else {
					worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.flowing_lava);
					return;
				}
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("joules", joules);
			this.networkPack(data, 50);
			
			joules = 0;

			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19) {
				ffgeuaInit();
				
				if(!getTact())
					power = 0;
			}
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {

		joules = data.getLong("joules");
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {

		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 1, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
	}
	
	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			return tank.getFill();
		else
			return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			return tank.getMaxFill();
		else
			return 0;
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
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tank);
		
		return list;
	}

	@Override
	public void addEnergy(World world, int x, int y, int z, long energy, ForgeDirection dir) {
		
		//only accept lasers from the front
		if(dir.getOpposite().ordinal() == this.getBlockMetadata()) {
			joules += energy;
		} else {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			worldObj.createExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 2.5F, true);
		}
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
		
		power = nbt.getLong("power");
		joules = nbt.getLong("joules");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setLong("joules", joules);
		tank.writeToNBT(nbt, "tank");
	}
}
