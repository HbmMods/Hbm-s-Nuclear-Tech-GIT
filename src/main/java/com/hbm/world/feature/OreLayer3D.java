package com.hbm.world.feature;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class OreLayer3D {

	NoiseGeneratorPerlin noiseX;
	NoiseGeneratorPerlin noiseY;
	NoiseGeneratorPerlin noiseZ;
	
	Block block;
	int meta;
	
	public OreLayer3D(Block block, int meta) {
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
		
		if(world.provider.dimensionId != 0)
			return;
		
		int cX = event.chunkX;
		int cZ = event.chunkZ;

		double scaleH = 0.02D;
		double scaleV = 0.2D;
		
		for(int x = cX + 8; x < cX + 24; x++) {
			for(int z = cZ + 8; z < cZ + 24; z++) {
				
				double nY = this.noiseY.func_151601_a(x * scaleH, z * scaleH);
				
				if(nY > 4) {
					
					for(int y = 64; y > 5; y--) {
						double nX = this.noiseX.func_151601_a(y * scaleV, z * scaleH);
						double nZ = this.noiseZ.func_151601_a(x * scaleH, y * scaleV);
						
						if(nX > 4 && nZ > 4) {
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
}
