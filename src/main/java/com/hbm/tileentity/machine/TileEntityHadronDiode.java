package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityTickingBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHadronDiode extends TileEntityTickingBase {
	
	int age = 0;
	boolean fatherIAskOfYouToUpdateMe = false;
	
	public DiodeConfig[] sides = new DiodeConfig[6];

	@Override
	public String getInventoryName() {
		return "";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			age++;
			
			if(age >= 20) {
				age = 0;
				sendSides();
			}
		} else {
			
			if(fatherIAskOfYouToUpdateMe) {
				fatherIAskOfYouToUpdateMe = false;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}
	
	public void sendSides() {
		
		NBTTagCompound data = new NBTTagCompound();
		
		for(int i = 0; i < 6; i++) {
			
			if(sides[i] != null)
				data.setInteger("" + i, sides[i].ordinal());
		}
		
		this.networkPack(data, 250);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		for(int i = 0; i < 6; i++) {
			sides[i] = DiodeConfig.values()[nbt.getInteger("" + i)];
		}
		
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public DiodeConfig getConfig(int side) {
		
		if(ForgeDirection.getOrientation(side) == ForgeDirection.UNKNOWN)
			return DiodeConfig.NONE;
		
		DiodeConfig conf = sides[side];
		
		if(conf == null)
			return DiodeConfig.NONE;
		
		return conf;
	}
	
	public void setConfig(int side, int config) {
		sides[side] = DiodeConfig.values()[config];
		this.markDirty();
		sendSides();
	}
	
	public static enum DiodeConfig {
		NONE,
		IN,
		OUT
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 6; i++) {
			sides[i] = DiodeConfig.values()[nbt.getInteger("side_" + i)];
		}
		
		fatherIAskOfYouToUpdateMe = true;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 6; i++) {
			
			if(sides[i] != null) {
				nbt.setInteger("side_" + i, sides[i].ordinal());
			}
		}
	}
}
