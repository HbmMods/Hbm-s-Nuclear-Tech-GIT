package com.hbm.entity.grenade;

import com.hbm.blocks.ModBlocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityWastePearl extends EntityGrenadeBase {

	public EntityWastePearl(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityWastePearl(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityWastePearl(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote) {
			this.setDead();

			int x = (int)Math.floor(posX);
			int y = (int)Math.floor(posY);
			int z = (int)Math.floor(posZ);
			
			for(int ix = x - 3; ix <= x + 3; ix++) {
				for(int iy = y - 3; iy <= y + 3; iy++) {
					for(int iz = z - 3; iz <= z + 3; iz++) {
						
						if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock(ix, iy, iz).isReplaceable(worldObj, ix, iy, iz) && ModBlocks.fallout.canPlaceBlockAt(worldObj, ix, iy, iz)) {
							worldObj.setBlock(ix, iy, iz, ModBlocks.fallout);
						} else if(worldObj.getBlock(ix, iy, iz) == Blocks.air) {
							
							if(rand.nextBoolean())
								worldObj.setBlock(ix, iy, iz, ModBlocks.gas_radon);
							else
								worldObj.setBlock(ix, iy, iz, ModBlocks.gas_radon_dense);
						}
					}
				}
			}
		}
	}
}
