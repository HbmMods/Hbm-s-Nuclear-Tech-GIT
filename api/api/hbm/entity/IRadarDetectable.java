package api.hbm.entity;

public interface IRadarDetectable {

	public static enum RadarTargetType {
		MISSILE_TIER0("Micro Missile"),				//tier 0 missile (micro missile assemblies)
		MISSILE_TIER1("Tier 1 Missile"),			//tier 1 missiles
		MISSILE_TIER2("Tier 2 Missile"),			//tier 2 missiles
		MISSILE_TIER3("Tier 3 Missile"),			//tier 3 missiles
		MISSILE_TIER4("Tier 4 Missile"),			//tier 4 missiles (nuclear, thermo, doomsday)
		MISSILE_10("Size 10 Custom Missile"),		//size 10 custom missiles
		MISSILE_10_15("Size 10/15 Custom Missile"),	//size 10/15 custom missiles
		MISSILE_15("Size 15 Custom Missile"),		//size 15 custom missiles
		MISSILE_15_20("Size 15/20 Custom Missile"),	//size 15/20 custom missiles
		MISSILE_20("Size 20 Custom Missile"),		//size 20 custom missiles
		MISSILE_AB("Anti-Ballistic Missile"),		//anti ballistic missile
		PLAYER("Player");							//airborne players
		
		public String name;
		
		private RadarTargetType(String name) {
			this.name = name;
		}
	}
	
	public RadarTargetType getTargetType();
}
