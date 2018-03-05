package com.hbm.explosion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.main.MainRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class ExplosionNukeRay {
	
	List<FloatTriplet> affectedBlocks = new ArrayList();
	int posX;
	int posY;
	int posZ;
	Random rand = new Random();
	World world;
	int strength;
	int count;
	int speed;
	int processed;
	int length;
	
	public ExplosionNukeRay(World world, int x, int y, int z, int strength, int count, int speed, int length) {
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.strength = strength;
		this.count = count;
		this.speed = speed;
		this.length = length;
	}
	
	/*public void processBunch(int count) {
		for(int l = processed; l < processed + count; l++) {

            if(l > affectedBlocks.size() - 1)
            	break;
            
			int x = affectedBlocks.get(l).chunkPosX;
			int y = affectedBlocks.get(l).chunkPosY;
			int z = affectedBlocks.get(l).chunkPosZ;
			
            if(world.getBlock(x, y, z) != Blocks.air)
            {
            	world.setBlock(x, y, z, Blocks.air);
            }
		}
		
		processed += count;
	}
	
	public void collectBunch(int count) {
		
		for(int k = 0; k < count; k++) {
			double phi = rand.nextDouble() * (Math.PI * 2);
			double costheta = rand.nextDouble() * 2 - 1;
			double theta = Math.acos(costheta);
			double x = Math.sin( theta) * Math.cos( phi );
			double y = Math.sin( theta) * Math.sin( phi );
			double z = Math.cos( theta );
			
			Vec3 vec = Vec3.createVectorHelper(x, y, z);
			int length = (int)Math.ceil(strength);
			
			float res = strength;
			
			for(int i = 0; i < length; i ++) {
				
				if(i > this.length)
					break;
				
				int x0 = (int)(posX + (vec.xCoord * i));
				int y0 = (int)(posY + (vec.yCoord * i));
				int z0 = (int)(posZ + (vec.zCoord * i));
				if(!world.getBlock(x0, y0, z0).getMaterial().isLiquid())
					res -= Math.pow(world.getBlock(x0, y0, z0).getExplosionResistance(null), 1.25);
				else
					res -= Math.pow(Blocks.air.getExplosionResistance(null), 1.25);
				
				if(res > 0 && world.getBlock(x0, y0, z0) != Blocks.air) {
					if(affectedBlocks.size() < Integer.MAX_VALUE - 100)
						affectedBlocks.add(new ChunkPosition(x0, y0, z0));
				}
			}
		}
	}*/
	
	public void processTip(int count) {
		for(int l = processed; l < processed + count; l++) {

            if(l > affectedBlocks.size() - 1)
            	break;
            
			float x = affectedBlocks.get(l).xCoord;
			float y = affectedBlocks.get(l).yCoord;
			float z = affectedBlocks.get(l).zCoord;
			
			world.setBlock((int)x, (int)y, (int)z, Blocks.air);
			
			Vec3 vec = Vec3.createVectorHelper(x - this.posX, y - this.posY, z - this.posZ);
			double pX = vec.xCoord / vec.lengthVector();
			double pY = vec.yCoord / vec.lengthVector();
			double pZ = vec.zCoord / vec.lengthVector();
			
			for(int i = 0; i < vec.lengthVector(); i ++) {
				int x0 = (int)(posX + pX * i);
				int y0 = (int)(posY + pY * i);
				int z0 = (int)(posZ + pZ * i);
				
				//if(world.getBlock(x0, y0, z0) != Blocks.air)
					world.setBlock(x0, y0, z0, Blocks.air);
			}
		}
		
		processed += count;
	}
	
	public void collectTip(int count) {
		
		for(int k = 0; k < count; k++) {
			double phi = rand.nextDouble() * (Math.PI * 2);
			double costheta = rand.nextDouble() * 2 - 1;
			double theta = Math.acos(costheta);
			double x = Math.sin(theta) * Math.cos(phi);
			double y = Math.sin(theta) * Math.sin(phi);
			double z = Math.cos(theta);
			
			Vec3 vec = Vec3.createVectorHelper(x, y, z);
			int length = (int)Math.ceil(strength);
			
			float res = strength;
			
			FloatTriplet lastPos = null;
			
			for(int i = 0; i < length; i ++) {
				
				if(i > this.length)
					break;
				
				float x0 = (float) (posX + (vec.xCoord * i));
				float y0 = (float) (posY + (vec.yCoord * i));
				float z0 = (float) (posZ + (vec.zCoord * i));
				
				if(!world.getBlock((int)x0, (int)y0, (int)z0).getMaterial().isLiquid())
					res -= Math.pow(world.getBlock((int)x0, (int)y0, (int)z0).getExplosionResistance(null), 1.0);
				else
					res -= Math.pow(Blocks.air.getExplosionResistance(null), 1.0);

				if(res > 0 && world.getBlock((int)x0, (int)y0, (int)z0) != Blocks.air) {
					lastPos = new FloatTriplet(x0, y0, z0);
				}
				
				if(res <= 0 || i + 1 >= this.length) {
					if(affectedBlocks.size() < Integer.MAX_VALUE - 100 && lastPos != null)
						affectedBlocks.add(new FloatTriplet(lastPos.xCoord, lastPos.yCoord, lastPos.zCoord));
					break;
				}
			}
		}
	}
	
	public void deleteStorage() {
		this.affectedBlocks.clear();
	}
	
	public int getStoredSize() {
		return this.affectedBlocks.size();
	}
	
	public int getProgress() {
		return this.processed;
	}
	
	public class FloatTriplet {
		public float xCoord;
		public float yCoord;
		public float zCoord;
		
		public FloatTriplet(float x, float y, float z) {
			xCoord = x;
			yCoord = y;
			zCoord = z;
		}
	}

}
