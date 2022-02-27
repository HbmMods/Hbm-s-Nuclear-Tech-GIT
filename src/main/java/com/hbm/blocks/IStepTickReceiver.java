package com.hbm.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IStepTickReceiver {

	public void onPlayerStep(World world, int x, int y, int z, EntityPlayer player);
}
