package com.hbm.world;

import com.hbm.handler.ImpactWorldHandler;
import com.hbm.handler.RogueWorldHandler;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class WorldChunkManagerNTM extends WorldChunkManager {
	
	public WorldChunkManagerNTM() {
	}
	
    /**
     * Return an adjusted version of a given temperature based on the y height
     */
	@Override
    @SideOnly(Side.CLIENT)
    public float getTemperatureAtHeight(float temp, int height)
    {
        return 0;
    }
}