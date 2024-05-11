package com.hbm.dim.dres;

import com.hbm.dim.WorldProviderCelestial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderDres extends WorldProviderCelestial {
	
    @Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerDres(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Dres";
	}
	
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderDres(this.worldObj, this.getSeed(), false);
    }

    @Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderDres();
	}

}