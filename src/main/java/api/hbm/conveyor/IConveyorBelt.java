package api.hbm.conveyor;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IConveyorBelt {

	public Vec3 getTravelLocation(World world, int x, int y, int z, Vec3 itemPos, double speed);
	public Vec3 getClosestSnappingPosition(World world, int x, int y, int z, Vec3 itemPos);
}
