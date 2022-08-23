package com.hbm.wiaj.actions;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

import net.minecraft.block.Block;

/**
 * Simple action that places one block instantly with no delay
 * @author hmb
 */
public class ActionSetBlock implements IJarAction {
	
	int x;
	int y;
	int z;
	Block b;
	int meta;
	
	public ActionSetBlock(int x, int y, int z, Block b) {
		this(x, y, z, b, 0);
	}
	
	public ActionSetBlock(int x, int y, int z, Block b, int meta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.b = b;
		this.meta = meta;
	}

	@Override
	public void act(WorldInAJar world, JarScene scene) {
		world.setBlock(x, y, z, b, meta);
	}

	@Override
	public int getDuration() {
		return 0;
	}
}
