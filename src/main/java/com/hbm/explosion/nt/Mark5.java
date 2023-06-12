package com.hbm.explosion.nt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Deprecated
public class Mark5 implements IExplosionLogic {

	//holds rays after being calculated up to where the blocks get removed
	private List<MVRay> pendingRays = new ArrayList();
	//once a ray has been processed, it will be buffered for extension
	private List<MVRay> completedRays = new ArrayList();
	//buffered blocks marked for deletion
	private Set<BlockPos> buffer = new HashSet();
	
	private World world;
	private float strength;
	float originX;
	float originY;
	float originZ;
	float angularDelta;
	
	//0: calculate blocks to destroy
	//1: destroy blocks
	//2: generate new rays
	int phase = 0;
	
	public Mark5(World world, float strength, float x, float y, float z) {
		this.world = world;
		this.strength = strength;
		this.originX = x;
		this.originY = y;
		this.originZ = z;
		
		this.initRays();
	}
	
	private void initRays() {
		
		float startLen = 0.5F;
		
		//top
		pendingRays.add(new MVRay(strength, originX, originY, originZ, 0F, (float) Math.PI * 0.5F, startLen));
		//bottom
		pendingRays.add(new MVRay(strength, originX, originY, originZ, 0F, -(float) Math.PI * 0.5F, startLen));
		
		float pilet = (float) Math.PI * 0.25F;
		
		for(int i = 0; i < 8; i++) {
			pendingRays.add(new MVRay(strength, originX, originY, originZ, pilet * i, 0F, startLen));
			
			{//if(i % 2 == 0) {
				pendingRays.add(new MVRay(strength, originX, originY, originZ, pilet * i, -pilet, startLen));
				pendingRays.add(new MVRay(strength, originX, originY, originZ, pilet * i, pilet, startLen));
			}
		}
		
		this.angularDelta = pilet;
	}

	@Override
	public void updateLogic() {
		
		switch(this.phase) {
		case 0: processRays(5000); break;
		case 1: breakBlocks(10000); break;
		case 2: repopulate(20000); break;
		}
	}
	
	private void processRays(int amount) {
		
		if(this.phase == 0 && this.pendingRays.isEmpty()) {
			this.phase = 1;
			return;
		}
		
		int rem = 0;
		
		for(MVRay ray : this.pendingRays) {
			
			Vec3 vec = Vec3.createVectorHelper(1, 0, 0);
			vec.rotateAroundZ(ray.pitch);
			vec.rotateAroundY(ray.yaw);
			boolean shouldSplit = true;
			float lastIndex = 0;
			
			for(float i = 0; i < ray.length; i += 0.5F) {
				
				lastIndex = i;
				int x = (int)Math.floor(ray.x + vec.xCoord * i);
				int y = (int)Math.floor(ray.y + vec.yCoord * i);
				int z = (int)Math.floor(ray.z + vec.zCoord * i);
				
				BlockPos pos = new BlockPos(x, y, z);
				
				if(y > 255 || y < 0) {
					shouldSplit = false;
					break;
				}
				
				Block b = world.getBlock(x, y, z);
				
				float res = (b.getMaterial().isLiquid() ? 0 : b.getExplosionResistance(null)) + Blocks.air.getExplosionResistance(null);
				
				ray.power -= (res * res);
				
				if(ray.power <= 0) {
					shouldSplit = false;
					break;
				}
				
				if(b != Blocks.air)
					this.buffer.add(pos);
			}
			
			if(shouldSplit) {
				ray.x = ray.x + (float)vec.xCoord * lastIndex;
				ray.y = ray.y + (float)vec.yCoord * lastIndex;
				ray.z = ray.z + (float)vec.zCoord * lastIndex;
				this.completedRays.add(ray);
			}
			
			rem++;
			
			if(rem == amount)
				break;
		}
		
		this.pendingRays.subList(0, rem).clear();
		
		if(this.pendingRays.isEmpty()) {
			this.phase = 1;
		}
	}
	
	private void breakBlocks(int amount) {
		
		if(this.phase == 1 && this.buffer.isEmpty()) {
			this.angularDelta *= 0.5F;
			this.phase = 2;
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
			this.angularDelta *= 0.5F;
			this.phase = 2;
		}
	}
	
	private void repopulate(int amount) {
		
		if(this.phase == 2 && this.completedRays.isEmpty()) {
			this.phase = 0;
		}
		
		int rem = 0;
		
		for(MVRay ray : this.completedRays) {
			
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 2; j++) {
					MVRay child = new MVRay(ray.power, ray.x, ray.y, ray.z, ray.yaw + this.angularDelta * i, ray.pitch + this.angularDelta * j, ray.length * 2);
					this.pendingRays.add(child);
				}
			}
			
			rem++;
			
			if(rem == amount)
				break;
		}
		
		this.completedRays.subList(0, rem).clear();
	}

	@Override
	public boolean isDone() {
		return this.pendingRays.isEmpty() && this.completedRays.isEmpty() && this.buffer.isEmpty();
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