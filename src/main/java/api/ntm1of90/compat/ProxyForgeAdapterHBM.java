package api.ntm1of90.compat;

import api.hbm.fluidmk2.IFluidConnectorMK2;
import com.hbm.inventory.fluid.FluidType;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * An adapter class that makes proxy tile entities compatible with both Forge's IFluidHandler interface
 * and HBM's IFluidConnectorMK2 interface.
 * This allows multiblock structures using proxy tile entities to interact with both HBM's fluid pipes
 * and other mods' fluid systems.
 */
public class ProxyForgeAdapterHBM extends ProxyForgeAdapter implements IFluidConnectorMK2 {

    public ProxyForgeAdapterHBM() {
        super();
    }

    public ProxyForgeAdapterHBM(boolean inventory, boolean power, boolean fluid) {
        super(inventory, power, fluid);
    }

    /**
     * Implement the IFluidConnectorMK2 interface to allow HBM's fluid pipes to connect
     */
    @Override
    public boolean canConnect(FluidType type, ForgeDirection dir) {
        TileEntity te = getTE();
        if (te instanceof IFluidConnectorMK2) {
            return ((IFluidConnectorMK2) te).canConnect(type, dir);
        }
        return true; // Default to allowing connections
    }
}
