package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopTurbofan extends SoundLoopMachine {
	
	public static List<SoundLoopTurbofan> list = new ArrayList<SoundLoopTurbofan>();

	public SoundLoopTurbofan(ResourceLocation path, TileEntity te) {
		super(path, te);
		list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(te instanceof TileEntityMachineTurbofan) {
			TileEntityMachineTurbofan drill = (TileEntityMachineTurbofan)te;
			
			if(this.volume != 10)
				volume = 10;
			
			if(!drill.wasOn)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}

}
