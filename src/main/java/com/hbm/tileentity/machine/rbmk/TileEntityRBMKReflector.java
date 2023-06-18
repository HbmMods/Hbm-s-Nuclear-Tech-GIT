package com.hbm.tileentity.machine.rbmk;

import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityRBMKReflector extends TileEntityRBMKBase {
	protected int setting = 0;
	protected String mode = "NORMAL";
	public void modeSetting() {
		setting++;
		if(setting > 4) {
			setting = 0;
		}
		String[] mode = new String[]{"NORMAL","NORTH","EAST","SOUTH","WEST"};
		this.mode = mode[setting];
	}
	@Override
	public void onMelt(int reduce) {
		
		int count = 1 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.REFLECTOR;
	}
	public int getSetting(){ return this.setting; }
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		this.mode = nbt.getString("mode");
		//this.setting = nbt.getInteger("setting");
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("mode", this.mode);
	}
}
