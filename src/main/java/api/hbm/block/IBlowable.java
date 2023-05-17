package api.hbm.block;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IBlowable { //sloppy toppy
	
	/** Called server-side when a fan blows on an IBlowable in range every tick. */
	public void applyFan(World world, int x, int y, int z, ForgeDirection dir, int dist);
}
