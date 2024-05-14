package com.hbm.dim.laythe;

import com.hbm.dim.WorldProviderCelestial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderLaythe extends WorldProviderCelestial {

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerLaythe(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Laythe";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderLaythe(this.worldObj, this.getSeed(), false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderLaytheSunset();
	}

}