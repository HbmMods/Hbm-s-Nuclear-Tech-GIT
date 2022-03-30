package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb.BombReturnCode;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DetCord extends Block implements IDetConnectible {

	public DetCord(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion p_149723_5_) {
		this.explode(world, x, y, z);
	}
	
	@Override
	public boolean canDropFromExplosion(Explosion explosion) {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_) {
		if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
			this.explode(world, x, y, z);
		}
	}

	public void explode(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			world.setBlock(x, y, z, Blocks.air);
			world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 1.5F, true);
		}
	}
}