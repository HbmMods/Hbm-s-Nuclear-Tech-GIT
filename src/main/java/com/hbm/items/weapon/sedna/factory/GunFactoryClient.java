package com.hbm.items.weapon.sedna.factory;

import static com.hbm.items.weapon.sedna.factory.GunFactory.*;
import static com.hbm.items.weapon.sedna.factory.XFactory12ga.*;
import static com.hbm.items.weapon.sedna.factory.XFactory22lr.*;
import static com.hbm.items.weapon.sedna.factory.XFactory357.*;
import static com.hbm.items.weapon.sedna.factory.XFactory40mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactory44.*;
import static com.hbm.items.weapon.sedna.factory.XFactory50.*;
import static com.hbm.items.weapon.sedna.factory.XFactory556mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactory762mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactory9mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactoryRocket.*;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.render.item.weapon.sedna.*;

import net.minecraftforge.client.MinecraftForgeClient;

public class GunFactoryClient {

	public static void init() {
		//GUNS
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_debug, new ItemRenderDebug());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_pepperbox, new ItemRenderPepperbox());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_light_revolver, new ItemRenderAtlas());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_light_revolver_dani, new ItemRenderDANI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_henry, new ItemRenderHenry());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_greasegun, new ItemRenderGreasegun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_maresleg, new ItemRenderMaresleg());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_maresleg_akimbo, new ItemRenderMareslegAkimbo());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flaregun, new ItemRenderFlaregun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_heavy_revolver, new ItemRenderHeavyRevolver());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_carbine, new ItemRenderCarbine());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_am180, new ItemRenderAm180());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_liberator, new ItemRenderLiberator());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_congolake, new ItemRenderCongoLake());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flamer, new ItemRenderFlamer());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lag, new ItemRenderLAG());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi, new ItemRenderUzi());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_spas12, new ItemRenderSPAS12());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_panzerschreck, new ItemRenderPanzerschreck());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_g3, new ItemRenderG3());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_stinger, new ItemRenderStinger());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_chemthrower, new ItemRenderChemthrower());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_m2, new ItemRenderM2());
		//PROJECTILES
		ammo_debug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		ammo_debug_buckshot.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m357_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m357_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m357_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m357_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		m357_express.setRenderer(LegoClient.RENDER_EXPRESS_BULLET);
		m44_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m44_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m44_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m44_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		m44_express.setRenderer(LegoClient.RENDER_EXPRESS_BULLET);
		p9_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p9_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p9_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p9_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		g12_bp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_bp_magnum.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_bp_slug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		r762_du.setRenderer(LegoClient.RENDER_DU_BULLET);
		p22_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p22_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p22_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p22_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		g40_flare.setRenderer(LegoClient.RENDER_FLARE);
		g40.setRenderer(LegoClient.RENDER_GRENADE);
		rocket_rpzb_he.setRenderer(LegoClient.RENDER_RPZB);
		rocket_rpzb_heat.setRenderer(LegoClient.RENDER_RPZB);
		r556_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r556_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r556_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r556_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		bmg50_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		bmg50_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		bmg50_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		bmg50_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		bmg50_du.setRenderer(LegoClient.RENDER_DU_BULLET);
		//HUDS
		((ItemGunBaseNT) ModItems.gun_debug)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_pepperbox)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_light_revolver)	.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_henry)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_greasegun)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_maresleg)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_flaregun)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_heavy_revolver)	.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_carbine)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_am180)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_liberator)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_congolake)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_flamer)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO_NOCOUNTER);
		((ItemGunBaseNT) ModItems.gun_lag)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_uzi)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_spas12)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_panzerschreck)	.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_g3)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_stinger)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_chemthrower)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_m2)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		
		((ItemGunBaseNT) ModItems.gun_light_revolver_dani).getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY_MIRROR, LegoClient.HUD_COMPONENT_AMMO_MIRROR);
		((ItemGunBaseNT) ModItems.gun_light_revolver_dani).getConfig(null, 1).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_maresleg_akimbo).getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY_MIRROR, LegoClient.HUD_COMPONENT_AMMO_MIRROR);
		((ItemGunBaseNT) ModItems.gun_maresleg_akimbo).getConfig(null, 1).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
	}
}
