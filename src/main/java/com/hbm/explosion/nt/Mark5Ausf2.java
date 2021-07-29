package com.hbm.explosion.nt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Mark5Ausf2 implements IExplosionLogic {

	//holds rays after being calculated up to where the blocks get removed
	private HorizontalSegment[] segments;
	private HorizontalSegment[] repopulatedSegments;
	//buffered blocks marked for deletion
	private Set<BlockPos> buffer = new HashSet();
	
	private World world;
	private float strength;
	float originX;
	float originY;
	float originZ;
	float length;
	
	//0: calculate blocks to destroy
	//1: destroy blocks
	//2: generate new rays
	int phase = 0;
	
	public Mark5Ausf2(World world, float strength, float x, float y, float z) {
		this.world = world;
		this.strength = strength;
		this.originX = x;
		this.originY = y;
		this.originZ = z;
		
		this.initRays();
	}
	
	private double getResolutionMult() {
		return 5D;
	}
	
	private void initRays() {
		
		this.length = 5F;		//how long the current rays are
		
		double bow = length * Math.PI;								//one "bow", the distance between the poles along the circumference ( U/2 )
		int bowCount = (int) Math.ceil(bow * getResolutionMult());		//how many segments we want along one bow, usually 1 per block + a resolution multiplier
		double bowDelta = (Math.PI / (double) (bowCount - 1));		//the angular difference (pitch) between where each segment is
		
		segments = new HorizontalSegment[bowCount];
		
		for(int i = 0; i < bowCount; i++) {
			double currBow = bowDelta * i;														//the current angle along the bow (i.e. the pitch of the horizontal ring)
			double ringRadius = Math.sin(currBow) * length;										//the radius of the current ring
			double ringCircumference = 2D * ringRadius * Math.PI;								//the circumference for the ring
			int ringCount = (int) Math.max(Math.ceil(ringCircumference * getResolutionMult()), 1);	//how many rays we want per ring (1 per block plus extra resolution)
			double ringDelta = ((Math.PI * 2D) / (double) (ringCount - 1));						//the angular difference (yaw) between rays in a ring
			
			HorizontalSegment seg = new HorizontalSegment(ringCount);
			float pitch = (float) currBow;
			
			for(int j = 0; j < ringCount; j++) {
				float yaw = (float)(ringDelta * j);
				
				//if(yaw < Math.PI * 0.5 && pitch < Math.PI)
				seg.rays[j] = new MVRay(strength, originX, originY, originZ, yaw, pitch, length);
			}

			segments[i] = seg;
		}
		
		/*System.out.println("Initialized with" + segments.length + " segments!");
		
		for(HorizontalSegment seg : segments) {
			
			if(seg == null) {
				System.out.println("NULL");
			} else {
				System.out.println(seg.rays.length);
			}
		}*/
		System.out.println("STOP");
	}

	@Override
	public void updateLogic() {
		
		switch(this.phase) {
		case 0: processRays(50000); break;
		case 1: breakBlocks(2000); break;
		case 2: repopulate(); break;
		}
	}
	
	private int processBow = 0;
	private int processRing = 0;
	
	private void endPhaseZero() {
		this.processBow = 0;
		this.processRing = 0;
		this.phase = 1;
		System.out.println("Ending phase 0");
	}
	
	private void processRays(int amount) {
		
		while(true) {
			
			if(amount < 0) {
				return;
			}
			
			if(this.segments[processBow] == null || this.processRing >= this.segments[processBow].rays.length) {
				this.processRing = 0;
				this.processBow++;
				
				if(this.processBow >= this.segments.length) {
					endPhaseZero();
					return;
				}
				continue;
			}
			
			if(this.processBow >= this.segments.length) {
				endPhaseZero();
				return;
			}
			
			MVRay ray = this.segments[processBow].rays[this.processRing];
			
			if(ray != null) {
				
				Vec3 vec = Vec3.createVectorHelper(1, 0, 0);
				vec.rotateAroundZ(ray.pitch);
				vec.rotateAroundY(ray.yaw);
				
				double finalX = 0;
				double finalY = 0;
				double finalZ = 0;
				
				for(float i = 0; i < ray.length; i += 0.5F) {

					finalX = ray.x + vec.xCoord * i;
					finalY = ray.y + vec.yCoord * i;
					finalZ = ray.z + vec.zCoord * i;
					int x = (int)Math.floor(finalX);
					int y = (int)Math.floor(finalY);
					int z = (int)Math.floor(finalZ);
					
					//System.out.println(ray.pitch + " " + ray.yaw + " / " + x + " " + y + " " + z);
					
					BlockPos pos = new BlockPos(x, y, z);
					
					if(y > 255 || y < 0) {
						break;
					}
					
					Block b = world.getBlock(x, y, z);
					
					float res = (b.getMaterial().isLiquid() ? (float)Math.pow(Blocks.air.getExplosionResistance(null), 1.25) : (float)Math.pow(b.getExplosionResistance(null), 1.25));
					
					ray.power -= res;
					
					if(ray.power <= 0) {
						break;
					}
					
					if(b != Blocks.air)
						this.buffer.add(pos);
				}
				
				if(ray.power <= 0 || finalY < 0 || finalY > 255 || this.length > this.strength) {
					this.segments[processBow].rays[this.processRing] = null;
				}
				ray.x = (float) finalX;
				ray.y = (float) finalY;
				ray.z = (float) finalZ;
			}
			
			amount--;
			this.processRing++;
		}
	}
	
	private void breakBlocks(int amount) {
		
		if(this.phase == 1 && this.buffer.isEmpty()) {
			this.phase = 2;
			System.out.println("Ending phase 1");
			return;
		}
		
		int rem = 0;
		
		List<BlockPos> toRem = new ArrayList();
		
		for(BlockPos pos : this.buffer) {
			world.setBlock(pos.x, pos.y, pos.z, Blocks.air, 0, 3);
			
			toRem.add(pos);
			rem++;
			
			if(rem == amount)
				break;
		}
		
		this.buffer.removeAll(toRem);
		
		if(this.buffer.isEmpty()) {
			this.phase = 2;
			System.out.println("Ending phase 1");
		}
	}
	
	private void repopulate() {
		
		boolean didYouDoLiterallyAnything = false;
		this.length *= 2;
		
		double bow = length * Math.PI;
		int bowCount = (int) Math.ceil(bow * getResolutionMult());
		double bowDelta = (Math.PI / (double) (bowCount - 1));
		
		repopulatedSegments = new HorizontalSegment[bowCount];
		
		for(int i = 0; i < bowCount; i++) {
			
			double currBow = bowDelta * i;
			double ringRadius = Math.sin(currBow) * length;
			double ringCircumference = 2D * ringRadius * Math.PI;
			int ringCount = (int) Math.max(Math.ceil(ringCircumference * getResolutionMult()), 1);
			double ringDelta = ((Math.PI * 2D) / (double) (ringCount - 1));

			float pitch = (float) currBow;
			HorizontalSegment parentSegment = fromAngle(pitch);
			
			if(parentSegment != null) {
				
				HorizontalSegment seg = new HorizontalSegment(ringCount);
				
				for(int j = 0; j < ringCount; j++) {
					float yaw = (float)(ringDelta * j);
					
					MVRay parentRay = parentSegment.fromAngle(yaw);
					
					if(parentRay != null && parentRay.power > 0) {
						
						/*Vec3 newDest = Vec3.createVectorHelper(length, 0D, 0D);
						newDest.rotateAroundZ(pitch);
						newDest.rotateAroundY(yaw);
						Vec3 delta = Vec3.createVectorHelper(newDest.xCoord - parentRay.x, newDest.yCoord - parentRay.y, newDest.zCoord - parentRay.z);

						double deltaLen = delta.lengthVector();
						float pLen = parentRay.length;
						
						double s_deltaLen = Math.sqrt(delta.xCoord * delta.xCoord + delta.yCoord * delta.yCoord);
						double s_alpha0 = parentRay.pitch;
						double s_delta = Math.acos((pLen * pLen + s_deltaLen * s_deltaLen - length * length) / (2 * pLen * s_deltaLen));
						double newPitch = s_alpha0 + (Math.PI - s_delta);

						double r_deltaLen = Math.sqrt(delta.xCoord * delta.xCoord + delta.zCoord * delta.zCoord);
						double r_alpha0 = parentRay.yaw;
						double r_delta = Math.acos((pLen * pLen + r_deltaLen * r_deltaLen - length * length) / (2 * pLen * r_deltaLen));
						double newYaw = r_alpha0 + (Math.PI - r_delta);*/

						/*System.out.println("deltaLen: " + deltaLen);
						System.out.println("pLen: " + pLen);
						System.out.println("s_deltaLen: " + s_deltaLen);
						System.out.println("s_alpha0: " + s_alpha0);
						System.out.println("s_delta: " + s_delta);
						System.out.println("newPitch: " + newPitch);
						System.out.println("r_deltaLen: " + r_deltaLen);
						System.out.println("r_alpha0: " + r_alpha0);
						System.out.println("r_delta: " + r_delta);
						System.out.println("newYaw: " + newYaw);*/
						/*System.out.println("(pLen * pLen + s_deltaLen * s_deltaLen - length * length) / (2 * pLen * s_deltaLen)");
						System.out.println("" + (pLen * pLen + s_deltaLen * s_deltaLen - length * length) / (2 * pLen * s_deltaLen));
						System.out.println("(pLen * pLen + r_deltaLen * r_deltaLen - length * length) / (2 * pLen * r_deltaLen)");
						System.out.println("" + (pLen * pLen + r_deltaLen * r_deltaLen - length * length) / (2 * pLen * r_deltaLen));*/
						
						Vec3 len = Vec3.createVectorHelper(parentRay.x - originX, parentRay.y - originY, parentRay.z - originZ);
						double totalLen = len.lengthVector();
						Vec3 normal = Vec3.createVectorHelper(totalLen, 0D, 0D);
						normal.rotateAroundZ(pitch);
						normal.rotateAroundY(yaw);

						//double surface = 4 * Math.PI * this.length * this.length;
						//double prevSurface = 4 * Math.PI * totalLen * totalLen;
						float newPower = parentRay.power;
						
						MVRay newRay = new MVRay(newPower,
								(float)(this.originX - normal.xCoord),
								(float)(this.originY - normal.yCoord),
								(float)(this.originZ - normal.zCoord),
								yaw, pitch, (float) (this.length - totalLen));
						
						seg.rays[j] = newRay;
						repopulatedSegments[i] = seg;
						didYouDoLiterallyAnything = true;
					}
				}
			}
		}
		
		this.segments = this.repopulatedSegments;
		this.repopulatedSegments = null;
		
		System.out.println("Ending phase 2");
		
		System.out.println("Initialized with" + segments.length + " segments!");
		
		/*for(HorizontalSegment seg : segments) {
			
			if(seg == null) {
				System.out.println("NULL");
			} else {
				System.out.println(seg.rays.length);
			}
		}
		System.out.println("STOP");*/
		
		this.phase = 0;
		
		if(!didYouDoLiterallyAnything) {
			System.out.println("Done!");
			this.isDone = true;
		}
	}
	
	private HorizontalSegment fromAngle(float pitch) {
		int size = this.segments.length;
		int index = (int)Math.floor((pitch * size) / Math.PI) % this.segments.length;
		
		if(index >= this.segments.length || index < 0)
			return null;
		
		return this.segments[index];
	}
	
	private boolean isDone = false;

	@Override
	public boolean isDone() {
		return isDone;
	}
	
	public static class HorizontalSegment {
		
		MVRay[] rays;
		
		private HorizontalSegment(int size) {
			rays = new MVRay[size];
		}
		
		private MVRay fromAngle(float yaw) {
			int size = rays.length;
			int index = (int)Math.floor((yaw * size) / (Math.PI * 2)) % this.rays.length;
			
			if(index >= this.rays.length || index < 0)
				return null;
			
			return rays[index];
		}
	}

	public static class MVRay {
		
		//initiated with starting power, once processed it shows the power left
		float power;
		//once calculated, this becomes true
		boolean collected = false;
		float x;
		float y;
		float z;
		float yaw;
		float pitch;
		float length;
		boolean tracked;
		
		public MVRay(float power, float x, float y, float z, float yaw, float pitch, float length) {
			this.power = power;
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
			this.length = length;
		}
		
		public MVRay(float power, float x, float y, float z, Vec3 dir, float length) {
			
			double len = dir.lengthVector();
			
			float yaw = (float)Math.atan2(dir.zCoord, dir.xCoord);
			float pitch = (float)Math.asin(dir.yCoord / len);
			
			this.power = power;
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
			this.length = length;
		}
	}
	
	public static class BlockPos {

		int x;
		int y;
		int z;
		
		public BlockPos(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			result = prime * result + z;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			
			if(this == obj) return true;
			if(obj == null) return false;
			if(getClass() != obj.getClass()) return false;
			
			BlockPos other = (BlockPos) obj;
			if(x != other.x) return false;
			if(y != other.y) return false;
			if(z != other.z) return false;
			
			return true;
		}
	}
}
