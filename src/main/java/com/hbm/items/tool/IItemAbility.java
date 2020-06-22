package com.hbm.items.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IItemAbility {
	
	public void breakExtraBlock(World world, int x, int y, int z, EntityPlayer player, int refX, int refY, int refZ);
}
