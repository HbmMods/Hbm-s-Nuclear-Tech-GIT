package com.hbm.items.food;

import java.util.List;

import com.hbm.config.VersatileConfig;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCanteen extends Item {

	public ItemCanteen(int cooldown) {

		this.setMaxDamage(cooldown);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {

		if(stack.getItemDamage() > 0 && entity.ticksExisted % 20 == 0)
			stack.setItemDamage(stack.getItemDamage() - 1);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		stack.setItemDamage(stack.getMaxDamage());

		if(this == ModItems.canteen_vodka) {
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, 10 * 20, 0));
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 2));
		}

		VersatileConfig.applyPotionSickness(player, 5);

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 10;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.drink;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(stack.getItemDamage() == 0 && !VersatileConfig.hasPotionSickness(player))
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		if(this == ModItems.canteen_vodka) {
			list.add("Cooldown: 3 minutes");
			list.add("Nausea I for 10 seconds");
			list.add("Strength III for 30 seconds");
			list.add("");

			if(MainRegistry.polaroidID == 11)
				// list.add("Why sipp when you can succ?");
				list.add("Time to get hammered & sickled!");
			else
				list.add("Smells like disinfectant, tastes like disinfectant.");
		}
	}

}
