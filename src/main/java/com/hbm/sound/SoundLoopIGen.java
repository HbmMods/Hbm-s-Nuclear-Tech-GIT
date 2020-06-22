package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineIGenerator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopIGen extends SoundLoopMachine {
	
	public static List<SoundLoopIGen> list = new ArrayList<SoundLoopIGen>();

	public SoundLoopIGen(ResourceLocation path, TileEntity te) {
		super(path, te);
		list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(te instanceof TileEntityMachineIGenerator) {
			TileEntityMachineIGenerator drill = (TileEntityMachineIGenerator)te;
			
			if(this.volume != 3)
				volume = 3;
			
			if(drill.torque <= 0)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}

}
