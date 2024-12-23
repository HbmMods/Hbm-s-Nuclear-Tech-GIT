package com.hbm.lib;

public class HbmCollection {
	
	/// FREQUENTLY USED TRANSLATION KEYS
	// GUN MANUFACTURERS
	public static enum EnumGunManufacturer {
		/**Armalite**/
		ARMALITE,
		/**Auto-Ordnance Corporation**/
		AUTO_ORDINANCE,
		/**BAE Systems plc**/
		BAE,
		/**Benelli Armi SpA**/
		BENELLI,
		/**Black Mesa Research Facility**/
		BLACK_MESA,
		/**Cerix Magnus**/
		CERIX,
		/**Colt's Manufacturing Company**/
		COLT,
		/**The Universal Union**/
		COMBINE,
		/**Cube 2: Sauerbraten**/
		CUBE,
		/**Deep Rock Galactic**/
		DRG,
		/**Enzinger Union**/
		ENZINGER,
		/**Equestria Missile Systems**/
		EQUESTRIA,
		/**Fisher Price**/
		F_PRICE,
		/**Fort Strong**/
		F_STRONG,
		/**FlimFlam Industries**/
		FLIMFLAM,
		/**Gloria GmbH**/
		GLORIA,
		/**Heckler & Koch**/
		H_AND_K,
		/**Harrington & Richardson**/
		H_AND_R,
		/**Hasbro**/
		HASBRO,
		/**Ironshod Firearms**/
		IF,
		/**Israel Military Industries**/
		IMI,
		/**IMI / Big MT**/
		IMI_BIGMT,
		/**Langford Research Laboratories**/
		LANGFORD,
		/**Magnum Research / Israel Military Industries**/
		MAGNUM_R_IMI,
		/**Open Mann Co.**/
		MANN,
		/**Hiram Maxim**/
		MAXIM,
		/**Metro Gunsmiths**/
		METRO,
		/**MWT Prototype Labs**/
		MWT,
		/**Naval Air Weapons Station**/
		NAWS,
		/**Erfurter Maschinenfabrik Geipel**/
		ERFURT,
		/**No manufacturer, just puts "-" **/
		NONE,
		/**OxfordEM Technologies**/
		OXFORD,
		/**Lunar Defense Corp**/
		LUNA,
		/**Raytheon Missile Systems**/
		RAYTHEON,
		/**Remingotn Arms**/
		REMINGTON,
		/**Rockwell International Corporation**/
		ROCKWELL,
		/**Rockwell International Corporation?**/
		ROCKWELL_U,
		/**Ryan Industries**/
		RYAN,
		/**Saab Bofors Dynamics**/
		SAAB,
		/**Saco Defense / US Ordnance**/
		SACO,
		/**Tulsky Oruzheiny Zavod**/
		TULSKY,
		/**Union Aerospace Corporation**/
		UAC,
		/**Unknown manufacturer, puts "???"**/
		UNKNOWN,
		/**WestTek**/
		WESTTEK,
		/**Wilhelm-Gustloff-Werke**/
		WGW,
		/**Winchester Repeating Arms Company**/
		WINCHESTER,
		/**Winchester Repeating Arms Company / Big MT**/
		WINCHESTER_BIGMT;
	
		public String getKey() {
			return "gun.make." + toString();
		}
	}
	
	// GUN DETAILS
	public static final String ammo = "desc.item.gun.ammo";
	public static final String ammoMag = "desc.item.gun.ammoMag";
	public static final String ammoBelt = "desc.item.gun.ammoBelt";
	public static final String ammoEnergy = "desc.item.gun.ammoEnergy";
	public static final String altAmmoEnergy = "desc.item.gun.ammoEnergyAlt";
	public static final String ammoType = "desc.item.gun.ammoType";
	public static final String altAmmoType = "desc.item.gun.ammoTypeAlt";
	public static final String gunName = "desc.item.gun.name";
	public static final String gunMaker = "desc.item.gun.manufacturer";
	public static final String gunDamage = "desc.item.gun.damage";
	public static final String gunPellets = "desc.item.gun.pellets";
	// MISC
	public static final String capacity = "desc.block.barrel.capacity";
	public static final String durability = "desc.item.durability";
	public static final String meltPoint = "desc.misc.meltPoint";
	public static final String lctrl = "desc.misc.lctrl";
	public static final String lshift = "desc.misc.lshift";
}
