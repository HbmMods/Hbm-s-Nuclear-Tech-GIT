package com.hbm.dim.Ike;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.WorldProviderCelestial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderIke extends WorldProviderCelestial {
	
    @Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(new BiomeGenIke(SpaceConfig.ikeBiome), dimensionId);
	}

	@Override
	public String getDimensionName() {
		return "Ike";
	}
	
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderIke(this.worldObj, this.getSeed(), false);
    }

    @Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderIke();
	}

}