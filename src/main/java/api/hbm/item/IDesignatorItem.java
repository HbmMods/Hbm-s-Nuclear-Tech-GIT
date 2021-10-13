package api.hbm.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IDesignatorItem {

	/**
	 * Whether the target is valid
	 * @param world for things like restricting dimensions or getting entities
	 * @param stack to check NBT and metadata
	 * @param x position of the launch pad
	 * @param y position of the launch pad
	 * @param z position of the launch pad
	 * @return
	 */
	public boolean isReady(World world, ItemStack stack, int x, int y, int z);
	
	/**
	 * The target position if the designator is ready
	 * @param world
	 * @param stack
	 * @param x
	 * @param y
	 * @param z
	 * @return the target
	 */
	public Vec3 getCoords(World world, ItemStack stack, int x, int y, int z);
}
