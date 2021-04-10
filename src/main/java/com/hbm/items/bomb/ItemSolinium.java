package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.special.ItemHazard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemSolinium extends ItemHazard {
	
	public ItemSolinium(float radiation, boolean blinding) {
		super(radiation, false, blinding);
	}
	
	public ItemSolinium(float radiation, float drx, boolean blinding)
	{
		this.getModule().addRadiation(radiation);
		this.getModule().addDigamma(drx);
		if (blinding)
			this.getModule().addBlinding();
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used in:");
		list.add("Solinium Bomb");
		super.addInformation(itemstack, player, list, bool);
	}
}
