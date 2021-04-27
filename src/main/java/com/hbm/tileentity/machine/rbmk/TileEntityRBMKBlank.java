package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;

import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

public class TileEntityRBMKBlank extends TileEntityRBMKBase {
	
	@Override
	public void onMelt(int reduce) {

		reduce = MathHelper.clamp_int(reduce, 1, 3);
		
		if(worldObj.rand.nextInt(3) == 0)
			reduce++;
		
		for(int i = 3; i >= 0; i--) {
			
			if(i <= 4 - reduce) {
				
				if(reduce > 1 && i == 4 - reduce) {
					
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.pribris_burning);
					
				} else {
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.pribris);
				}
				
			} else {
				worldObj.setBlock(xCoord, yCoord + i, zCoord, Blocks.air);
			}
			worldObj.markBlockForUpdate(xCoord, yCoord + i, zCoord);
		}
		
		int count = 1 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		super.onMelt(reduce);
	}
}
