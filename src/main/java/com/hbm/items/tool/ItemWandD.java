package com.hbm.items.tool;

import java.util.List;

import com.hbm.lib.Library;
import com.hbm.world.dungeon.Ruin001;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemWandD extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			return stack;
		
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1);
		
		if(pos != null) {
			
			int x = pos.blockX;
			int z = pos.blockZ;
			int y = world.getHeightValue(x, z);

			new Ruin001().generate_r0(world, world.rand, x, y - 8, z);
			
			//CellularDungeonFactory.test.generate(world, x, y, z, world.rand);
		}
		
		return stack;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used for debugging purposes.");
	}
}
