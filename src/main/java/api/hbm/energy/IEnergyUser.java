package api.hbm.energy;

/**
 * For machines and things that have an energy buffer and are affected by EMPs
 * @author hbm
 */
public interface IEnergyUser extends IEnergyConnector {
	
	/**
	 * Not to be used for actual energy transfer, rather special external things like EMPs and sync packets
	 */
	public void setPower(long power);
}
