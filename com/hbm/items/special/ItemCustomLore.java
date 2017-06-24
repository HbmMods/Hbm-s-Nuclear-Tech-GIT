package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemCustomLore extends ItemRadioactive {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.flame_pony)
		{
			//list.add("Blue horse beats yellow horse, look it up!");
			list.add("Yellow horse beats blue horse, that's a proven fact!");
		}
		if(this == ModItems.flame_conspiracy)
		{
			list.add("Steel beams can't melt jet fuel!");
		}
		if(this == ModItems.flame_politics)
		{
			list.add("Donald Duck will build the wall!");
		}
		if(this == ModItems.flame_opinion)
		{
			list.add("Well, I like it...");
		}

		if(this == ModItems.ingot_neptunium)
		{
			if(MainRegistry.polaroidID == 11) {
				list.add("Pretty weird...you know what I mean, right?");
				list.add("All of it. The lore, the polaroid, the...");
			} else
				list.add("That one's my favourite!");
		}

		if(this == ModItems.pellet_rtg)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Contains ~100% Pu238 oxide.");
			else
				list.add("RTG fuel pellet for infinite energy! (almost)");
		}

		if(this == ModItems.pellet_rtg_weak)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Meh.");
			else
				list.add("Cheaper and weaker pellet, now with more U238!");
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
			list.add("");
			list.add("If you tell anybody about this, I will");
			list.add("tear your living guts out and use them to");
			list.add("grease the treads of my tank.");
			list.add("Got that? Good.");
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
		
		if(this == ModItems.fuse)
		{
			list.add("This item is needed for every large");
			list.add("nuclear reactor, as it allows the");
			list.add("reactor to generate electricity and");
			list.add("use up it's fuel. Removing the fuse");
			list.add("from a reactor will instantly shut");
			list.add("it down.");
		}
		
		if(this == ModItems.rod_lithium)
		{
			list.add("Turns into Tritium Rod");
		}
		
		if(this == ModItems.rod_dual_lithium)
		{
			list.add("Turns into Dual Tritium Rod");
		}
		
		if(this == ModItems.rod_quad_lithium)
		{
			list.add("Turns into Quad Tritium Rod");
		}
		
		if(this == ModItems.tritium_deuterium_cake)
		{
			list.add("Not actual cake, but great");
			list.add("universal fusion fuel!");
		}
		
		if(this == ModItems.dust)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Another one bites the dust!");
			else
				list.add("I hate dust!");
		}
		
		if(this == ModItems.ingot_combine_steel)
		{
			/*list.add("\"I mean, it's a verb for crying out loud.");
			list.add("The aliens aren't verbs. They're nouns!\"");
			list.add("\"Actually, I think it's also the name");
			list.add("of some kind of farm equipment, like a");
			list.add("thresher or something.\"");
			list.add("\"That's even worse. Now we have a word");
			list.add("that could mean 'to mix things together',");
			list.add("a piece of farm equipment, and let's see...");
			list.add("oh yea, it can also mean 'the most advanced");
			list.add("form of life in the known universe'.\"");
			list.add("\"So?\"");
			list.add("\"'So?' C'mon man, they're ALIENS!\"");*/
			list.add("*insert Freeman's Mind reference here*");
		}
		
		if(this == ModItems.gun_super_shotgun)
		{
			list.add("It's super broken!");
		}
		
		if(this == ModItems.screwdriver)
		{
			list.add("Could be used instead of a fuse...");
		}
		
		if(this == ModItems.overfuse)
		{
			list.add("Say what?");
		}
		
		if(this == ModItems.crystal_horn)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("An actual horn.");
			else
				list.add("Not an actual horn.");
		}
		
		if(this == ModItems.crystal_charred)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Also a real horn. Weird, right?");
			else
				list.add("High quality silicate, slightly burned.");
		}
		
		if(this == ModItems.crystal_energy)
		{
			list.add("Densely packed energy powder.");
			list.add("Not edible.");
		}
		
		if(this == ModItems.pellet_coolant)
		{
			list.add("Required for cyclotron operation.");
			list.add("Do NOT operate cyclotron without it!");
		}
		
		if(this == ModItems.upgrade_speed_1)
		{
			list.add("Mining drill speed upgrade");
			list.add("Effect: Delay -15 (from 50)");
			list.add("Drawback: Power consumption +300 (from 100)");
		}
		
		if(this == ModItems.upgrade_speed_2)
		{
			list.add("Mining drill speed upgrade");
			list.add("Effect: Delay -30 (from 50)");
			list.add("Drawback: Power consumption +600 (from 100)");
		}
		
		if(this == ModItems.upgrade_speed_3)
		{
			list.add("Mining drill speed upgrade");
			list.add("Effect: Delay -45 (from 50)");
			list.add("Drawback: Power consumption +900 (from 100)");
		}
		
		if(this == ModItems.upgrade_effect_1)
		{
			list.add("Mining drill effectiveness upgrade");
			list.add("Effect: Radius +1 (from 1)");
			list.add("Drawback: Power consumption +80 (from 100)");
		}
		
		if(this == ModItems.upgrade_effect_2)
		{
			list.add("Mining drill effectiveness upgrade");
			list.add("Effect: Radius +2 (from 1)");
			list.add("Drawback: Power consumption +160 (from 100)");
		}
		
		if(this == ModItems.upgrade_effect_3)
		{
			list.add("Mining drill effectiveness upgrade");
			list.add("Effect: Radius +3 (from 1)");
			list.add("Drawback: Power consumption +240 (from 100)");
		}
		
		if(this == ModItems.upgrade_power_1)
		{
			list.add("Mining drill power saving upgrade");
			list.add("Effect: Power consumption -30 (from 100)");
			list.add("Drawback: Delay +5 (from 50)");
		}
		
		if(this == ModItems.upgrade_power_2)
		{
			list.add("Mining drill power saving upgrade");
			list.add("Effect: Power consumption -60 (from 100)");
			list.add("Drawback: Delay +10 (from 50)");
		}
		
		if(this == ModItems.upgrade_power_3)
		{
			list.add("Mining drill power saving upgrade");
			list.add("Effect: Power consumption -90 (from 100)");
			list.add("Drawback: Delay +15 (from 50)");
		}
		
		if(this == ModItems.upgrade_fortune_1)
		{
			list.add("Mining drill fortune upgrade");
			list.add("Effect: Fortune +1 (from 0");
			list.add("Drawback: Delay +15 (from 50)");
		}
		
		if(this == ModItems.upgrade_fortune_2)
		{
			list.add("Mining drill fortune upgrade");
			list.add("Effect: Fortune +2 (from 0");
			list.add("Drawback: Delay +30 (from 50)");
		}
		
		if(this == ModItems.upgrade_fortune_3)
		{
			list.add("Mining drill fortune upgrade");
			list.add("Effect: Fortune +3 (from 0");
			list.add("Drawback: Delay +45 (from 50)");
		}
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

    	if(this == ModItems.nugget_euphemium || this == ModItems.ingot_euphemium || this == ModItems.rod_quad_euphemium || this == ModItems.watch || this == ModItems.powder_iodine || this == ModItems.powder_thorium || this == ModItems.powder_neodymium || this == ModItems.powder_neptunium || this == ModItems.powder_astatine || this == ModItems.powder_caesium || this == ModItems.powder_strontium || this == ModItems.powder_cobalt || this == ModItems.powder_bromine || this == ModItems.powder_niobium || this == ModItems.powder_tennessine || this == ModItems.powder_cerium)
    	{
    		return EnumRarity.epic;
    	}
    	
    	if(this == ModItems.rod_schrabidium || this == ModItems.rod_dual_schrabidium || this == ModItems.rod_quad_schrabidium || this == ModItems.ingot_schrabidium || this == ModItems.nugget_schrabidium || this == ModItems.plate_schrabidium || this == ModItems.cell_sas3 || this == ModItems.powder_schrabidium || this == ModItems.wire_schrabidium || this == ModItems.circuit_schrabidium || this == ModItems.gun_revolver_schrabidium_ammo)
    	{
    		return EnumRarity.rare;
    	}
    	
    	if(this == ModItems.gun_revolver_cursed_ammo || this == ModItems.plate_paa || this == ModItems.gun_mp_ammo || this == ModItems.powder_power || this == ModItems.ingot_australium || this == ModItems.ingot_weidanium || this == ModItems.ingot_reiium || this == ModItems.ingot_unobtainium || this == ModItems.ingot_daffergon || this == ModItems.ingot_verticium || this == ModItems.nugget_australium || this == ModItems.nugget_weidanium || this == ModItems.nugget_reiium || this == ModItems.nugget_unobtainium || this == ModItems.nugget_daffergon || this == ModItems.nugget_verticium || this == ModItems.powder_australium || this == ModItems.powder_weidanium || this == ModItems.powder_reiium || this == ModItems.powder_unobtainium || this == ModItems.powder_daffergon || this == ModItems.powder_verticium)
    	{
    		return EnumRarity.uncommon;
    	}
    	
		return EnumRarity.common;
    }

}
