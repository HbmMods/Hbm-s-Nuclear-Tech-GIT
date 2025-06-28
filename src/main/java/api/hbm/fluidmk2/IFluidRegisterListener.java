package api.hbm.fluidmk2;

public interface IFluidRegisterListener {

	/**
	 * Called when the fluid registry initializes all fluids. Use CompatFluidRegistry to create new instances of FluidType, which are automatically registered.
	 */
	public void onFluidsLoad();
}
