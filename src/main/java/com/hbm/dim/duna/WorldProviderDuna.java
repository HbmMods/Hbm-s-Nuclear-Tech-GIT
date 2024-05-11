package com.hbm.dim.duna;

import com.hbm.dim.WorldProviderCelestial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderDuna extends WorldProviderCelestial {

    @Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerDuna(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Duna";
	}
	
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderDuna(this.worldObj, this.getSeed(), false);
    }

    @Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderDuna();
	}

}