package com.hbm.explosion;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ExplosionNukeRay {

	List<FloatTriplet> affectedBlocks = new ArrayList<>();
	int posX;
	int posY;
	int posZ;
	World world;

	int strength;
	int length;
	int processed;

	int gspNumMax;
	int gspNum;
	double gspX;
	double gspY;

	public boolean isAusf3Complete = false;

	/*[[unused]]
	int count;
	int speed;
	int startY;
	int startCir;
	Random rand = new Random();
	private double overrideRange = 0;
	*/

	public ExplosionNukeRay(World world, int x, int y, int z, int strength, int count, int speed, int length) {
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.strength = strength;
		this.length = length;
		//Ausf3, must be double
		//Mk 4.5, must be int32

		// Total number of points
		this.gspNumMax = (int)(2.5 * Math.PI * Math.pow(this.strength,2));
		this.gspNum = 1;

		// The beginning of the generalized spiral points
		this.gspX = Math.PI;
		this.gspY = 0.0;


		/*[[unused]]
		// this.startY = strength;
		this.startY = 0;
		this.startCir = 0;

		this.count = count;
		this.speed = speed;
		
		//starts at around 80, becomes 8 at length 500
		this.overrideRange = Math.max((Math.log(length) * 4 - 2.5D) * 10, 0);
		*/
	}

	// Raise one generalized spiral points
	private void generateGspUp(){
		if (this.gspNum < this.gspNumMax) {
			int k = this.gspNum + 1;
			double hk = -1.0 + 2.0 * (k - 1.0) / (this.gspNumMax - 1.0);
			this.gspX = Math.acos(hk);

			double prev_lon = this.gspY;
			double lon = prev_lon + 3.6 / Math.sqrt(this.gspNumMax) / Math.sqrt(1.0 - hk * hk);
			this.gspY = lon % (Math.PI * 2);
		} else {
			this.gspX = 0.0;
			this.gspY = 0.0;
		}
		this.gspNum++;
	}

	// Get Cartesian coordinates for spherical coordinates
	private Vec3 getSpherical2cartesian(){
		double dx = Math.sin(this.gspX) * Math.cos(this.gspY);
		double dz = Math.sin(this.gspX) * Math.sin(this.gspY);
		double dy = Math.cos(this.gspX);
		return Vec3.createVectorHelper(dx, dy, dz);
	}

	//currently used by mk4
	public void collectTipMk4_5(int count) {

		int amountProcessed = 0;

		while (this.gspNumMax >= this.gspNum){
			// Get Cartesian coordinates for spherical coordinates
			Vec3 vec = this.getSpherical2cartesian();

			int length = (int)Math.ceil(strength);
			float res = strength;
			FloatTriplet lastPos = null;

			for(int i = 0; i < length; i ++) {

				if(i > this.length)
					break;

				float x0 = (float) (posX + (vec.xCoord * i));
				float y0 = (float) (posY + (vec.yCoord * i));
				float z0 = (float) (posZ + (vec.zCoord * i));

				double fac = 100 - ((double) i) / ((double) length) * 100;
				fac *= 0.07D;

				if(!world.getBlock((int)x0, (int)y0, (int)z0).getMaterial().isLiquid())
					res -= Math.pow(world.getBlock((int)x0, (int)y0, (int)z0).getExplosionResistance(null), 4D - fac);
				else
					res -= Math.pow(Blocks.air.getExplosionResistance(null), 4D - fac);

				if(res > 0 && world.getBlock((int)x0, (int)y0, (int)z0) != Blocks.air) {
					lastPos = new FloatTriplet(x0, y0, z0);
				}

				if(res <= 0 || i + 1 >= this.length) {
					if(affectedBlocks.size() < Integer.MAX_VALUE - 100 && lastPos != null) {
						affectedBlocks.add(lastPos);
					}
					break;
				}
			}
			// Raise one generalized spiral points
			this.generateGspUp();

			amountProcessed++;
			if(amountProcessed >= count) {
				return;
			}
		}
		isAusf3Complete = true;
	}

	public void processTip(int count) {

		int processedBlocks = 0;
		int braker = 0;

		for(int l = 0; l < Integer.MAX_VALUE; l++) {

			if(processedBlocks >= count)
				return;

			if(braker >= count * 50)
				return;

			if(l > affectedBlocks.size() - 1)
				break;

			if(affectedBlocks.isEmpty())
				return;

			int in = affectedBlocks.size() - 1;

			float x = affectedBlocks.get(in).xCoord;
			float y = affectedBlocks.get(in).yCoord;
			float z = affectedBlocks.get(in).zCoord;

			world.setBlock((int)x, (int)y, (int)z, Blocks.air);

			Vec3 vec = Vec3.createVectorHelper(x - this.posX, y - this.posY, z - this.posZ);
			double pX = vec.xCoord / vec.lengthVector();
			double pY = vec.yCoord / vec.lengthVector();
			double pZ = vec.zCoord / vec.lengthVector();

			for(int i = 0; i < vec.lengthVector(); i ++) {
				int x0 = (int)(posX + pX * i);
				int y0 = (int)(posY + pY * i);
				int z0 = (int)(posZ + pZ * i);

				if(!world.isAirBlock(x0, y0, z0)) {
					world.setBlock(x0, y0, z0, Blocks.air);
					processedBlocks++;
				}

				braker++;
			}

			affectedBlocks.remove(in);
		}

		processed += count;
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
/*
	@Untested //override range
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
					res -= Math.pow(world.getBlock((int)x0, (int)y0, (int)z0).getExplosionResistance(null), 1.25);
				else
					res -= Math.pow(Blocks.air.getExplosionResistance(null), 1.25);

				//Blast resistance calculations are still done to preserve the general shape,
				//but if the blast were to be stopped within this range we go through with it anyway.
				//There is currently no blast resistance limit on this, could change in the future.
				boolean inOverrideRange = this.overrideRange >= length;

				if((res > 0 || inOverrideRange) && world.getBlock((int)x0, (int)y0, (int)z0) != Blocks.air) {
					lastPos = new FloatTriplet(x0, y0, z0);
				}
				

				// Only stop if we are either out of range or if the remaining strength is 0 while being outside the override range

				if((res <= 0 && !inOverrideRange) || i + 1 >= this.length) {
					if(affectedBlocks.size() < Integer.MAX_VALUE - 100 && lastPos != null)
						affectedBlocks.add(new FloatTriplet(lastPos.xCoord, lastPos.yCoord, lastPos.zCoord));
					break;
				}
			}
		}
	}
*/
	/*
	public void collectTipExperimental(int count) {
		
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
				
				double fac = 100 - ((double) i) / ((double) length) * 100;
				fac *= 0.07D;
				
				if(!world.getBlock((int)x0, (int)y0, (int)z0).getMaterial().isLiquid())
					res -= Math.pow(world.getBlock((int)x0, (int)y0, (int)z0).getExplosionResistance(null), 7.5D - fac);
				else
					res -= Math.pow(Blocks.air.getExplosionResistance(null), 7.5D - fac);

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
	*/
	/*public void collectTipAusf3(int count) {
		
		int amountProcessed = 0;
		
		//Axial
		//StartY starts at this.length
		for(double y = startY; y >= -strength; y -= (0.35D + (   Math.pow((strength - Math.abs(y)) / strength, 1.5D)   * 0.3D))) {
			
			double sectionRad = Math.sqrt(Math.pow(strength, 2) - Math.pow(y, 2));
			double circumference = (1.5 * Math.PI * sectionRad + rand.nextDouble()) * (sectionRad / strength + 0.1D);
			
			//circumference = Math.ceil(circumference);
			
			//Radial
			//StartCir starts at circumference
			for(int r = startCir; r < circumference; r ++) {
				
				Vec3 vec = Vec3.createVectorHelper(sectionRad, y, 0);
				vec = vec.normalize();
				if(y > 0)
					vec.rotateAroundZ((float) (y / sectionRad) * 0.15F);
				if(y < 0)
					vec.rotateAroundZ((float) (y / sectionRad) * -0.15F);
				vec.rotateAroundY((float) (360 / circumference * r));
				
				int length = (int)Math.ceil(strength);
				
				float res = strength;
				
				FloatTriplet lastPos = null;
				
				for(int i = 0; i < length; i ++) {
					
					if(i > this.length)
						break;
					
					float x0 = (float) (posX + (vec.xCoord * i));
					float y0 = (float) (posY + (vec.yCoord * i));
					float z0 = (float) (posZ + (vec.zCoord * i));
					
					double fac = 100 - ((double) i) / ((double) length) * 100;
					fac *= 0.07D;
					
					if(!world.getBlock((int)x0, (int)y0, (int)z0).getMaterial().isLiquid())
						res -= Math.pow(world.getBlock((int)x0, (int)y0, (int)z0).getExplosionResistance(null), 7.5D - fac);
					else
						res -= Math.pow(Blocks.air.getExplosionResistance(null), 7.5D - fac);
	
					if(res > 0 && world.getBlock((int)x0, (int)y0, (int)z0) != Blocks.air) {
						lastPos = new FloatTriplet(x0, y0, z0);
					}
					
					if(res <= 0 || i + 1 >= this.length) {
						if(affectedBlocks.size() < Integer.MAX_VALUE - 100 && lastPos != null) {
							affectedBlocks.add(new FloatTriplet(lastPos.xCoord, lastPos.yCoord, lastPos.zCoord));
						}
						break;
					}
				}
				
				amountProcessed++;
				
				if(amountProcessed >= count) {
					startY = y + 1;
					startCir = startCir + 1;
					return;
				}
			}
		}
		
		isAusf3Complete = true;
	}*/

	//Dysfunctional, punches hole into ground
	/*public void collectTipAusf3(int count) {
		
		int amountProcessed = 0;
		
		//Axial
		//StartY starts at this.length
		for(int y = startY; y >= -length; y--) {
			
			double circumference = 2 * Math.PI * Math.sqrt(Math.pow(y, 2) + Math.pow(length, 2));
			
			circumference = Math.ceil(circumference);
			
			//Radial
			//StartCir starts at circumference
			for(int r = startCir; r < circumference; r ++) {
				
				Vec3 vec = Vec3.createVectorHelper(1, y, 0);
				vec.normalize();
				vec.rotateAroundY((float) (360 / circumference * r));
				
				int length = (int)Math.ceil(strength);
				
				float res = strength;
				
				FloatTriplet lastPos = null;
				
				for(int i = 0; i < length; i ++) {
					
					if(i > this.length)
						break;
					
					float x0 = (float) (posX + (vec.xCoord * i));
					float y0 = (float) (posY + (vec.yCoord * i));
					float z0 = (float) (posZ + (vec.zCoord * i));
					
					double fac = 100 - ((double) i) / ((double) length) * 100;
					fac *= 0.07D;
					
					if(!world.getBlock((int)x0, (int)y0, (int)z0).getMaterial().isLiquid())
						res -= Math.pow(world.getBlock((int)x0, (int)y0, (int)z0).getExplosionResistance(null), 7.5D - fac);
					else
						res -= Math.pow(Blocks.air.getExplosionResistance(null), 7.5D - fac);
	
					if(res > 0 && world.getBlock((int)x0, (int)y0, (int)z0) != Blocks.air) {
						lastPos = new FloatTriplet(x0, y0, z0);
					}
					
					if(res <= 0 || i + 1 >= this.length) {
						if(affectedBlocks.size() < Integer.MAX_VALUE - 100 && lastPos != null)
							affectedBlocks.add(new FloatTriplet(lastPos.xCoord, lastPos.yCoord, lastPos.zCoord));
						break;
					}
				}
				
				amountProcessed++;
				
				if(amountProcessed >= count) {
					startY = y + 1;
					startCir = startCir + 1;
					return;
				}
			}
		}
		
		isAusf3Complete = true;
	}*/
	
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
