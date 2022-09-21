package api.hbm.block;

import com.hbm.inventory.material.Mats.MaterialStack;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface ICrucibleAcceptor {

	/*
	 * Pouring: The metal leaves the channel/crucible and usually (but not always) falls down. The additional double coords give a more precise impact location.
	 * Also useful for entities like large crucibles since they are filled from the top.
	 */
	public boolean canAcceptPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);
	public MaterialStack canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);
	public void pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack);

	/*
	 * Flowing: The "safe" transfer of metal using a channel or other means, usually from block to block and usually horizontally (but not necessarily).
	 * May also be used for entities like minecarts that could be loaded from the side.
	 */
	public boolean canAcceptFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack);
	public MaterialStack canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack);
	public void flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack);
}
