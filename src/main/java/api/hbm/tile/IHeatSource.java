package api.hbm.tile;

public interface IHeatSource {

	public int getHeatStored();
	
	/**
	 * Removes heat from the system. Implementation has to include the checks preventing the heat from going into the negative.
	 * @param heat
	 */
	public void useUpHeat(int heat);
}
