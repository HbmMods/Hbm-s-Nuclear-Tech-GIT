package com.hbm.items.tool;

import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDigammaDiagnostic extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(!world.isRemote) {
			world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);
			ContaminationUtil.printDiagnosticData(player);
		}

		return stack;
	}
}
