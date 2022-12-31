package com.hbm.wiaj;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveHandlerMP;

@SideOnly(Side.CLIENT)
public class FauxWorld extends World {

	public FauxWorld(String p_i45368_2_, WorldProvider p_i45368_3_, WorldSettings p_i45368_4_, Profiler p_i45368_5_) {
		super(new SaveHandlerMP(), "FauxWorld", p_i45368_3_, p_i45368_4_, p_i45368_5_);
	}

	@Override
	protected IChunkProvider createChunkProvider() {
		return null;
	}

	@Override
	protected int func_152379_p() {
		return 0;
	}

	@Override
	public Entity getEntityByID(int id) {
		return null;
	}
}
