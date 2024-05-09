package api.hbm.block;

import com.hbm.entity.item.EntityTNTPrimedBase;

import net.minecraft.world.World;

public interface IFuckingExplode {

	// Anything that can be detonated by another explosion should implement this and spawn an EntityTNTPrimedBase when hit by an explosion
	// This prevents chained explosions causing a stack overflow
	// Note that the block can still safely immediately explode, as long as the source isn't another explosion

	public void explodeEntity(World world, double x, double y, double z, EntityTNTPrimedBase entity);

}
