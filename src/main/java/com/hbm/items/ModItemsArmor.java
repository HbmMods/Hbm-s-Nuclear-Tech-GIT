package com.hbm.items;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.armor.*;
import com.hbm.items.armor.IArmorDisableModel.EnumPlayerPart;
import com.hbm.items.special.ItemDrop;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ArmorUtil;

import static com.hbm.items.ModItems.*;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

public class ModItemsArmor {

	public static void init() {
		ArmorMaterial aMatRags = EnumHelper.addArmorMaterial("HBM_RAGS", 150, new int[] { 1, 1, 1, 1 }, 0);
		aMatRags.customCraftingMaterial = ModItems.rag;

		goggles = new ArmorModel(ArmorMaterial.IRON, 0).setUnlocalizedName("goggles").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":goggles");
		ashglasses = new ArmorAshGlasses(ArmorMaterial.IRON, 0).setUnlocalizedName("ashglasses").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ashglasses");
		gas_mask = new ArmorGasMask().setUnlocalizedName("gas_mask").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask");
		gas_mask_m65 = new ArmorGasMask().setUnlocalizedName("gas_mask_m65").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask_m65");
		gas_mask_mono = new ArmorGasMask().setUnlocalizedName("gas_mask_mono").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask_mono");
		gas_mask_olde = new ArmorGasMask().setUnlocalizedName("gas_mask_olde").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask_olde");
		mask_rag = new ModArmor(aMatRags, 0).setUnlocalizedName("mask_rag").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mask_rag");
		mask_piss = new ModArmor(aMatRags, 0).setUnlocalizedName("mask_piss").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mask_piss");
		hat = new ArmorHat(MainRegistry.aMatAlloy, 0).setUnlocalizedName("nossy_hat").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hat");
		no9 = new ArmorNo9(MainRegistry.aMatSteel, 0).setUnlocalizedName("no9").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":no9");
		beta = new ItemDrop().setUnlocalizedName("beta").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":beta");
		
		euphemium_helmet = new ArmorEuphemium(MainRegistry.aMatEuph, 0).setUnlocalizedName("euphemium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_helmet");
		euphemium_plate = new ArmorEuphemium(MainRegistry.aMatEuph, 1).setUnlocalizedName("euphemium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_plate");
		euphemium_legs = new ArmorEuphemium(MainRegistry.aMatEuph, 2).setUnlocalizedName("euphemium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_legs");
		euphemium_boots = new ArmorEuphemium(MainRegistry.aMatEuph, 3).setUnlocalizedName("euphemium_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_boots");


		schrabidium_helmet = new ArmorFSB(MainRegistry.aMatSchrab, 0, RefStrings.MODID + ":textures/armor/schrabidium_1.png")
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 2))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.setUnlocalizedName("schrabidium_helmet").setTextureName(RefStrings.MODID + ":schrabidium_helmet");
		schrabidium_plate = new ArmorFSB(MainRegistry.aMatSchrab, 1, RefStrings.MODID + ":textures/armor/schrabidium_1.png").cloneStats((ArmorFSB) schrabidium_helmet).setUnlocalizedName("schrabidium_plate").setTextureName(RefStrings.MODID + ":schrabidium_plate");
		schrabidium_legs = new ArmorFSB(MainRegistry.aMatSchrab, 2, RefStrings.MODID + ":textures/armor/schrabidium_2.png").cloneStats((ArmorFSB) schrabidium_helmet).setUnlocalizedName("schrabidium_legs").setTextureName(RefStrings.MODID + ":schrabidium_legs");
		schrabidium_boots = new ArmorFSB(MainRegistry.aMatSchrab, 3, RefStrings.MODID + ":textures/armor/schrabidium_1.png").cloneStats((ArmorFSB) schrabidium_helmet).setUnlocalizedName("schrabidium_boots").setTextureName(RefStrings.MODID + ":schrabidium_boots");
		bismuth_helmet = new ArmorBismuth(MainRegistry.aMatBismuth, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.addEffect(new PotionEffect(Potion.jump.id, 20, 6))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 6))
				.addEffect(new PotionEffect(Potion.regeneration.id, 20, 1))
				.addEffect(new PotionEffect(Potion.nightVision.id, 15 * 20, 0))
				.setDashCount(3)
				.setUnlocalizedName("bismuth_helmet").setTextureName(RefStrings.MODID + ":bismuth_helmet");
		bismuth_plate = new ArmorBismuth(MainRegistry.aMatBismuth, 1, RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) bismuth_helmet).setUnlocalizedName("bismuth_plate").setTextureName(RefStrings.MODID + ":bismuth_plate");
		bismuth_legs = new ArmorBismuth(MainRegistry.aMatBismuth, 2, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) bismuth_helmet).setUnlocalizedName("bismuth_legs").setTextureName(RefStrings.MODID + ":bismuth_legs");
		bismuth_boots = new ArmorBismuth(MainRegistry.aMatBismuth, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) bismuth_helmet).setUnlocalizedName("bismuth_boots").setTextureName(RefStrings.MODID + ":bismuth_boots");
		titanium_helmet = new ArmorFSB(MainRegistry.aMatTitan, 0, RefStrings.MODID + ":textures/armor/titanium_1.png").setUnlocalizedName("titanium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_helmet");
		titanium_plate = new ArmorFSB(MainRegistry.aMatTitan, 1, RefStrings.MODID + ":textures/armor/titanium_1.png").cloneStats((ArmorFSB) titanium_helmet).setUnlocalizedName("titanium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_plate");
		titanium_legs = new ArmorFSB(MainRegistry.aMatTitan, 2, RefStrings.MODID + ":textures/armor/titanium_2.png").cloneStats((ArmorFSB) titanium_helmet).setUnlocalizedName("titanium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_legs");
		titanium_boots = new ArmorFSB(MainRegistry.aMatTitan, 3, RefStrings.MODID + ":textures/armor/titanium_1.png").cloneStats((ArmorFSB) titanium_helmet).setUnlocalizedName("titanium_boots").setTextureName(RefStrings.MODID + ":titanium_boots");
		steel_helmet = new ArmorFSB(MainRegistry.aMatSteel, 0, RefStrings.MODID + ":textures/armor/steel_1.png").setUnlocalizedName("steel_helmet").setTextureName(RefStrings.MODID + ":steel_helmet");
		steel_plate = new ArmorFSB(MainRegistry.aMatSteel, 1, RefStrings.MODID + ":textures/armor/steel_1.png").cloneStats((ArmorFSB) steel_helmet).setUnlocalizedName("steel_plate").setTextureName(RefStrings.MODID + ":steel_plate");
		steel_legs = new ArmorFSB(MainRegistry.aMatSteel, 2, RefStrings.MODID + ":textures/armor/steel_2.png").cloneStats((ArmorFSB) steel_helmet).setUnlocalizedName("steel_legs").setTextureName(RefStrings.MODID + ":steel_legs");
		steel_boots = new ArmorFSB(MainRegistry.aMatSteel, 3, RefStrings.MODID + ":textures/armor/steel_1.png").cloneStats((ArmorFSB) steel_helmet).setUnlocalizedName("steel_boots").setTextureName(RefStrings.MODID + ":steel_boots");
		alloy_helmet = new ArmorFSB(MainRegistry.aMatAlloy, 0, RefStrings.MODID + ":textures/armor/alloy_1.png").setUnlocalizedName("alloy_helmet").setTextureName(RefStrings.MODID + ":alloy_helmet");
		alloy_plate = new ArmorFSB(MainRegistry.aMatAlloy, 1, RefStrings.MODID + ":textures/armor/alloy_1.png").cloneStats((ArmorFSB) alloy_helmet).setUnlocalizedName("alloy_plate").setTextureName(RefStrings.MODID + ":alloy_plate");
		alloy_legs = new ArmorFSB(MainRegistry.aMatAlloy, 2, RefStrings.MODID + ":textures/armor/alloy_2.png").cloneStats((ArmorFSB) alloy_helmet).setUnlocalizedName("alloy_legs").setTextureName(RefStrings.MODID + ":alloy_legs");
		alloy_boots = new ArmorFSB(MainRegistry.aMatAlloy, 3, RefStrings.MODID + ":textures/armor/alloy_1.png").cloneStats((ArmorFSB) alloy_helmet).setUnlocalizedName("alloy_boots").setTextureName(RefStrings.MODID + ":alloy_boots");
		cmb_helmet = new ArmorFSB(MainRegistry.aMatCMB, 0, RefStrings.MODID + ":textures/armor/cmb_1.png")
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 4))
				.setUnlocalizedName("cmb_helmet").setTextureName(RefStrings.MODID + ":cmb_helmet");
		cmb_plate = new ArmorFSB(MainRegistry.aMatCMB, 1, RefStrings.MODID + ":textures/armor/cmb_1.png").cloneStats((ArmorFSB) cmb_helmet).setUnlocalizedName("cmb_plate").setTextureName(RefStrings.MODID + ":cmb_plate");
		cmb_legs = new ArmorFSB(MainRegistry.aMatCMB, 2, RefStrings.MODID + ":textures/armor/cmb_2.png").cloneStats((ArmorFSB) cmb_helmet).setUnlocalizedName("cmb_legs").setTextureName(RefStrings.MODID + ":cmb_legs");
		cmb_boots = new ArmorFSB(MainRegistry.aMatCMB, 3, RefStrings.MODID + ":textures/armor/cmb_1.png").cloneStats((ArmorFSB) cmb_helmet).setUnlocalizedName("cmb_boots").setTextureName(RefStrings.MODID + ":cmb_boots");
		paa_plate = new ArmorFSB(MainRegistry.aMatPaa, 1, RefStrings.MODID + ":textures/armor/paa_1.png").setNoHelmet(true)
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 0)).setUnlocalizedName("paa_plate").setTextureName(RefStrings.MODID + ":paa_plate");
		paa_legs = new ArmorFSB(MainRegistry.aMatPaa, 2, RefStrings.MODID + ":textures/armor/paa_2.png").cloneStats((ArmorFSB) paa_plate).setUnlocalizedName("paa_legs").setTextureName(RefStrings.MODID + ":paa_legs");
		paa_boots = new ArmorFSB(MainRegistry.aMatPaa, 3, RefStrings.MODID + ":textures/armor/paa_1.png").cloneStats((ArmorFSB) paa_plate).setUnlocalizedName("paa_boots").setTextureName(RefStrings.MODID + ":paa_boots");
		asbestos_helmet = new ArmorFSB(MainRegistry.aMatAsbestos, 0, RefStrings.MODID + ":textures/armor/asbestos_1.png").setOverlay(RefStrings.MODID + ":textures/misc/overlay_asbestos.png").setUnlocalizedName("asbestos_helmet").setTextureName(RefStrings.MODID + ":asbestos_helmet");
		asbestos_plate = new ArmorFSB(MainRegistry.aMatAsbestos, 1, RefStrings.MODID + ":textures/armor/asbestos_1.png").setUnlocalizedName("asbestos_plate").setTextureName(RefStrings.MODID + ":asbestos_plate");
		asbestos_legs = new ArmorFSB(MainRegistry.aMatAsbestos, 2, RefStrings.MODID + ":textures/armor/asbestos_2.png").setUnlocalizedName("asbestos_legs").setTextureName(RefStrings.MODID + ":asbestos_legs");
		asbestos_boots = new ArmorFSB(MainRegistry.aMatAsbestos, 3, RefStrings.MODID + ":textures/armor/asbestos_1.png").setUnlocalizedName("asbestos_boots").setTextureName(RefStrings.MODID + ":asbestos_boots");
		security_helmet = new ArmorFSB(MainRegistry.aMatSecurity, 0, RefStrings.MODID + ":textures/armor/security_1.png").setUnlocalizedName("security_helmet").setTextureName(RefStrings.MODID + ":security_helmet");
		security_plate = new ArmorFSB(MainRegistry.aMatSecurity, 1, RefStrings.MODID + ":textures/armor/security_1.png").cloneStats((ArmorFSB) security_helmet).setUnlocalizedName("security_plate").setTextureName(RefStrings.MODID + ":security_plate");
		security_legs = new ArmorFSB(MainRegistry.aMatSecurity, 2, RefStrings.MODID + ":textures/armor/security_2.png").cloneStats((ArmorFSB) security_helmet).setUnlocalizedName("security_legs").setTextureName(RefStrings.MODID + ":security_legs");
		security_boots = new ArmorFSB(MainRegistry.aMatSecurity, 3, RefStrings.MODID + ":textures/armor/security_1.png").cloneStats((ArmorFSB) security_helmet).setUnlocalizedName("security_boots").setTextureName(RefStrings.MODID + ":security_boots");
		cobalt_helmet = new ArmorFSB(MainRegistry.aMatCobalt, 0, RefStrings.MODID + ":textures/armor/cobalt_1.png").setUnlocalizedName("cobalt_helmet").setTextureName(RefStrings.MODID + ":cobalt_helmet");
		cobalt_plate = new ArmorFSB(MainRegistry.aMatCobalt, 1, RefStrings.MODID + ":textures/armor/cobalt_1.png").cloneStats((ArmorFSB) cobalt_helmet).setUnlocalizedName("cobalt_plate").setTextureName(RefStrings.MODID + ":cobalt_plate");
		cobalt_legs = new ArmorFSB(MainRegistry.aMatCobalt, 2, RefStrings.MODID + ":textures/armor/cobalt_2.png").cloneStats((ArmorFSB) cobalt_helmet).setUnlocalizedName("cobalt_legs").setTextureName(RefStrings.MODID + ":cobalt_legs");
		cobalt_boots = new ArmorFSB(MainRegistry.aMatCobalt, 3, RefStrings.MODID + ":textures/armor/cobalt_1.png").cloneStats((ArmorFSB) cobalt_helmet).setUnlocalizedName("cobalt_boots").setTextureName(RefStrings.MODID + ":cobalt_boots");
		starmetal_helmet = new ArmorFSB(MainRegistry.aMatStarmetal, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.setUnlocalizedName("starmetal_helmet").setTextureName(RefStrings.MODID + ":starmetal_helmet");
		starmetal_plate = new ArmorFSB(MainRegistry.aMatStarmetal, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) starmetal_helmet).setUnlocalizedName("starmetal_plate").setTextureName(RefStrings.MODID + ":starmetal_plate");
		starmetal_legs = new ArmorFSB(MainRegistry.aMatStarmetal, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) starmetal_helmet).setUnlocalizedName("starmetal_legs").setTextureName(RefStrings.MODID + ":starmetal_legs");
		starmetal_boots = new ArmorFSB(MainRegistry.aMatStarmetal, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) starmetal_helmet).setUnlocalizedName("starmetal_boots").setTextureName(RefStrings.MODID + ":starmetal_boots");

		robes_helmet = new ArmorFSB(ArmorMaterial.CHAIN, 0, RefStrings.MODID + ":textures/armor/robes_1.png").setUnlocalizedName("robes_helmet").setTextureName(RefStrings.MODID + ":robes_helmet");
		robes_plate = new ArmorFSB(ArmorMaterial.CHAIN, 1, RefStrings.MODID + ":textures/armor/robes_1.png").cloneStats((ArmorFSB) robes_helmet).setUnlocalizedName("robes_plate").setTextureName(RefStrings.MODID + ":robes_plate");
		robes_legs = new ArmorFSB(ArmorMaterial.CHAIN, 2, RefStrings.MODID + ":textures/armor/robes_2.png").cloneStats((ArmorFSB) robes_helmet).setUnlocalizedName("robes_legs").setTextureName(RefStrings.MODID + ":robes_legs");
		robes_boots = new ArmorFSB(ArmorMaterial.CHAIN, 3, RefStrings.MODID + ":textures/armor/robes_1.png").cloneStats((ArmorFSB) robes_helmet).setUnlocalizedName("robes_boots").setTextureName(RefStrings.MODID + ":robes_boots");


		ArmorMaterial aMatZirconium = EnumHelper.addArmorMaterial("HBM_ZIRCONIUM", 1000, new int[] { 2, 5, 3, 1 }, 1000);
		aMatZirconium.customCraftingMaterial = ModItems.ingot_zirconium;
		zirconium_legs = new ArmorFSB(aMatZirconium, 2, RefStrings.MODID + ":textures/armor/zirconium_2.png").setUnlocalizedName("zirconium_legs").setTextureName(RefStrings.MODID + ":zirconium_legs");

		ArmorMaterial aMatDNT = EnumHelper.addArmorMaterial("HBM_DNT_LOLOLOL", 3, new int[] { 1, 1, 1, 1 }, 0);
		aMatDNT.customCraftingMaterial = ModItems.ingot_dineutronium;
		dnt_helmet = new ArmorFSB(aMatDNT, 0, RefStrings.MODID + ":textures/armor/dnt_1.png")
				.setUnlocalizedName("dnt_helmet").setTextureName(RefStrings.MODID + ":dnt_helmet");
		dnt_plate = new ArmorFSB(aMatDNT, 1, RefStrings.MODID + ":textures/armor/dnt_1.png").cloneStats((ArmorFSB) dnt_helmet).setUnlocalizedName("dnt_plate").setTextureName(RefStrings.MODID + ":dnt_plate");
		dnt_legs = new ArmorFSB(aMatDNT, 2, RefStrings.MODID + ":textures/armor/dnt_2.png").cloneStats((ArmorFSB) dnt_helmet).setUnlocalizedName("dnt_legs").setTextureName(RefStrings.MODID + ":dnt_legs");
		dnt_boots = new ArmorFSB(aMatDNT, 3, RefStrings.MODID + ":textures/armor/dnt_1.png").cloneStats((ArmorFSB) dnt_helmet).setUnlocalizedName("dnt_boots").setTextureName(RefStrings.MODID + ":dnt_boots");

		ArmorMaterial aMatT51 = EnumHelper.addArmorMaterial("HBM_T51", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatT51.customCraftingMaterial = ModItems.plate_armor_titanium;
		t51_helmet = new ArmorT51(aMatT51, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 1000, 5)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setStep("hbm:step.metal").setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land")
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_NO_LIGHT).setRadResist(1D /*90%*/)
				.setUnlocalizedName("t51_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":armor");
		t51_plate = new ArmorT51(aMatT51, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 1000, 5).cloneStats((ArmorFSB) t51_helmet).setUnlocalizedName("t51_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":armor");
		t51_legs = new ArmorT51(aMatT51, 2, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 1000, 5).cloneStats((ArmorFSB) t51_helmet).setUnlocalizedName("t51_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":armor");
		t51_boots = new ArmorT51(aMatT51, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 1000, 5).cloneStats((ArmorFSB) t51_helmet).setUnlocalizedName("t51_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":armor");

		ArmorMaterial aMatDesh = EnumHelper.addArmorMaterial("HBM_DESH", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatDesh.customCraftingMaterial = ModItems.ingot_desh;
		steamsuit_helmet = new ArmorDesh(aMatDesh, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.STEAM, 64_000, 500, 50, 1)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 4))
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(1.3D /*95%*/)
				.setUnlocalizedName("steamsuit_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steamsuit_helmet");
		steamsuit_plate = new ArmorDesh(aMatDesh, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.STEAM, 64_000, 500, 50, 1).cloneStats((ArmorFSB) steamsuit_helmet).setUnlocalizedName("steamsuit_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steamsuit_plate");
		steamsuit_legs = new ArmorDesh(aMatDesh, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", Fluids.STEAM, 64_000, 500, 50, 1).cloneStats((ArmorFSB) steamsuit_helmet).setUnlocalizedName("steamsuit_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steamsuit_legs");
		steamsuit_boots = new ArmorDesh(aMatDesh, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.STEAM, 64_000, 500, 50, 1).cloneStats((ArmorFSB) steamsuit_helmet).setUnlocalizedName("steamsuit_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steamsuit_boots");

		ArmorMaterial aMatDiesel = EnumHelper.addArmorMaterial("HBM_BNUUY", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatDiesel.customCraftingMaterial = ModItems.plate_copper;
		dieselsuit_helmet = new ArmorDiesel(aMatDiesel, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.DIESEL, 64_000, 500, 50, 1)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 2))
				.enableThermalSight(true)
				.enableVATS(true)
				.setUnlocalizedName("dieselsuit_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":dieselsuit_helmet");
		dieselsuit_plate = new ArmorDiesel(aMatDiesel, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.DIESEL, 64_000, 500, 50, 1).cloneStats((ArmorFSB) dieselsuit_helmet).setUnlocalizedName("dieselsuit_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":dieselsuit_plate");
		dieselsuit_legs = new ArmorDiesel(aMatDiesel, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", Fluids.DIESEL, 64_000, 500, 50, 1).cloneStats((ArmorFSB) dieselsuit_helmet).setUnlocalizedName("dieselsuit_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":dieselsuit_legs");
		dieselsuit_boots = new ArmorDiesel(aMatDiesel, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.DIESEL, 64_000, 500, 50, 1).cloneStats((ArmorFSB) dieselsuit_helmet).setUnlocalizedName("dieselsuit_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":dieselsuit_boots");

		ArmorMaterial aMatAJR = EnumHelper.addArmorMaterial("HBM_T45AJR", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatAJR.customCraftingMaterial = ModItems.plate_armor_ajr;
		ajr_helmet = new ArmorAJR(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setStep("hbm:step.metal").setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land")
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(1.3D /*95%*/)
				.setUnlocalizedName("ajr_helmet").setTextureName(RefStrings.MODID + ":ajr_helmet");
		ajr_plate = new ArmorAJR(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajr_helmet).setUnlocalizedName("ajr_plate").setTextureName(RefStrings.MODID + ":ajr_plate");
		ajr_legs = new ArmorAJR(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajr_helmet).setUnlocalizedName("ajr_legs").setTextureName(RefStrings.MODID + ":ajr_legs");
		ajr_boots = new ArmorAJR(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajr_helmet).setUnlocalizedName("ajr_boots").setTextureName(RefStrings.MODID + ":ajr_boots");

		ajro_helmet = new ArmorAJRO(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setStep("hbm:step.metal").setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land")
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(1.3D /*95%*/)
				.setUnlocalizedName("ajro_helmet").setTextureName(RefStrings.MODID + ":ajro_helmet");
		ajro_plate = new ArmorAJRO(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajro_helmet).setUnlocalizedName("ajro_plate").setTextureName(RefStrings.MODID + ":ajro_plate");
		ajro_legs = new ArmorAJRO(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajro_helmet).setUnlocalizedName("ajro_legs").setTextureName(RefStrings.MODID + ":ajro_legs");
		ajro_boots = new ArmorAJRO(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajro_helmet).setUnlocalizedName("ajro_boots").setTextureName(RefStrings.MODID + ":ajro_boots");

		rpa_helmet = new ArmorRPA(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 3))
				.setStep("hbm:step.powered")
				.setJump("hbm:step.powered")
				.setFall("hbm:step.powered")
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(2D /*99%*/)
				.setUnlocalizedName("rpa_helmet").setTextureName(RefStrings.MODID + ":rpa_helmet");
		rpa_plate = new ArmorRPA(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) rpa_helmet).setUnlocalizedName("rpa_plate").setTextureName(RefStrings.MODID + ":rpa_plate");
		rpa_legs = new ArmorRPA(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) rpa_helmet).setUnlocalizedName("rpa_legs").setTextureName(RefStrings.MODID + ":rpa_legs");
		rpa_boots = new ArmorRPA(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) rpa_helmet).setUnlocalizedName("rpa_boots").setTextureName(RefStrings.MODID + ":rpa_boots");

		ncrpa_helmet = new ArmorNCRPA(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 3))
				.setStep("hbm:step.powered")
				.setJump("hbm:step.powered")
				.setFall("hbm:step.powered")
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(1.7D /*97%*/)
				.setUnlocalizedName("ncrpa_helmet").setTextureName(RefStrings.MODID + ":rpa_helmet");
		ncrpa_plate = new ArmorNCRPA(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ncrpa_helmet).setUnlocalizedName("ncrpa_plate").setTextureName(RefStrings.MODID + ":rpa_plate");
		ncrpa_legs = new ArmorNCRPA(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ncrpa_helmet).setUnlocalizedName("ncrpa_legs").setTextureName(RefStrings.MODID + ":rpa_legs");
		ncrpa_boots = new ArmorNCRPA(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ncrpa_helmet).setUnlocalizedName("ncrpa_boots").setTextureName(RefStrings.MODID + ":rpa_boots");

		ArmorMaterial aMatBJ = EnumHelper.addArmorMaterial("HBM_BLACKJACK", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatBJ.customCraftingMaterial = ModItems.plate_armor_lunar;
		
		bj_helmet = new ArmorBJ(aMatBJ, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100)
				.enableVATS(true)
				.enableThermalSight(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.field_76443_y.id, 20, 0))
				.addEffect(new PotionEffect(HbmPotion.radx.id, 20, 0))
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land")
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(1D /*90%*/)
				.setUnlocalizedName("bj_helmet").setTextureName(RefStrings.MODID + ":bj_helmet");
		bj_eyepatch = new ArmorBJEyepatch(aMatBJ, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100)
				.cloneStats((ArmorFSB) bj_helmet)
				.setHazardClass(HazardClass.LIGHT, HazardClass.SAND)
				.setUnlocalizedName("bj_eyepatch").setTextureName(RefStrings.MODID + ":bj_helmet");
				
		bj_plate = new ArmorBJ(aMatBJ, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_plate").setTextureName(RefStrings.MODID + ":bj_plate");
		bj_plate_jetpack = new ArmorBJJetpack(aMatBJ, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_plate_jetpack").setTextureName(RefStrings.MODID + ":bj_plate_jetpack");
		bj_legs = new ArmorBJ(aMatBJ, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_legs").setTextureName(RefStrings.MODID + ":bj_legs");
		bj_boots = new ArmorBJ(aMatBJ, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_boots").setTextureName(RefStrings.MODID + ":bj_boots");

		ArmorMaterial aMatEnv = EnumHelper.addArmorMaterial("HBM_ENV", 150, new int[] { 3, 8, 6, 3 }, 10);
		aMatEnv.customCraftingMaterial = ModItems.plate_armor_hev;
		envsuit_helmet = new ArmorEnvsuit(aMatEnv, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 100_000, 1_000, 250, 0)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(1.0D /*90%*/)
				.setUnlocalizedName("envsuit_helmet").setTextureName(RefStrings.MODID + ":envsuit_helmet");
		envsuit_plate = new ArmorEnvsuit(aMatEnv, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 100_000, 1_000, 250, 0).cloneStats((ArmorFSB) envsuit_helmet).setUnlocalizedName("envsuit_plate").setTextureName(RefStrings.MODID + ":envsuit_plate");
		envsuit_legs = new ArmorEnvsuit(aMatEnv, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 100_000, 1_000, 250, 0).cloneStats((ArmorFSB) envsuit_helmet).setUnlocalizedName("envsuit_legs").setTextureName(RefStrings.MODID + ":envsuit_legs");
		envsuit_boots = new ArmorEnvsuit(aMatEnv, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 100_000, 1_000, 250, 0).cloneStats((ArmorFSB) envsuit_helmet).setUnlocalizedName("envsuit_boots").setTextureName(RefStrings.MODID + ":envsuit_boots");

		ArmorMaterial aMatHEV = EnumHelper.addArmorMaterial("HBM_HEV", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatHEV.customCraftingMaterial = ModItems.plate_armor_hev;
		hev_helmet = new ArmorHEV(aMatHEV, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 2500, 0)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.setHasGeigerSound(true)
				.setHasCustomGeiger(true)
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(2.3D /*99.5%*/)
				.setUnlocalizedName("hev_helmet").setTextureName(RefStrings.MODID + ":hev_helmet");
		hev_plate = new ArmorHEV(aMatHEV, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 2500, 0).cloneStats((ArmorFSB) hev_helmet).setUnlocalizedName("hev_plate").setTextureName(RefStrings.MODID + ":hev_plate");
		hev_legs = new ArmorHEV(aMatHEV, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 1000000, 10000, 2500, 0).cloneStats((ArmorFSB) hev_helmet).setUnlocalizedName("hev_legs").setTextureName(RefStrings.MODID + ":hev_legs");
		hev_boots = new ArmorHEV(aMatHEV, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 2500, 0).cloneStats((ArmorFSB) hev_helmet).setUnlocalizedName("hev_boots").setTextureName(RefStrings.MODID + ":hev_boots");

		jackt = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt").setTextureName(RefStrings.MODID + ":jackt");
		jackt2 = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt2").setTextureName(RefStrings.MODID + ":jackt2");

		ArmorMaterial aMatFau = EnumHelper.addArmorMaterial("HBM_DIGAMMA", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatFau.customCraftingMaterial = ModItems.plate_armor_fau;
		fau_helmet = new ArmorDigamma(aMatFau, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 2500, 0)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.setHasGeigerSound(true)
				.enableThermalSight(true)
				.setHasHardLanding(true)
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land")
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(4D /*99.99%*/)
				.setUnlocalizedName("fau_helmet").setTextureName(RefStrings.MODID + ":fau_helmet");
		fau_plate = new ArmorDigamma(aMatFau, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 2500, 0).cloneStats((ArmorFSB) fau_helmet).setFullSetForHide().setUnlocalizedName("fau_plate").setTextureName(RefStrings.MODID + ":fau_plate");
		fau_legs = new ArmorDigamma(aMatFau, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 10000000, 10000, 2500, 0).cloneStats((ArmorFSB) fau_helmet).hides(EnumPlayerPart.LEFT_LEG, EnumPlayerPart.RIGHT_LEG).setFullSetForHide().setUnlocalizedName("fau_legs").setTextureName(RefStrings.MODID + ":fau_legs");
		fau_boots = new ArmorDigamma(aMatFau, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 2500, 0).cloneStats((ArmorFSB) fau_helmet).setUnlocalizedName("fau_boots").setTextureName(RefStrings.MODID + ":fau_boots");

		ArmorMaterial aMatDNS = EnumHelper.addArmorMaterial("HBM_DNT_NANO", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatDNS.customCraftingMaterial = ModItems.plate_armor_dnt;
		dns_helmet = new ArmorDNT(aMatDNS, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000000, 1000000, 100000, 115)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 9))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 7))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 2))
				.setHasGeigerSound(true)
				.enableVATS(true)
				.enableThermalSight(true)
				.setHasHardLanding(true)
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land")
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(5D /*99.999%*/)
				.setUnlocalizedName("dns_helmet").setTextureName(RefStrings.MODID + ":dns_helmet");
		dns_plate = new ArmorDNT(aMatDNS, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000000, 1000000, 100000, 115).cloneStats((ArmorFSB) dns_helmet).setUnlocalizedName("dns_plate").setTextureName(RefStrings.MODID + ":dns_plate");
		dns_legs = new ArmorDNT(aMatDNS, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 1000000000, 1000000, 100000, 115).cloneStats((ArmorFSB) dns_helmet).setUnlocalizedName("dns_legs").setTextureName(RefStrings.MODID + ":dns_legs");
		dns_boots = new ArmorDNT(aMatDNS, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000000, 1000000, 100000, 115).cloneStats((ArmorFSB) dns_helmet).setUnlocalizedName("dns_boots").setTextureName(RefStrings.MODID + ":dns_boots");

		ArmorMaterial aMatTaurun = EnumHelper.addArmorMaterial("HBM_TRENCH", 150, new int[] { 3, 8, 6, 3 }, 10);
		aMatTaurun.customCraftingMaterial = ModItems.plate_iron;
		taurun_helmet = new ArmorTaurun(aMatTaurun, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setStepSize(1)
				.hides(EnumPlayerPart.HAT)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setRadResist(0.125D /*25%*/)
				.setUnlocalizedName("taurun_helmet").setTextureName(RefStrings.MODID + ":taurun_helmet");
		taurun_plate = new ArmorTaurun(aMatTaurun, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) taurun_helmet).setUnlocalizedName("taurun_plate").setTextureName(RefStrings.MODID + ":taurun_plate");
		taurun_legs = new ArmorTaurun(aMatTaurun, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) taurun_helmet).setUnlocalizedName("taurun_legs").setTextureName(RefStrings.MODID + ":taurun_legs");
		taurun_boots = new ArmorTaurun(aMatTaurun, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) taurun_helmet).setUnlocalizedName("taurun_boots").setTextureName(RefStrings.MODID + ":taurun_boots");
		ArmorMaterial aMatTrench = EnumHelper.addArmorMaterial("HBM_TRENCH", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatTrench.customCraftingMaterial = ModItems.plate_iron;
		trenchmaster_helmet = new ArmorTrenchmaster(aMatTrench, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 2))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 0))
				.enableVATS(true)
				.setStepSize(1)
				.hides(EnumPlayerPart.HAT)
				.setRadResist(1D /*90%*/)
				.setHazardClass(ArmorUtil.FULL_PACKAGE).setUnlocalizedName("trenchmaster_helmet").setTextureName(RefStrings.MODID + ":trenchmaster_helmet");
		trenchmaster_plate = new ArmorTrenchmaster(aMatTrench, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) trenchmaster_helmet).setUnlocalizedName("trenchmaster_plate").setTextureName(RefStrings.MODID + ":trenchmaster_plate");
		trenchmaster_legs = new ArmorTrenchmaster(aMatTrench, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) trenchmaster_helmet).setUnlocalizedName("trenchmaster_legs").setTextureName(RefStrings.MODID + ":trenchmaster_legs");
		trenchmaster_boots = new ArmorTrenchmaster(aMatTrench, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) trenchmaster_helmet).setUnlocalizedName("trenchmaster_boots").setTextureName(RefStrings.MODID + ":trenchmaster_boots");

		jackt = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt").setTextureName(RefStrings.MODID + ":jackt");
		jackt2 = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt2").setTextureName(RefStrings.MODID + ":jackt2");
	}
}
