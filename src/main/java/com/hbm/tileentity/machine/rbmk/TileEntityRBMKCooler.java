package com.hbm.tileentity.machine.rbmk;

import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class TileEntityRBMKCooler extends TileEntityRBMKBase implements IFluidAcceptor {
	
	private FluidTank tank;
	private int lastCooled;
	
	public TileEntityRBMKCooler() {
		super();
		
		this.tank = new FluidTank(Fluids.CRYOGEL, 8000, 0);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if((int)(this.heat) > 750) {
				
				int heatProvided = (int)(this.heat - 750D);
				int cooling = Math.min(heatProvided, tank.getFill());
				
				this.heat -= cooling;
				this.tank.setFill(this.tank.getFill() - cooling);
				
				this.lastCooled = cooling;
				
				if(lastCooled > 0) {
					List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 4, zCoord, xCoord + 1, yCoord + 8, zCoord + 1));
					
					for(Entity e : entities) {
						e.setFire(5);
						e.attackEntityFrom(DamageSource.inFire, 10);
					}
				}
			} else {
				this.lastCooled = 0;
			}
			
		} else {
			
			if(this.lastCooled > 100) {
				for(int i = 0; i < 2; i++) {
					worldObj.spawnParticle("flame", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, 0, 0.2, 0);
					worldObj.spawnParticle("smoke", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, 0, 0.2, 0);
				}
				
				if(worldObj.rand.nextInt(20) == 0)
					worldObj.spawnParticle("lava", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, 0, 0.0, 0);
			} else if(this.lastCooled > 50) {
				for(int i = 0; i < 2; i++) {
					worldObj.spawnParticle("cloud", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, worldObj.rand.nextGaussian() * 0.05, 0.2, worldObj.rand.nextGaussian() * 0.05);
				}
			} else if(this.lastCooled > 0) {
				
				if(worldObj.getTotalWorldTime() % 2 == 0)
					worldObj.spawnParticle("cloud", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, 0, 0.2, 0);
				
			}
		}
		
		super.updateEntity();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		tank.readFromNBT(nbt, "cryo");
		this.lastCooled = nbt.getInteger("cooled");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		tank.writeToNBT(nbt, "cryo");
		nbt.setInteger("cooled", this.lastCooled);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.COOLER;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == tank.getTankType())
			tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type == tank.getTankType() ? tank.getFill() : 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type == tank.getTankType() ? tank.getMaxFill() : 0;
	}

}
