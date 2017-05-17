package com.hbm.entity.effect;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.explosion.ExplosionNukeGeneric;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBlackHole extends Entity {
	
	Random rand = new Random();

	public EntityBlackHole(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityBlackHole(World world, float size) {
		super(world);
		this.dataWatcher.updateObject(16, size);
	}
	
	@Override
	public void onUpdate() {
		
		float size = this.dataWatcher.getWatchableObjectFloat(16);
		
		for(int k = 0; k < size * 5; k++) {
			double phi = rand.nextDouble() * (Math.PI * 2);
			double costheta = rand.nextDouble() * 2 - 1;
			double theta = Math.acos(costheta);
			double x = Math.sin( theta) * Math.cos( phi );
			double y = Math.sin( theta) * Math.sin( phi );
			double z = Math.cos( theta );
			
			Vec3 vec = Vec3.createVectorHelper(x, y, z);
			int length = (int)Math.ceil(size * 15);
			
			for(int i = 0; i < length; i ++) {
				int x0 = (int)(this.posX + (vec.xCoord * i));
				int y0 = (int)(this.posY + (vec.yCoord * i));
				int z0 = (int)(this.posZ + (vec.zCoord * i));
				
				if(!worldObj.isRemote) {
					if(worldObj.getBlock(x0, y0, z0) != Blocks.air) {
						EntityRubble rubble = new EntityRubble(worldObj);
						rubble.posX = x0 + 0.5F;
						rubble.posY = y0;
						rubble.posZ = z0 + 0.5F;
						rubble.setMetaBasedOnMat(worldObj.getBlock(x0, y0, z0).getMaterial());
						
						worldObj.spawnEntityInWorld(rubble);
					
						worldObj.setBlock(x0, y0, z0, Blocks.air);
						break;
					}
				}
			}
		}

		ExplosionNukeGeneric.succ(worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, (int)Math.ceil(size * 15));
		
		if(ExplosionNukeGeneric.dedify(worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, (int)Math.ceil(size * 2))) {
			this.setDead();
			int r = (int)Math.ceil(size);
			int r2 = r * r;
			int r22 = r2 / 2;
			for (int xx = -r; xx < r; xx++) {
				int X = xx + (int)this.posX;
				int XX = xx * xx;
				for (int yy = -r; yy < r; yy++) {
					int Y = yy + (int)this.posY;
					int YY = XX + yy * yy;
					for (int zz = -r; zz < r; zz++) {
						int Z = zz + (int)this.posZ;
						int ZZ = YY + zz * zz;
						if (ZZ < r22) {
							worldObj.setBlock(X, Y, Z, ModBlocks.gravel_obsidian);
						}
					}
				}
			}
		}
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, (float) 0.5F);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.dataWatcher.updateObject(16, nbt.getFloat("size"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat("size", this.dataWatcher.getWatchableObjectFloat(16));
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }

}
