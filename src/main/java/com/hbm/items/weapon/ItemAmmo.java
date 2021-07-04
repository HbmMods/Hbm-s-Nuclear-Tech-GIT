package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

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
		if(this == ModItems.ammo_12gauge_shrapnel) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Extra bouncy");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_12gauge_du) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Penetrating");
			list.add(EnumChatFormatting.YELLOW + "* Heavy Metal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_12gauge_marauder) {
			list.add(EnumChatFormatting.BLUE + "+ Instantly removes annoying and unbalanced enemies");
			list.add(EnumChatFormatting.YELLOW + "* No drawbacks lole");
		}
		if(this == ModItems.ammo_12gauge_sleek) {
			list.add(EnumChatFormatting.YELLOW + "* Fires a tracer which summons a storm of DU-flechettes");
		}
		
		//20 GAUGE
		if(this == ModItems.ammo_20gauge_flechette) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Less bouncy");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_20gauge_slug) {
			list.add(EnumChatFormatting.BLUE + "+ Near-perfect accuracy");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_20gauge_incendiary) {
			list.add(EnumChatFormatting.BLUE + "+ Incendiary");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_20gauge_shrapnel) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Extra bouncy");
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
		if(this == ModItems.ammo_20gauge_sleek) {
			list.add(EnumChatFormatting.YELLOW + "* Fires a tracer which summons a storm of DU-flechettes");
		}
		
		//23mm
		if(this == ModItems.ammo_4gauge_flechette) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Less bouncy");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_4gauge_flechette_phosphorus) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(EnumChatFormatting.YELLOW + "* Twice the warcrime in a single round!");
			list.add(EnumChatFormatting.YELLOW + "* Less bouncy");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_4gauge_slug) {
			list.add(EnumChatFormatting.BLUE + "+ Near-perfect accuracy");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_explosive) {
			list.add(EnumChatFormatting.BLUE + "+ Explosive");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.YELLOW + "* It's a 40mm grenade that we squeezed to fit the barrel!");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
			list.add(EnumChatFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_semtex) {
			list.add(EnumChatFormatting.BLUE + "+ Explosive");
			list.add(EnumChatFormatting.BLUE + "+ Explosion drops all blocks");
			list.add(EnumChatFormatting.RED + "- No splash damage");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
			list.add(EnumChatFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_balefire) {
			list.add(EnumChatFormatting.BLUE + "+ Explosive");
			list.add(EnumChatFormatting.BLUE + "+ Balefire");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
			list.add(EnumChatFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_kampf) {
			list.add(EnumChatFormatting.BLUE + "+ Explosive");
			list.add(EnumChatFormatting.BLUE + "+ Rocket Propelled");
			list.add(EnumChatFormatting.BLUE + "+ Increased accuracy");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Increased wear");
			list.add(EnumChatFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_sleek) {
			list.add(EnumChatFormatting.YELLOW + "* Fires a tracer which summons a storm of DU-flechettes");
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
		if(this == ModItems.ammo_44_phosphorus) {
			list.add(EnumChatFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(EnumChatFormatting.YELLOW + "* Technically a warcrime");
			list.add(EnumChatFormatting.RED + "- Increased wear");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_44_pip) {
			list.add(EnumChatFormatting.BLUE + "+ Boxcar");
			list.add(EnumChatFormatting.RED + "- Highly decreased damage");
		}
		if(this == ModItems.ammo_44_bj) {
			list.add(EnumChatFormatting.BLUE + "+ Boat");
			list.add(EnumChatFormatting.RED + "- Highly decreased damage");
		}
		if(this == ModItems.ammo_44_silver) {
			list.add(EnumChatFormatting.BLUE + "+ Building");
			list.add(EnumChatFormatting.RED + "- Highly decreased damage");
		}
		if(this == ModItems.ammo_44_rocket) {
			list.add(EnumChatFormatting.BLUE + "+ Rocket");
			list.add(EnumChatFormatting.YELLOW + "* Uhhhh");
		}
		if(this == ModItems.ammo_44_star) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Starmetal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_44_chlorophyte) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(EnumChatFormatting.YELLOW + "* Homing");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
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
		if(this == ModItems.ammo_5mm_chlorophyte) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(EnumChatFormatting.YELLOW + "* Homing");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
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
		if(this == ModItems.ammo_9mm_chlorophyte) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(EnumChatFormatting.YELLOW + "* Homing");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		
		//.22LR
		if(this == ModItems.ammo_22lr_ap) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_22lr_chlorophyte) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(EnumChatFormatting.YELLOW + "* Homing");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		
		//.50 BMG
		if(this == ModItems.ammo_50bmg) {
			list.add(EnumChatFormatting.YELLOW + "12.7mm anti-materiel round");
			list.add(EnumChatFormatting.YELLOW + "You shoot down planes with these, using");
			list.add(EnumChatFormatting.YELLOW + "them against people would be nasty.");
		}
		if(this == ModItems.ammo_50bmg_incendiary) {
			list.add(EnumChatFormatting.BLUE + "+ Incendiary");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_50bmg_phosphorus) {
			list.add(EnumChatFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(EnumChatFormatting.YELLOW + "* Technically a warcrime");
			list.add(EnumChatFormatting.RED + "- Increased wear");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_50bmg_explosive) {
			list.add(EnumChatFormatting.BLUE + "+ Explosive");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_50bmg_ap) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Increased wear");
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
		if(this == ModItems.ammo_50bmg_sleek) {
			list.add(EnumChatFormatting.YELLOW + "* Fires a high-damage round that summons a small meteorite");
		}
		if(this == ModItems.ammo_50bmg_chlorophyte) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(EnumChatFormatting.YELLOW + "* Homing");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_50bmg_flechette) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
		}
		if(this == ModItems.ammo_50bmg_flechette_am) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.GREEN + "+ Highly Radioactive");
			list.add(EnumChatFormatting.YELLOW + "* Yes.");
		}
		if(this == ModItems.ammo_50bmg_flechette_po) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.GREEN + "+ Highly Radioactive");
			list.add(EnumChatFormatting.YELLOW + "* Maybe?");
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
		if(this == ModItems.ammo_50ae_chlorophyte) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(EnumChatFormatting.YELLOW + "* Homing");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
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
		if(this == ModItems.ammo_rocket_phosphorus) {
			list.add(EnumChatFormatting.BLUE + "+ Phosphorus splash");
			list.add(EnumChatFormatting.YELLOW + "* Technically a warcrime");
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
		if(this == ModItems.ammo_rocket_digamma) {
			
			if(new Random().nextInt(3) < 2)
				list.add(EnumChatFormatting.RED + "COVER YOURSELF IN OIL");
			else
				list.add(EnumChatFormatting.RED + "" + EnumChatFormatting.OBFUSCATED + "COVER YOURSELF IN OIL");
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
		if(this == ModItems.ammo_grenade_phosphorus) {
			list.add(EnumChatFormatting.BLUE + "+ Phosphorus splash");
			list.add(EnumChatFormatting.YELLOW + "* Technically a warcrime");
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
		if(this == ModItems.ammo_grenade_kampf) {
			list.add(EnumChatFormatting.BLUE + "+ Rocket Propelled");
			list.add(EnumChatFormatting.BLUE + "+ Increased blast radius");
			list.add(EnumChatFormatting.BLUE + "+ Increased accuracy");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		
		//FUEL
		if(this == ModItems.ammo_fuel_napalm) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Increased range");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_fuel_phosphorus) {
			list.add(EnumChatFormatting.BLUE + "+ Phosphorus splash");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Increased range");
			list.add(EnumChatFormatting.BLUE + "+ Increased accuracy");
			list.add(EnumChatFormatting.YELLOW + "* Technically a warcrime");
			list.add(EnumChatFormatting.RED + "- Single projectile");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_fuel_vaporizer) {
			list.add(EnumChatFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(EnumChatFormatting.BLUE + "+ Increased flame count");
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.YELLOW + "* For removing big mistakes");
			list.add(EnumChatFormatting.RED + "- Highly decreased accuracy");
			list.add(EnumChatFormatting.RED + "- Highly decreased range");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
			list.add(EnumChatFormatting.RED + "- No lingering fire");
		}
		if(this == ModItems.ammo_fuel_gas) {
			list.add(EnumChatFormatting.BLUE + "+ No gravity");
			list.add(EnumChatFormatting.BLUE + "+ Poison splash");
			list.add(EnumChatFormatting.RED + "- No damage");
			list.add(EnumChatFormatting.RED + "- Not incendiary");
		}
		
		//FIRE EXT
		if(this == ModItems.ammo_fireext_foam) {
			list.add(EnumChatFormatting.BLUE + "+ Can put out any fire type");
			list.add(EnumChatFormatting.BLUE + "+ Creates protective foam layer");
			list.add(EnumChatFormatting.YELLOW + "* Broader spray");
		}
		if(this == ModItems.ammo_fireext_sand) {
			list.add(EnumChatFormatting.BLUE + "+ Creates protective sand layer");
			list.add(EnumChatFormatting.YELLOW + "* Very broad spray");
			list.add(EnumChatFormatting.RED + "- No extinguishing AoE");
		}
		
		//5.56mm
		if(this == ModItems.ammo_556_phosphorus) {
			list.add(EnumChatFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(EnumChatFormatting.YELLOW + "* Technically a warcrime");
			list.add(EnumChatFormatting.RED + "- Increased wear");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_ap) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_556_du) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Heavy metal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_556_star) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Starmetal");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_556_sleek) {
			list.add(EnumChatFormatting.YELLOW + "* Fires a high-damage round that summons a small meteorite");
		}
		if(this == ModItems.ammo_556_flechette) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.YELLOW + "* Less bouncy");
			list.add(EnumChatFormatting.RED + "- Increased wear");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_flechette_incendiary) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Incendiary");
			list.add(EnumChatFormatting.YELLOW + "* Less bouncy");
			list.add(EnumChatFormatting.RED + "- Increased wear");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_flechette_phosphorus) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(EnumChatFormatting.YELLOW + "* Twice the warcrime in a single round!");
			list.add(EnumChatFormatting.YELLOW + "* Less bouncy");
			list.add(EnumChatFormatting.RED + "- Increased wear");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_flechette_du) {
			list.add(EnumChatFormatting.BLUE + "+ Highly increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Penetrating");
			list.add(EnumChatFormatting.YELLOW + "* Heavy metal");
			list.add(EnumChatFormatting.YELLOW + "* Less bouncy");
			list.add(EnumChatFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_556_flechette_sleek) {
			list.add(EnumChatFormatting.YELLOW + "* Fires a high-damage round that summons a small meteorite");
		}
		if(this == ModItems.ammo_556_tracer) {
			list.add(EnumChatFormatting.YELLOW + "* Tracer");
		}
		if(this == ModItems.ammo_556_k) {
			list.add(EnumChatFormatting.YELLOW + "* It's a blank");
		}
		if(this == ModItems.ammo_556_chlorophyte) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(EnumChatFormatting.YELLOW + "* Homing");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_flechette_chlorophyte) {
			list.add(EnumChatFormatting.BLUE + "+ Increased damage");
			list.add(EnumChatFormatting.BLUE + "+ Decreased wear");
			list.add(EnumChatFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(EnumChatFormatting.YELLOW + "* Homing");
			list.add(EnumChatFormatting.RED + "- Not penetrating");
		}
		
		//BOLTS
		if(this == ModItems.ammo_75bolt) {
			list.add(EnumChatFormatting.YELLOW + "Gyro-stabilized armor-piercing");
			list.add(EnumChatFormatting.YELLOW + "DU round with tandem charge");
		}
		if(this == ModItems.ammo_75bolt_incendiary) {
			list.add(EnumChatFormatting.YELLOW + "Armor-piercing explosive round");
			list.add(EnumChatFormatting.YELLOW + "filled with oxy-phosphorous gel");
		}
		if(this == ModItems.ammo_75bolt_he) {
			list.add(EnumChatFormatting.YELLOW + "Armor-piercing penetrator filled");
			list.add(EnumChatFormatting.YELLOW + "with a powerful explosive charge");
		}
		
		//NUKES
		if(this== ModItems.ammo_nuke_low) {
			list.add(EnumChatFormatting.RED + "- Decreased blast radius");
		}
		if(this== ModItems.ammo_nuke_high) {
			list.add(EnumChatFormatting.BLUE + "+ Increased blast radius");
			list.add(EnumChatFormatting.BLUE + "+ Fallout");
		}
		if(this== ModItems.ammo_nuke_tots) {
			list.add(EnumChatFormatting.BLUE + "+ Increased bomb count");
			list.add(EnumChatFormatting.YELLOW + "* Fun for the whole family!");
			list.add(EnumChatFormatting.RED + "- Highly decreased accuracy");
			list.add(EnumChatFormatting.RED + "- Decreased blast radius");
			list.add(EnumChatFormatting.RED + "- Not recommended for the Proto MIRV");
		}
		if(this== ModItems.ammo_nuke_safe) {
			list.add(EnumChatFormatting.RED + "- Decreased blast radius");
			list.add(EnumChatFormatting.RED + "- No block damage");
		}
		if(this== ModItems.ammo_nuke_pumpkin) {
			list.add(EnumChatFormatting.RED + "- Not even a nuke");
		}
		
		//MIRV
		if(this== ModItems.ammo_mirv_low) {
			list.add(EnumChatFormatting.RED + "- Decreased blast radius");
		}
		if(this== ModItems.ammo_mirv_high) {
			list.add(EnumChatFormatting.BLUE + "+ Increased blast radius");
			list.add(EnumChatFormatting.BLUE + "+ Fallout");
		}
		if(this== ModItems.ammo_mirv_safe) {
			list.add(EnumChatFormatting.RED + "- Decreased blast radius");
			list.add(EnumChatFormatting.RED + "- No block damage");
		}
		if(this== ModItems.ammo_mirv_special) {
			list.add(EnumChatFormatting.BLUE + "+ 6 Low-yield mini nukes");
			list.add(EnumChatFormatting.BLUE + "+ 6 Mini nukes");
			list.add(EnumChatFormatting.BLUE + "+ 6 Tiny tots");
			list.add(EnumChatFormatting.BLUE + "+ 6 Balefire shells");
			list.add(EnumChatFormatting.WHITE + "* Sticky!");
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
