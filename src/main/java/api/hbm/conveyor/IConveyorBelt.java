package api.hbm.conveyor;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IConveyorBelt {

	/** Returns true if the item should stay on the conveyor, false if the item should drop off */
	public boolean canItemStay(World world, int x, int y, int z, Vec3 itemPos);
	public Vec3 getTravelLocation(World world, int x, int y, int z, Vec3 itemPos, double speed);
	public Vec3 getClosestSnappingPosition(World world, int x, int y, int z, Vec3 itemPos);
}
