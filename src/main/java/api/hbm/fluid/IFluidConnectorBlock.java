package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public interface IFluidConnectorBlock {

	public boolean canConnect(FluidType type, IBlockAccess world, int x, int y, int z, ForgeDirection dir);
}
