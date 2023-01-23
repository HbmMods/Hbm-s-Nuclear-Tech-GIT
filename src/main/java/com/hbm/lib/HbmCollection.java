package com.hbm.lib;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.hbm.items.weapon.ItemAmmo.AmmoItemTrait;

public class HbmCollection {

	public static final Set<AmmoItemTrait> APType = ImmutableSet.of(AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> FlechetteType = ImmutableSet.of(AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.NEU_LESS_BOUNCY, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> IncendiaryType = ImmutableSet.of(AmmoItemTrait.PRO_INCENDIARY, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> PhosphorusType = ImmutableSet.of(AmmoItemTrait.PRO_PHOSPHORUS, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR, AmmoItemTrait.CON_PENETRATION);
	public static final Set<AmmoItemTrait> PhosphorusTypeSpecial = ImmutableSet.of(AmmoItemTrait.PRO_PHOSPHORUS_SPLASH, AmmoItemTrait.NEU_WARCRIME1, AmmoItemTrait.CON_WEAR);
	public static final Set<AmmoItemTrait> ExplosiveType = ImmutableSet.of(AmmoItemTrait.PRO_EXPLOSIVE, AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.CON_HEAVY_WEAR);
	public static final Set<AmmoItemTrait> DUType = ImmutableSet.of(AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.NEU_HEAVY_METAL, AmmoItemTrait.CON_HEAVY_WEAR);
	public static final Set<AmmoItemTrait> StarmetalType = ImmutableSet.of(AmmoItemTrait.PRO_HEAVY_DAMAGE, AmmoItemTrait.NEU_STARMETAL, AmmoItemTrait.CON_HEAVY_WEAR);
	public static final Set<AmmoItemTrait> ChlorophyteType = ImmutableSet.of(AmmoItemTrait.PRO_DAMAGE, AmmoItemTrait.PRO_WEAR, AmmoItemTrait.NEU_CHLOROPHYTE, AmmoItemTrait.NEU_HOMING, AmmoItemTrait.CON_PENETRATION);
}
