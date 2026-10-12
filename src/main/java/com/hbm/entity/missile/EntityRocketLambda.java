package com.hbm.entity.missile;

import com.hbm.main.MainRegistry;
import com.hbm.util.Vec3NT;

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
			spawnExhaust(posX, posY + 1, posZ);
		}
	}
	
	private void spawnExhaust(double x, double y, double z) {

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "exhaust");
		data.setString("mode", "lambda");
		data.setInteger("count", 1);
		data.setDouble("width", 0);
		data.setDouble("posX", x);
		data.setDouble("posY", y);
		data.setDouble("posZ", z);
		
		MainRegistry.proxy.effectNT(data);
		
		if(this.ticksExisted % 10 == 0) {
	
			Vec3NT vec = new Vec3NT(1, 0, 0);
			vec.rotateAroundYDeg(45 * this.rand.nextInt(8));
			double j = 0.5;
			
			data.setDouble("posX", posX - vec.xCoord * j);
			data.setDouble("posY", posY - vec.yCoord * j);
			data.setDouble("posZ", posZ - vec.zCoord * j);
			data.setString("type", "missileContrail");
			data.setFloat("scale", 1F);
			data.setDouble("moX", vec.xCoord * 1);
			data.setDouble("moY", this.motionY - 0.5);
			data.setDouble("moZ", vec.zCoord * 1);
			data.setInteger("maxAge", 60 + rand.nextInt(20));
			MainRegistry.proxy.effectNT(data);
		}
	}
}
