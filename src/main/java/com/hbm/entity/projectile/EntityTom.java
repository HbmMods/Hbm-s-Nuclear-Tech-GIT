package com.hbm.entity.projectile;

import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.entity.logic.EntityTomBlast;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityTom extends EntityThrowable implements IChunkLoader {
	private Ticket loaderTicket;
	public EntityTom(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
	}

	@Override
	public void onUpdate() {

		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);

		if(this.ticksExisted % 100 == 0) {
			worldObj.playSoundEffect(posX, posY, posZ, "hbm:alarm.chime", 10000, 1.0F);
		}

		motionY = -0.5;

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air) {
			if(!this.worldObj.isRemote) {
				EntityTomBlast tom = new EntityTomBlast(worldObj);
				tom.posX = posX;
				tom.posY = posY;
				tom.posZ = posZ;
				tom.destructionRange = 600;
				worldObj.spawnEntityInWorld(tom);

				EntityCloudTom cloud = new EntityCloudTom(worldObj, 500);
				cloud.setLocationAndAngles(posX, posY, posZ, 0, 0);
				worldObj.spawnEntityInWorld(cloud);
			}
			this.setDead();
		}
	}
	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
	}
	public void init(Ticket ticket) {
		if(!worldObj.isRemote) {

            if(ticket != null) {

                if(loaderTicket == null) {

                	loaderTicket = ticket;
                	loaderTicket.bindEntity(this);
                	loaderTicket.getModData();
                }

                ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
            }
        }
	}
	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}
}