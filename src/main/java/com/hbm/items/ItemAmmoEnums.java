package com.hbm.items;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.guncfg.*;
import com.hbm.items.ItemEnums.IItemEnum;
import com.hbm.items.weapon.ItemAmmo.AmmoItemTrait;
import com.hbm.lib.HbmCollection;

import net.minecraft.item.EnumRarity;

public class ItemAmmoEnums
{
	
	public enum AmmoHandGrenade
	{
		GENERIC(4),
		DYNAMITE(3),
		SMOKE(4),
		STRONG(5),
		FRAG(4),
		FIRE(4),
		SHRAPNEL(4),
		CLUSTER(5),
		FLARE(0),
		ELECTRIC(5),
		POISON(4),
		GAS(4),
		PULSE(4),
		PLASMA(5),
		TAU(5),
		SCHRABIDIUM(7),
		LEMON(4),
		GASCAN(-1),
		PINEAPPLE(5),
		KYIV(-1),
		ASCHRAB(-1),
		NUKE(-1),
		NUCLEAR(7),
		ZOMG(7),
		BLACK_HOLE(7),
		CLOUD(-1),
		PINK_CLOUD(-1),
		LUNATIC(5),
		SMART(-1),
		MIRV(1),
		BREACH(-1),
		WASTE_PEARL(-1),
		STUNNING(-1),
		IF_GENERIC(4),
		IF_HE(5),
		IF_BOUNCY(4),
		IF_STICKY(4),
		IF_IMPACT(-1),
		IF_INCENDIARY(4),
		IF_TOXIC(4),
		IF_CONCUSSION(4),
		IF_BRIMSTONE(5),
		IF_MYSTERY(5),
		IF_SPARK(7),
		IF_HOPWIRE(7),
		IF_NULL(7);
		public final int fuse;
		private AmmoHandGrenade(int fuse)
		{
			this.fuse = fuse;
		}
		
		public int getFuse()
		{
			return fuse;
		}
	}

	public enum AmmoLunaticSniper implements IAmmoItemEnum
	{
		SABOT(Gun50BMGFactory.getLunaticSabotRound()),
		INCENDIARY(Gun50BMGFactory.getLunaticIncendiaryRound()),
		EXPLOSIVE(Gun50BMGFactory.getLunaticSabotRound());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private AmmoLunaticSniper(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}
		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
		
	}
	
