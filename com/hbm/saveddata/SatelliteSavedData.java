package com.hbm.saveddata;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class SatelliteSavedData extends WorldSavedData {
	
	public int globalAccessThingy;
    private World worldObj;

	public SatelliteSavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public SatelliteSavedData(World p_i1678_1_)
    {
        super("satellites");
        this.worldObj = p_i1678_1_;
        this.markDirty();
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		globalAccessThingy = nbt.getInteger("gat");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("gat", globalAccessThingy);
	}

}
