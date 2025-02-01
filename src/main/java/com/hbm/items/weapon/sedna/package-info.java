/**
 * 
 */
/**
 * @author hbm
 *
 */
package com.hbm.items.weapon.sedna;

/*

The MK2 unified gun system SEDNA

ItemGunBase - NBT, timer, keybind handling
	| GunConfig (1) - durability and sights
		| Receiver (n) - base damage, fire modes
			| Magazine (1) - NBT, reload management
				| BulletConfig (n) - ammo stats

Based on this system, alt fire that should logically use the same receiver actually use two different receivers, and
by extension two different mag fields. In this case, make sure to use the same mag instance (or an identical one)
on either receiver to ensure that both receivers access the same ammo pool and accept the same ammo types.

*/