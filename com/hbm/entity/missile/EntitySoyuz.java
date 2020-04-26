package com.hbm.entity.missile;

import java.util.List;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntitySoyuz extends Entity {

	double acceleration = 0.00D;
	public int mode;

	private ItemStack[] payload;

	public EntitySoyuz(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        this.setSize(5.0F, 50.0F);
        payload = new ItemStack[18];
	}
	
	@Override
	public void onUpdate() {
		
		if(motionY < 2.0D) {
			acceleration += 0.00025D;
			motionY += acceleration;
		}
		
		this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
		
		if(!worldObj.isRemote) {
			
			//if(this.ticksExisted < 20) {
			//	ExplosionLarge.spawnShock(worldObj, posX, posY, posZ, 13 + rand.nextInt(3), 4 + rand.nextGaussian() * 2);
			//}
		}
		
		if(worldObj.isRemote) {
			spawnExhaust(posX, posY, posZ);
			spawnExhaust(posX + 2.75, posY, posZ);
			spawnExhaust(posX - 2.75, posY, posZ);
			spawnExhaust(posX, posY, posZ + 2.75);
			spawnExhaust(posX, posY, posZ - 2.75);
		}
		
		if(this.posY > 600) {
			deployPayload();
		}
	}
	
	private void spawnExhaust(double x, double y, double z) {

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "exhaust");
		data.setString("mode", "soyuz");
		data.setInteger("count", 2);
		data.setDouble("width", worldObj.rand.nextDouble() * 0.35 - 0.7);
		data.setDouble("posX", x);
		data.setDouble("posY", y);
		data.setDouble("posZ", z);
		
		MainRegistry.proxy.effectNT(data);
	}
	
	private void deployPayload() {

		if(mode == 0 && payload != null) {
			
		}
		
		this.setDead();
	}

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(8, 0);
	}
	
	public void setSat(ItemStack stack) {
		this.payload[0] = stack;
	}
	
	public void setPayload(List<ItemStack> payload) {
		
		for(int i = 0; i < payload.size(); i++) {
			this.payload[i] = payload.get(i);
		}
	}
	
	public void setSkin(int i) {
		this.dataWatcher.updateObject(8, i);
	}
	
	public int getSkin() {
		return this.dataWatcher.getWatchableObjectInt(8);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {

		NBTTagList list = nbt.getTagList("items", 10);

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < payload.length) {
				payload[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < payload.length; i++) {
			if (payload[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				payload[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
}
