package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.generic.BlockRadShield;
import com.hbm.interfaces.IBlockRadShield;
import com.hbm.modules.BlockRadShieldModule;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBlockRadShield extends ItemBlockLore
{
	BlockRadShieldModule module;
	public ItemBlockRadShield(Block blockIn)
	{
		super(blockIn);
		if (blockIn instanceof BlockRadShield)
			module = ((IBlockRadShield) blockIn).getShieldModule();
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(itemstack, player, list, bool);
		if (module != null)
			module.addInformation(itemstack, player, list, bool);
	}
}
