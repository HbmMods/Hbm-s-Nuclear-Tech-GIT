package api.hbm.entity;

public interface IRadarDetectableNT {

	public static final int TIER0 =		0;
	public static final int TIER1 =		1;
	public static final int TIER2 =		2;
	public static final int TIER3 =		3;
	public static final int TIER4 =		4;
	public static final int TIER10 =	5;
	public static final int TIER10_15 =	6;
	public static final int TIER15 =	7;
	public static final int TIER15_20 =	8;
	public static final int TIER20 =	9;
	public static final int TIER_AB =	10;
	public static final int PLAYER =	11;
	public static final int ARTY =		12;
	/** Reserved type that shows a unique purple blip. Used for when nothing else applies. */
	public static final int SPECIAL =	13;

	/** Name use for radar display, uses I18n for lookup */
	public String getUnlocalizedName();
	/** The type of dot to show on the radar as well as the redstone level in tier mode */
	public int getBlipLevel();
	/** Whether the object can be seen by this type of radar */
	public boolean canBeSeenBy(Object radar);
	/** Whether the object is currently visible, as well as whether the radar's setting allow for picking this up */
	public boolean paramsApplicable(RadarScanParams params);
	/** Whether this radar entry should be counted for the redstone output */
	public boolean suppliesRedstone(RadarScanParams params);
	
	public static class RadarScanParams {
		public boolean scanMissiles = true;
		public boolean scanShells = true;
		public boolean scanPlayers = true;
		public boolean smartMode = true;
		
		public RadarScanParams(boolean m, boolean s, boolean p, boolean smart) {
			this.scanMissiles = m;
			this.scanShells = s;
			this.scanPlayers = p;
			this.smartMode = smart;
		}
	}
}