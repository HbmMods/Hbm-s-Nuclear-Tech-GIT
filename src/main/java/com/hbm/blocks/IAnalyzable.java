package com.hbm.blocks;

import java.util.List;

import net.minecraft.world.World;

public interface IAnalyzable {

	public List<String> getDebugInfo(World world, int x, int y, int z);
}
