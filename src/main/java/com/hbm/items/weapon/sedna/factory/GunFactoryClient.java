package com.hbm.items.weapon.sedna.factory;

import static com.hbm.items.weapon.sedna.factory.GunFactory.*;
import static com.hbm.items.weapon.sedna.factory.XFactory10ga.*;
import static com.hbm.items.weapon.sedna.factory.XFactory12ga.*;
import static com.hbm.items.weapon.sedna.factory.XFactory22lr.*;
import static com.hbm.items.weapon.sedna.factory.XFactory357.*;
import static com.hbm.items.weapon.sedna.factory.XFactory35800.*;
import static com.hbm.items.weapon.sedna.factory.XFactory40mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactory44.*;
import static com.hbm.items.weapon.sedna.factory.XFactory45.*;
import static com.hbm.items.weapon.sedna.factory.XFactory50.*;
import static com.hbm.items.weapon.sedna.factory.XFactory556mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactory75Bolt.*;
import static com.hbm.items.weapon.sedna.factory.XFactory762mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactory9mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactoryAccelerator.*;
import static com.hbm.items.weapon.sedna.factory.XFactoryBlackPowder.*;
import static com.hbm.items.weapon.sedna.factory.XFactoryCatapult.*;
import static com.hbm.items.weapon.sedna.factory.XFactoryEnergy.*;
import static com.hbm.items.weapon.sedna.factory.XFactoryFolly.*;
import static com.hbm.items.weapon.sedna.factory.XFactoryTurret.*;
import static com.hbm.items.weapon.sedna.factory.XFactoryRocket.*;

import java.util.function.BiConsumer;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityBulletBeamBase;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.weapon.sedna.*;

import net.minecraftforge.client.MinecraftForgeClient;

public class GunFactoryClient {

