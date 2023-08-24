package com.hbm.items.weapon;

/**
 * Probably not the best idea, but it'll do.
 * Order shouldn't particularly matter since it should only be used in runtime, but being organized is nice.
 * Originally made for associating guns with magazines, so ones that don't use that system aren't here, but you can add them anyway if needed for something else.
 * @author UFFR
 *
 */
public enum EnumGun
{
	/// Revolvers ///
	REVOLVER,
	REVOLVER_SATURNITE,
	REVOLVER_GOLD,
	REVOLVER_CURSED,
	REVOLVER_SCHRABIDIUM,
	REVOLVER_NIGHTMARE1,
	REVOLVER_NIGHTMARE2,
	REVOLVER_PIP,
	REVOLVER_NOPIP,
	REVOLVER_BLACKJACK,
	REVOLVER_SILVER,
	REVOLVER_RED,
	REVOLVER_BIO,
	
	/// Shotguns ///
	SG_M4,
	
	/// Pistols ///
	PISTOL_DEAGLE,
	PISTOL_UAC,
	
	/// Sub-machine guns ///
	SMG_MP40,
	SMG_THOMPSON,
	SMG_UZI,
	SMG_UZI_SILENCED,
	SMG_UZI_SATURNITE,
	SMG_UZI_SATURNITE_SILENCED,
	SMG_LLR,
	SMG_UAC,
	
	/// Rifles ///
	RIFLE_FLECHETTE,
	RIFLE_AR15,
	RIFLE_MLR,
	RIFLE_UAC_DMR,
	RIFLE_UAC_HDMR,
	RIFLE_UAC_CARBINE,
	
	/// Machine guns ///
	MG_CALAMITY,
	MG_M2,
	MG_M60,
	MG_MINIGUN,
	MG_MINIGUN_AVENGER,
	MG_MINIGUN_LACUNAE,
	MG_UAC_LMG,
	
	/// Other ///
	MISC_BOLTER,
	MISC_BOLTER_DIGAMMA;
}
