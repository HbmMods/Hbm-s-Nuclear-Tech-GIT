package com.hbm.util;

import api.hbm.item.IGasMask;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.HazmatRegistry;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorRegistry.HazardClass;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArmorUtil {

	/*
	 * The less horrifying part
	 */

	public static void register() {
		ArmorRegistry.registerHazard(ModItems.gas_mask_filter, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.GAS_BLISTERING, HazardClass.BACTERIA);
		ArmorRegistry.registerHazard(ModItems.gas_mask_filter_mono, HazardClass.PARTICLE_COARSE, HazardClass.GAS_MONOXIDE);
		ArmorRegistry.registerHazard(ModItems.gas_mask_filter_combo, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.GAS_BLISTERING, HazardClass.BACTERIA, HazardClass.GAS_MONOXIDE);
		ArmorRegistry.registerHazard(ModItems.gas_mask_filter_rag, HazardClass.PARTICLE_COARSE);
		ArmorRegistry.registerHazard(ModItems.gas_mask_filter_piss, HazardClass.PARTICLE_COARSE, HazardClass.GAS_LUNG);

		ArmorRegistry.registerHazard(ModItems.gas_mask, HazardClass.SAND, HazardClass.LIGHT);
		ArmorRegistry.registerHazard(ModItems.gas_mask_m65, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.mask_rag, HazardClass.PARTICLE_COARSE);
		ArmorRegistry.registerHazard(ModItems.mask_piss, HazardClass.PARTICLE_COARSE, HazardClass.GAS_LUNG);

		ArmorRegistry.registerHazard(ModItems.goggles, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.ashglasses, HazardClass.LIGHT, HazardClass.SAND);

		ArmorRegistry.registerHazard(ModItems.attachment_mask, HazardClass.SAND);

		ArmorRegistry.registerHazard(ModItems.asbestos_helmet, HazardClass.SAND, HazardClass.LIGHT);
		ArmorRegistry.registerHazard(ModItems.hazmat_helmet, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.hazmat_helmet_red, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.hazmat_helmet_grey, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.hazmat_paa_helmet, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.liquidator_helmet, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.t45_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.ajr_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.ajro_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.steamsuit_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.hev_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.fau_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.dns_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.schrabidium_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.euphemium_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.rpa_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.envsuit_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		ArmorRegistry.registerHazard(ModItems.trenchmaster_helmet, HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);

		//Ob ihr wirklich richtig steht, seht ihr wenn das Licht angeht!
		registerIfExists(Compat.MOD_GT6, "gt.armor.hazmat.universal.head", HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		registerIfExists(Compat.MOD_GT6, "gt.armor.hazmat.biochemgas.head", HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
		registerIfExists(Compat.MOD_GT6, "gt.armor.hazmat.radiation.head", HazardClass.PARTICLE_COARSE, HazardClass.PARTICLE_FINE, HazardClass.GAS_LUNG, HazardClass.BACTERIA, HazardClass.GAS_BLISTERING, HazardClass.GAS_MONOXIDE, HazardClass.LIGHT, HazardClass.SAND);
	}

	private static void registerIfExists(String domain, String name, HazardClass... classes) {
		Item item = Compat.tryLoadItem(domain, name);
		if(item != null)
			ArmorRegistry.registerHazard(item, classes);
	}

	public static boolean checkArmor(EntityLivingBase entity, Item... armor) {

		for(int i = 0; i < 4; i++) {
			if(!checkArmorPiece(entity, armor[i], 3 - i))
				return false;
		}

		return true;
	}

	public static boolean checkArmorPiece(EntityLivingBase entity, Item armor, int slot) {
		return !checkArmorNull(entity, slot) && entity.getEquipmentInSlot(slot + 1).getItem() == armor;
	}

	public static boolean checkArmorNull(EntityLivingBase player, int slot) {
		return player.getEquipmentInSlot(slot + 1) == null;
	}

	public static void damageSuit(EntityLivingBase entity, int slot, int amount) {

		if(entity.getEquipmentInSlot(slot + 1) == null)
			return;

		entity.getEquipmentInSlot(slot + 1).damageItem(amount, entity);

		if(entity.getEquipmentInSlot(slot + 1).stackSize == 0) {
			entity.setCurrentItemOrArmor(slot + 1, null);
		}
	}

	public static void resetFlightTime(EntityPlayer player) {

		if(player instanceof EntityPlayerMP) {
			EntityPlayerMP mp = (EntityPlayerMP) player;
			ReflectionHelper.setPrivateValue(NetHandlerPlayServer.class, mp.playerNetServerHandler, 0, "floatingTickCount", "field_147365_f");
		}
	}

	/*
	 * The more horrifying part
	 */
	public static boolean checkForHazmat(EntityLivingBase player) {

		if(checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) ||
				checkArmor(player, ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red) ||
				checkArmor(player, ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey) ||
				checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots) ||
				checkArmor(player, ModItems.schrabidium_helmet, ModItems.schrabidium_plate, ModItems.schrabidium_legs, ModItems.schrabidium_boots) ||
				checkForHaz2(player)) {

			return true;
		}

		if(player.isPotionActive(HbmPotion.mutation))
			return true;

		return false;
	}

	public static boolean checkForHaz2(EntityLivingBase player) {

		if(checkArmor(player, ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots) ||
				checkArmor(player, ModItems.liquidator_helmet, ModItems.liquidator_plate, ModItems.liquidator_legs, ModItems.liquidator_boots) ||
				checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots) ||
				checkArmor(player, ModItems.rpa_helmet, ModItems.rpa_plate, ModItems.rpa_legs, ModItems.rpa_boots) ||
				checkArmor(player, ModItems.fau_helmet, ModItems.fau_plate, ModItems.fau_legs, ModItems.fau_boots) ||
				checkArmor(player, ModItems.dns_helmet, ModItems.dns_plate, ModItems.dns_legs, ModItems.dns_boots))
		{
			return true;
		}

		return false;
	}

	public static boolean checkForAsbestos(EntityLivingBase player) {

		if(checkArmor(player, ModItems.asbestos_helmet, ModItems.asbestos_plate, ModItems.asbestos_legs, ModItems.asbestos_boots))
			return true;

		return false;
	}

	public static boolean checkForDigamma(EntityPlayer player) {

		if(checkArmor(player, ModItems.fau_helmet, ModItems.fau_plate, ModItems.fau_legs, ModItems.fau_boots))
			return true;

		if(checkArmor(player, ModItems.dns_helmet, ModItems.dns_plate, ModItems.dns_legs, ModItems.dns_boots))
			return true;

		if(player.isPotionActive(HbmPotion.stability.id))
			return true;

		return false;
	}

	public static boolean checkForDigamma2(EntityPlayer player) {

		if(!checkArmor(player, ModItems.robes_helmet, ModItems.robes_plate, ModItems.robes_legs, ModItems.robes_boots))
			return false;

		if(player.isPotionActive(HbmPotion.stability.id))
			return true;

		for(int i = 0; i < 4; i++) {

			ItemStack armor = player.getCurrentArmor(i);

			if(armor != null && ArmorModHandler.hasMods(armor)) {

				ItemStack mods[] = ArmorModHandler.pryMods(armor);

				if(!(mods[ArmorModHandler.cladding] != null && mods[ArmorModHandler.cladding].getItem() == ModItems.cladding_iron))
					return false;
			}
		}

		return player.getMaxHealth() < 3;
	}

	public static boolean checkForFaraday(EntityPlayer player) {

		ItemStack[] armor = player.inventory.armorInventory;

		if(armor[0] == null || armor[1] == null || armor[2] == null || armor[3] == null) return false;

		if(isFaradayArmor(armor[0]) && isFaradayArmor(armor[1]) && isFaradayArmor(armor[2]) && isFaradayArmor(armor[3]))
			return true;

		return false;
	}

	public static final String[] metals = new String[] {
			"chainmail",
			"iron",
			"silver",
			"gold",
			"platinum",
			"tin",
			"lead",
			"liquidator",
			"schrabidium",
			"euphemium",
			"steel",
			"cmb",
			"titanium",
			"alloy",
			"copper",
			"bronze",
			"electrum",
			"t45",
			"bj",
			"starmetal",
			"hazmat", //also count because rubber is insulating
			"rubber",
			"hev",
			"ajr",
			"rpa",
			"spacesuit"
	};

	public static boolean isFaradayArmor(ItemStack item) {

		String name = item.getUnlocalizedName();

		for(String metal : metals) {

			if(name.toLowerCase(Locale.US).contains(metal))
				return true;
		}

		if(HazmatRegistry.getCladding(item) > 0)
			return true;

		return false;
	}

	public static boolean checkForFiend(EntityPlayer player) {

		return checkArmorPiece(player, ModItems.jackt, 2) && Library.checkForHeld(player, ModItems.shimmer_sledge);
	}

	public static boolean checkForFiend2(EntityPlayer player) {

		return checkArmorPiece(player, ModItems.jackt2, 2) && Library.checkForHeld(player, ModItems.shimmer_axe);
	}

	/*
	 * Default implementations for IGasMask items
	 */
	public static final String FILTERK_KEY = "hfrFilter";

	public static void installGasMaskFilter(ItemStack mask, ItemStack filter) {

		if(mask == null || filter == null)
			return;

		if(!mask.hasTagCompound())
			mask.stackTagCompound = new NBTTagCompound();

		NBTTagCompound attach = new NBTTagCompound();
		filter.writeToNBT(attach);

		mask.stackTagCompound.setTag(FILTERK_KEY, attach);
	}

	public static void removeFilter(ItemStack mask) {

		if(mask == null)
			return;

		if(!mask.hasTagCompound())
			return;

		mask.stackTagCompound.removeTag(FILTERK_KEY);
	}

	/**
	 * Grabs the installed filter or the filter of the attachment, used for attachment rendering
	 * @param mask
	 * @param entity
	 * @return
	 */
	public static ItemStack getGasMaskFilterRecursively(ItemStack mask, EntityLivingBase entity) {

		ItemStack filter = getGasMaskFilter(mask);

		if(filter == null && ArmorModHandler.hasMods(mask)) {

			ItemStack mods[] = ArmorModHandler.pryMods(mask);

			if(mods[ArmorModHandler.helmet_only] != null && mods[ArmorModHandler.helmet_only].getItem() instanceof IGasMask)
				filter = ((IGasMask)mods[ArmorModHandler.helmet_only].getItem()).getFilter(mods[ArmorModHandler.helmet_only], entity);
		}

		return filter;
	}

	public static ItemStack getGasMaskFilter(ItemStack mask) {

		if(mask == null)
			return null;

		if(!mask.hasTagCompound())
			return null;

		NBTTagCompound attach = mask.stackTagCompound.getCompoundTag(FILTERK_KEY);
		ItemStack filter = ItemStack.loadItemStackFromNBT(attach);

		return filter;
	}

	public static void damageGasMaskFilter(EntityLivingBase entity, int damage) {

		ItemStack mask = entity.getEquipmentInSlot(4);

		if(mask == null)
			return;

		if(!(mask.getItem() instanceof IGasMask)) {

			if(ArmorModHandler.hasMods(mask)) {

				ItemStack mods[] = ArmorModHandler.pryMods(mask);

				if(mods[ArmorModHandler.helmet_only] != null && mods[ArmorModHandler.helmet_only].getItem() instanceof IGasMask)
					mask = mods[ArmorModHandler.helmet_only];
			}
		}

		if(mask != null)
			damageGasMaskFilter(mask, damage);
	}

	public static void damageGasMaskFilter(ItemStack mask, int damage) {
		ItemStack filter = getGasMaskFilter(mask);

		if(filter == null) {
			if(ArmorModHandler.hasMods(mask)) {
				ItemStack mods[] = ArmorModHandler.pryMods(mask);

				if(mods[ArmorModHandler.helmet_only] != null && mods[ArmorModHandler.helmet_only].getItem() instanceof IGasMask)
					filter = getGasMaskFilter(mods[ArmorModHandler.helmet_only]);
			}
		}

		if(filter == null || filter.getMaxDamage() == 0)
			return;

		filter.setItemDamage(filter.getItemDamage() + damage);

		if(filter.getItemDamage() > filter.getMaxDamage())
			removeFilter(mask);
		else
			installGasMaskFilter(mask, filter);
	}

	public static void addGasMaskTooltip(ItemStack mask, EntityPlayer player, List list, boolean ext) {

		if(mask == null || !(mask.getItem() instanceof IGasMask))
			return;

		ItemStack filter = ((IGasMask)mask.getItem()).getFilter(mask, player);

		if(filter == null) {
			list.add(EnumChatFormatting.RED + "No filter installed!");
			return;
		}

		list.add(EnumChatFormatting.GOLD + "Installed filter:");

		int meta = filter.getItemDamage();
		int max = filter.getMaxDamage();

		String append = "";

		if(max > 0) {
			append = " (" + ((max - meta) * 100 / max) + "%)";
		}

		List<String> lore = new ArrayList();
		list.add("  " + filter.getDisplayName() + append);
		filter.getItem().addInformation(filter, player, lore, ext);
		ForgeEventFactory.onItemTooltip(filter, player, lore, ext);
		lore.forEach(x -> list.add(EnumChatFormatting.YELLOW + "  " + x));
	}

	public static boolean isWearingEmptyMask(EntityPlayer player) {

		ItemStack mask = player.getEquipmentInSlot(4);

		if(mask == null)
			return false;

		if(mask.getItem() instanceof IGasMask) {
			return getGasMaskFilter(mask) == null;
		}

		ItemStack mod = ArmorModHandler.pryMods(mask)[ArmorModHandler.helmet_only];

		if(mod != null && mod.getItem() instanceof IGasMask) {
			return getGasMaskFilter(mod) == null;
		}

		return false;
	}
}
