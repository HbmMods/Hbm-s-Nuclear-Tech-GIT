package com.hbm.entity.missile;

import com.hbm.main.MainRegistry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRocketLambda extends EntityRocketBase {

	public EntityRocketLambda(World world) {
		super(world, 1);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(worldObj.isRemote) {
			spawnExhaust(posX, posY, posZ);
		}
	}
	
	private void spawnExhaust(double x, double y, double z) {

		// TODO
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "exhaust");
		data.setString("mode", "soyuz");
		data.setInteger("count", 1);
		data.setDouble("width", worldObj.rand.nextDouble() * 0.25 - 0.5);
		data.setDouble("posX", x);
		data.setDouble("posY", y);
		data.setDouble("posZ", z);
		
		MainRegistry.proxy.effectNT(data);
	}
}
