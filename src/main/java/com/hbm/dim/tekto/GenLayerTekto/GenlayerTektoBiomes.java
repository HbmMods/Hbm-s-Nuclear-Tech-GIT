package com.hbm.dim.tekto.GenLayerTekto;

import com.hbm.dim.eve.biome.BiomeGenBaseEve;
import com.hbm.dim.tekto.biome.BiomeGenBaseTekto;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenlayerTektoBiomes extends GenLayer {

    private static final BiomeGenBase[] biomes = new BiomeGenBase[] { BiomeGenBaseTekto.polyvinylPlains, BiomeGenBaseTekto.halogenHills };
   // protected BiomeGenBase[] rareBiomes = { BiomeGenBaseEve.eveSeismicPlains };

    public GenlayerTektoBiomes(long l) {
        super(l);
    }

    @Override
    public int[] getInts(int x, int z, int width, int depth) {
        int[] dest = IntCache.getIntCache(width * depth);

        for(int dz = 0; dz < depth; dz++) {
            for(int dx = 0; dx < width; dx++) {
                this.initChunkSeed(dx + x, dz + z);

               /// if(this.nextInt(20) == 0) {
                 ///   dest[dx + dz * width] = this.rareBiomes[this.nextInt(this.rareBiomes.length)].biomeID;
                ///} else {
                dest[dx + dz * width] = biomes[this.nextInt(biomes.length)].biomeID;
               /// }
            }
        }
        return dest;
    }
}
