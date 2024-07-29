package com.hbm.dim;

public class WorldProviderEarth extends WorldProviderCelestial {

    @Override
    public void registerWorldChunkManager() {
        this.worldChunkMgr = terrainType.getChunkManager(worldObj);
    }

    @Override
    public String getDimensionName() {
        return "Earth";
    }

    @Override
    public boolean hasLife() {
        return true;
    }

    @Override
	public boolean canRespawnHere() {
		return true;
	}
    
}
