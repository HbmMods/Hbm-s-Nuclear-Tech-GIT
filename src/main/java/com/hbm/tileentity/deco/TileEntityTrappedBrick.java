package com.hbm.tileentity.deco;

import com.hbm.blocks.generic.TrappedBrick.Trap;
import com.hbm.blocks.generic.TrappedBrick.TrapType;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTrappedBrick extends TileEntity {
	
	AxisAlignedBB detector = null;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(detector == null) {
				setDetector();
			}
		}
	}
	
	private void setDetector() {
		
		Trap trap = Trap.get(this.getBlockMetadata());
		
		switch(trap) {
		case FALLING_ROCKS: break;
		case ARROW: break;
		case FLAMING_ARROW: break;
		case PILLAR: break;
		case POISON_DART: break;
		case ZOMBIE: break;
		case SPIDERS: break;
		}
	}
}
