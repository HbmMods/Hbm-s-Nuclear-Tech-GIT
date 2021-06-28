package com.hbm.entity.effect;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.VersatileConfig;
import com.hbm.saveddata.AuxSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
        	
        	for(int i = 0; i < BombConfig.fSpeed; i++) {
        		
	        	Vec3 vec = Vec3.createVectorHelper(radProgress * 0.5, 0, 0);
	        	double circum = radProgress * 2 * Math.PI * 2;
	        	
	        	///
	        	if(circum == 0)
	        		circum = 1;
	        	///
	        	
	        	double part = 360D / circum;
	        	
	        	vec.rotateAroundY((float) (part * revProgress));
	        	
	        	int x = (int) (posX + vec.xCoord);
	        	int z = (int) (posZ + vec.zCoord);
	        	
	        	//int y = worldObj.getHeightValue(x, z) - 1;
	        	
	        	//if(worldObj.getBlock(x, y, z) == Blocks.grass)
	        	//	worldObj.setBlock(x, y, z, ModBlocks.waste_earth);
	        	
	        	double dist = radProgress * 100 / getScale() * 0.5;
	        	
	        	stomp(x, z, dist);
	        	
	        	revProgress++;
	        	
	        	if(revProgress > circum) {
	        		revProgress = 0;
	        		radProgress++;
	        	}
	        	
	        	if(radProgress > getScale() * 2D) {
	        		this.setDead();
	        	}
        	}
        	
        	if(this.isDead) {
        		if(RadiationConfig.rain > 0 && getScale() > 150) {
        			worldObj.getWorldInfo().setRaining(true);
    				worldObj.getWorldInfo().setThundering(true);
    				worldObj.getWorldInfo().setRainTime(RadiationConfig.rain);
    				worldObj.getWorldInfo().setThunderTime(RadiationConfig.rain);
    				AuxSavedData.setThunder(worldObj, RadiationConfig.rain);
        		}
        	}
        }
    }
    
    private void stomp(int x, int z, double dist) {
    	
    	int depth = 0;
    	
    	for(int y = 255; y >= 0; y--) {

    		Block b =  worldObj.getBlock(x, y, z);
    		Block ab =  worldObj.getBlock(x, y + 1, z);
    		int meta = worldObj.getBlockMetadata(x, y, z);
    		
    		if(b.getMaterial() == Material.air)
    			continue;
    		
    		if(b != ModBlocks.fallout && (ab == Blocks.air || (ab.isReplaceable(worldObj, x, y + 1, z) && !ab.getMaterial().isLiquid()))) {
    			
    			double d = (double) radProgress / (double) getScale() * 0.5;
    			
    			double chance = 0.05 - Math.pow((d - 0.6) * 0.5, 2);
    			
    			if(chance >= rand.nextDouble() && ModBlocks.fallout.canPlaceBlockAt(worldObj, x, y + 1, z))
    				worldObj.setBlock(x, y + 1, z, ModBlocks.fallout);
    		}
    		
    		if(b.isFlammable(worldObj, x, y, z, ForgeDirection.UP)) {
    			if(rand.nextInt(5) == 0)
    				worldObj.setBlock(x, y + 1, z, Blocks.fire);
    		}
    		
			if (b == Blocks.leaves || b == Blocks.leaves2) {
				worldObj.setBlock(x, y, z, Blocks.air);
			}
    		
			else if(b == Blocks.stone) {
				
				depth++;
				
				if(dist < 5)
					worldObj.setBlock(x, y, z, ModBlocks.sellafield_1);
				else if(dist < 15)
					worldObj.setBlock(x, y, z, ModBlocks.sellafield_0);
				else if(dist < 75)
					worldObj.setBlock(x, y, z, ModBlocks.sellafield_slaked);
				else
					return;
				
    			if(depth > 2)
    				return;
			
			}else if(b == Blocks.grass) {
    			worldObj.setBlock(x, y, z, ModBlocks.waste_earth);
    			return;
    			
    		} else if(b == Blocks.mycelium) {
    			worldObj.setBlock(x, y, z, ModBlocks.waste_mycelium);
    			return;
    		} else if(b == Blocks.sand) {
    			
    			if(rand.nextInt(60) == 0)
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
				int ra = rand.nextInt(150);
				if (ra < 7) {
					worldObj.setBlock(x, y, z, Blocks.diamond_ore);
				} else if (ra < 10) {
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
				if (rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
					worldObj.setBlock(x, y, z, ModBlocks.ore_schrabidium);
				else
					worldObj.setBlock(x, y, z, ModBlocks.ore_uranium_scorched);
    			return;
			}

			else if (b == ModBlocks.ore_nether_uranium) {
				if (rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
					worldObj.setBlock(x, y, z, ModBlocks.ore_nether_schrabidium);
				else
					worldObj.setBlock(x, y, z, ModBlocks.ore_nether_uranium_scorched);
    			return;
			}

			else if(b == ModBlocks.ore_gneiss_uranium) {
				if(rand.nextInt(VersatileConfig.getSchrabOreChance()) == 0)
					worldObj.setBlock(x, y, z, ModBlocks.ore_gneiss_schrabidium);
				else
					worldObj.setBlock(x, y, z, ModBlocks.ore_gneiss_uranium_scorched);
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

		int scale = this.dataWatcher.getWatchableObjectInt(16);
		
		return scale == 0 ? 1 : scale;
	}
}
