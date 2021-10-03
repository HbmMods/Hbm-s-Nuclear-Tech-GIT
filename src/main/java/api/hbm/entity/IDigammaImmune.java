package api.hbm.entity;

public interface IDigammaImmune
{
	/** If fully immune to DRX, use this instead of setting {@link#getResistance()} to 0 for easier checks and to prevent math funnies **/
	public boolean fullyAscended();
	/** Natural resistance to DRX, between 0 being 100% and 1.0 0%, set {@link#fullyAscended()} to true instead of this to 0 to prevent some potential math funnies and to be easier to check **/
	public float getResistance();
	/**
	 * Scale DRX in by an entity's natural resistance
	 * @param drxIn The incoming DRX
	 * @return The DRX reduced according to {@link#getResistance()} or 0 if {@link #fullyAscended()} is {@code
	 * true}.
	 */
	public default float getDRXScaled(float drxIn)
	{
		if (fullyAscended())
			return 0F;
		
		return drxIn * getResistance();
	}
	
	public default boolean isRadiationImmune()
	{
		return this instanceof IRadiationImmune;
	}
}
