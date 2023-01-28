package com.hbm.items;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.guncfg.*;
import com.hbm.items.weapon.ItemAmmo.AmmoItemTrait;
import com.hbm.lib.HbmCollection;

public class ItemAmmoEnums {

	public enum AmmoLunaticSniper implements IAmmoItemEnum {
		SABOT("ammo_lunar", Gun50BMGFactory.getLunaticSabotRound()),
		INCENDIARY("ammo_lunar_incendiary", Gun50BMGFactory.getLunaticIncendiaryRound()),
		EXPLOSIVE("ammo_lunar_explosive", Gun50BMGFactory.getLunaticSabotRound());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private AmmoLunaticSniper(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoFireExt implements IAmmoItemEnum {
		WATER("ammo_fireext", GunEnergyFactory.getFextConfig()),
		FOAM("ammo_fireext_foam", GunEnergyFactory.getFextFoamConfig()),
		SAND("ammo_fireext_sand", GunEnergyFactory.getFextSandConfig());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private AmmoFireExt(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoFlamethrower implements IAmmoItemEnum {
		DIESEL("ammo_fuel", GunEnergyFactory.getFlameConfig()),
		NAPALM("ammo_fuel_napalm", GunEnergyFactory.getNapalmConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.CON_HEAVY_WEAR),
		PHOSPHORUS("ammo_fuel_phosphorus", GunEnergyFactory.getPhosphorusConfig(), AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.PRO_ACCURATE1, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_SING_PROJECTILE, AmmoItemTrait.CON_HEAVY_WEAR),
		VAPORIZER("ammo_fuel_vaporizer", GunEnergyFactory.getVaporizerConfig(), AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.PRO_FLAMES, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_ERASER, AmmoItemTrait.CON_ACCURACY2, AmmoItemTrait.CON_RANGE2, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_LING_FIRE),
		CHLORINE("ammo_fuel_gas", GunEnergyFactory.getGasConfig(), AmmoItemTrait.PRO_NO_GRAVITY, AmmoItemTrait.PRO_POISON_GAS, AmmoItemTrait.CON_NO_DAMAGE, AmmoItemTrait.CON_NO_FIRE);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private AmmoFlamethrower(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoMisc implements IAmmoItemEnum {
		//LUNA_SNIPER("ammo_lunar", Gun50BMGFactory.getLunaticSabotRound(), AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.PRO_ACCURATE2, AmmoItemTrait.NEU_HEAVY_METAL),
		DGK("ammo_dkg", GunDGKFactory.getDGKConfig());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private AmmoMisc(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoStinger implements IAmmoItemEnum {
		STOCK("ammo_stinger_rocket", GunRocketHomingFactory.getRocketStingerConfig()),
		HE("ammo_stinger_rocket_he", GunRocketHomingFactory.getRocketStingerHEConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_WEAR),
		INCENDIARY("ammo_stinger_rocket_incendiary", GunRocketHomingFactory.getRocketStingerIncendiaryConfig(), HbmCollection.IncendiaryType),
		NUCLEAR("ammo_stinger_rocket_nuclear", GunRocketHomingFactory.getRocketStingerNuclearConfig(), AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.CON_SUPER_WEAR),
		BONES("ammo_stinger_rocket_bones", GunRocketHomingFactory.getRocketStingerBonesConfig());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private AmmoStinger(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private AmmoStinger(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoRocket implements IAmmoItemEnum {
		STOCK("ammo_rocket", GunRocketFactory.getRocketConfig()),
		HE("ammo_rocket_he", GunRocketFactory.getRocketHEConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_WEAR),
		INCENDIARY("ammo_rocket_incendiary", GunRocketFactory.getRocketIncendiaryConfig(), HbmCollection.IncendiaryType),
		EMP("ammo_rocket_emp", GunRocketFactory.getRocketEMPConfig(), AmmoItemTrait.PRO_EMP, AmmoItemTrait.CON_RADIUS),
		SLEEK("ammo_rocket_sleek", GunRocketFactory.getRocketSleekConfig(), AmmoItemTrait.PRO_RADIUS_HIGH, AmmoItemTrait.PRO_NO_GRAVITY, AmmoItemTrait.CON_SPEED),
		SHRAPNEL("ammo_rocket_shrapnel", GunRocketFactory.getRocketShrapnelConfig(), AmmoItemTrait.PRO_SHRAPNEL),
		GLARE("ammo_rocket_glare", GunRocketFactory.getRocketGlareConfig(), AmmoItemTrait.PRO_SPEED, AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		NUCLEAR("ammo_rocket_nuclear", GunRocketFactory.getRocketNukeConfig(), AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.CON_SUPER_WEAR, AmmoItemTrait.CON_SPEED),
		CHLORINE("ammo_rocket_toxic", GunRocketFactory.getRocketChlorineConfig(), AmmoItemTrait.PRO_CHLORINE, AmmoItemTrait.CON_NO_EXPLODE1, AmmoItemTrait.CON_SPEED),
		RPC("ammo_rocket_rpc", GunRocketFactory.getRocketRPCConfig(),AmmoItemTrait.PRO_CHAINSAW, AmmoItemTrait.PRO_PENETRATION, AmmoItemTrait.PRO_NO_GRAVITY, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_NO_EXPLODE1, AmmoItemTrait.NEU_UHH ),
		PHOSPHORUS("ammo_rocket_phosphorus", GunRocketFactory.getRocketPhosphorusConfig(), HbmCollection.PhosphorusTypeSpecial),
		CANISTER("ammo_rocket_canister", GunRocketFactory.getRocketCanisterConfig()),
		DIGAMMA("ammo_rocket_digamma", GunRocketFactory.getRocketErrorConfig());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private AmmoRocket(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private AmmoRocket(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoGrenade implements IAmmoItemEnum {
		STOCK("ammo_grenade", GunGrenadeFactory.getGrenadeConfig()),
		HE("ammo_grenade_he", GunGrenadeFactory.getGrenadeHEConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_WEAR),
		INCENDIARY("ammo_grenade_incendiary", GunGrenadeFactory.getGrenadeIncendirayConfig(), AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		PHOSPHORUS("ammo_grenade_phosphorus", GunGrenadeFactory.getGrenadePhosphorusConfig(), AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR),
		CHLORINE("ammo_grenade_toxic", GunGrenadeFactory.getGrenadeChlorineConfig(), AmmoItemTrait.PRO_CHLORINE, AmmoItemTrait.CON_NO_EXPLODE1),
		SLEEK("ammo_grenade_sleek", GunGrenadeFactory.getGrenadeSleekConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.NEU_JOLT),
		CONCUSSION("ammo_grenade_concussion", GunGrenadeFactory.getGrenadeConcussionConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_NO_EXPLODE2),
		FINNED("ammo_grenade_finned", GunGrenadeFactory.getGrenadeFinnedConfig(), AmmoItemTrait.PRO_GRAVITY, AmmoItemTrait.CON_RADIUS),
		NUCLEAR("ammo_grenade_nuclear", GunGrenadeFactory.getGrenadeNuclearConfig(), AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.CON_HEAVY_WEAR),
		TRACER("ammo_grenade_tracer", GunGrenadeFactory.getGrenadeTracerConfig(), AmmoItemTrait.NEU_BLANK),
		KAMPF("ammo_grenade_kampf", GunGrenadeFactory.getGrenadeKampfConfig(), AmmoItemTrait.PRO_ROCKET_PROPELLED, AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_ACCURATE1, AmmoItemTrait.CON_WEAR);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private AmmoGrenade(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoFatman implements IAmmoItemEnum {
		STOCK("ammo_nuke", GunFatmanFactory.getNukeConfig()),
		LOW("ammo_nuke_low", GunFatmanFactory.getNukeLowConfig(), AmmoItemTrait.CON_RADIUS),
		HIGH("ammo_nuke_high", GunFatmanFactory.getNukeHighConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_FALLOUT),
		TOTS("ammo_nuke_tots", GunFatmanFactory.getNukeTotsConfig(), AmmoItemTrait.PRO_BOMB_COUNT, AmmoItemTrait.NEU_FUN, AmmoItemTrait.CON_ACCURACY2, AmmoItemTrait.CON_RADIUS, AmmoItemTrait.CON_NO_MIRV),
		SAFE("ammo_nuke_safe", GunFatmanFactory.getNukeSafeConfig(), AmmoItemTrait.CON_RADIUS, AmmoItemTrait.CON_NO_EXPLODE2),
		PUMPKIN("ammo_nuke_pumpkin", GunFatmanFactory.getNukePumpkinConfig(), AmmoItemTrait.CON_NN),
		MIRV("ammo_mirv", GunFatmanFactory.getMirvConfig()),
		MIRV_LOW("ammo_mirv_low", GunFatmanFactory.getMirvLowConfig(), AmmoItemTrait.CON_RADIUS),
		MIRV_HIGH("ammo_mirv_high", GunFatmanFactory.getMirvHighConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_FALLOUT),
		MIRV_SAFE("ammo_mirv_safe", GunFatmanFactory.getMirvSafeConfig(), AmmoItemTrait.CON_RADIUS, AmmoItemTrait.CON_NO_EXPLODE2),
		MIRV_SPECIAL("ammo_mirv_special", GunFatmanFactory.getMirvSpecialConfig()),
		BALEFIRE("gun_bf_ammo", GunFatmanFactory.getBalefireConfig()),
		BARREL("ammo_nuke_barrel", GunFatmanFactory.getNukeBarrelConfig());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private AmmoFatman(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum AmmoDart implements IAmmoItemEnum {
		GPS("ammo_dart", GunDartFactory.getGPSConfig()),
		NUCLEAR("ammo_dart_nuclear", GunDartFactory.getNukeConfig()),
		NERF("ammo_dart_nerf", GunDartFactory.getNERFConfig());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private AmmoDart(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo240Shell implements IAmmoItemEnum {
		STOCK("ammo_shell", GunCannonFactory.getShellConfig()),
		EXPLOSIVE("ammo_shell_explosive", GunCannonFactory.getShellExplosiveConfig()),
		APFSDS_T("ammo_shell_apfsds_t", GunCannonFactory.getShellAPConfig()),
		APFSDS_DU("ammo_shell_apfsds_du", GunCannonFactory.getShellDUConfig()),
		W9("ammo_shell_w9", GunCannonFactory.getShellW9Config());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo240Shell(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo9mm implements IAmmoItemEnum {
		STOCK("ammo_9mm", Gun9mmFactory.get9mmConfig()),
		AP("ammo_9mm_ap", Gun9mmFactory.get9mmAPConfig(), HbmCollection.APType),
		DU("ammo_9mm_du", Gun9mmFactory.get9mmDUConfig(), HbmCollection.DUType),
		CHLOROPHYTE("ammo_9mm_chlorophyte", Gun9mmFactory.get9mmConfig().getChlorophyte(), HbmCollection.ChlorophyteType),
		ROCKET("ammo_9mm_rocket", Gun9mmFactory.get9mmRocketConfig(), AmmoItemTrait.PRO_ROCKET, AmmoItemTrait.NEU_UHH);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo9mm(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo9mm(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo762NATO implements IAmmoItemEnum {
		STOCK("ammo_762", Gun762mmFactory.get762NATOConfig()),
		AP("ammo_762_ap", Gun762mmFactory.get762APConfig(), HbmCollection.APType),
		DU("ammo_762_du", Gun762mmFactory.get762DUConfig(), HbmCollection.DUType),
		TRACER("ammo_762_tracer", Gun762mmFactory.get762TracerConfig(), AmmoItemTrait.NEU_TRACER),
		PHOSPHORUS("ammo_762_phosphorus", Gun762mmFactory.get762WPConfig(), HbmCollection.PhosphorusType),
		BLANK("ammo_762_blank", Gun762mmFactory.get762BlankConfig(), AmmoItemTrait.NEU_BLANK);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo762NATO(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo762NATO(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo75Bolt implements IAmmoItemEnum {
		STOCK("ammo_75bolt", Gun75BoltFactory.get75BoltConfig()),
		INCENDIARY("ammo_75bolt_incendiary", Gun75BoltFactory.get75BoltIncConfig()),
		HE("ammo_75bolt_he", Gun75BoltFactory.get75BoltHEConfig());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo75Bolt(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo5mm implements IAmmoItemEnum {
		STOCK("ammo_5mm", Gun5mmFactory.get5mmConfig()),
		EXPLOSIVE("ammo_5mm_explosive", Gun5mmFactory.get5mmExplosiveConfig(), HbmCollection.ExplosiveType),
		DU("ammo_5mm_du", Gun5mmFactory.get5mmDUConfig(), HbmCollection.DUType),
		STAR("ammo_5mm_star", Gun5mmFactory.get5mmStarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_5mm_chlorophyte", Gun5mmFactory.get5mmConfig().getChlorophyte(), HbmCollection.ChlorophyteType);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo5mm(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo5mm(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo556mm implements IAmmoItemEnum {
		STOCK("ammo_556", Gun556mmFactory.get556Config()),
		GOLD("gun_pm_ammo", Gun556mmFactory.get556GoldConfig()),
		PHOSPHORUS("ammo_556_phosphorus", Gun556mmFactory.get556PhosphorusConfig(), HbmCollection.PhosphorusType),
		AP("ammo_556_ap", Gun556mmFactory.get556APConfig(), HbmCollection.APType),
		DU("ammo_556_du", Gun556mmFactory.get556DUConfig(), HbmCollection.DUType),
		STAR("ammo_556_star", Gun556mmFactory.get556StarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_556_chlorophyte", Gun556mmFactory.get556Config().getChlorophyte(), HbmCollection.ChlorophyteType),
		SLEEK("ammo_556_sleek", Gun556mmFactory.get556SleekConfig(), AmmoItemTrait.NEU_MASKMAN_METEORITE),
		TRACER("ammo_556_tracer", Gun556mmFactory.get556TracerConfig(), AmmoItemTrait.NEU_TRACER),
		FLECHETTE("ammo_556_flechette", Gun556mmFactory.get556FlechetteConfig(), HbmCollection.FlechetteType),
		FLECHETTE_INCENDIARY("ammo_556_flechette_incendiary", Gun556mmFactory.get556FlechetteIncendiaryConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_PENETRATION),
		FLECHETTE_PHOSPHORUS("ammo_556_flechette_phosphorus", Gun556mmFactory.get556FlechettePhosphorusConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.NEU_WARCRIME2, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_PENETRATION),
		FLECHETTE_DU("ammo_556_flechette_du", Gun556mmFactory.get556FlechetteDUConfig(), AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.PRO_PENETRATION, AmmoItemTrait.NEU_HEAVY_METAL, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_HEAVY_WEAR),
		FLECHETTE_CHLOROPHYTE("ammo_556_flechette_chlorophyte", Gun556mmFactory.get556FlechetteConfig().getChlorophyte(), HbmCollection.ChlorophyteType),
		FLECHETTE_SLEEK("ammo_556_flechette_sleek", Gun556mmFactory.get556FlechetteSleekConfig(), AmmoItemTrait.NEU_MASKMAN_METEORITE),
		K("ammo_556_k", Gun556mmFactory.get556KConfig(), AmmoItemTrait.NEU_BLANK);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo556mm(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo556mm(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo50BMG implements IAmmoItemEnum {
		STOCK("ammo_50bmg", Gun50BMGFactory.get50BMGConfig()),
		INCENDIARY("ammo_50bmg_incendiary", Gun50BMGFactory.get50BMGFireConfig(), HbmCollection.IncendiaryType),
		PHOSPHORUS("ammo_50bmg_phosphorus", Gun50BMGFactory.get50BMGPhosphorusConfig(), HbmCollection.PhosphorusType),
		EXPLOSIVE("ammo_50bmg_explosive", Gun50BMGFactory.get50BMGExplosiveConfig(), HbmCollection.ExplosiveType),
		AP("ammo_50bmg_ap", Gun50BMGFactory.get50BMGAPConfig(), HbmCollection.APType),
		DU("ammo_50bmg_du", Gun50BMGFactory.get50BMGDUConfig(), HbmCollection.DUType),
		STAR("ammo_50bmg_star", Gun50BMGFactory.get50BMGStarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_50bmg_chlorophyte", Gun50BMGFactory.get50BMGConfig().getChlorophyte(), HbmCollection.ChlorophyteType),
		SLEEK("ammo_50bmg_sleek", Gun50BMGFactory.get50BMGSleekConfig(), AmmoItemTrait.NEU_MASKMAN_METEORITE),
		FLECHETTE("ammo_50bmg_flechette", Gun50BMGFactory.get50BMGFlechetteConfig(), AmmoItemTrait.PRO_DAMAGE),
		FLECHETTE_AM("ammo_50bmg_flechette_am", Gun50BMGFactory.get50BMGFlechetteAMConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_UHH),
		FLECHETTE_PO("ammo_50bmg_flechette_po", Gun50BMGFactory.get50BMGFlechettePOConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_UHH);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo50BMG(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo50BMG(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo50AE implements IAmmoItemEnum {
		STOCK("ammo_50ae", Gun50AEFactory.get50AEConfig()),
		AP("ammo_50ae_ap", Gun50AEFactory.get50APConfig(), HbmCollection.APType),
		DU("ammo_50ae_du", Gun50AEFactory.get50DUConfig(), HbmCollection.DUType),
		STAR("ammo_50ae_star", Gun50AEFactory.get50StarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_50ae_chlorophyte", Gun50AEFactory.get50AEConfig().getChlorophyte(), HbmCollection.ChlorophyteType);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo50AE(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo50AE(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo4Gauge implements IAmmoItemEnum {
		STOCK("ammo_4gauge", Gun4GaugeFactory.get4GaugeConfig()),
		SLUG("ammo_4gauge_slug", Gun4GaugeFactory.get4GaugeSlugConfig(), AmmoItemTrait.PRO_ACCURATE2, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		FLECHETTE("ammo_4gauge_flechette", Gun4GaugeFactory.get4GaugeFlechetteConfig(), HbmCollection.FlechetteType),
		FLECHETTE_PHOSPHORUS("ammo_4gauge_flechette_phosphorus", Gun4GaugeFactory.get4GaugeFlechettePhosphorusConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.NEU_WARCRIME2, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR),
		EXPLOSIVE("ammo_4gauge_explosive", Gun4GaugeFactory.get4GaugeExplosiveConfig(), AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_40MM, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		MINING("ammo_4gauge_semtex", Gun4GaugeFactory.get4GaugeMiningConfig(), AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_MINING, AmmoItemTrait.CON_NO_EXPLODE3, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		BALEFIRE("ammo_4gauge_balefire", Gun4GaugeFactory.get4GaugeBalefireConfig(), AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_BALEFIRE, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		KAMPF("ammo_4gauge_kampf", Gun4GaugeFactory.getGrenadeKampfConfig(), AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_ROCKET_PROPELLED, AmmoItemTrait.PRO_ACCURATE1, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		CANISTER("ammo_4gauge_canister", Gun4GaugeFactory.getGrenadeCanisterConfig()),
		SLEEK("ammo_4gauge_sleek", Gun4GaugeFactory.get4GaugeSleekConfig(), AmmoItemTrait.NEU_MASKMAN_FLECHETTE),
		CLAW("ammo_4gauge_claw", Gun4GaugeFactory.get4GaugeClawConfig()),
		VAMPIRE("ammo_4gauge_vampire", Gun4GaugeFactory.get4GaugeVampireConfig()),
		VOID("ammo_4gauge_void", Gun4GaugeFactory.get4GaugeVoidConfig()),
		QUACK("ammo_4gauge_titan", Gun4GaugeFactory.get4GaugeQuackConfig(), AmmoItemTrait.PRO_MARAUDER, AmmoItemTrait.NEU_NO_CON);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo4Gauge(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo4Gauge(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo45ACP implements IAmmoItemEnum {
		STOCK("ammo_45", Gun45ACPFactory.get45AutoConfig()),
		AP("ammo_45_ap", Gun45ACPFactory.get45AutoAPConfig(), HbmCollection.APType),
		DU("ammo_45_du", Gun45ACPFactory.get45AutoDUConfig(), HbmCollection.DUType);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo45ACP(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo45ACP(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo44Magnum implements IAmmoItemEnum {
		STOCK("ammo_44", Gun44MagnumFactory.getNoPipConfig()),
		AP("ammo_44_ap", Gun44MagnumFactory.getNoPipAPConfig(), HbmCollection.APType),
		DU("ammo_44_du", Gun44MagnumFactory.getNoPipDUConfig(), HbmCollection.DUType),
		PHOSPHORUS("ammo_44_phosphorus", Gun44MagnumFactory.getPhosphorusConfig(), HbmCollection.PhosphorusType),
		STAR("ammo_44_star", Gun44MagnumFactory.getNoPipStarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE("ammo_44_chlorophyte", Gun44MagnumFactory.getNoPipConfig().getChlorophyte(), HbmCollection.ChlorophyteType),
		PIP("ammo_44_pip", Gun44MagnumFactory.getPipConfig(), AmmoItemTrait.NEU_BOXCAR, AmmoItemTrait.CON_DAMAGE),
		BJ("ammo_44_bj", Gun44MagnumFactory.getBJConfig(), AmmoItemTrait.NEU_BOAT, AmmoItemTrait.CON_DAMAGE),
		SILVER("ammo_44_silver", Gun44MagnumFactory.getSilverStormConfig(), AmmoItemTrait.NEU_BUILDING, AmmoItemTrait.CON_DAMAGE),
		ROCKET("ammo_44_rocket", Gun44MagnumFactory.getRocketConfig(), AmmoItemTrait.PRO_ROCKET, AmmoItemTrait.NEU_UHH);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo44Magnum(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo44Magnum(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo357Magnum implements IAmmoItemEnum {
		IRON("gun_revolver_iron_ammo", Gun357MagnumFactory.getRevIronConfig()),
		LEAD("gun_revolver_ammo", Gun357MagnumFactory.getRevLeadConfig()),
		NUCLEAR("gun_revolver_lead_ammo", Gun357MagnumFactory.getRevNuclearConfig()),
		GOLD("gun_revolver_gold_ammo", Gun357MagnumFactory.getRevGoldConfig()),
		DESH("ammo_357_desh", Gun357MagnumFactory.getRevDeshConfig(), AmmoItemTrait.PRO_FIT_357, AmmoItemTrait.PRO_DAMAGE_SLIGHT),
		SCHRABIDIUM("gun_revolver_schrabidium_ammo", Gun357MagnumFactory.getRevSchrabidiumConfig()),
		STEEL("gun_revolver_cursed_ammo", Gun357MagnumFactory.getRevCursedConfig()),
		NIGHTMARE1("gun_revolver_nightmare_ammo", Gun357MagnumFactory.getRevNightmare1Config()),
		NIGHTMARE2("gun_revolver_nightmare2_ammo", Gun357MagnumFactory.getRevNightmare2Config());
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo357Magnum(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo22LR implements IAmmoItemEnum {
		STOCK("ammo_22lr", Gun22LRFactory.get22LRConfig()),
		AP("ammo_22lr_ap", Gun22LRFactory.get22LRAPConfig(), HbmCollection.APType),
		CHLOROPHYTE("ammo_22lr_chlorophyte", Gun22LRFactory.get22LRConfig().getChlorophyte(), HbmCollection.ChlorophyteType);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo22LR(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo22LR(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo20Gauge implements IAmmoItemEnum {
		STOCK("ammo_20gauge", Gun20GaugeFactory.get20GaugeConfig()),
		SLUG("ammo_20gauge_slug", Gun20GaugeFactory.get20GaugeSlugConfig(), AmmoItemTrait.PRO_ACCURATE2, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		FLECHETTE("ammo_20gauge_flechette", Gun20GaugeFactory.get20GaugeFlechetteConfig(), HbmCollection.FlechetteType),
		INCENDIARY("ammo_20gauge_incendiary", Gun20GaugeFactory.get20GaugeFireConfig(), AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		SHRAPNEL("ammo_20gauge_shrapnel", Gun20GaugeFactory.get20GaugeShrapnelConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_MORE_BOUNCY, AmmoItemTrait.CON_WEAR),
		EXPLOSIVE("ammo_20gauge_explosive", Gun20GaugeFactory.get20GaugeExplosiveConfig(), HbmCollection.ExplosiveType),
		CAUSTIC("ammo_20gauge_caustic", Gun20GaugeFactory.get20GaugeCausticConfig(), AmmoItemTrait.PRO_TOXIC, AmmoItemTrait.PRO_CAUSTIC, AmmoItemTrait.NEU_NO_BOUNCE, AmmoItemTrait.CON_HEAVY_WEAR),
		SHOCK("ammo_20gauge_shock", Gun20GaugeFactory.get20GaugeShockConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_STUNNING, AmmoItemTrait.PRO_EMP, AmmoItemTrait.NEU_NO_BOUNCE, AmmoItemTrait.CON_HEAVY_WEAR),
		WITHER("ammo_20gauge_wither", Gun20GaugeFactory.get20GaugeWitherConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WITHERING),
		SLEEK("ammo_20gauge_sleek", Gun20GaugeFactory.get20GaugeSleekConfig(), AmmoItemTrait.NEU_MASKMAN_FLECHETTE);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo20Gauge(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.config = config;
			this.traits = safeAssign(traits);
			this.unloc = unloc;
		}

		private Ammo20Gauge(String unloc, BulletConfiguration config, Set<AmmoItemTrait> traits) {
			this.config = config;
			this.traits = traits;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public enum Ammo12Gauge implements IAmmoItemEnum {
		STOCK("ammo_12gauge", Gun12GaugeFactory.get12GaugeConfig()),
		INCENDIARY("ammo_12gauge_incendiary", Gun12GaugeFactory.get12GaugeFireConfig(), AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		SHRAPNEL("ammo_12gauge_shrapnel", Gun12GaugeFactory.get12GaugeShrapnelConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_MORE_BOUNCY, AmmoItemTrait.CON_WEAR),
		DU("ammo_12gauge_du", Gun12GaugeFactory.get12GaugeDUConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_PENETRATION, AmmoItemTrait.NEU_HEAVY_METAL, AmmoItemTrait.CON_HEAVY_WEAR),
		MARAUDER("ammo_12gauge_marauder", Gun12GaugeFactory.get12GaugeAMConfig(), AmmoItemTrait.PRO_MARAUDER, AmmoItemTrait.NEU_NO_CON),
		SLEEK("ammo_12gauge_sleek", Gun12GaugeFactory.get12GaugeSleekConfig(), AmmoItemTrait.NEU_MASKMAN_FLECHETTE);
		
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private final String unloc;

		private Ammo12Gauge(String unloc, BulletConfiguration config, AmmoItemTrait... traits) {
			this.traits = safeAssign(traits);
			this.config = config;
			this.unloc = unloc;
		}

		@Override
		public Set<AmmoItemTrait> getTraits() {
			return traits;
		}

		@Override
		public BulletConfiguration getConfig() {
			return config;
		}

		@Override
		public String getInternalName() {
			return unloc;
		}
	}

	public interface IAmmoItemEnum {
		public Set<AmmoItemTrait> getTraits();
		public BulletConfiguration getConfig();
		public String getInternalName();
	}

	static Set<AmmoItemTrait> safeAssign(AmmoItemTrait[] traits) {
		return traits == null ? ImmutableSet.of() : ImmutableSet.copyOf(traits);
	}
}
