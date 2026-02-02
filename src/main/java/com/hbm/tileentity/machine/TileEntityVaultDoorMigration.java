package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockDoorGeneric;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityVaultDoorMigration extends TileEntity {

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			int meta = getBlockMetadata();
			if(meta <= 5) {
				getWorldObj().setBlock(xCoord, yCoord, zCoord, ModBlocks.vault_door, meta + 10, 3);
				((BlockDoorGeneric) ModBlocks.vault_door).fillSpace(getWorldObj(), xCoord, yCoord, zCoord, ForgeDirection.getOrientation(meta), 0);
			}
		}
	}
}