	public static void init() {
		//GUNS
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_debug,						new ItemRenderDebug());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_pepperbox,					new ItemRenderPepperbox());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_light_revolver,				new ItemRenderAtlas(ResourceManager.bio_revolver_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_light_revolver_atlas,		new ItemRenderAtlas(ResourceManager.bio_revolver_atlas_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_light_revolver_dani,			new ItemRenderDANI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_henry,						new ItemRenderHenry(ResourceManager.henry_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_henry_lincoln,				new ItemRenderHenry(ResourceManager.henry_lincoln_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_greasegun,					new ItemRenderGreasegun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_maresleg,					new ItemRenderMaresleg(ResourceManager.maresleg_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_maresleg_akimbo,				new ItemRenderMareslegAkimbo());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_maresleg_broken,				new ItemRenderMaresleg(ResourceManager.maresleg_broken_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flaregun,					new ItemRenderFlaregun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_heavy_revolver,				new ItemRenderHeavyRevolver(ResourceManager.heavy_revolver_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_heavy_revolver_lilmac,		new ItemRenderHeavyRevolver(ResourceManager.lilmac_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_heavy_revolver_protege,		new ItemRenderHeavyRevolver(ResourceManager.heavy_revolver_protege_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_carbine,						new ItemRenderCarbine());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_am180,						new ItemRenderAm180());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_liberator,					new ItemRenderLiberator());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_congolake,					new ItemRenderCongoLake());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flamer,						new ItemRenderFlamer(ResourceManager.flamethrower_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flamer_topaz,				new ItemRenderFlamer(ResourceManager.flamethrower_topaz_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flamer_daybreaker,			new ItemRenderFlamer(ResourceManager.flamethrower_daybreaker_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lag,							new ItemRenderLAG());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi,							new ItemRenderUzi());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi_akimbo,					new ItemRenderUziAkimbo());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_spas12,						new ItemRenderSPAS12());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_panzerschreck,				new ItemRenderPanzerschreck());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_g3,							new ItemRenderG3(ResourceManager.g3_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_g3_zebra,					new ItemRenderG3(ResourceManager.g3_zebra_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_stinger,						new ItemRenderStinger());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_chemthrower,					new ItemRenderChemthrower());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_m2,							new ItemRenderM2());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_autoshotgun,					new ItemRenderShredder(ResourceManager.shredder_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_autoshotgun_shredder,		new ItemRenderShredder(ResourceManager.shredder_orig_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_autoshotgun_sexy,			new ItemRenderShredder(ResourceManager.sexy_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_quadro,						new ItemRenderQuadro());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_minigun,						new ItemRenderMinigun(ResourceManager.minigun_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_minigun_lacunae,				new ItemRenderMinigun(ResourceManager.minigun_lacunae_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_missile_launcher,			new ItemRenderMissileLauncher());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_tesla_cannon,				new ItemRenderTeslaCannon());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_laser_pistol,				new ItemRenderLaserPistol(ResourceManager.laser_pistol_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_laser_pistol_pew_pew,		new ItemRenderLaserPistol(ResourceManager.laser_pistol_pew_pew_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_laser_pistol_morning_glory,	new ItemRenderLaserPistol(ResourceManager.laser_pistol_morning_glory_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_stg77,						new ItemRenderSTG77());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_tau,							new ItemRenderTau());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_fatman,						new ItemRenderFatMan());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lasrifle,					new ItemRenderLasrifle());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_coilgun,						new ItemRenderCoilgun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_hangman,						new ItemRenderHangman());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_mas36,						new ItemRenderMAS36());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolter,						new ItemRenderBolter());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_folly,						new ItemRenderFolly());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_aberrator,					new ItemRenderAberrator());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_aberrator_eott,				new ItemRenderEOTT());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_double_barrel,				new ItemRenderDoubleBarrel(ResourceManager.double_barrel_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_double_barrel_sacred_dragon,	new ItemRenderDoubleBarrel(ResourceManager.double_barrel_sacred_dragon_tex));
		//PROJECTILES
		ammo_debug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		
		stone.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		flint.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		iron.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		shot.setRenderer(LegoClient.RENDER_STANDARD_BULLET);

		m357_bp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m357_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m357_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m357_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m357_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		m357_express.setRenderer(LegoClient.RENDER_EXPRESS_BULLET);

		m44_bp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m44_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m44_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m44_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		m44_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		m44_express.setRenderer(LegoClient.RENDER_EXPRESS_BULLET);
		m44_equestrian_pip.setRenderer(LegoClient.RENDER_LEGENDARY_BULLET);
		m44_equestrian_mn7.setRenderer(LegoClient.RENDER_LEGENDARY_BULLET);

		p22_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p22_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p22_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p22_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		
		p9_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p9_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p9_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p9_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		
		p45_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p45_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p45_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p45_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		p45_du.setRenderer(LegoClient.RENDER_DU_BULLET);

		r556_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r556_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r556_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r556_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		
		setRendererBulk(LegoClient.RENDER_AP_BULLET, r556_inc_sp, r556_inc_fmj, r556_inc_jhp, r556_inc_ap);
		
		r762_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		r762_du.setRenderer(LegoClient.RENDER_DU_BULLET);
		r762_he.setRenderer(LegoClient.RENDER_HE_BULLET);
		
		bmg50_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		bmg50_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		bmg50_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		bmg50_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		bmg50_du.setRenderer(LegoClient.RENDER_DU_BULLET);
		bmg50_he.setRenderer(LegoClient.RENDER_HE_BULLET);

		b75.setRenderer(LegoClient.RENDER_AP_BULLET);
		b75_inc.setRenderer(LegoClient.RENDER_AP_BULLET);
		b75_exp.setRenderer(LegoClient.RENDER_EXPRESS_BULLET);
		
		g12_bp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_bp_magnum.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_bp_slug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_slug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_flechette.setRenderer(LegoClient.RENDER_FLECHETTE_BULLET);
		g12_magnum.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_explosive.setRenderer(LegoClient.RENDER_EXPRESS_BULLET);
		g12_phosphorus.setRenderer(LegoClient.RENDER_AP_BULLET);
		g12_equestrian_bj.setRenderer(LegoClient.RENDER_LEGENDARY_BULLET);
		g12_equestrian_tkr.setRenderer(LegoClient.RENDER_LEGENDARY_BULLET);

		g12_sub.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_sub_slug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_sub_flechette.setRenderer(LegoClient.RENDER_FLECHETTE_BULLET);
		g12_sub_magnum.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_sub_explosive.setRenderer(LegoClient.RENDER_EXPRESS_BULLET);
		g12_sub_phosphorus.setRenderer(LegoClient.RENDER_AP_BULLET);
		
		setRendererBulkBeam(LegoClient.RENDER_LASER_CYAN, g12_shredder, g12_shredder_slug, g12_shredder_flechette, g12_shredder_magnum, g12_shredder_explosive, g12_shredder_phosphorus);

		g10.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g10_shrapnel.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g10_du.setRenderer(LegoClient.RENDER_DU_BULLET);
		g10_slug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g10_explosive.setRenderer(LegoClient.RENDER_HE_BULLET);

		g26_flare.setRenderer(LegoClient.RENDER_FLARE);
		g26_flare_supply.setRenderer(LegoClient.RENDER_FLARE_SUPPLY);
		g26_flare_weapon.setRenderer(LegoClient.RENDER_FLARE_WEAPON);
		
		setRendererBulk(LegoClient.RENDER_GRENADE, g40_he, g40_heat, g40_demo, g40_inc, g40_phosphorus);
		
		setRendererBulk(LegoClient.RENDER_RPZB, rocket_rpzb);
		setRendererBulk(LegoClient.RENDER_QD, rocket_qd);
		setRendererBulk(LegoClient.RENDER_ML, rocket_ml);
		
		setRendererBulk(LegoClient.RENDER_NUKE, nuke_standard, nuke_demo, nuke_high);
		nuke_tots.setRenderer(LegoClient.RENDER_GRENADE);
		nuke_hive.setRenderer(LegoClient.RENDER_HIVE);

		setRendererBulkBeam(LegoClient.RENDER_LIGHTNING, energy_tesla, energy_tesla_overcharge);
		setRendererBulkBeam(LegoClient.RENDER_TAU, tau_uranium);
		setRendererBulkBeam(LegoClient.RENDER_TAU_CHARGE, tau_uranium_charge);
		setRendererBulkBeam(LegoClient.RENDER_LASER_RED, energy_las, energy_las_overcharge, energy_las_ir);
		setRendererBulkBeam(LegoClient.RENDER_LASER_PURPLE, energy_lacunae, energy_lacunae_overcharge, energy_lacunae_ir);
		setRendererBulkBeam(LegoClient.RENDER_LASER_EMERALD, energy_emerald, energy_emerald_overcharge, energy_emerald_ir);
		
		setRendererBulk(LegoClient.RENDER_AP_BULLET, coil_tungsten, coil_ferrouranium);
		
		folly_sm.setRendererBeam(LegoClient.RENDER_FOLLY);
		folly_nuke.setRenderer(LegoClient.RENDER_BIG_NUKE);

		p35800.setRendererBeam(LegoClient.RENDER_CRACKLE);
		
		setRendererBulk(LegoClient.RENDER_GRENADE, shell_normal, shell_explosive, shell_ap, shell_du, shell_w9); //TODO: change the sabots
		
		//HUDS
		((ItemGunBaseNT) ModItems.gun_debug)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_pepperbox)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_light_revolver)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_light_revolver_atlas)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_henry)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_henry_lincoln)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_greasegun)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_maresleg)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_maresleg_broken)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_flaregun)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_heavy_revolver)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_heavy_revolver_lilmac)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_heavy_revolver_protege)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_carbine)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_am180)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_liberator)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_congolake)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_flamer)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO_NOCOUNTER);
		((ItemGunBaseNT) ModItems.gun_flamer_topaz)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO_NOCOUNTER);
		((ItemGunBaseNT) ModItems.gun_flamer_daybreaker)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO_NOCOUNTER);
		((ItemGunBaseNT) ModItems.gun_uzi)							.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_spas12)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_panzerschreck)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_g3)							.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_g3_zebra)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_stinger)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_chemthrower)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_m2)							.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_autoshotgun)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_autoshotgun_shredder)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_autoshotgun_sexy)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_quadro)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_lag)							.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_minigun)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_minigun_lacunae)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_missile_launcher)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_tesla_cannon)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_laser_pistol)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_laser_pistol_pew_pew)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_laser_pistol_morning_glory)	.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_stg77)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_tau)							.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_fatman)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_lasrifle)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_coilgun)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_hangman)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_mas36)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_bolter)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_folly)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_aberrator)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_double_barrel)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_double_barrel_sacred_dragon)	.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		
		((ItemGunBaseNT) ModItems.gun_light_revolver_dani)	.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY_MIRROR, LegoClient.HUD_COMPONENT_AMMO_MIRROR);
		((ItemGunBaseNT) ModItems.gun_light_revolver_dani)	.getConfig(null, 1).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_maresleg_akimbo)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY_MIRROR, LegoClient.HUD_COMPONENT_AMMO_MIRROR);
		((ItemGunBaseNT) ModItems.gun_maresleg_akimbo)		.getConfig(null, 1).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_uzi_akimbo)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY_MIRROR, LegoClient.HUD_COMPONENT_AMMO_MIRROR);
		((ItemGunBaseNT) ModItems.gun_uzi_akimbo)			.getConfig(null, 1).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_aberrator_eott)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_AMMO_MIRROR);
		((ItemGunBaseNT) ModItems.gun_aberrator_eott)		.getConfig(null, 1).hud(LegoClient.HUD_COMPONENT_AMMO);
	}
	
	public static void setRendererBulk(BiConsumer<EntityBulletBaseMK4, Float> renderer, BulletConfig... configs) { for(BulletConfig config : configs) config.setRenderer(renderer); }
	public static void setRendererBulkBeam(BiConsumer<EntityBulletBeamBase, Float> renderer, BulletConfig... configs) { for(BulletConfig config : configs) config.setRendererBeam(renderer); }
}
