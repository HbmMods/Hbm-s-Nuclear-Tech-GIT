package com.hbm.items.weapon.sedna.mods;

public abstract class WeaponModBase implements IWeaponMod {
	
	public String[] slots;
	public int priority = 0;

	public WeaponModBase(int id, String... slots) { this.slots = slots; WeaponModManager.idToMod.put(id, this); }
	public WeaponModBase setPriority(int priority) { this.priority = priority; return this; }

	@Override public int getModPriority() { return priority; }

	@Override public String[] getSlots() { return slots; }
	
	/**
	 * Java generics are cool and all but once you actually get to use them, they suck ass.
	 * This piece of shit only exists to prevent double cast, casting from int to <T> would require (T) (Integer) int, which makes me want to vomit.
	 * Using this method here implicitly casts the int (or whatever it is) to Object, and Object can be cast to <T>.
	 * @param The value that needs to be cast
	 * @param Any value with the type that should be cast to
	 * @return
	 */
	public <T> T fagSlop(Object arg, T castTo) { return (T) arg; } //TODO: rename this to something more tactful
}
