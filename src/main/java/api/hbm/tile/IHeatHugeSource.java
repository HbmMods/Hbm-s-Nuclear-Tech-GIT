package api.hbm.tile;

public interface IHeatHugeSource {

	//public int getHeatStored();

	public long getHeatStored();	
	/**
	 * Removes heat from the system. Implementation has to include the checks preventing the heat from going into the negative.
	 * @param heat
	 */
	public void useUpHeat(long heat);
}
