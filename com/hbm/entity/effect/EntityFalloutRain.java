package com.hbm.entity.effect;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.NukeEnvironmentalEffect;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFalloutRain extends Entity {
	
	public int revProgress;
	public int radProgress;

	public EntityFalloutRain(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

	public EntityFalloutRain(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		this.setSize(4, 20);
		this.isImmuneToFire = true;
	}

    @Override
	public void onUpdate() {

        if(!worldObj.isRemote) {
        	
        	for(int i = 0; i < 256; i++) {
        		
	        	Vec3 vec = Vec3.createVectorHelper(radProgress, 0, 0);
	        	double circum = radProgress * 2 * Math.PI * 2;
	        	double part = 360D / circum;
	        	
	        	vec.rotateAroundY((float) (part * revProgress));
	        	
	        	int x = (int) (posX + vec.xCoord);
	        	int z = (int) (posZ + vec.zCoord);
	        	
	        	//int y = worldObj.getHeightValue(x, z) - 1;
	        	
	        	//if(worldObj.getBlock(x, y, z) == Blocks.grass)
	        	//	worldObj.setBlock(x, y, z, ModBlocks.waste_earth);
	        	
	        	stomp(x, z);
	        	
	        	revProgress++;
	        	
	        	if(revProgress > circum) {
	        		revProgress = 0;
	        		radProgress++;
	        	}
	        	
	        	if(radProgress > getScale())
	        		this.setDead();
        	}
        }
    }
    
    private void stomp(int x, int z) {
    	
    	for(int y = 255; y >= 0; y--) {
    		
    		Block b =  worldObj.getBlock(x, y, z);
    		int meta = worldObj.getBlockMetadata(x, y, z);
    		
    		if(b.getMaterial() == Material.air)
    			continue;
    		
			if (b == Blocks.leaves || b == Blocks.leaves2) {
				worldObj.setBlock(x, y, z, Blocks.air);
			}
    		
    		else if(b == Blocks.grass) {
    			worldObj.setBlock(x, y, z, ModBlocks.waste_earth);
    			return;
    			
    		} else if(b == Blocks.mycelium) {
    			worldObj.setBlock(x, y, z, ModBlocks.waste_mycelium);
    			return;
    		} else if(b == Blocks.sand) {
    			
    			if(rand.nextInt(20) == 0)
    				worldObj.setBlock(x, y, z, meta == 0 ? ModBlocks.waste_trinitite : ModBlocks.waste_trinitite_red);
    			return;
    		}

			else if (b == Blocks.clay) {
				worldObj.setBlock(x, y, z, Blocks.hardened_clay);
    			return;
			}

			else if (b == Blocks.mossy_cobblestone) {
				worldObj.setBlock(x, y, z, Blocks.coal_ore);
    			return;
			}

			else if (b == Blocks.coal_ore) {
				int ra = rand.nextInt(10);
				if (ra < 3) {
					worldObj.setBlock(x, y, z, Blocks.diamond_ore);
				}
				if (ra == 3) {
					worldObj.setBlock(x, y, z, Blocks.emerald_ore);
				}
    			return;
			}

			else if (b == Blocks.log || b == Blocks.log2) {
				worldObj.setBlock(x, y, z, ModBlocks.waste_log);
			}

			else if (b == Blocks.brown_mushroom_block || b == Blocks.red_mushroom_block) {
				if (meta == 10) {
					worldObj.setBlock(x, y, z, ModBlocks.waste_log);
				} else {
					worldObj.setBlock(x, y, z, Blocks.air,0,2);
				}
			}
			
			else if (b.getMaterial() == Material.wood && b.isOpaqueCube() && b != ModBlocks.waste_log) {
				worldObj.setBlock(x, y, z, ModBlocks.waste_planks);
			}

			else if (b == ModBlocks.ore_uranium) {
				if (rand.nextInt(30) == 0)
					worldObj.setBlock(x, y, z, ModBlocks.ore_schrabidium);
    			return;
			}

			else if (b == ModBlocks.ore_nether_uranium) {
				if (rand.nextInt(30) == 0)
					worldObj.setBlock(x, y, z, ModBlocks.ore_nether_schrabidium);
    			return;
    			
    		//this piece stops the "stomp" from reaching below ground
			} else if(b.isNormalCube()) {

				return;
			}
    	}
    }

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, Integer.valueOf(0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		setScale(p_70037_1_.getInteger("scale"));
		revProgress = p_70037_1_.getInteger("revProgress");
		radProgress = p_70037_1_.getInteger("radProgress");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setInteger("scale", getScale());
		p_70014_1_.setInteger("revProgress", revProgress);
		p_70014_1_.setInteger("radProgress", radProgress);
		
	}

	public void setScale(int i) {

		this.dataWatcher.updateObject(16, Integer.valueOf(i));
	}

	public int getScale() {

		return this.dataWatcher.getWatchableObjectInt(16);
	}
}
