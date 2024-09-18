package com.hbm.dim.moho.biome;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenMohoBasalt extends BiomeGenBaseMoho {

	public static final BiomeGenBase.Height height = new BiomeGenBase.Height(0, 0.224F);

	public BiomeGenMohoBasalt(int id) {
		super(id);
		this.setBiomeName("Moho Basalt Deltas");

		this.setHeight(height);

		this.topBlock = ModBlocks.basalt;
		this.fillerBlock = ModBlocks.basalt;
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] meta, int x, int z, double noise) {
		Block block = this.topBlock;
		byte b0 = (byte) (this.field_150604_aj & 255);
		Block block1 = this.fillerBlock;
		int k = -1;
		int l = (int) (noise / 8.0D + 8.0D + rand.nextDouble() * 0.50D);
		int bx = x & 15;
		int bz = z & 15;
		int s = blocks.length / 256;

		for(int by = 255; by >= 0; --by) {
			int i = (bz * 16 + bx) * s + by;

			boolean contour = (noise > -0.25D && noise < 1.0D) || (noise > 1.5D && noise < 2.0D) || (noise > 2.5D && noise < 3.0D);
			double offset = contour ? 0 : Math.pow(rand.nextDouble(), 4) * 8;

			if(by <= 0 + rand.nextInt(5)) {
				blocks[i] = Blocks.bedrock;
			} else {
				Block block2 = blocks[i];

				if(block2 != null && block2.getMaterial() != Material.air) {
					if(block2 == ModBlocks.moho_stone) {
						if(k == -1) {
							if(l <= 0) {
								block = null;
								b0 = 0;
								block1 = ModBlocks.moho_stone;
							} else if(by >= 59 && by <= 64) {
								block = this.topBlock;
								b0 = (byte) (this.field_150604_aj & 255);
								block1 = this.fillerBlock;
							}

							if(by < 63 && (block == null || block.getMaterial() == Material.air)) {
								if(this.getFloatTemperature(x, by, z) < 0.15F) {
									block = this.topBlock;
									b0 = 0;
								} else {
									block = this.topBlock;
									b0 = 0;
								}
							}

							k = l;

							if(by >= 62) {
								blocks[i] = block;
								meta[i] = b0;
							} else if(by < 56 - l) {
								block = null;
								block1 = ModBlocks.moho_stone;
								blocks[i] = Blocks.gravel;
							} else {
								blocks[i] = block1;
							}

							for(int oy = 0; oy < offset; oy++) {
								blocks[i+oy] = this.topBlock;
							}
						} else if(k > 0) {
							--k;
							blocks[i] = block1;
						}
					}
				} else {
					k = -1;
				}
			}
		}
	}

}