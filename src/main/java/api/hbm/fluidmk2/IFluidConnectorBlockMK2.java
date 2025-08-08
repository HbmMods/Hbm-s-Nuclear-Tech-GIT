package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public interface IFluidConnectorBlockMK2 {

	/** dir is the face that is connected to, the direction going outwards from the block */
	public boolean canConnect(FluidType type, IBlockAccess world, int x, int y, int z, ForgeDirection dir);
}
