package com.hbm.world.feature;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class OreLayer3DMoon {

	NoiseGeneratorPerlin noiseX;
	NoiseGeneratorPerlin noiseY;
	NoiseGeneratorPerlin noiseZ;
	
	Block block;
	int meta;
	
	public OreLayer3DMoon(Block block, int meta) {
		this.block = block;
		this.meta = meta;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onDecorate(DecorateBiomeEvent.Pre event) {

		if(this.noiseX == null) this.noiseX = new NoiseGeneratorPerlin(new Random(event.world.getSeed() + 101), 4);
		if(this.noiseY == null) this.noiseY = new NoiseGeneratorPerlin(new Random(event.world.getSeed() + 102), 4);
		if(this.noiseZ == null) this.noiseZ = new NoiseGeneratorPerlin(new Random(event.world.getSeed() + 103), 4);

		World world = event.world;
		
		if(world.provider.dimensionId != 15)//& world.provider.dimensionId != 15
			return;
		
		int cX = event.chunkX;
		int cZ = event.chunkZ;

		double scaleH = 0.04D;
		double scaleV = 0.25D;
		
		for(int x = cX + 8; x < cX + 24; x++) {
			for(int z = cZ + 8; z < cZ + 24; z++) {
				for(int y = 64; y > 5; y--) {
					double nX = this.noiseX.func_151601_a(y * scaleV, z * scaleH);
					double nY = this.noiseY.func_151601_a(x * scaleH, z * scaleH);
					double nZ = this.noiseZ.func_151601_a(x * scaleH, y * scaleV);
					
					if(nX * nY * nZ > 220) {
						Block target = world.getBlock(x, y, z);
						
						if(target.isNormalCube() && target.getMaterial() == Material.rock) {
							world.setBlock(x, y, z, block, meta, 2);
						}
					}
				}
			}
		}
	}
}
