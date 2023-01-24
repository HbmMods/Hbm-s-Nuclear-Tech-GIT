package com.hbm.entity.missile;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.satellites.Satellite;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityCarrier extends EntityThrowable {

	double acceleration = 0.00D;
	
	private ItemStack payload;

	public EntityCarrier(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        this.setSize(3.0F, 26.0F);
	}
	
	@Override
	public void onUpdate() {
		
		//this.setDead();
		
		if(motionY < 3.0D) {
			acceleration += 0.0005D;
			motionY += acceleration;
		}
		
		this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
		
		if(!worldObj.isRemote) {
			for(int i = 0; i < 10; i++) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "gasfire");
				data.setDouble("mY", -0.2D);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX + rand.nextGaussian() * 0.75D, posY - 0.25D, posZ + rand.nextGaussian() * 0.75D), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 200));
			}
			
			if(this.dataWatcher.getWatchableObjectInt(8) == 1)
				for(int i = 0; i < 2; i++) {
					NBTTagCompound d1 = new NBTTagCompound();
					d1.setString("type", "gasfire");
					d1.setDouble("mY", -0.2D);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(d1, posX + rand.nextGaussian() * 0.75D + 2.5, posY - 0.25D, posZ + rand.nextGaussian() * 0.75D), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 200));
					
					NBTTagCompound d2 = new NBTTagCompound();
					d2.setString("type", "gasfire");
					d2.setDouble("mY", -0.2D);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(d2, posX + rand.nextGaussian() * 0.75D - 2.5, posY - 0.25D, posZ + rand.nextGaussian() * 0.75D), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 200));
					
					NBTTagCompound d3 = new NBTTagCompound();
					d3.setString("type", "gasfire");
					d3.setDouble("mY", -0.2D);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(d3, posX + rand.nextGaussian() * 0.75D, posY - 0.25D, posZ + rand.nextGaussian() * 0.75D + 2.5), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 200));
					
					NBTTagCompound d4 = new NBTTagCompound();
					d4.setString("type", "gasfire");
					d4.setDouble("mY", -0.2D);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(d4, posX + rand.nextGaussian() * 0.75D, posY - 0.25D, posZ + rand.nextGaussian() * 0.75D - 2.5), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 200));
				}
			
			
			if(this.ticksExisted < 20) {
				ExplosionLarge.spawnShock(worldObj, posX, posY, posZ, 13 + rand.nextInt(3), 4 + rand.nextGaussian() * 2);
			}
		}
		
		if(this.posY > 300 && this.dataWatcher.getWatchableObjectInt(8) == 1)
			this.disengageBoosters();
			//this.setDead();
		
		if(this.posY > 600) {
			deployPayload();
		}
	}
	
	private void deployPayload() {

		if(payload != null) {
			
			if(payload.getItem() == ModItems.flame_pony) {
				ExplosionLarge.spawnTracers(worldObj, posX, posY, posZ, 25);
				for(Object p : worldObj.playerEntities)
					((EntityPlayer)p).triggerAchievement(MainRegistry.achSpace);
			}
			
			if(payload.getItem() == ModItems.sat_foeq) {
				for(Object p : worldObj.playerEntities)
					((EntityPlayer)p).triggerAchievement(MainRegistry.achFOEQ);
			}
			
			if(payload.getItem() instanceof ISatChip) {
				
			    int freq = ISatChip.getFreqS(payload);
		    	
		    	Satellite.orbit(worldObj, Satellite.getIDFromItem(payload.getItem()), freq, posX, posY, posZ);
			}
		}
		
		this.setDead();
	}

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(8, 1);
	}
	
	public void setPayload(ItemStack stack) {
		this.payload = stack.copy();
	}
	
	private void disengageBoosters() {
		this.dataWatcher.updateObject(8, 0);
		
		if(!worldObj.isRemote) {
			EntityBooster boost1 = new EntityBooster(worldObj);
			boost1.posX = posX + 1.5D;
			boost1.posY = posY;
			boost1.posZ = posZ;
			boost1.motionX = 0.45D + rand.nextDouble() * 0.2D;
			boost1.motionY = motionY;
			boost1.motionZ = rand.nextGaussian() * 0.1D;
			worldObj.spawnEntityInWorld(boost1);
			
			EntityBooster boost2 = new EntityBooster(worldObj);
			boost2.posX = posX - 1.5D;
			boost2.posY = posY;
			boost2.posZ = posZ;
			boost2.motionX = -0.45D - rand.nextDouble() * 0.2D;
			boost2.motionY = motionY;
			boost2.motionZ = rand.nextGaussian() * 0.1D;
			worldObj.spawnEntityInWorld(boost2);
			
			EntityBooster boost3 = new EntityBooster(worldObj);
			boost3.posX = posX;
			boost3.posY = posY;
			boost3.posZ = posZ + 1.5D;
			boost3.motionZ = 0.45D + rand.nextDouble() * 0.2D;
			boost3.motionY = motionY;
			boost3.motionX = rand.nextGaussian() * 0.1D;
			worldObj.spawnEntityInWorld(boost3);
			
			EntityBooster boost4 = new EntityBooster(worldObj);
			boost4.posX = posX;
			boost4.posY = posY;
			boost4.posZ = posZ - 1.5D;
			boost4.motionZ = -0.45D - rand.nextDouble() * 0.2D;
			boost4.motionY = motionY;
			boost4.motionX = rand.nextGaussian() * 0.1D;
			worldObj.spawnEntityInWorld(boost4);
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }
}
