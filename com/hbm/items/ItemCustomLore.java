package com.hbm.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCustomLore extends Item {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.flame_pony)
		{
			//list.add("Stop being so grumpy, just smeel.");
			list.add("Don't compare the color codes, you know exactly who you are!!");
		}
		if(this == ModItems.flame_conspiracy)
		{
			list.add("Steel beams can't melt jet fuel!");
		}
		if(this == ModItems.flame_politics)
		{
			list.add("Dolan Tremp 4 president!");
		}
		if(this == ModItems.flame_opinion)
		{
			list.add("Well, I like it...");
		}

		if(this == ModItems.ingot_neptunium)
		{
			list.add("That one's my favourite!");
		}

		if(this == ModItems.pellet_rtg)
		{
			list.add("RTG fuel pellet for infinite energy! (almost)");
		}

		if(this == ModItems.pellet_cluster)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Adds some extra boom!");
		}

		if(this == ModItems.powder_fire)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Incendiary bombs are fun!");
		}

		if(this == ModItems.powder_poison)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Warning: Poisonous!");
		}

		if(this == ModItems.pellet_gas)
		{
			list.add("Used in multi purpose bombs:");
			list.add("*cough cough* Halp pls!");
		}

		if(this == ModItems.rod_uranium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Plutonium Rod");
		}
		
		if(this == ModItems.rod_u235)
		{
			list.add("Worth 3 operations in breeding reactor");
			list.add("Worth 15 operations in nuclear powered furnace");
			list.add("Turns into Neptunium Rod");
		}
		
		if(this == ModItems.rod_u238)
		{
			list.add("Worth 1 operation in breeding reactor");
			list.add("Worth 5 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 239 Rod");
		}

		if(this == ModItems.rod_neptunium)
		{
			list.add("Worth 3 operations in breeding reactor");
			list.add("Worth 15 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 238 Rod");
		}

		if(this == ModItems.rod_plutonium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Lead Rod");
		}

		if(this == ModItems.rod_pu238)
		{
			list.add("Worth 5 operations in breeding reactor");
			list.add("Worth 25 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 239 Rod");
		}
		
		if(this == ModItems.rod_pu239)
		{
			list.add("Worth 3 operations in breeding reactor");
			list.add("Worth 15 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 240 Rod");
		}
		
		if(this == ModItems.rod_pu240)
		{
			list.add("Worth 1 operation in breeding reactor");
			list.add("Worth 5 operations in nuclear powered furnace");
			list.add("Turns into Lead Rod");
		}
		
		if(this == ModItems.rod_schrabidium)
		{
			list.add("Worth 15 operations in breeding reactor");
			list.add("Worth 75 operations in nuclear powered furnace");
		}

		if(this == ModItems.rod_dual_uranium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium Rod");
		}
		
		if(this == ModItems.rod_dual_u235)
		{
			list.add("Worth 6 operations in breeding reactor");
			list.add("Worth 30 operations in nuclear powered furnace");
			list.add("Turns into Dual Neptunium Rod");
		}
		
		if(this == ModItems.rod_dual_u238)
		{
			list.add("Worth 2 operations in breeding reactor");
			list.add("Worth 10 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 239 Rod");
		}

		if(this == ModItems.rod_dual_neptunium)
		{
			list.add("Worth 6 operations in breeding reactor");
			list.add("Worth 30 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 238 Rod");
		}

		if(this == ModItems.rod_dual_plutonium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Dual Lead Rod");
		}

		if(this == ModItems.rod_dual_pu238)
		{
			list.add("Worth 10 operations in breeding reactor");
			list.add("Worth 50 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 239 Rod");
		}
		
		if(this == ModItems.rod_dual_pu239)
		{
			list.add("Worth 6 operations in breeding reactor");
			list.add("Worth 30 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 240 Rod");
		}
		
		if(this == ModItems.rod_dual_pu240)
		{
			list.add("Worth 2 operations in breeding reactor");
			list.add("Worth 10 operations in nuclear powered furnace");
			list.add("Turns into Dual Lead Rod");
		}
		
		if(this == ModItems.rod_dual_schrabidium)
		{
			list.add("Worth 30 operations in breeding reactor");
			list.add("Worth 150 operations in nuclear powered furnace");
		}

		if(this == ModItems.rod_quad_uranium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium Rod");
		}
		
		if(this == ModItems.rod_quad_u235)
		{
			list.add("Worth 12 operations in breeding reactor");
			list.add("Worth 60 operations in nuclear powered furnace");
			list.add("Turns into Quad Neptunium Rod");
		}
		
		if(this == ModItems.rod_quad_u238)
		{
			list.add("Worth 4 operations in breeding reactor");
			list.add("Worth 20 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 239 Rod");
		}

		if(this == ModItems.rod_quad_neptunium)
		{
			list.add("Worth 12 operations in breeding reactor");
			list.add("Worth 60 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 238 Rod");
		}

		if(this == ModItems.rod_quad_plutonium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Quad Lead Rod");
		}

		if(this == ModItems.rod_quad_pu238)
		{
			list.add("Worth 20 operations in breeding reactor");
			list.add("Worth 100 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 239 Rod");
		}
		
		if(this == ModItems.rod_quad_pu239)
		{
			list.add("Worth 12 operations in breeding reactor");
			list.add("Worth 60 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 240 Rod");
		}
		
		if(this == ModItems.rod_quad_pu240)
		{
			list.add("Worth 4 operations in breeding reactor");
			list.add("Worth 20 operations in nuclear powered furnace");
			list.add("Turns into Quad Lead Rod");
		}
		
		if(this == ModItems.rod_quad_schrabidium)
		{
			list.add("Worth 60 operations in breeding reactor");
			list.add("Worth 300 operations in nuclear powered furnace");
		}
		
		if(this == ModItems.igniter)
		{
			/*list.add("Don't get me wrong, but you use it");
			list.add("by directly right-clicking the");
			list.add("prototype. And yes, you also die. Why?");
			list.add("Because you stand two meters next to");
			list.add("the bomb, you fool.");*/
			list.add("(Used by right-clicking the Prototype)");
			list.add("It's a green metal handle with a");
			list.add("bright red button and a small lid.");
			list.add("At the bottom, the initials N.E. are");
			list.add("engraved. Whoever N.E. was, he had");
			list.add("a great taste in shades of green.");
		}
		
		if(this == ModItems.rod_quad_euphemium)
		{
			list.add("A quad fuel rod which contains a");
			list.add("very small ammount of a strange new element.");
		}
		
		if(this == ModItems.ingot_euphemium)
		{
			list.add("A very special and yet strange element.");
		}
		
		if(this == ModItems.nugget_euphemium)
		{
			list.add("A small piece of a pink metal.");
			list.add("It's properties are still unknown,");
			list.add("DEAL WITH IT carefully.");
		}
		
		if(this == ModItems.watch)
		{
			list.add("A small blue pocket watch.");
			list.add("It's glass has a few cracks in it,");
			list.add("and some shards are missing.");
			list.add("It stopped ticking at 2:34.");
		}
	}

    public EnumRarity getRarity(ItemStack p_77613_1_) {

    	if(this == ModItems.nugget_euphemium || this == ModItems.ingot_euphemium || this == ModItems.rod_quad_euphemium || this == ModItems.watch)
    	{
    		return EnumRarity.epic;
    	}
    	
    	if(this == ModItems.rod_schrabidium || this == ModItems.rod_dual_schrabidium || this == ModItems.rod_quad_schrabidium || this == ModItems.ingot_schrabidium || this == ModItems.nugget_schrabidium || this == ModItems.plate_schrabidium || this == ModItems.cell_sas3 || this == ModItems.powder_schrabidium || this == ModItems.wire_schrabidium || this == ModItems.circuit_schrabidium || this == ModItems.gun_revolver_schrabidium_ammo)
    	{
    		return EnumRarity.rare;
    	}
    	
    	if(this == ModItems.gun_revolver_cursed_ammo)
    	{
    		return EnumRarity.uncommon;
    	}
    	
		return EnumRarity.common;
    }

}
