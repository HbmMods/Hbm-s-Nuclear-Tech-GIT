package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.TileEntityMachineChemplant;
import com.hbm.tileentity.TileEntityMachineMiningDrill;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopChemplant extends SoundLoopMachine {
	
	public static List<SoundLoopChemplant> list = new ArrayList<SoundLoopChemplant>();

	public SoundLoopChemplant(ResourceLocation path, TileEntity te) {
		super(path, te);
		list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(te instanceof TileEntityMachineChemplant) {
			TileEntityMachineChemplant plant = (TileEntityMachineChemplant)te;
			
			if(this.volume != 3)
				volume = 3;
			
			if(!plant.isProgressing)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}

}
