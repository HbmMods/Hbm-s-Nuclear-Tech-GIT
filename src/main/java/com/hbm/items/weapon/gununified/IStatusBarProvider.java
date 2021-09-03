package com.hbm.items.weapon.gununified;

import java.util.List;

import com.hbm.util.Tuple.Triplet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IStatusBarProvider {

	public void provideBars(ItemStack stack, EntityPlayer player, List<Triplet<Double, Integer, Integer>> bars);
}
