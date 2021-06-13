package api.hbm.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IScrewable {
	
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ);
}
