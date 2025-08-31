package com.hbm.render.util;

import java.util.HashMap;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;
import com.hbm.main.ResourceManager;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class MissilePart {
	
	public static HashMap<Integer, MissilePart> parts = new HashMap<Integer, MissilePart>();

	public Item part;
	public PartType type;
	public double height;
	public double guiheight;
	public IModelCustom model;
	public ResourceLocation texture;
	
	private MissilePart(Item item, PartType type, double height, double guiheight, IModelCustom model, ResourceLocation texture) {
		this.part = item;
		this.type = type;
		this.height = height;
		this.guiheight = guiheight;
		this.model = model;
		this.texture = texture;
	}
	
	public static void registerAllParts() {
		
		parts.clear();

		MissilePart.registerPart(ModItems.mp_thruster_10_kerosene, PartType.THRUSTER, 1, 1, ResourceManager.mp_t_10_kerosene, ResourceManager.mp_t_10_kerosene_tex);
		MissilePart.registerPart(ModItems.mp_thruster_10_solid, PartType.THRUSTER, 0.5, 1, ResourceManager.mp_t_10_solid, ResourceManager.mp_t_10_solid_tex);
		MissilePart.registerPart(ModItems.mp_thruster_10_xenon, PartType.THRUSTER, 0.5, 1, ResourceManager.mp_t_10_xenon, ResourceManager.mp_t_10_xenon_tex);
		//
		MissilePart.registerPart(ModItems.mp_thruster_15_kerosene, PartType.THRUSTER, 1.5, 1.5, ResourceManager.mp_t_15_kerosene, ResourceManager.mp_t_15_kerosene_tex);
		MissilePart.registerPart(ModItems.mp_thruster_15_kerosene_triple, PartType.THRUSTER, 1, 1.5, ResourceManager.mp_t_15_kerosene_triple, ResourceManager.mp_t_15_kerosene_dual_tex);
		MissilePart.registerPart(ModItems.mp_thruster_15_solid, PartType.THRUSTER, 0.5, 1, ResourceManager.mp_t_15_solid, ResourceManager.mp_t_15_solid_tex);
		MissilePart.registerPart(ModItems.mp_thruster_15_solid_hexdecuple, PartType.THRUSTER, 0.5, 1, ResourceManager.mp_t_15_solid_hexdecuple, ResourceManager.mp_t_15_solid_hexdecuple_tex);
		MissilePart.registerPart(ModItems.mp_thruster_15_hydrogen, PartType.THRUSTER, 1.5, 1.5, ResourceManager.mp_t_15_kerosene, ResourceManager.mp_t_15_hydrogen_tex);
		MissilePart.registerPart(ModItems.mp_thruster_15_hydrogen_dual, PartType.THRUSTER, 1, 1.5, ResourceManager.mp_t_15_kerosene_dual, ResourceManager.mp_t_15_hydrogen_dual_tex);
		MissilePart.registerPart(ModItems.mp_thruster_15_balefire_short, PartType.THRUSTER, 2, 2, ResourceManager.mp_t_15_balefire_short, ResourceManager.mp_t_15_balefire_short_tex);
		MissilePart.registerPart(ModItems.mp_thruster_15_balefire, PartType.THRUSTER, 3, 2.5, ResourceManager.mp_t_15_balefire, ResourceManager.mp_t_15_balefire_tex);
		MissilePart.registerPart(ModItems.mp_thruster_15_balefire_large, PartType.THRUSTER, 3, 2.5, ResourceManager.mp_t_15_balefire_large, ResourceManager.mp_t_15_balefire_large_tex);
		MissilePart.registerPart(ModItems.mp_thruster_15_balefire_large_rad, PartType.THRUSTER, 3, 2.5, ResourceManager.mp_t_15_balefire_large, ResourceManager.mp_t_15_balefire_large_rad_tex);
		//
		MissilePart.registerPart(ModItems.mp_thruster_20_kerosene, PartType.THRUSTER, 3, 2.5, ResourceManager.mp_t_20_kerosene, ResourceManager.mp_t_20_kerosene_tex);
		MissilePart.registerPart(ModItems.mp_thruster_20_kerosene_dual, PartType.THRUSTER, 2, 2, ResourceManager.mp_t_20_kerosene_dual, ResourceManager.mp_t_20_kerosene_dual_tex);
		MissilePart.registerPart(ModItems.mp_thruster_20_kerosene_triple, PartType.THRUSTER, 2, 2, ResourceManager.mp_t_20_kerosene_triple, ResourceManager.mp_t_20_kerosene_dual_tex);
		MissilePart.registerPart(ModItems.mp_thruster_20_solid, PartType.THRUSTER, 1, 1.75, ResourceManager.mp_t_20_solid, ResourceManager.mp_t_20_solid_tex);
		MissilePart.registerPart(ModItems.mp_thruster_20_solid_multi, PartType.THRUSTER, 0.5, 1.5, ResourceManager.mp_t_20_solid_multi, ResourceManager.mp_t_20_solid_multi_tex);
		MissilePart.registerPart(ModItems.mp_thruster_20_solid_multier, PartType.THRUSTER, 0.5, 1.5, ResourceManager.mp_t_20_solid_multi, ResourceManager.mp_t_20_solid_multier_tex);

		//////
		
		MissilePart.registerPart(ModItems.mp_stability_10_flat, PartType.FINS, 0, 2, ResourceManager.mp_s_10_flat, ResourceManager.mp_s_10_flat_tex);
		MissilePart.registerPart(ModItems.mp_stability_10_cruise, PartType.FINS, 0, 3, ResourceManager.mp_s_10_cruise, ResourceManager.mp_s_10_cruise_tex);
		MissilePart.registerPart(ModItems.mp_stability_10_space, PartType.FINS, 0, 2, ResourceManager.mp_s_10_space, ResourceManager.mp_s_10_space_tex);
		//
		MissilePart.registerPart(ModItems.mp_stability_15_flat, PartType.FINS, 0, 3, ResourceManager.mp_s_15_flat, ResourceManager.mp_s_15_flat_tex);
		MissilePart.registerPart(ModItems.mp_stability_15_thin, PartType.FINS, 0, 3, ResourceManager.mp_s_15_thin, ResourceManager.mp_s_15_thin_tex);
		MissilePart.registerPart(ModItems.mp_stability_15_soyuz, PartType.FINS, 0, 3, ResourceManager.mp_s_15_soyuz, ResourceManager.mp_s_15_soyuz_tex);
		//
		MissilePart.registerPart(ModItems.mp_stability_20_flat, PartType.FINS, 0, 3, ResourceManager.mp_s_20, ResourceManager.universal);

		//////
		
		MissilePart.registerPart(ModItems.mp_fuselage_10_kerosene, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_kerosene_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_kerosene_camo, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_kerosene_camo_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_kerosene_desert, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_kerosene_desert_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_kerosene_sky, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_kerosene_sky_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_kerosene_insulation, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_kerosene_insulation_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_kerosene_flames, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_kerosene_flames_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_kerosene_sleek, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_kerosene_sleek_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_kerosene_metal, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_kerosene_metal_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_kerosene_taint, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_kerosene_taint_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_solid, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_solid_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_solid_flames, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_solid_flames_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_solid_insulation, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_solid_insulation_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_solid_sleek, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_solid_sleek_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_solid_soviet_glory, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_solid_soviet_glory_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_solid_cathedral, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_solid_cathedral_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_solid_moonlit, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_solid_moonlit_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_solid_battery, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_solid_battery_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_solid_duracell, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_solid_duracell_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_xenon, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_xenon_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_xenon_bhole, PartType.FUSELAGE, 4, 3, ResourceManager.mp_f_10_kerosene, ResourceManager.mp_f_10_xenon_bhole_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_camo, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_camo_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_desert, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_desert_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_sky, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_sky_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_flames, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_flames_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_insulation, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_insulation_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_sleek, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_sleek_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_metal, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_metal_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_dash, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_dash_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_taint, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_taint_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_kerosene_vap, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_kerosene_vap_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_solid, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_solid_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_solid_flames, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_solid_flames_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_solid_insulation, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_solid_insulation_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_solid_sleek, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_solid_sleek_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_solid_soviet_glory, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_solid_soviet_glory_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_solid_bullet, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_solid_bullet_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_long_solid_silvermoonlight, PartType.FUSELAGE, 7, 5, ResourceManager.mp_f_10_long_kerosene, ResourceManager.mp_f_10_long_solid_silvermoonlight_tex);
		//
		MissilePart.registerPart(ModItems.mp_fuselage_10_15_kerosene, PartType.FUSELAGE, 9, 5.5, ResourceManager.mp_f_10_15_kerosene, ResourceManager.mp_f_10_15_kerosene_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_15_solid, PartType.FUSELAGE, 9, 5.5, ResourceManager.mp_f_10_15_kerosene, ResourceManager.mp_f_10_15_solid_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_15_hydrogen, PartType.FUSELAGE, 9, 5.5, ResourceManager.mp_f_10_15_kerosene, ResourceManager.mp_f_10_15_hydrogen_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_10_15_balefire, PartType.FUSELAGE, 9, 5.5, ResourceManager.mp_f_10_15_kerosene, ResourceManager.mp_f_10_15_balefire_tex);
		//
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_camo, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_camo_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_desert, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_desert_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_sky, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_sky_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_insulation, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_insulation_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_metal, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_metal_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_decorated, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_decorated_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_steampunk, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_steampunk_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_polite, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_polite_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_blackjack, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_blackjack_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_lambda, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_lambda_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_minuteman, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_minuteman_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_pip, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_pip_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_taint, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_taint_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_kerosene_yuck, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_kerosene_yuck_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_insulation, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_insulation_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_desh, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_desh_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_soviet_glory, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_soviet_glory_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_soviet_stank, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_soviet_stank_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_faust, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_faust_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_silvermoonlight, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_silvermoonlight_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_snowy, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_snowy_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_panorama, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_panorama_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_roses, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_roses_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_solid_mimi, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_kerosene, ResourceManager.mp_f_15_solid_mimi_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_hydrogen, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_hydrogen, ResourceManager.mp_f_15_hydrogen_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_hydrogen_cathedral, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_hydrogen, ResourceManager.mp_f_15_hydrogen_cathedral_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_balefire, PartType.FUSELAGE, 10, 6, ResourceManager.mp_f_15_hydrogen, ResourceManager.mp_f_15_balefire_tex);
		
		MissilePart.registerPart(ModItems.mp_fuselage_15_20_kerosene, PartType.FUSELAGE, 16, 10, ResourceManager.mp_f_15_20_kerosene, ResourceManager.mp_f_15_20_kerosene_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_20_kerosene_magnusson, PartType.FUSELAGE, 16, 10, ResourceManager.mp_f_15_20_kerosene, ResourceManager.mp_f_15_20_kerosene_magnusson_tex);
		MissilePart.registerPart(ModItems.mp_fuselage_15_20_solid, PartType.FUSELAGE, 16, 10, ResourceManager.mp_f_15_20_kerosene, ResourceManager.mp_f_15_20_solid_tex);

		//////
		
		MissilePart.registerPart(ModItems.mp_warhead_10_he, PartType.WARHEAD, 2, 1.5, ResourceManager.mp_w_10_he, ResourceManager.mp_w_10_he_tex);
		MissilePart.registerPart(ModItems.mp_warhead_10_incendiary, PartType.WARHEAD, 2.5, 2, ResourceManager.mp_w_10_incendiary, ResourceManager.mp_w_10_incendiary_tex);
		MissilePart.registerPart(ModItems.mp_warhead_10_buster, PartType.WARHEAD, 0.5, 1, ResourceManager.mp_w_10_buster, ResourceManager.mp_w_10_buster_tex);
		MissilePart.registerPart(ModItems.mp_warhead_10_nuclear, PartType.WARHEAD, 2, 1.5, ResourceManager.mp_w_10_nuclear, ResourceManager.mp_w_10_nuclear_tex);
		MissilePart.registerPart(ModItems.mp_warhead_10_nuclear_large, PartType.WARHEAD, 2.5, 1.5, ResourceManager.mp_w_10_nuclear_large, ResourceManager.mp_w_10_nuclear_large_tex);
		MissilePart.registerPart(ModItems.mp_warhead_10_taint, PartType.WARHEAD, 2.25, 1.5, ResourceManager.mp_w_10_taint, ResourceManager.mp_w_10_taint_tex);
		MissilePart.registerPart(ModItems.mp_warhead_10_cloud, PartType.WARHEAD, 2.25, 1.5, ResourceManager.mp_w_10_taint, ResourceManager.mp_w_10_cloud_tex);
		//
		MissilePart.registerPart(ModItems.mp_warhead_15_he, PartType.WARHEAD, 2, 1.5, ResourceManager.mp_w_15_he, ResourceManager.mp_w_15_he_tex);
		MissilePart.registerPart(ModItems.mp_warhead_15_incendiary, PartType.WARHEAD, 2, 1.5, ResourceManager.mp_w_15_incendiary, ResourceManager.mp_w_15_incendiary_tex);
		MissilePart.registerPart(ModItems.mp_warhead_15_nuclear, PartType.WARHEAD, 3.5, 2, ResourceManager.mp_w_15_nuclear, ResourceManager.mp_w_15_nuclear_tex);
		MissilePart.registerPart(ModItems.mp_warhead_15_nuclear_shark, PartType.WARHEAD, 3.5, 2, ResourceManager.mp_w_15_nuclear, ResourceManager.mp_w_15_nuclear_shark_tex);
		MissilePart.registerPart(ModItems.mp_warhead_15_nuclear_mimi, PartType.WARHEAD, 3.5, 2, ResourceManager.mp_w_15_nuclear, ResourceManager.mp_w_15_nuclear_mimi_tex);
		MissilePart.registerPart(ModItems.mp_warhead_15_boxcar, PartType.WARHEAD, 2.25, 7.5, ResourceManager.mp_w_15_boxcar, ResourceManager.boxcar_tex);
		MissilePart.registerPart(ModItems.mp_warhead_15_n2, PartType.WARHEAD, 3, 2, ResourceManager.mp_w_15_n2, ResourceManager.mp_w_15_n2_tex);
		MissilePart.registerPart(ModItems.mp_warhead_15_balefire, PartType.WARHEAD, 2.75, 2, ResourceManager.mp_w_15_balefire, ResourceManager.mp_w_15_balefire_tex);
		MissilePart.registerPart(ModItems.mp_warhead_15_turbine, PartType.WARHEAD, 2.25, 2, ResourceManager.mp_w_15_turbine, ResourceManager.mp_w_15_turbine_tex);
	}
	
	public static void registerPart(Item item, PartType type, double height, double guiheight, IModelCustom model, ResourceLocation texture) {
		
		MissilePart part = new MissilePart(item, type, height, guiheight, model, texture);
		parts.put(item.hashCode(), part);
	}
	
	public static MissilePart getPart(Item item) {
		
		if(item == null)
			return null;
		
		return parts.get(item.hashCode());
	}

}
