package com.hbm.tileentity.machine.rbmk;

import com.hbm.items.machine.ItemRBMKRod;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRBMKRod extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver {
	
	//amount of "neutron energy" buffered for the next tick to use for the reaction
	private double fluxFast;
	private double fluxSlow;

	public TileEntityRBMKRod() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.rbmkRod";
	}

	@Override
	public void receiveFlux(NType type, double flux) {
		
		switch(type) {
		case FAST: this.fluxFast += flux; break;
		case SLOW: this.fluxSlow += flux; break;
		}
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			super.updateEntity();
			
			if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
				
				ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());
				
				double fluxIn = fluxFromType(rod.nType);
				double fluxOut = rod.burn(slots[0], fluxIn);
				NType rType = rod.rType;
				
				//for spreading, we want the buffered flux to be 0 because we want to know exactly how much gets reflected back
				this.fluxFast = 0;
				this.fluxSlow = 0;
				
				spreadFlux(rType, fluxOut);
			} else {

				this.fluxFast = 0;
				this.fluxSlow = 0;
			}
		}
	}
	
	/**
	 * SLOW: full efficiency for slow neutrons, fast neutrons have half efficiency
	 * FAST: fast neutrons have 100% efficiency, slow only 30%
	 * ANY: just add together whatever we have because who cares
	 * @param type
	 * @return
	 */
	
	private double fluxFromType(NType type) {
		
		switch(type) {
		case SLOW: return this.fluxFast * 0.5D + this.fluxSlow;
		case FAST: return this.fluxFast + this.fluxSlow * 0.3D;
		case ANY: return this.fluxFast + this.fluxSlow;
		}
		
		return 0.0D;
	}
	
	public static final ForgeDirection[] fluxDirs = new ForgeDirection[] {
			ForgeDirection.NORTH,
			ForgeDirection.EAST,
			ForgeDirection.SOUTH,
			ForgeDirection.WEST
	};
	
	private void spreadFlux(NType type, double fluxOut) {
		
		int range = 5;
		
		for(ForgeDirection dir : fluxDirs) {
			
			NType stream = type;
			
			for(int i = 1; i <= range; i++) {
			
				TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX * i, yCoord, zCoord + dir.offsetZ * i);

				//burn baby burn
				if(te instanceof TileEntityRBMKRod) {
					TileEntityRBMKRod rod = (TileEntityRBMKRod)te;
					rod.receiveFlux(stream, fluxOut);
					break;
				}
				
				//set neutrons to slow
				if(te instanceof TileEntityRBMKModerator) {
					stream = NType.SLOW;
					continue;
				}
				
				//return the neutrons back to this with no further action required
				if(te instanceof TileEntityRBMKReflector) {
					this.receiveFlux(stream, fluxOut);
					break;
				}
				
				//break the neutron flow and nothign else
				if(te instanceof TileEntityRBMKAbsorber) {
					break;
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.fluxFast = nbt.getDouble("fluxFast");
		this.fluxSlow = nbt.getDouble("fluxSlow");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("fluxFast", this.fluxFast);
		nbt.setDouble("fluxSlow", this.fluxSlow);
	}
	
	public void getDiagData(NBTTagCompound nbt) {
		this.writeToNBT(nbt);
		
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			
			ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());

			nbt.setString("f_yield", rod.getYield(slots[0]) + " / " + rod.yield + " (" + (rod.getEnrichment(slots[0]) * 100) + "%)");
			nbt.setString("f_xenon", rod.getPoison(slots[0]) + "%");
			nbt.setString("f_heat", rod.getCoreHeat(slots[0]) + " / " + rod.getHullHeat(slots[0])  + " / " + rod.meltingPoint);
		}
	}
}
