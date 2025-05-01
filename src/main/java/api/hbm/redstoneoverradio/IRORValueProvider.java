package api.hbm.redstoneoverradio;

public interface IRORValueProvider extends IRORInfo {

	/** Grabs the specified value from this ROR component, operations should not cause any changes with the component itself */
	public String provideRORValue(String name);
}
