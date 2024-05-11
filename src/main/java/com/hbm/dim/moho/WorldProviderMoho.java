package com.hbm.dim.moho;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.WorldProviderCelestial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderMoho extends WorldProviderCelestial {

    @Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(new BiomeGenMoho(SpaceConfig.mohoBiome), dimensionId);
	}

	@Override
	public String getDimensionName() {
		return "Moho";
	}
	
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderMoho(this.worldObj, this.getSeed(), false);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderMoho();
	}

}