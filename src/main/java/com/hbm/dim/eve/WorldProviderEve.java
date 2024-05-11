package com.hbm.dim.eve;

import com.hbm.dim.WorldProviderCelestial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderEve extends WorldProviderCelestial {

    @Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerEve(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Eve";
	}
	
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderEve(this.worldObj, this.getSeed(), false);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderEve();
	}

}