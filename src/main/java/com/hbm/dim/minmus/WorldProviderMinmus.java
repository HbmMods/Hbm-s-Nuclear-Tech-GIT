package com.hbm.dim.minmus;

import com.hbm.dim.WorldProviderCelestial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderMinmus extends WorldProviderCelestial {
	
    @Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerMinmus(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Minmus";
	}
	
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderMinmus(this.worldObj, this.getSeed(), false);
    }

    @Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderMinmus();
	}

}