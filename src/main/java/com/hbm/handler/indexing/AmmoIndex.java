package com.hbm.handler.indexing;

import java.util.HashMap;
import java.util.Set;

import com.google.common.collect.Sets;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.item.Item;

public class AmmoIndex {
	
	private static final HashMap<ComparableStack, Set<AmmoTrait>> ammo = new HashMap();

	public static void registerAmmo(Item ammo, AmmoTrait... traits) {
		registerAmmo(new ComparableStack(ammo), traits);
	}
	
	public static void registerAmmo(ComparableStack ammo, AmmoTrait... traits) {
		AmmoIndex.ammo.put(ammo, Sets.newHashSet(traits));
	}
	
	public static enum AmmoTrait {
		//calibers
		CALIBER_357,
		CALIBER_44,
		CALIBER_22LR,
		CALIBER_9MM,
		CALIBER_556MM,
		CALIBER_50BMG,
		CALIBER_50AE,
		CALIBER_4GA,
		CALIBER_12GA,
		CALIBER_20GA,
		CALIBER_240MM,
		
		//types
		BULLET,
		FLECHETTE,
		PELLET,
		SLUG,
		SHELL,			//tank or arty shells
		GRENADE,		//from grenade launchers
		ROCKET,
		CATAPULT,		//large payloads from the M42
		DART,
		
		//projectile traits
		EXPLOSIVE,
		INCENDIARY,
		POISONOUS,
		NUCLEAR,
		
		//meta
		NO_CONTAINER,	//not obtainable via the ammo container
		NO_HANDHELD,	//only for turrets
		RARE,
		VERY_RARE
	}
}
