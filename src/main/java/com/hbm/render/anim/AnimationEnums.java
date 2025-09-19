package com.hbm.render.anim;

public class AnimationEnums {

	// A NOTE ON SHOTGUN STYLE RELOADS
	// Make sure the RELOAD adds shells, not just RELOAD_CYCLE, they all proc once for each loaded shell
	public static enum GunAnimation {
		RELOAD,			//either a full reload or start of a reload
		RELOAD_CYCLE,	//animation that plays for every individual round (for shotguns and similar single round loading weapons)
		RELOAD_END,		//animation for transitioning from our RELOAD_CYCLE to idle
		CYCLE,			//animation for every firing cycle
		CYCLE_EMPTY,	//animation for the final shot in the magazine
		CYCLE_DRY,		//animation for trying to fire, but no round is available
		ALT_CYCLE,		//animation for alt fire cycles
		SPINUP,			//animation for actionstart
		SPINDOWN,		//animation for actionend
		EQUIP,			//animation for drawing the weapon
		INSPECT,		//animation for inspecting the weapon
		JAMMED,			//animation for jammed weapons
	}

	public static enum ToolAnimation {
		SWING,
		EQUIP,
	}

}
