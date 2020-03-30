package com.hbm.tileentity.machine;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCore extends TileEntityMachineBase {
	
	public int field;
	public int heat;
	public int color;
	public FluidTank[] tanks;

	public TileEntityCore() {
		super(3);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.DEUTERIUM, 128000, 0);
		tanks[1] = new FluidTank(FluidType.TRITIUM, 128000, 1);
	}

	@Override
	public String getName() {
		return "container.dfc_core";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("tank0", tanks[0].getTankType().ordinal());
			data.setInteger("tank1", tanks[1].getTankType().ordinal());
			data.setInteger("fill0", tanks[0].getFill());
			data.setInteger("fill1", tanks[1].getFill());
			data.setInteger("field", field);
			data.setInteger("heat", heat);
			networkPack(data, 250);
		}
		
	}
	
	public void networkUnpack(NBTTagCompound data) {

		tanks[0].setTankType(FluidType.getEnum(data.getInteger("tank0")));
		tanks[1].setTankType(FluidType.getEnum(data.getInteger("tank1")));
		tanks[0].setFill(data.getInteger("fill0"));
		tanks[1].setFill(data.getInteger("fill1"));
		field = data.getInteger("field");
		heat = data.getInteger("heat");
	}
	
	public int getFieldScaled(int i) {
		return (field * i) / 100;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / 100;
	}
	
	public long burn(long joules) {
		
		return 0;
	}
	
	public float getFuelEfficiency(FluidType type) {
		
		switch(type) {

		case HYDROGEN:
			return 1.0F;
		case DEUTERIUM:
			return 1.5F;
		case TRITIUM:
			return 1.7F;
		case OXYGEN:
			return 1.2F;
		case ACID:
			return 1.4F;
		case XENON:
			return 1.5F;
		case SAS3:
			return 2.0F;
		case BALEFIRE:
			return 2.5F;
		case AMAT:
			return 2.2F;
		case ASCHRAB:
			return 2.7F;
		default:
			return 0;
		}
	}

}
