package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityTickingBase;

import io.netty.buffer.ByteBuf;
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
				this.networkPackNT(250);
			}
		} else {
			
			if(fatherIAskOfYouToUpdateMe) {
				fatherIAskOfYouToUpdateMe = false;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}

	@Override public void serialize(ByteBuf buf) {
		for(int i = 0; i < 6; i++) {
			buf.writeByte(sides[i].ordinal());
		}
	}

	@Override public void deserialize(ByteBuf buf) {
		for(int i = 0; i < 6; i++) {
			sides[i] = DiodeConfig.values()[buf.readByte()];
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
		this.networkPackNT(250);
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
