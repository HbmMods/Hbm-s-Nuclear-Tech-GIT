package com.hbm.entity.projectile;

import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.ParticleBurstPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRubble extends EntityThrowableNT {

    public EntityRubble(World world)
    {
        super(world);
    }

    @Override
	public void entityInit() {
        this.dataWatcher.addObject(16, (int)Integer.valueOf(0));
        this.dataWatcher.addObject(17, (int)Integer.valueOf(0));
    }

    public EntityRubble(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
	protected void onImpact(MovingObjectPosition mop)
    {
        if (mop.entityHit != null)
        {
            byte b0 = 15;

            mop.entityHit.attackEntityFrom(ModDamageSource.rubble, b0);
        }

        if(this.ticksExisted > 2) {
        	this.setDead();
        	
    		worldObj.playSoundAtEntity(this, "hbm:block.debris", 1.5F, 1.0F);
            //worldObj.playAuxSFX(2001, (int)posX, (int)posY, (int)posZ, this.dataWatcher.getWatchableObjectInt(16) + (this.dataWatcher.getWatchableObjectInt(17) << 12));
    		
    		if(!worldObj.isRemote)
    			PacketDispatcher.wrapper.sendToAllAround(new ParticleBurstPacket((int)Math.floor(posX), (int)posY, (int)Math.floor(posZ), this.dataWatcher.getWatchableObjectInt(16), this.dataWatcher.getWatchableObjectInt(17)), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 50));
        }
    }

    @Override
    protected float getAirDrag() {
        return 1F;
    }

    public void setMetaBasedOnBlock(Block b, int i) {

    	this.dataWatcher.updateObject(16, Block.getIdFromBlock(b));
    	this.dataWatcher.updateObject(17, i);
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(16, nbt.getInteger("block"));
		this.dataWatcher.updateObject(17, nbt.getInteger("meta"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("block", this.dataWatcher.getWatchableObjectInt(16));
		nbt.setInteger("meta", this.dataWatcher.getWatchableObjectInt(17));
	}
}
