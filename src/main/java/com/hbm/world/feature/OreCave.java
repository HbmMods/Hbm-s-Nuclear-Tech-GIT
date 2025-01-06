package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockStalagmite;
import com.hbm.inventory.RecipesCommon.MetaBlock;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class OreCave {

	private NoiseGeneratorPerlin noise;
	private MetaBlock ore;
	/** The number that is being deducted flat from the result of the perlin noise before all other processing. Increase this to make strata rarer. */
	private double threshold = 2D;
	/** The mulitplier for the remaining bit after the threshold has been deducted. Increase to make strata wavier. */
	private int rangeMult = 3;
	/** The maximum range after multiplying - anything above this will be subtracted from (maxRange * 2) to yield the proper range. Increase this to make strata thicker. */
	private int maxRange = 4;
	/** The y-level around which the stratum is centered. */
	private int yLevel = 30;
	private Block fluid;
	int dim = 0;
	
	public OreCave(Block ore) {
		this(ore, 0);
	}
	
	public OreCave(Block ore, int meta) {
		this.ore = new MetaBlock(ore, meta);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public OreCave setThreshold(double threshold) {
		this.threshold = threshold;
		return this;
	}
	
	public OreCave setRangeMult(int rangeMult) {
		this.rangeMult = rangeMult;
		return this;
	}
	
	public OreCave setMaxRange(int maxRange) {
		this.maxRange = maxRange;
		return this;
	}
	
	public OreCave setYLevel(int yLevel) {
		this.yLevel = yLevel;
		return this;
	}
	
	public OreCave withFluid(Block fluid) {
		this.fluid = fluid;
		return this;
	}
	
	public OreCave setDimension(int dim) {
		this.dim = dim;
		return this;
	}

	@SuppressWarnings("incomplete-switch")
	@SubscribeEvent
	public void onDecorate(DecorateBiomeEvent.Pre event) {
		
		World world = event.world;
		
		if(world.provider == null || world.provider.dimensionId != this.dim) return;
		
		if(this.noise == null) {
			this.noise = new NoiseGeneratorPerlin(new Random(event.world.getSeed() + (ore.getID() * 31) + yLevel), 2);
		}
		
		int cX = event.chunkX;
		int cZ = event.chunkZ;
		
		double scale = 0.01D;
		
		for(int x = cX + 8; x < cX + 24; x++) {
			for(int z = cZ + 8; z < cZ + 24; z++) {
				
				double n = noise.func_151601_a(x * scale, z * scale);
				
				if(n > threshold) {
					int range = (int)((n - threshold) * rangeMult);
					
					if(range > maxRange)
						range = (maxRange * 2) - range;
					
					if(range < 0)
						continue;
					
					for(int y = yLevel - range; y <= yLevel + range; y++) {
						Block genTarget = world.getBlock(x, y, z);
						
						if(genTarget.isNormalCube() && (genTarget.getMaterial() == Material.rock || genTarget.getMaterial() == Material.ground) && genTarget.isReplaceableOreGen(world, x, y, z, Blocks.stone)) {
							
							boolean shouldGen = false;
							boolean canGenFluid = event.rand.nextBoolean();
							
							for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
								Block neighbor = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
								if(neighbor.getMaterial() == Material.air || neighbor instanceof BlockStalagmite) {
									shouldGen = true;
								}
								
								if(shouldGen && (fluid == null || !canGenFluid))
									break;
								
								if(fluid != null) {
									switch(dir) {
									case UP: if(neighbor.getMaterial() != Material.air && !(neighbor instanceof BlockStalagmite)) canGenFluid = false; break;
									case DOWN: if(!neighbor.isNormalCube()) canGenFluid = false; break;
									case NORTH:
									case SOUTH:
									case EAST:
									case WEST:
										if(!neighbor.isNormalCube() && neighbor != fluid) canGenFluid = false; break;
									}
								}
							}
							
							if(fluid != null && canGenFluid) {
								world.setBlock(x, y, z, fluid, 0, 2);
								world.setBlock(x, y - 1, z, ore.block, ore.meta, 2);
								
								for(int i = 2; i < 6; i++) {
									ForgeDirection dir = ForgeDirection.getOrientation(i);
									int clX = x + dir.offsetX;
									int clZ = z + dir.offsetZ;
									Block neighbor = world.getBlock(clX, y, clZ);
									
									if(neighbor.isNormalCube())
										world.setBlock(clX, y, clZ, ore.block, ore.meta, 2);
								}
								
							} else if(shouldGen) {
								world.setBlock(x, y, z, ore.block, ore.meta, 2);
							}
							
						} else {
							
							if((genTarget.getMaterial() == Material.air || !genTarget.isNormalCube()) && event.rand.nextInt(5) == 0 && !genTarget.getMaterial().isLiquid()) {
								
								if(ModBlocks.stalactite.canPlaceBlockAt(world, x, y, z)) {
									world.setBlock(x, y, z, ModBlocks.stalactite, BlockStalagmite.getMetaFromResource(ore.meta), 2);
								} else {
									if(ModBlocks.stalagmite.canPlaceBlockAt(world, x, y, z)) {
										world.setBlock(x, y, z, ModBlocks.stalagmite, BlockStalagmite.getMetaFromResource(ore.meta), 2);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