	public enum AmmoFireExt implements IAmmoItemEnum
	{
		WATER(GunEnergyFactory.getFextConfig()),
		FOAM(GunEnergyFactory.getFextFoamConfig()),
		SAND(GunEnergyFactory.getFextSandConfig());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private AmmoFireExt(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum AmmoFlamethrower implements IAmmoItemEnum
	{
		DIESEL(GunEnergyFactory.getFlameConfig()),
		NAPALM(GunEnergyFactory.getNapalmConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.CON_HEAVY_WEAR),
		PHOSPHORUS(GunEnergyFactory.getPhosphorusConfig(), AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.PRO_ACCURATE1, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_SING_PROJECTILE, AmmoItemTrait.CON_HEAVY_WEAR),
		VAPORIZER(GunEnergyFactory.getVaporizerConfig(), AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.PRO_FLAMES, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_ERASER, AmmoItemTrait.CON_ACCURACY2, AmmoItemTrait.CON_RANGE2, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_LING_FIRE),
		CHLORINE(GunEnergyFactory.getGasConfig(), AmmoItemTrait.PRO_NO_GRAVITY, AmmoItemTrait.PRO_POISON_GAS, AmmoItemTrait.CON_NO_DAMAGE, AmmoItemTrait.CON_NO_FIRE);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private AmmoFlamethrower(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}
	
	public enum AmmoMisc implements IAmmoItemEnum
	{
		LUNA_SNIPER(Gun50BMGFactory.getLunaticSabotRound(), AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.PRO_ACCURATE2, AmmoItemTrait.NEU_HEAVY_METAL),
		DGK(GunDGKFactory.getDGKConfig());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private AmmoMisc(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum AmmoStinger implements IAmmoItemEnum
	{
		STOCK(GunRocketHomingFactory.getRocketStingerConfig()),
		HE(GunRocketHomingFactory.getRocketStingerHEConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_WEAR),
		INCENDIARY(GunRocketHomingFactory.getRocketStingerIncendiaryConfig(), HbmCollection.IncendiaryType),
		NUCLEAR(GunRocketHomingFactory.getRocketStingerNuclearConfig(), AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.CON_SUPER_WEAR),
		BONES(GunRocketHomingFactory.getRocketStingerBonesConfig());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private AmmoStinger(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private AmmoStinger(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum AmmoRocket implements IAmmoItemEnum
	{
		STOCK(GunRocketFactory.getRocketConfig()),
		HE(GunRocketFactory.getRocketHEConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_WEAR),
		INCENDIARY(GunRocketFactory.getRocketIncendiaryConfig(), HbmCollection.IncendiaryType),
		EMP(GunRocketFactory.getRocketEMPConfig(), AmmoItemTrait.PRO_EMP, AmmoItemTrait.CON_RADIUS),
		SLEEK(GunRocketFactory.getRocketSleekConfig(), AmmoItemTrait.PRO_RADIUS_HIGH, AmmoItemTrait.PRO_NO_GRAVITY, AmmoItemTrait.CON_SPEED),
		SHRAPNEL(GunRocketFactory.getRocketShrapnelConfig(), AmmoItemTrait.PRO_SHRAPNEL),
		GLARE(GunRocketFactory.getRocketGlareConfig(), AmmoItemTrait.PRO_SPEED, AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		NUCLEAR(GunRocketFactory.getRocketNukeConfig(), AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.CON_SUPER_WEAR, AmmoItemTrait.CON_SPEED),
		CHLORINE(GunRocketFactory.getRocketChlorineConfig(), AmmoItemTrait.PRO_CHLORINE, AmmoItemTrait.CON_NO_EXPLODE1, AmmoItemTrait.CON_SPEED),
		RPC(GunRocketFactory.getRocketRPCConfig(),AmmoItemTrait.PRO_CHAINSAW, AmmoItemTrait.PRO_PENETRATION, AmmoItemTrait.PRO_NO_GRAVITY, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_NO_EXPLODE1, AmmoItemTrait.NEU_UHH ),
		PHOSPHORUS(GunRocketFactory.getRocketPhosphorusConfig(), HbmCollection.PhosphorusTypeSpecial),
		CANISTER(GunRocketFactory.getRocketCanisterConfig()),
		DIGAMMA(GunRocketFactory.getRocketErrorConfig());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private AmmoRocket(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private AmmoRocket(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum AmmoGrenade implements IAmmoItemEnum
	{
		STOCK(GunGrenadeFactory.getGrenadeConfig()),
		HE(GunGrenadeFactory.getGrenadeHEConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_WEAR),
		INCENDIARY(GunGrenadeFactory.getGrenadeIncendirayConfig(), AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		PHOSPHORUS(GunGrenadeFactory.getGrenadePhosphorusConfig(), AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR),
		SMOKE(GunGrenadeFactory.getGrenadeSmokeConfig(), AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.CON_NO_EXPLODE1, AmmoItemTrait.CON_NO_EXPLODE3),
		CHLORINE(GunGrenadeFactory.getGrenadeChlorineConfig(), AmmoItemTrait.PRO_CHLORINE, AmmoItemTrait.CON_NO_EXPLODE1),
		SLEEK(GunGrenadeFactory.getGrenadeSleekConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.NEU_JOLT),
		CONCUSSION(GunGrenadeFactory.getGrenadeConcussionConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.CON_NO_EXPLODE2),
		FINNED(GunGrenadeFactory.getGrenadeFinnedConfig(), AmmoItemTrait.PRO_GRAVITY, AmmoItemTrait.CON_RADIUS),
		NUCLEAR(GunGrenadeFactory.getGrenadeNuclearConfig(), AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.CON_HEAVY_WEAR),
		LUNATIC(GunGrenadeFactory.getGrenadeLunaticConfig(), AmmoItemTrait.PRO_LUNATIC, AmmoItemTrait.PRO_RANGE, AmmoItemTrait.CON_NO_EXPLODE2, AmmoItemTrait.CON_SUPER_WEAR),
		TRACER(GunGrenadeFactory.getGrenadeTracerConfig(), AmmoItemTrait.NEU_BLANK),
		KAMPF(GunGrenadeFactory.getGrenadeKampfConfig(), AmmoItemTrait.PRO_ROCKET_PROPELLED, AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_ACCURATE1, AmmoItemTrait.CON_WEAR);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private AmmoGrenade(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum AmmoFatman implements IAmmoItemEnum
	{
		STOCK(GunFatmanFactory.getNukeConfig()),
		LOW(GunFatmanFactory.getNukeLowConfig(), AmmoItemTrait.CON_RADIUS),
		HIGH(GunFatmanFactory.getNukeHighConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_FALLOUT),
		TOTS(GunFatmanFactory.getNukeTotsConfig(), AmmoItemTrait.PRO_BOMB_COUNT, AmmoItemTrait.NEU_FUN, AmmoItemTrait.CON_ACCURACY2, AmmoItemTrait.CON_RADIUS, AmmoItemTrait.CON_NO_MIRV),
		SAFE(GunFatmanFactory.getNukeSafeConfig(), AmmoItemTrait.CON_RADIUS, AmmoItemTrait.CON_NO_EXPLODE2),
		PUMPKIN(GunFatmanFactory.getNukePumpkinConfig(), AmmoItemTrait.CON_NN),
		MIRV(GunFatmanFactory.getMirvConfig()),
		MIRV_LOW(GunFatmanFactory.getMirvLowConfig(), AmmoItemTrait.CON_RADIUS),
		MIRV_HIGH(GunFatmanFactory.getMirvHighConfig(), AmmoItemTrait.PRO_RADIUS, AmmoItemTrait.PRO_FALLOUT),
		MIRV_SAFE(GunFatmanFactory.getMirvSafeConfig(), AmmoItemTrait.CON_RADIUS, AmmoItemTrait.CON_NO_EXPLODE2),
		MIRV_SPECIAL(GunFatmanFactory.getMirvSpecialConfig()),
		BALEFIRE(GunFatmanFactory.getBalefireConfig()),
		BARREL(GunFatmanFactory.getNukeBarrelConfig());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private AmmoFatman(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum AmmoDart implements IAmmoItemEnum
	{
		GPS(GunDartFactory.getGPSConfig()),
		NUCLEAR(GunDartFactory.getNukeConfig()),
		NERF(GunDartFactory.getNERFConfig());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private AmmoDart(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo240Shell implements IAmmoItemEnum, IItemEnum
	{
		STOCK(GunCannonFactory.getShellConfig()),
		EXPLOSIVE(GunCannonFactory.getShellExplosiveConfig()),
		APFSDS_T(GunCannonFactory.getShellAPConfig()),
		APFSDS_DU(GunCannonFactory.getShellDUConfig()),
		W9(GunCannonFactory.getShellW9Config()),
		W9_TRUE(GunCannonFactory.getShellW9FullConfig(), AmmoItemTrait.PRO_NUCLEAR, AmmoItemTrait.PRO_HEAVY_DAMAGE);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo240Shell(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
		
		@Override
		public EnumRarity getRarity()
		{
			return this == W9_TRUE ? EnumRarity.uncommon : EnumRarity.common;
		}
	}

	public enum Ammo9mm implements IAmmoItemEnum
	{
		STOCK(Gun9mmFactory.get9mmConfig()),
		AP(Gun9mmFactory.get9mmAPConfig(), HbmCollection.APType),
		DU(Gun9mmFactory.get9mmDUConfig(), HbmCollection.DUType),
		CHLOROPHYTE(Gun9mmFactory.get9mmConfig().getChlorophyte(), HbmCollection.ChlorophyteType),
		ROCKET(Gun9mmFactory.get9mmRocketConfig(), AmmoItemTrait.PRO_ROCKET, AmmoItemTrait.NEU_UHH);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo9mm(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo9mm(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo762NATO implements IAmmoItemEnum
	{
		STOCK(Gun762mmFactory.get762NATOConfig()),
		AP(Gun762mmFactory.get762APConfig(), HbmCollection.APType),
		DU(Gun762mmFactory.get762DUConfig(), HbmCollection.DUType),
		TRACER(Gun762mmFactory.get762TracerConfig(), AmmoItemTrait.NEU_TRACER),
		BLANK(Gun762mmFactory.get762BlankConfig(), AmmoItemTrait.NEU_BLANK);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo762NATO(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo762NATO(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo75Bolt implements IAmmoItemEnum
	{
		STOCK(Gun75BoltFactory.get75BoltConfig()),
		INCENDIARY(Gun75BoltFactory.get75BoltIncConfig()),
		HE(Gun75BoltFactory.get75BoltHEConfig());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo75Bolt(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo5mm implements IAmmoItemEnum
	{
		STOCK(Gun5mmFactory.get5mmConfig()),
		EXPLOSIVE(Gun5mmFactory.get5mmExplosiveConfig(), HbmCollection.ExplosiveType),
		DU(Gun5mmFactory.get5mmDUConfig(), HbmCollection.DUType),
		STAR(Gun5mmFactory.get5mmStarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE(Gun5mmFactory.get5mmConfig().getChlorophyte(), HbmCollection.ChlorophyteType);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo5mm(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo5mm(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo556mm implements IAmmoItemEnum
	{
		STOCK(Gun556mmFactory.get556Config()),
		GOLD(Gun556mmFactory.get556GoldConfig()),
		PHOSPHORUS(Gun556mmFactory.get556PhosphorusConfig(), HbmCollection.PhosphorusType),
		AP(Gun556mmFactory.get556APConfig(), HbmCollection.APType),
		DU(Gun556mmFactory.get556DUConfig(), HbmCollection.DUType),
		STAR(Gun556mmFactory.get556StarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE(Gun556mmFactory.get556Config().getChlorophyte(), HbmCollection.ChlorophyteType),
		SLEEK(Gun556mmFactory.get556SleekConfig(), AmmoItemTrait.NEU_MASKMAN_METEORITE),
		TRACER(Gun556mmFactory.get556TracerConfig(), AmmoItemTrait.NEU_TRACER),
		FLECHETTE(Gun556mmFactory.get556FlechetteConfig(), HbmCollection.FlechetteType),
		FLECHETTE_INCENDIARY(Gun556mmFactory.get556FlechetteIncendiaryConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_PENETRATION),
		FLECHETTE_PHOSPHORUS(Gun556mmFactory.get556FlechettePhosphorusConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.NEU_WARCRIME2, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_PENETRATION),
		FLECHETTE_DU(Gun556mmFactory.get556FlechetteDUConfig(), AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.PRO_PENETRATION, AmmoItemTrait.NEU_HEAVY_METAL, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_HEAVY_WEAR),
		FLECHETTE_CHLOROPHYTE(Gun556mmFactory.get556FlechetteConfig().getChlorophyte(), HbmCollection.ChlorophyteType),
		FLECHETTE_SLEEK(Gun556mmFactory.get556FlechetteSleekConfig(), AmmoItemTrait.NEU_MASKMAN_METEORITE),
		K(Gun556mmFactory.get556KConfig(), AmmoItemTrait.NEU_BLANK);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo556mm(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo556mm(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo50BMG implements IAmmoItemEnum
	{
		STOCK(Gun50BMGFactory.get50BMGConfig()),
		INCENDIARY(Gun50BMGFactory.get50BMGFireConfig(), HbmCollection.IncendiaryType),
		PHOSPHORUS(Gun50BMGFactory.get50BMGPhosphorusConfig(), HbmCollection.PhosphorusType),
		EXPLOSIVE(Gun50BMGFactory.get50BMGExplosiveConfig(), HbmCollection.ExplosiveType),
		AP(Gun50BMGFactory.get50BMGAPConfig(), HbmCollection.APType),
		DU(Gun50BMGFactory.get50BMGDUConfig(), HbmCollection.DUType),
		STAR(Gun50BMGFactory.get50BMGStarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE(Gun50BMGFactory.get50BMGConfig().getChlorophyte(), HbmCollection.ChlorophyteType),
		SLEEK(Gun50BMGFactory.get50BMGSleekConfig(), AmmoItemTrait.NEU_MASKMAN_METEORITE),
		FLECHETTE(Gun50BMGFactory.get50BMGFlechetteConfig()),
		FLECHETTE_AM(Gun50BMGFactory.get50BMGFlechetteAMConfig()),
		FLECHETTE_PO(Gun50BMGFactory.get50BMGFlechettePOConfig());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo50BMG(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo50BMG(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo50AE implements IAmmoItemEnum
	{
		STOCK(Gun50AEFactory.get50AEConfig()),
		AP(Gun50AEFactory.get50APConfig(), HbmCollection.APType),
		DU(Gun50AEFactory.get50DUConfig(), HbmCollection.DUType),
		STAR(Gun50AEFactory.get50StarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE(Gun50AEFactory.get50AEConfig().getChlorophyte(), HbmCollection.ChlorophyteType);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo50AE(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo50AE(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo4Gauge implements IAmmoItemEnum
	{
		STOCK(Gun4GaugeFactory.get4GaugeConfig()),
		SLUG(Gun4GaugeFactory.get4GaugeSlugConfig(), AmmoItemTrait.PRO_ACCURATE2, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		FLECHETTE(Gun4GaugeFactory.get4GaugeFlechetteConfig(), HbmCollection.FlechetteType),
		FLECHETTE_PHOSPHORUS(Gun4GaugeFactory.get4GaugeFlechettePhosphorusConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.NEU_WARCRIME2, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR),
		EXPLOSIVE(Gun4GaugeFactory.get4GaugeExplosiveConfig(), AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_40MM, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		MINING(Gun4GaugeFactory.get4GaugeMiningConfig(), AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_MINING, AmmoItemTrait.CON_NO_EXPLODE3, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		BALEFIRE(Gun4GaugeFactory.get4GaugeBalefireConfig(), AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_BALEFIRE, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_HEAVY_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		KAMPF(Gun4GaugeFactory.getGrenadeKampfConfig(), AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_ROCKET_PROPELLED, AmmoItemTrait.PRO_ACCURATE1, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		CANISTER(Gun4GaugeFactory.getGrenadeCanisterConfig()),
		SLEEK(Gun4GaugeFactory.get4GaugeSleekConfig(), AmmoItemTrait.NEU_MASKMAN_FLECHETTE),
		CLAW(Gun4GaugeFactory.get4GaugeClawConfig()),
		VAMPIRE(Gun4GaugeFactory.get4GaugeVampireConfig()),
		VOID(Gun4GaugeFactory.get4GaugeVoidConfig()),
		QUACK(Gun4GaugeFactory.get4GaugeQuackConfig(), AmmoItemTrait.PRO_MARAUDER, AmmoItemTrait.NEU_NO_CON);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo4Gauge(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo4Gauge(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo45ACP implements IAmmoItemEnum
	{
		STOCK(Gun45ACPFactory.get45AutoConfig()),
		AP(Gun45ACPFactory.get45AutoAPConfig(), HbmCollection.APType),
		DU(Gun45ACPFactory.get45AutoDUConfig(), HbmCollection.DUType);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo45ACP(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo45ACP(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo44Magnum implements IAmmoItemEnum
	{
		STOCK(Gun44MagnumFactory.getNoPipConfig()),
		AP(Gun44MagnumFactory.getNoPipAPConfig(), HbmCollection.APType),
		DU(Gun44MagnumFactory.getNoPipDUConfig(), HbmCollection.DUType),
		PHOSPHORUS(Gun44MagnumFactory.getPhosphorusConfig(), HbmCollection.PhosphorusType),
		STAR(Gun44MagnumFactory.getNoPipStarConfig(), HbmCollection.StarmetalType),
		CHLOROPHYTE(Gun44MagnumFactory.getNoPipConfig().getChlorophyte(), HbmCollection.ChlorophyteType),
		PIP(Gun44MagnumFactory.getPipConfig(), AmmoItemTrait.NEU_BOXCAR, AmmoItemTrait.CON_DAMAGE),
		BJ(Gun44MagnumFactory.getBJConfig(), AmmoItemTrait.NEU_BOAT, AmmoItemTrait.CON_DAMAGE),
		SILVER(Gun44MagnumFactory.getSilverStormConfig(), AmmoItemTrait.NEU_BUILDING, AmmoItemTrait.CON_DAMAGE),
		ROCKET(Gun44MagnumFactory.getRocketConfig(), AmmoItemTrait.PRO_ROCKET, AmmoItemTrait.NEU_UHH);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo44Magnum(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo44Magnum(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}
		
		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo357Magnum implements IAmmoItemEnum
	{
		IRON(Gun357MagnumFactory.getRevIronConfig()),
		LEAD(Gun357MagnumFactory.getRevLeadConfig()),
		NUCLEAR(Gun357MagnumFactory.getRevNuclearConfig()),
		GOLD(Gun357MagnumFactory.getRevGoldConfig()),
		DESH(Gun357MagnumFactory.getRevDeshConfig(), AmmoItemTrait.PRO_FIT_357, AmmoItemTrait.PRO_DAMAGE_SLIGHT),
		SCHRABIDIUM(Gun357MagnumFactory.getRevSchrabidiumConfig()),
		STEEL(Gun357MagnumFactory.getRevCursedConfig()),
		NIGHTMARE1(Gun357MagnumFactory.getRevNightmare1Config()),
		NIGHTMARE2(Gun357MagnumFactory.getRevNightmare2Config());
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo357Magnum(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo22LR implements IAmmoItemEnum
	{
		STOCK(Gun22LRFactory.get22LRConfig()),
		AP(Gun22LRFactory.get22LRAPConfig(), HbmCollection.APType),
		CHLOROPHYTE(Gun22LRFactory.get22LRConfig().getChlorophyte(), HbmCollection.ChlorophyteType);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo22LR(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo22LR(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}
	
	public enum Ammo20Gauge implements IAmmoItemEnum
	{
		STOCK(Gun20GaugeFactory.get20GaugeConfig()),
		SLUG(Gun20GaugeFactory.get20GaugeSlugConfig(), AmmoItemTrait.PRO_ACCURATE2, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WEAR, AmmoItemTrait.CON_SING_PROJECTILE),
		FLECHETTE(Gun20GaugeFactory.get20GaugeFlechetteConfig(), HbmCollection.FlechetteType),
		INCENDIARY(Gun20GaugeFactory.get20GaugeFireConfig(), AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		SHRAPNEL(Gun20GaugeFactory.get20GaugeShrapnelConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_MORE_BOUNCY, AmmoItemTrait.CON_WEAR),
		EXPLOSIVE(Gun20GaugeFactory.get20GaugeExplosiveConfig(), HbmCollection.ExplosiveType),
		CAUSTIC(Gun20GaugeFactory.get20GaugeCausticConfig(), AmmoItemTrait.PRO_TOXIC, AmmoItemTrait.PRO_CAUSTIC, AmmoItemTrait.NEU_NO_BOUNCE, AmmoItemTrait.CON_HEAVY_WEAR),
		SHOCK(Gun20GaugeFactory.get20GaugeShockConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_STUNNING, AmmoItemTrait.PRO_EMP, AmmoItemTrait.NEU_NO_BOUNCE, AmmoItemTrait.CON_HEAVY_WEAR),
		WITHER(Gun20GaugeFactory.get20GaugeWitherConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WITHERING),
		SLEEK(Gun20GaugeFactory.get20GaugeSleekConfig(), AmmoItemTrait.NEU_MASKMAN_FLECHETTE);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo20Gauge(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.config = config;
			this.traits = safeAssign(traits);
		}
		private Ammo20Gauge(BulletConfiguration config, Set<AmmoItemTrait> traits)
		{
			this.config = config;
			this.traits = traits;
		}
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public enum Ammo12Gauge implements IAmmoItemEnum
	{
		STOCK(Gun12GaugeFactory.get12GaugeConfig()),
		INCENDIARY(Gun12GaugeFactory.get12GaugeFireConfig(), AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR),
		SHRAPNEL(Gun12GaugeFactory.get12GaugeShrapnelConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_MORE_BOUNCY, AmmoItemTrait.CON_WEAR),
		DU(Gun12GaugeFactory.get12GaugeDUConfig(), AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_PENETRATION, AmmoItemTrait.NEU_HEAVY_METAL, AmmoItemTrait.CON_HEAVY_WEAR),
		MARAUDER(Gun12GaugeFactory.get12GaugeAMConfig(), AmmoItemTrait.PRO_MARAUDER, AmmoItemTrait.NEU_NO_CON),
		SLEEK(Gun12GaugeFactory.get12GaugeSleekConfig(), AmmoItemTrait.NEU_MASKMAN_FLECHETTE);
		private final Set<AmmoItemTrait> traits;
		private final BulletConfiguration config;
		private Ammo12Gauge(BulletConfiguration config, AmmoItemTrait...traits)
		{
			this.traits = safeAssign(traits);
			this.config = config;
		}
		
		@Override
		public Set<AmmoItemTrait> getTraits()
		{
			return traits;
		}

		@Override
		public BulletConfiguration getConfig()
		{
			return config;
		}
	}

	public interface IAmmoItemEnum
	{
		public Set<AmmoItemTrait> getTraits();
		public BulletConfiguration getConfig();
	}

	static Set<AmmoItemTrait> safeAssign(AmmoItemTrait[] traits)
	{
		return traits == null ? ImmutableSet.of() : ImmutableSet.copyOf(traits);
	}
	
	static Set<AmmoItemTrait> safeAssign(Collection<AmmoItemTrait> traits)
	{
		return traits == null || traits.size() == 0 ? ImmutableSet.of() : ImmutableSet.copyOf(traits);
	}
}
