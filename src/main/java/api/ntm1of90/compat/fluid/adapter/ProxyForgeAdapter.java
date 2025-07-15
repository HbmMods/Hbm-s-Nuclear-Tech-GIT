package api.ntm1of90.compat.fluid.adapter;

import com.hbm.tileentity.TileEntityProxyBase;

import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * An adapter class that makes proxy tile entities compatible with Forge's IFluidHandler interface.
 * This allows multiblock structures using proxy tile entities to interact with other mods' fluid systems.
 *
 * This is part of the official NTM API and can be used by other mods to create Forge-compatible
 * multiblock fluid containers.
 */
public class ProxyForgeAdapter extends TileEntityProxyBase implements IFluidHandler {

    static {
        // Initialize the fluid mapping registry
        FluidMappingRegistry.initialize();
    }

    private boolean isFluidHandler = false;

    public ProxyForgeAdapter() {
        super();
    }

    public ProxyForgeAdapter(boolean inventory, boolean power, boolean fluid) {
        super();
        this.isFluidHandler = fluid;
    }

    // IFluidHandler implementation

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (!isFluidHandler || resource == null || resource.amount <= 0) {
            return 0;
        }

        TileEntity te = getTE();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).fill(from, resource, doFill);
        }

        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (!isFluidHandler || resource == null || resource.amount <= 0) {
            return null;
        }

        TileEntity te = getTE();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).drain(from, resource, doDrain);
        }

        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (!isFluidHandler || maxDrain <= 0) {
            return null;
        }

        TileEntity te = getTE();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).drain(from, maxDrain, doDrain);
        }

        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (!isFluidHandler) {
            return false;
        }

        TileEntity te = getTE();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).canFill(from, fluid);
        }

        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (!isFluidHandler) {
            return false;
        }

        TileEntity te = getTE();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).canDrain(from, fluid);
        }

        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (!isFluidHandler) {
            return new FluidTankInfo[0];
        }

        TileEntity te = getTE();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).getTankInfo(from);
        }

        return new FluidTankInfo[0];
    }

    // Additional methods for compatibility with various mods

    public boolean canConnectFluid(ForgeDirection from) {
        return isFluidHandler;
    }

    public boolean isConnectable(ForgeDirection from) {
        return isFluidHandler;
    }

    public boolean canInterface(ForgeDirection from) {
        return isFluidHandler;
    }

    public boolean canInputFluid(ForgeDirection from) {
        return isFluidHandler;
    }

    public boolean canOutputFluid(ForgeDirection from) {
        return isFluidHandler;
    }

    public boolean canReceiveFrom(ForgeDirection from) {
        return isFluidHandler;
    }

    public boolean canSendTo(ForgeDirection from) {
        return isFluidHandler;
    }

    public boolean canAcceptFluid(ForgeDirection from) {
        return isFluidHandler;
    }

    public boolean canProvideFluid(ForgeDirection from) {
        return isFluidHandler;
    }

    public boolean isFluidHandler(ForgeDirection from) {
        return isFluidHandler;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.isFluidHandler = nbt.getBoolean("isFluidHandler");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("isFluidHandler", isFluidHandler);
    }
}
