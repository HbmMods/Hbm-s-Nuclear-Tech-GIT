package com.hbm.items.armor;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class JetpackPart extends ItemEnumMulti {

	public JetpackPart() {
		super(EnumJetpackPart.class, true, true);
	}

	public static enum EnumJetpackPart {
		TANK_COALGAS,
		TANK_KEROSENE,
		TANK_JETFUEL,
		EXTENSION_TANKS,
		THRUSTERS,
		THRUSTER_LARGE,
		STABILIZER,
		WINGS
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		ItemStack jetpack = getWornJetpack(player);
		if(jetpack == null) return stack;
		//TBI
		return stack;
	}
	
	public static ItemStack getWornJetpack(EntityPlayer player) {
		ItemStack stack = player.inventory.armorInventory[2];
		
		if(stack != null) {
			
			if(stack.getItem() == ModItems.jetpack_modular) {
				return stack;
			}
			
			ItemStack mod = ArmorModHandler.pryMods(stack)[ArmorModHandler.plate_only];
			
			if(mod != null && mod.getItem() == ModItems.jetpack_modular) {
				return mod;
			}
		}
		
		return null;
	}
}
