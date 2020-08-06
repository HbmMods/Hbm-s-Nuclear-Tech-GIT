package api.hbm.energy;

public interface IRadarDetectable {

	public static enum RadarTargetType {
		MISSILE_TIER0,		//tier 0 missile (micro missile assemblies)
		MISSILE_TIER1,		//tier 1 missiles
		MISSILE_TIER2,		//tier 2 missiles
		MISSILE_TIER3,		//tier 3 missiles
		MISSILE_TIER4,		//tier 4 missiles (nuclear, thermo, doomsday)
		MISSILE_10,			//size 10 custom missiles
		MISSILE_10_15,		//size 10/15 custom missiles
		MISSILE_15,			//size 15 custom missiles
		MISSILE_15_20,		//size 15/20 custom missiles
		MISSILE_20,			//size 20 custom missiles
		MISSILE_AB,			//anti ballistic missile
		PLAYER,				//airborne players
	}
	
	public RadarTargetType getTargetType();
}
