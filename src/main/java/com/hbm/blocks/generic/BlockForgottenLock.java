package com.hbm.blocks.generic;

import com.hbm.blocks.machine.BlockPillar;
import com.hbm.items.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockForgottenLock extends BlockPillar {

	public BlockForgottenLock(Material mat, String top) {
		super(mat, top);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		// placeholder
		if(player.getHeldItem() != null) {
			boolean cracked = player.getHeldItem().getItem() == ModItems.key_red_cracked;
			if((player.getHeldItem().getItem() == ModItems.key_red || cracked) && side != 0 && side != 1) {
				if(cracked) player.getHeldItem().stackSize--;
				if(world.isRemote) return true;
				int meta = world.getBlockMetadata(x, y, z);
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				
				generate(world, x, y, z, meta, dir);
				
				world.playSoundAtEntity(player, "hbm:block.lockOpen", 1.0F, 1.0F);
				return true;
			}
		}
		
		return false;
	}
	
	public static void generate(World world, int x, int y, int z, int meta, ForgeDirection dir) {
		
	}
}
