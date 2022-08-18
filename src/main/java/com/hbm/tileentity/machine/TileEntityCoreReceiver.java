package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.block.ILaserable;
import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreReceiver extends TileEntityMachineBase implements IEnergyGenerator, IFluidAcceptor, ILaserable, IFluidStandardReceiver, SimpleComponent {
	
	public long power;
	public long joules;
	public FluidTank tank;

	public TileEntityCoreReceiver() {
		super(0);
		tank = new FluidTank(Fluids.CRYOGEL, 64000, 0);
	}

	@Override
	public String getName() {
		return "container.dfcReceiver";
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			this.subscribeToAllAround(tank.getTankType(), this);
			
			power = joules * 5000;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			
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
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		joules = data.getLong("joules");
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public long getMaxPower() {
		return 0;
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
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
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

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}

	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "dfc_receiver";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInput(Context context, Arguments args) {
		return new Object[] {joules};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getOutput(Context context, Arguments args) {
		return new Object[] {power};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCryogel(Context context, Arguments args) {
		return new Object[] {tank.getFill()};
	}
}
