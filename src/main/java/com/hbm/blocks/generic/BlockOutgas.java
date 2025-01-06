package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockOutgas extends BlockOre {
	
	boolean randomTick;
	int rate;
	boolean onBreak;
	boolean onNeighbour;
	

	public BlockOutgas(Material mat, boolean randomTick, int rate, boolean onBreak) {
		super(mat);
		this.setTickRandomly(randomTick);
		this.randomTick = randomTick;
		this.rate = rate;
		this.onBreak = onBreak;
		this.onNeighbour = false;
	}

	public BlockOutgas(Material mat, boolean randomTick, int rate, boolean onBreak, boolean onNeighbour) {
		this(mat, randomTick, rate, onBreak);
		this.onNeighbour = onNeighbour;
	}

	public int tickRate(World world) {
		return rate;
	}
	
	protected Block getGas() {
		if(this == ModBlocks.ore_uranium || this == ModBlocks.ore_uranium_scorched || 
				this == ModBlocks.ore_gneiss_uranium || this == ModBlocks.ore_gneiss_uranium_scorched || 
				this == ModBlocks.ore_nether_uranium || this == ModBlocks.ore_nether_uranium_scorched) {
			return ModBlocks.gas_radon;
		}
		
		if(this == ModBlocks.block_corium_cobble)
			return ModBlocks.gas_radon;
		
		if(this == ModBlocks.ancient_scrap)
			return ModBlocks.gas_radon_tomb;
		
		if(this == ModBlocks.ore_coal_oil_burning || this == ModBlocks.ore_nether_coal) {
			return ModBlocks.gas_monoxide;
		}
		
		if(this == ModBlocks.ore_asbestos || this == ModBlocks.ore_gneiss_asbestos ||
				this == ModBlocks.block_asbestos || this == ModBlocks.deco_asbestos ||
				this == ModBlocks.brick_asbestos || this == ModBlocks.tile_lab ||
				this == ModBlocks.tile_lab_cracked || this == ModBlocks.tile_lab_broken) {
			return ModBlocks.gas_asbestos;
		}
		
		return Blocks.air;
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		
		if(this.randomTick && getGas() == ModBlocks.gas_asbestos) {
			
			if(world.getBlock(x, y + 1, z) == Blocks.air) {
				
				if(world.rand.nextInt(10) == 0)
				world.setBlock(x, y + 1, z, ModBlocks.gas_asbestos);
				
				for(int i = 0; i < 5; i++)
					world.spawnParticle("townaura", x + world.rand.nextFloat(), y + 1.1, z + world.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(6));
		
		if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == Blocks.air) {
			world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, getGas());
		}
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
		
		if(onBreak) {
			world.setBlock(x, y, z, this.getGas());
		}
		
		super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		if(onNeighbour && world.rand.nextInt(3) == 0) {

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				
				if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == Blocks.air) {
					world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, getGas());
				}
			}
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		super.breakBlock(world, x, y, z, block, i);
		
		if(this == ModBlocks.ancient_scrap) {
			for(int ix = -2; ix <= 2; ix++) {
				for(int iy = -2; iy <= 2; iy++) {
					for(int iz = -2; iz <= 2; iz++) {
						
						if(Math.abs(ix + iy + iz) < 5 && Math.abs(ix + iy + iz) > 0 && world.getBlock(x + ix, y + iy, z + iz) == Blocks.air) {
							world.setBlock(x + ix, y + iy, z + iz, this.getGas());
						}
					}
				}
			}
		}
	}
}
