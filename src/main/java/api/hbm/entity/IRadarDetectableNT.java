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

	public String getUnlocalizedName();
	public int getBlipLevel();
	public boolean canBeSeenBy(Object radar);
	public boolean paramsApplicable(RadarScanParams params);
	
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
