package api.hbm.entity;

public interface IDigammaImmune
{
	public boolean fullyAscended();
	
	public default boolean isRadiationImmune()
	{
		return this instanceof IRadiationImmune;
	}
}
