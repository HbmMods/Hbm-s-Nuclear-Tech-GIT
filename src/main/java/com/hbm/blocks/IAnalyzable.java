package com.hbm.blocks;

import net.minecraft.world.World;

import java.util.List;

public interface IAnalyzable {

	public List<String> getDebugInfo(World world, int x, int y, int z);
}
