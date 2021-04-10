package com.hbm.blocks.bomb;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityBalefire;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockElectronium extends Block
{

	public BlockElectronium(Material p_i45394_1_)
	{
		super(p_i45394_1_);
		this.lightValue = 15;
	}
	
	@Override
	public void onBlockAdded(World worldIn, int x, int y, int z)
	{
		worldIn.setBlockToAir(x, y, z);
		EntityBalefire bf = new EntityBalefire(worldIn);
		bf.posX = x;
		bf.posY = y;
		bf.posZ = z;
		bf.destructionRange = 200;
		worldIn.spawnEntityInWorld(bf);
		worldIn.spawnEntityInWorld(EntityNukeCloudSmall.statFacBale(worldIn, x, y, z, 200, 1000));
	}
}
