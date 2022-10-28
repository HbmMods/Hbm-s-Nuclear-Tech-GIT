package api.hbm.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IToolable {
	
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool);
	
	public static enum ToolType {
		SCREWDRIVER,
		HAND_DRILL,
		DEFUSER,
		WRENCH
	}
}
