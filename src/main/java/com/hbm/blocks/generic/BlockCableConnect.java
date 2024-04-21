package com.hbm.blocks.generic;

import com.hbm.blocks.BlockBase;

import api.hbm.energymk2.IEnergyConnectorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCableConnect extends BlockBase implements IEnergyConnectorBlock {

	public BlockCableConnect() {
		super();
	}

	public BlockCableConnect(Material material) {
		super(material);
	}

	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		return true;
	}
}
