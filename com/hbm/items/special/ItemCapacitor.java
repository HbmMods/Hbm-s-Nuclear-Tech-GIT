package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;

public class ItemCapacitor extends Item {

	public ItemCapacitor(int dura) {
		this.setMaxDamage(dura);
		this.setNoRepair();
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if (this == ModItems.redcoil_capacitor) {
			list.add("Right-click a block to negate positive charge.");
			list.add("Does not work in creative mode!");
			list.add("[Needed for Schrabidium Synthesis]");
		}
		if (this == ModItems.titanium_filter) {
			list.add("Can be used to remove waste");
			list.add("from a watz reactor!");
			list.add("[Needed for Watz Reaction]");
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_,
			float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (this == ModItems.redcoil_capacitor) {
			if (!player.isSneaking()) {
				if (stack.getItemDamage() > 0) {
					stack.setItemDamage((stack.getItemDamage() - 1));
					if (!world.isRemote) {
						world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 2.5F, true);
					}
					world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));

					return true;
				}
			}
		}

		return false;
	}
}
