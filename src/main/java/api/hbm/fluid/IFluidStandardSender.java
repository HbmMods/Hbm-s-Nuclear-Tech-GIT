package api.hbm.fluid;

import com.hbm.inventory.fluid.tank.FluidTank;

import api.hbm.fluidmk2.IFluidStandardSenderMK2;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Deprecated
public interface IFluidStandardSender extends IFluidStandardSenderMK2 {

	public default void sendFluid(FluidTank tank, World world, int x, int y, int z, ForgeDirection dir) {
		tryProvide(tank, world, x, y, z, dir);
	}

	public default void sendFluidToAll(FluidTank tank, TileEntity tile) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			tryProvide(tank, tile.getWorldObj(), tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ, dir);
		}
	}
}
