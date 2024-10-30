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
import static com.hbm.items.weapon.sedna.factory.XFactoryBlackPowder.*;
import static com.hbm.items.weapon.sedna.factory.XFactoryRocket.*;

import java.util.function.BiConsumer;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.weapon.sedna.*;

import net.minecraftforge.client.MinecraftForgeClient;

public class GunFactoryClient {

	public static void init() {
		//GUNS
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_debug,					new ItemRenderDebug());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_pepperbox,				new ItemRenderPepperbox());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_light_revolver,			new ItemRenderAtlas());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_light_revolver_dani,		new ItemRenderDANI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_henry,					new ItemRenderHenry());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_greasegun,				new ItemRenderGreasegun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_maresleg,				new ItemRenderMaresleg(ResourceManager.maresleg_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_maresleg_akimbo,			new ItemRenderMareslegAkimbo());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_maresleg_broken,			new ItemRenderMaresleg(ResourceManager.maresleg_broken_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flaregun,				new ItemRenderFlaregun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_heavy_revolver,			new ItemRenderHeavyRevolver(ResourceManager.heavy_revolver_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_heavy_revolver_lilmac,	new ItemRenderHeavyRevolver(ResourceManager.lilmac_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_carbine,					new ItemRenderCarbine());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_am180,					new ItemRenderAm180());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_liberator,				new ItemRenderLiberator());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_congolake,				new ItemRenderCongoLake());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flamer,					new ItemRenderFlamer());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lag,						new ItemRenderLAG());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi,						new ItemRenderUzi());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_spas12,					new ItemRenderSPAS12());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_panzerschreck,			new ItemRenderPanzerschreck());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_g3,						new ItemRenderG3());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_stinger,					new ItemRenderStinger());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_chemthrower,				new ItemRenderChemthrower());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_m2,						new ItemRenderM2());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_autoshotgun,				new ItemRenderShredder(ResourceManager.shredder_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_autoshotgun_sexy,		new ItemRenderShredder(ResourceManager.sexy_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_quadro,					new ItemRenderQuadro());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_minigun,					new ItemRenderMinigun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_missile_launcher,		new ItemRenderMissileLauncher());
		//PROJECTILES
		ammo_debug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		ammo_debug_buckshot.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		
		stone.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		flint.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		iron.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		shot.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		
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
		m44_equestrian.setRenderer(LegoClient.RENDER_LEGENDARY_BULLET);

		p22_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p22_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p22_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p22_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		
		p9_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p9_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p9_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		p9_ap.setRenderer(LegoClient.RENDER_AP_BULLET);

		r556_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r556_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r556_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r556_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		
		r762_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		r762_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		r762_du.setRenderer(LegoClient.RENDER_DU_BULLET);
		
		bmg50_sp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		bmg50_fmj.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		bmg50_jhp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		bmg50_ap.setRenderer(LegoClient.RENDER_AP_BULLET);
		bmg50_du.setRenderer(LegoClient.RENDER_DU_BULLET);
		
		g12_bp.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_bp_magnum.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_bp_slug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_slug.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_flechette.setRenderer(LegoClient.RENDER_FLECHETTE_BULLET);
		g12_magnum.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_explosive.setRenderer(LegoClient.RENDER_EXPRESS_BULLET);
		g12_phosphorus.setRenderer(LegoClient.RENDER_AP_BULLET);
		g12_anthrax.setRenderer(LegoClient.RENDER_STANDARD_BULLET);
		g12_equestrian.setRenderer(LegoClient.RENDER_LEGENDARY_BULLET);
		
		g26_flare.setRenderer(LegoClient.RENDER_FLARE);
		
		setRendererBulk(LegoClient.RENDER_GRENADE, g40_he, g40_heat, g40_demo, g40_inc);
		
		setRendererBulk(LegoClient.RENDER_RPZB, rocket_rpzb);
		setRendererBulk(LegoClient.RENDER_QD, rocket_qd);
		setRendererBulk(LegoClient.RENDER_ML, rocket_ml);
		//HUDS
		((ItemGunBaseNT) ModItems.gun_debug)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_pepperbox)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_light_revolver)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_henry)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_greasegun)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_maresleg)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_maresleg_broken)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_flaregun)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_heavy_revolver)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_heavy_revolver_lilmac)	.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_carbine)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_am180)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_liberator)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_congolake)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_flamer)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO_NOCOUNTER);
		((ItemGunBaseNT) ModItems.gun_lag)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_uzi)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_spas12)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_panzerschreck)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_g3)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_stinger)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_chemthrower)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_m2)						.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_autoshotgun)				.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_autoshotgun_sexy)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_quadro)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_minigun)					.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_missile_launcher)			.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		
		((ItemGunBaseNT) ModItems.gun_light_revolver_dani)	.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY_MIRROR, LegoClient.HUD_COMPONENT_AMMO_MIRROR);
		((ItemGunBaseNT) ModItems.gun_light_revolver_dani)	.getConfig(null, 1).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_maresleg_akimbo)		.getConfig(null, 0).hud(LegoClient.HUD_COMPONENT_DURABILITY_MIRROR, LegoClient.HUD_COMPONENT_AMMO_MIRROR);
		((ItemGunBaseNT) ModItems.gun_maresleg_akimbo)		.getConfig(null, 1).hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
	}
	
	public static void setRendererBulk(BiConsumer<EntityBulletBaseMK4, Float> renderer, BulletConfig... configs) {
		for(BulletConfig config : configs) config.setRenderer(renderer);
	}
}
