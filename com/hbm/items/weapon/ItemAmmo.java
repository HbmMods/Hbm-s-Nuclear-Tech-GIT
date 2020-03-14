package com.hbm.items.weapon;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemAmmo extends Item {

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		//12 GAUGE
		if(this == ModItems.ammo_12gauge_incendiary) {
			list.add(EnumChatFormatting.BLUE + "+ Incendiary");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_20gauge_slug) {
			list.add(EnumChatFormatting.BLUE + "+ Near-perfect accuracy");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.RED + "- Single projectile");
		}
		
		//20 GAUGE
		if(this == ModItems.ammo_20gauge_flechette) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Less bouncy");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_20gauge_incendiary) {
			list.add(EnumChatFormatting.BLUE + "+ Incendiary");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_20gauge_explosive) {
			list.add(EnumChatFormatting.BLUE + "+ Explosive");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_20gauge_caustic) {
			list.add(EnumChatFormatting.BLUE + "+ Toxic");
			list.add(EnumChatFormatting.BLUE + "+ Caustic");
			list.add(EnumChatFormatting.YELLOW + "* Not bouncy");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_20gauge_shock) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Stunning");
			list.add(EnumChatFormatting.BLUE + "+ EMP");
			list.add(EnumChatFormatting.YELLOW + "* Not bouncy");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_20gauge_wither) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Withering");
		}
		
		//.357 MAGNUM
		if(this == ModItems.ammo_357_desh) {
			list.add(EnumChatFormatting.BLUE + "+ Fits every .357 model");
			list.add(EnumChatFormatting.BLUE + "+ Above-average damage");
		}

		//.44 MAGNUM
		if(this == ModItems.ammo_44_ap) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_44_du) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Heavy metal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_44_pip) {
			list.add(EnumChatFormatting.BLUE + "+ Boxcar");
			list.add(EnumChatFormatting.RED + "- Highly decreased damage");
		}
		if(this == ModItems.ammo_44_bj) {
			list.add(EnumChatFormatting.BLUE + "+ Boat");
			list.add(EnumChatFormatting.RED + "- Highly decreased damage");
		}
		if(this == ModItems.ammo_44_rocket) {
			list.add(EnumChatFormatting.BLUE + "+ Rocket");
			list.add(EnumChatFormatting.YELLOW + "* Uhhhh");
		}
		if(this == ModItems.ammo_44_star) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Starmetal");
			list.add(EnumChatFormatting.RED + "- Highly decreased damage");
		}
		
		//5mm
		if(this == ModItems.ammo_5mm_explosive) {
			list.add(EnumChatFormatting.BLUE + "+ Explosive");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_5mm_du) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Heavy metal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_5mm_star) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Starmetal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		
		//9mm
		if(this == ModItems.ammo_9mm_ap) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_9mm_du) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Heavy metal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_9mm_rocket) {
			list.add(EnumChatFormatting.BLUE + "+ Rocket");
			list.add(EnumChatFormatting.YELLOW + "* Uhhhh");
		}
		
		//.22LR
		if(this == ModItems.ammo_22lr_ap) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		
		//.50 BMG
		if(this == ModItems.ammo_50bmg_incendiary) {
			list.add(EnumChatFormatting.BLUE + "+ Incendiary");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_50bmg_explosive) {
			list.add(EnumChatFormatting.BLUE + "+ Explosive");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_50bmg_du) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Heavy metal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_50bmg_star) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Starmetal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		
		//.50 AE
		if(this == ModItems.ammo_50ae_ap) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_50ae_du) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Heavy metal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_50ae_star) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Starmetal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		
		//84mm ROCKETS
		if(this == ModItems.ammo_rocket_he) {
			list.add(EnumChatFormatting.BLUE + "+ Increased blast radius");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_rocket_incendiary) {
			list.add(EnumChatFormatting.BLUE + "+ Incendiary explosion");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_rocket_shrapnel) {
			list.add(EnumChatFormatting.BLUE + "+ Shrapnel");
		}
		if(this == ModItems.ammo_rocket_emp) {
			list.add(EnumChatFormatting.BLUE + "+ EMP");
			list.add(EnumChatFormatting.RED + "- Decreased blast radius");
		}
		if(this == ModItems.ammo_rocket_glare) {
			list.add(EnumChatFormatting.BLUE + "+ Increased projectile speed");
			list.add(EnumChatFormatting.BLUE + "+ Incendiary explosion");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_rocket_toxic) {
			list.add(EnumChatFormatting.BLUE + "+ Chlorine gas");
			list.add(EnumChatFormatting.RED + "- No explosion");
			list.add(EnumChatFormatting.RED + "- Decreased projectile speed");
		}
		if(this == ModItems.ammo_rocket_sleek) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased blast radius");
			list.add(EnumChatFormatting.BLUE + "+ Not affected by gravity");
			list.add(EnumChatFormatting.YELLOW + "* Jolt");
		}
		if(this == ModItems.ammo_rocket_nuclear) {
			list.add(EnumChatFormatting.BLUE + "+ Nuclear");
			list.add(EnumChatFormatting.RED + "- Very highly increased wear");
			list.add(EnumChatFormatting.RED + "- Decreased projectile speed");
		}
		if(this == ModItems.ammo_rocket_rpc) {
			list.add(EnumChatFormatting.BLUE + "+ Chainsaw");
			list.add(EnumChatFormatting.BLUE + "+ Penetrating");
			list.add(EnumChatFormatting.BLUE + "+ Not affected by gravity");
			list.add(EnumChatFormatting.RED + "- Increased wear");
			list.add(EnumChatFormatting.RED + "- Non-explosive");
			list.add(EnumChatFormatting.YELLOW + "* Uhhhh");
		}
		
		//40mm GRENADES
		if(this == ModItems.ammo_grenade_he) {
			list.add(EnumChatFormatting.BLUE + "+ Increased blast radius");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_grenade_incendiary) {
			list.add(EnumChatFormatting.BLUE + "+ Incendiary explosion");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_grenade_toxic) {
			list.add(EnumChatFormatting.BLUE + "+ Chlorine gas");
			list.add(EnumChatFormatting.RED + "- No explosion");
		}
		if(this == ModItems.ammo_grenade_concussion) {
			list.add(EnumChatFormatting.BLUE + "+ Increased blast radius");
			list.add(EnumChatFormatting.RED + "- No block damage");
		}
		if(this == ModItems.ammo_grenade_finned) {
			list.add(EnumChatFormatting.BLUE + "+ Decreased gravity");
			list.add(EnumChatFormatting.RED + "- Decreased blast radius");
		}
		if(this == ModItems.ammo_grenade_sleek) {
			list.add(EnumChatFormatting.BLUE + "+ Increased blast radius");
			list.add(EnumChatFormatting.YELLOW + "* Jolt");
		}
		if(this == ModItems.ammo_grenade_nuclear) {
			list.add(EnumChatFormatting.BLUE + "+ Nuclear");
			list.add(EnumChatFormatting.BLUE + "+ Increased range");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		
		//FOLLY
		if(this == ModItems.ammo_folly) {
			list.add(EnumChatFormatting.BLUE + "+ Focused starmetal reaction blast");
		}
		if(this == ModItems.ammo_folly_nuclear) {
			list.add(EnumChatFormatting.BLUE + "+ Howitzer mini nuke shell");
		}
		if(this == ModItems.ammo_folly_du) {
			list.add(EnumChatFormatting.BLUE + "+ Howitzer 17kg U238 shell");
		}
	}

}
