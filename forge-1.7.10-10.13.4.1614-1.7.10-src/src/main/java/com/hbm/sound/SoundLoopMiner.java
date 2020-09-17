package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopMiner extends SoundLoopMachine {
	
	public static List<SoundLoopMiner> list = new ArrayList<SoundLoopMiner>();

	public SoundLoopMiner(ResourceLocation path, TileEntity te) {
		super(path, te);
		list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(te instanceof TileEntityMachineMiningDrill) {
			TileEntityMachineMiningDrill drill = (TileEntityMachineMiningDrill)te;
			
			if(this.volume != 3)
				volume = 3;
			
			if(drill.torque <= 0.5F)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}

}
