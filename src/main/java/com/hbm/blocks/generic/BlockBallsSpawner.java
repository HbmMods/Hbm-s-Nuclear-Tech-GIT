package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockBallsSpawner extends Block {

	public BlockBallsSpawner(Material mat) {
		super(mat);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float fx, float fy, float fz) {
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.mech_key) {
			player.getHeldItem().stackSize--;
			
			if(!world.isRemote) {
				
				EntityBOTPrimeHead bot = new EntityBOTPrimeHead(world);
				bot.setPositionAndRotation(x + 0.5, 300, z + 0.5, 0, 0);
				bot.motionY = -1.0;
				bot.onSpawnWithEgg(null);
				world.spawnEntityInWorld(bot);
				
				world.setBlock(x, y, z, ModBlocks.brick_jungle_cracked);
			}
		}
		
		return false;
	}

}
