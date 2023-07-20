package com.hbm.items;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.hbm.items.weapon.ItemAmmo.AmmoItemTrait;
import com.hbm.lib.HbmCollection;

public class ItemAmmoEnums {

	public enum AmmoLunaticSniper implements IAmmoItemEnum {
		SABOT("ammo_luna"),
		INCENDIARY("ammo_luna_incendiary"),
		EXPLOSIVE("ammo_luna_explosive"),
		DANGER("ammo_luna_danger");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private AmmoLunaticSniper(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoFireExt implements IAmmoItemEnum {
		WATER("ammo_fireext"),
		FOAM("ammo_fireext_foam"),
		SAND("ammo_fireext_sand");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private AmmoFireExt(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoFlamethrower implements IAmmoItemEnum {
		DIESEL("ammo_fuel"),
		NAPALM("ammo_fuel_napalm", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.CON_HEAVY_WEAR),
		PHOSPHORUS("ammo_fuel_phosphorus", AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.PRO_ACCURATE1, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_SING_PROJECTILE, AmmoItemTrait.CON_HEAVY_WEAR),
		VAPORIZER("ammo_fuel_vaporizer", AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.PRO_FLAMES, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_ERASER, AmmoItemTrait.CON_ACCURACY2, AmmoItemTrait.CON_RANGE2, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_LING_FIRE),
		CHLORINE("ammo_fuel_gas", AmmoItemTrait.PRO_NO_GRAVITY, AmmoItemTrait.PRO_POISON_GAS, AmmoItemTrait.CON_NO_DAMAGE, AmmoItemTrait.CON_NO_FIRE);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private AmmoFlamethrower(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoMisc implements IAmmoItemEnum {
		//LUNA_SNIPER("ammo_lunar", Gun50BMGFactory.getLunaticSabotRound(), AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.PRO_ACCURATE2, AmmoItemTrait.NEU_HEAVY_METAL),
		DGK("ammo_dkg");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private AmmoMisc(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoStinger implements IAmmoItemEnum {
		STOCK("ammo_stinger_rocket"),
		HE("ammo_stinger_rocket_he", AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_WEAR),
		INCENDIARY("ammo_stinger_rocket_incendiary", HbmCollection.IncendiaryType),
		NUCLEAR("ammo_stinger_rocket_nuclear", AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.CON_SUPER_WEAR),
		BONES("ammo_stinger_rocket_bones");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private AmmoStinger(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private AmmoStinger(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoRocket implements IAmmoItemEnum {
		STOCK("ammo_rocket"),
		HE("ammo_rocket_he", AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_WEAR),
		INCENDIARY("ammo_rocket_incendiary", HbmCollection.IncendiaryType),
		EMP("ammo_rocket_emp", AmmoItemTrait.PRO_EMP, AmmoItemTrait.CON_RADIUS),
		SLEEK("ammo_rocket_sleek", AmmoItemTrait.PRO_RADIUS_HIGH, AmmoItemTrait.PRO_NO_GRAVITY, AmmoItemTrait.CON_SPEED),
		SHRAPNEL("ammo_rocket_shrapnel", AmmoItemTrait.PRO_SHRAPNEL),
		GLARE("ammo_rocket_glare", AmmoItemTrait.PRO_SPEED, AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		NUCLEAR("ammo_rocket_nuclear", AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.CON_SUPER_WEAR, AmmoItemTrait.CON_SPEED),
		CHLORINE("ammo_rocket_toxic", AmmoItemTrait.PRO_CHLORINE, AmmoItemTrait.CON_NO_EXPLODE1, AmmoItemTrait.CON_SPEED),
		RPC("ammo_rocket_rpc", AmmoItemTrait.PRO_CHAINSAW, AmmoItemTrait.PRO_PENETRATION, AmmoItemTrait.PRO_NO_GRAVITY, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_NO_EXPLODE1, AmmoItemTrait.NEU_UHH ),
		PHOSPHORUS("ammo_rocket_phosphorus", HbmCollection.PhosphorusTypeSpecial),
		CANISTER("ammo_rocket_canister"),
		DIGAMMA("ammo_rocket_digamma");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private AmmoRocket(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private AmmoRocket(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoGrenade implements IAmmoItemEnum {
		STOCK("ammo_grenade"),
		HE("ammo_grenade_he", AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_WEAR),
		INCENDIARY("ammo_grenade_incendiary", AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		PHOSPHORUS("ammo_grenade_phosphorus", AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR),
		CHLORINE("ammo_grenade_toxic", AmmoItemTrait.PRO_CHLORINE, AmmoItemTrait.CON_NO_EXPLODE1),
		SLEEK("ammo_grenade_sleek", AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.NEU_JOLT),
		CONCUSSION("ammo_grenade_concussion", AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_NO_EXPLODE2),
		FINNED("ammo_grenade_finned", AmmoItemTrait.PRO_GRAVITY, AmmoItemTrait.CON_RADIUS),
		NUCLEAR("ammo_grenade_nuclear", AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.CON_HEAVY_WEAR),
		TRACER("ammo_grenade_tracer", AmmoItemTrait.NEU_BLANK),
		KAMPF("ammo_grenade_kampf", AmmoItemTrait.PRO_ROCKET_PROPELLED, AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_ACCURATE1, AmmoItemTrait.CON_WEAR);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private AmmoGrenade(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoFatman implements IAmmoItemEnum {
		STOCK("ammo_nuke"),
		LOW("ammo_nuke_low", AmmoItemTrait.CON_RADIUS),
		HIGH("ammo_nuke_high", AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_FALLOUT),
		TOTS("ammo_nuke_tots", AmmoItemTrait.PRO_BOMB_COUNT, AmmoItemTrait.NEU_FUN, AmmoItemTrait.CON_ACCURACY2, AmmoItemTrait.CON_RADIUS, AmmoItemTrait.CON_NO_MIRV),
		SAFE("ammo_nuke_safe", AmmoItemTrait.CON_RADIUS, AmmoItemTrait.CON_NO_EXPLODE2),
		PUMPKIN("ammo_nuke_pumpkin", AmmoItemTrait.CON_NN),
		MIRV("ammo_mirv"),
		MIRV_LOW("ammo_mirv_low", AmmoItemTrait.CON_RADIUS),
		MIRV_HIGH("ammo_mirv_high", AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_FALLOUT),
		MIRV_SAFE("ammo_mirv_safe", AmmoItemTrait.CON_RADIUS, AmmoItemTrait.CON_NO_EXPLODE2),
		MIRV_SPECIAL("ammo_mirv_special"),
		BALEFIRE("gun_bf_ammo"),
		BARREL("ammo_nuke_barrel");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private AmmoFatman(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoDart implements IAmmoItemEnum {
		GPS("ammo_dart"),
		NUCLEAR("ammo_dart_nuclear"),
		NERF("ammo_dart_nerf");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private AmmoDart(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo240Shell implements IAmmoItemEnum {
		STOCK("ammo_shell"),
		EXPLOSIVE("ammo_shell_explosive"),
		APFSDS_T("ammo_shell_apfsds_t"),
		APFSDS_DU("ammo_shell_apfsds_du"),
		W9("ammo_shell_w9");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo240Shell(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo9mm implements IAmmoItemEnum {
		STOCK("ammo_9mm"),
		AP("ammo_9mm_ap", HbmCollection.APType),
		DU("ammo_9mm_du", HbmCollection.DUType),
		CHLOROPHYTE("ammo_9mm_chlorophyte", HbmCollection.ChlorophyteType),
		ROCKET("ammo_9mm_rocket", AmmoItemTrait.PRO_ROCKET, AmmoItemTrait.NEU_UHH);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo9mm(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo9mm(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo762NATO implements IAmmoItemEnum {
		STOCK("ammo_762"),
		AP("ammo_762_ap", HbmCollection.APType),
		DU("ammo_762_du", HbmCollection.DUType),
		TRACER("ammo_762_tracer", AmmoItemTrait.NEU_TRACER),
		PHOSPHORUS("ammo_762_phosphorus", HbmCollection.PhosphorusType),
		BLANK("ammo_762_k", AmmoItemTrait.NEU_BLANK);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo762NATO(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo762NATO(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo75Bolt implements IAmmoItemEnum {
		STOCK("ammo_75bolt"),
		INCENDIARY("ammo_75bolt_incendiary"),
		HE("ammo_75bolt_he");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo75Bolt(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo5mm implements IAmmoItemEnum {
		STOCK("ammo_5mm"),
		EXPLOSIVE("ammo_5mm_explosive", HbmCollection.ExplosiveType),
		DU("ammo_5mm_du", HbmCollection.DUType),
		STAR("ammo_5mm_star", HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_5mm_chlorophyte", HbmCollection.ChlorophyteType);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo5mm(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo5mm(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo556mm implements IAmmoItemEnum {
		STOCK("ammo_556"),
		GOLD("gun_pm_ammo"),
		PHOSPHORUS("ammo_556_phosphorus", HbmCollection.PhosphorusType),
		AP("ammo_556_ap", HbmCollection.APType),
		DU("ammo_556_du", HbmCollection.DUType),
		STAR("ammo_556_star", HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_556_chlorophyte", HbmCollection.ChlorophyteType),
		SLEEK("ammo_556_sleek", AmmoItemTrait.NEU_MASKMAN_METEORITE),
		TRACER("ammo_556_tracer", AmmoItemTrait.NEU_TRACER),
		FLECHETTE("ammo_556_flechette", HbmCollection.FlechetteType),
		FLECHETTE_INCENDIARY("ammo_556_flechette_incendiary", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_PENETRATION),
		FLECHETTE_PHOSPHORUS("ammo_556_flechette_phosphorus", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.NEU_WARCRIME2, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_PENETRATION),
		FLECHETTE_DU("ammo_556_flechette_du", AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.PRO_PENETRATION, AmmoItemTrait.NEU_HEAVY_METAL, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_HEAVY_WEAR),
		FLECHETTE_CHLOROPHYTE("ammo_556_flechette_chlorophyte", HbmCollection.ChlorophyteType),
		FLECHETTE_SLEEK("ammo_556_flechette_sleek", AmmoItemTrait.NEU_MASKMAN_METEORITE),
		K("ammo_556_k", AmmoItemTrait.NEU_BLANK);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo556mm(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo556mm(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo50BMG implements IAmmoItemEnum {
		STOCK("ammo_50bmg"),
		INCENDIARY("ammo_50bmg_incendiary", HbmCollection.IncendiaryType),
		PHOSPHORUS("ammo_50bmg_phosphorus", HbmCollection.PhosphorusType),
		EXPLOSIVE("ammo_50bmg_explosive", HbmCollection.ExplosiveType),
		AP("ammo_50bmg_ap", HbmCollection.APType),
		DU("ammo_50bmg_du", HbmCollection.DUType),
		STAR("ammo_50bmg_star", HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_50bmg_chlorophyte", HbmCollection.ChlorophyteType),
		SLEEK("ammo_50bmg_sleek", AmmoItemTrait.NEU_MASKMAN_METEORITE),
		FLECHETTE("ammo_50bmg_flechette", AmmoItemTrait.PRO_DAMAGE),
		FLECHETTE_AM("ammo_50bmg_flechette_am", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_UHH),
		FLECHETTE_PO("ammo_50bmg_flechette_po", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_UHH);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo50BMG(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo50BMG(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo50AE implements IAmmoItemEnum {
		STOCK("ammo_50ae"),
		AP("ammo_50ae_ap", HbmCollection.APType),
		DU("ammo_50ae_du", HbmCollection.DUType),
		STAR("ammo_50ae_star", HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_50ae_chlorophyte", HbmCollection.ChlorophyteType);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo50AE(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo50AE(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo4Gauge implements IAmmoItemEnum {
		STOCK("ammo_4gauge"),
		SLUG("ammo_4gauge_slug", AmmoItemTrait.PRO_ACCURATE2, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		FLECHETTE("ammo_4gauge_flechette", HbmCollection.FlechetteType),
		FLECHETTE_PHOSPHORUS("ammo_4gauge_flechette_phosphorus", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.NEU_WARCRIME2, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR),
		EXPLOSIVE("ammo_4gauge_explosive", AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_40MM, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		MINING("ammo_4gauge_semtex", AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_MINING, AmmoItemTrait.CON_NO_EXPLODE3, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		BALEFIRE("ammo_4gauge_balefire", AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_BALEFIRE, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		KAMPF("ammo_4gauge_kampf", AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_ROCKET_PROPELLED, AmmoItemTrait.PRO_ACCURATE1, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		CANISTER("ammo_4gauge_canister"),
		SLEEK("ammo_4gauge_sleek", AmmoItemTrait.NEU_MASKMAN_FLECHETTE),
		CLAW("ammo_4gauge_claw"),
		VAMPIRE("ammo_4gauge_vampire"),
		VOID("ammo_4gauge_void"),
		LTBL("ammo_4gauge_flash1",AmmoItemTrait.PRO_FLASH, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		LTBL_SUPER("ammo_4gauge_flash2", AmmoItemTrait.PRO_FLASH, AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_STUNNING, AmmoItemTrait.NEU_WARCRIME2, AmmoItemTrait.CON_SUPER_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		QUACK("ammo_4gauge_titan", AmmoItemTrait.PRO_MARAUDER, AmmoItemTrait.NEU_NO_CON);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo4Gauge(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo4Gauge(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo45ACP implements IAmmoItemEnum {
		STOCK("ammo_45"),
		AP("ammo_45_ap", HbmCollection.APType),
		DU("ammo_45_du", HbmCollection.DUType);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo45ACP(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo45ACP(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo44Magnum implements IAmmoItemEnum {
		STOCK("ammo_44"),
		AP("ammo_44_ap", HbmCollection.APType),
		DU("ammo_44_du", HbmCollection.DUType),
		PHOSPHORUS("ammo_44_phosphorus", HbmCollection.PhosphorusType),
		STAR("ammo_44_star", HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_44_chlorophyte", HbmCollection.ChlorophyteType),
		PIP("ammo_44_pip", AmmoItemTrait.NEU_BOXCAR, AmmoItemTrait.CON_DAMAGE),
		BJ("ammo_44_bj", AmmoItemTrait.NEU_BOAT, AmmoItemTrait.CON_DAMAGE),
		SILVER("ammo_44_silver", AmmoItemTrait.NEU_BUILDING, AmmoItemTrait.CON_DAMAGE),
		ROCKET("ammo_44_rocket", AmmoItemTrait.PRO_ROCKET, AmmoItemTrait.NEU_UHH);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo44Magnum(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo44Magnum(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo357Magnum implements IAmmoItemEnum {
		IRON("gun_revolver_iron_ammo"),
		LEAD("gun_revolver_ammo"),
		NUCLEAR("gun_revolver_lead_ammo"),
		GOLD("gun_revolver_gold_ammo"),
		DESH("ammo_357_desh", AmmoItemTrait.PRO_FIT_357, AmmoItemTrait.PRO_DAMAGE_SLIGHT),
		SCHRABIDIUM("gun_revolver_schrabidium_ammo"),
		STEEL("gun_revolver_cursed_ammo"),
		NIGHTMARE1("gun_revolver_nightmare_ammo"),
		NIGHTMARE2("gun_revolver_nightmare2_ammo");
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo357Magnum(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo22LR implements IAmmoItemEnum {
		STOCK("ammo_22lr"),
		AP("ammo_22lr_ap", HbmCollection.APType),
		CHLOROPHYTE("ammo_22lr_chlorophyte", HbmCollection.ChlorophyteType);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo22LR(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo22LR(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo20Gauge implements IAmmoItemEnum {
		STOCK("ammo_20gauge"),
		SLUG("ammo_20gauge_slug", AmmoItemTrait.PRO_ACCURATE2, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		FLECHETTE("ammo_20gauge_flechette", HbmCollection.FlechetteType),
		INCENDIARY("ammo_20gauge_incendiary", AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		SHRAPNEL("ammo_20gauge_shrapnel", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_MORE_BOUNCY, AmmoItemTrait.CON_WEAR),
		EXPLOSIVE("ammo_20gauge_explosive", HbmCollection.ExplosiveType),
		CAUSTIC("ammo_20gauge_caustic", AmmoItemTrait.PRO_TOXIC, AmmoItemTrait.PRO_CAUSTIC, AmmoItemTrait.NEU_NO_BOUNCE, AmmoItemTrait.CON_HEAVY_WEAR),
		SHOCK("ammo_20gauge_shock", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_STUNNING, AmmoItemTrait.PRO_EMP, AmmoItemTrait.NEU_NO_BOUNCE, AmmoItemTrait.CON_HEAVY_WEAR),
		WITHER("ammo_20gauge_wither", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WITHERING),
		SLEEK("ammo_20gauge_sleek", AmmoItemTrait.NEU_MASKMAN_FLECHETTE);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo20Gauge(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo20Gauge(String unloc, Set<AmmoItemTrait> traits) {
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo12Gauge implements IAmmoItemEnum {
		STOCK("ammo_12gauge"),
		INCENDIARY("ammo_12gauge_incendiary", AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		SHRAPNEL("ammo_12gauge_shrapnel", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_MORE_BOUNCY, AmmoItemTrait.CON_WEAR),
		DU("ammo_12gauge_du", AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_PENETRATION, AmmoItemTrait.NEU_HEAVY_METAL, AmmoItemTrait.CON_HEAVY_WEAR),
		MARAUDER("ammo_12gauge_marauder", AmmoItemTrait.PRO_MARAUDER, AmmoItemTrait.NEU_NO_CON),
		SLEEK("ammo_12gauge_sleek", AmmoItemTrait.NEU_MASKMAN_FLECHETTE),
		PERCUSSION("ammo_12gauge_percussion", AmmoItemTrait.PRO_PERCUSSION, AmmoItemTrait.CON_NO_PROJECTILE);
		
		private final Set<AmmoItemTrait> traits;
		private final String unloc;

		private Ammo12Gauge(String unloc, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public interface IAmmoItemEnum {
		public Set<AmmoItemTrait> getTraits();
		public String getInternalName();
	}

	static Set<AmmoItemTrait> safeAssign(AmmoItemTrait[] traits) {
		return traits == null ? ImmutableSet.of() : ImmutableSet.copyOf(traits);
	}
}
