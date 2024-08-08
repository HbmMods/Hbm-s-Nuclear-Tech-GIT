package com.hbm.dim.eve.GenLayerEve;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenElectricVolcano extends WorldGenerator {

	public Block volBlock;
	public Block mainMat;

	public int height;
	public int width;

	public Block surfaceBlock;
	public Block stoneBlock;

	public WorldGenElectricVolcano(int height, int width, Block surfaceBlock, Block stoneBlock) {
		super();
		this.height = height;
		this.width = width;
		this.surfaceBlock = surfaceBlock;
		this.stoneBlock = stoneBlock;

		this.volBlock = ModBlocks.basalt;
		this.mainMat = ModBlocks.geysir_electric;
	}

	public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {

		if(p_76484_2_.nextInt(10) != 0) {
			return false;
		}
		while (p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_) && p_76484_4_ > 2) {
			--p_76484_4_;
		}

		if(p_76484_1_.getBlock(p_76484_3_, p_76484_4_, p_76484_5_) != surfaceBlock) {
			return false;
		} else {
			p_76484_4_ += p_76484_2_.nextInt(1);
			int l = p_76484_2_.nextInt(4) + height;
			int i1 = l / 4 + p_76484_2_.nextInt(width);

			if(i1 > 1 && p_76484_2_.nextInt(5) == 0) {
				p_76484_2_.nextInt(30);
			}

			int j1;
			int k1;
			int l1;

			for(j1 = 0; j1 < l; ++j1) {
				float f = (1.0F - (float) j1 / (float) l) * (float) i1;
				k1 = MathHelper.ceiling_float_int(f);

				for(l1 = -k1; l1 <= k1; ++l1) {
					float f1 = (float) MathHelper.abs_int(l1) - 0.25F;

					for(int i2 = -k1; i2 <= k1; ++i2) {
						float f2 = (float) MathHelper.abs_int(i2) - 0.75F;

						if((l1 == 0 && i2 == 0 || f1 * f1 + f2 * f2 <= f * f) && (l1 != -k1 && l1 != k1 && i2 != -k1 && i2 != k1 || p_76484_2_.nextFloat() <= 0.75F)) {
							Block block = p_76484_1_.getBlock(p_76484_3_ + l1, p_76484_4_ + j1, p_76484_5_ + i2);

							if(block.getMaterial() == Material.air || block == surfaceBlock || block == stoneBlock) {
								p_76484_1_.setBlock(p_76484_3_ + l1, p_76484_4_ + j1, p_76484_5_ + i2, ModBlocks.basalt);
							}

							if(j1 != 0 && k1 > 1) {
								block = p_76484_1_.getBlock(p_76484_3_ + l1, p_76484_4_ - j1, p_76484_5_ + i2);

								if(block.getMaterial() == Material.air || block == surfaceBlock || block == stoneBlock) {
									this.func_150515_a(p_76484_1_, p_76484_3_ + l1, p_76484_4_ - j1, p_76484_5_ + i2, ModBlocks.ore_depth_nether_neodymium);
								}
							}
						}
						if((l1 == 0 && j1 == 0) || f1 * f1 + f2 * f2 > f * f) {
							Block block = p_76484_1_.getBlock(p_76484_3_ + l1, p_76484_4_ + j1, p_76484_5_ + i2);

							if(block.getMaterial() == Material.air || block == surfaceBlock || block == stoneBlock) {
								this.func_150515_a(p_76484_1_, p_76484_3_, p_76484_4_ + j1, p_76484_5_, Blocks.air);
							}
						}
						if((l1 == 0 && j1 == 0) || f1 * f1 + f2 * f2 > f * f) {
							Block block = p_76484_1_.getBlock(p_76484_3_ + l1, p_76484_4_ + j1, p_76484_5_ + i2);

							if(block.getMaterial() == Material.air || block == surfaceBlock || block == stoneBlock) {
								this.func_150515_a(p_76484_1_, p_76484_3_, p_76484_4_ + 5, p_76484_5_, ModBlocks.geysir_electric);
							}
						}
					}
				}
			}

			return true;
		}
	}
}