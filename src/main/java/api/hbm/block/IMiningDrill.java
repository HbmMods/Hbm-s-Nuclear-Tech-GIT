package api.hbm.block;

public interface IMiningDrill {
	
	/**
	 * What era the drill belongs to, usually for IDrillInteraction to adjust outputs
	 * @return
	 */
	public DrillType getDrillTier();
	
	/**
	 * An arbitrary "rating" of the drill. Hand powered pre-industrial drills would be <10, the auto mining drill is 50 and the laser miner is 100.
	 * @return
	 */
	public int getDrillRating();

	public static enum DrillType {
		PRIMITIVE,
		INDUSTRIAL,
		HITECH
	}
}
