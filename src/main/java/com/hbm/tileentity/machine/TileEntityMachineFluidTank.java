package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
<<<<<<< HEAD
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.explosion.ExplosionFleija;
=======
import com.hbm.handler.FluidTypeHandler.FluidTrait;
>>>>>>> master
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineFluidTank extends TileEntityMachineBase implements IFluidContainer, IFluidSource, IFluidAcceptor {
	
	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
<<<<<<< HEAD
	private final boolean magnetic;
=======
>>>>>>> master
	
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	
<<<<<<< HEAD
	public TileEntityMachineFluidTank(boolean isMagnetic) {
=======
	public TileEntityMachineFluidTank() {
>>>>>>> master
		super(6);
		tank = new FluidTank(FluidType.NONE, 256000, 0);
		magnetic = isMagnetic;
	}

	@Override
<<<<<<< HEAD
	public String getName()
	{
		if (magnetic)
		{
			return "container.fluidtankMagnetic";
		}
		else
		{
			return "container.fluidtank";
		}
=======
	public String getName() {
		return "container.fluidtank";
>>>>>>> master
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			age++;
			
			if(age >= 20)
				age = 0;
			
			if((mode == 1 || mode == 2) && (age == 9 || age == 19))
				fillFluidInit(tank.getTankType());
			
			tank.loadTank(2, 3, slots);
			tank.setType(0, 1, slots);
			
<<<<<<< HEAD
			if(tank.getTankType().isAntimatter() && tank.getFill() > 0 && !magnetic)
			{
				if (tank.getTankType().equals(FluidType.ASCHRAB))
				{
					EntityNukeExplosionMK3 field = new EntityNukeExplosionMK3(getWorldObj());
					field.posX = xCoord;
					field.posY = yCoord;
					field.posZ = zCoord;
					field.destructionRange = BombConfig.aSchrabRadius;
					field.speed = 25;
					field.coefficient = 1.0F;
					field.waste = false;
					worldObj.spawnEntityInWorld(field);
					EntityCloudFleija effect = new EntityCloudFleija(getWorldObj(), BombConfig.aSchrabRadius);
					effect.posX = xCoord;
					effect.posY = yCoord;
					effect.posZ = zCoord;
					worldObj.spawnEntityInWorld(effect);
				}
				else
				{
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, true);
				}
=======
			if(tank.getFill() > 0) {
				if(tank.getTankType().isAntimatter()) {
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, true);
				}
				
				if(tank.getTankType().traits.contains(FluidTrait.CORROSIVE_2)) {
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
				}
>>>>>>> master
			}
			
			tank.unloadTank(4, 5, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("mode", mode);
			this.networkPack(data, 50);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		
		mode = data.getShort("mode");
	}
	
	public void handleButtonPacket(int value, int meta) {
		
		mode = (short) ((mode + 1) % modes);
		markDirty();
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
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 2, getTact(), type);
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
