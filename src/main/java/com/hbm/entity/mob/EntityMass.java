package com.hbm.entity.mob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.util.EnumUtil;
import com.hbm.util.Vec3NT;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMass extends EntityCreature implements IMob {
	
	public List<MassFragment> frags = new ArrayList();

	public EntityMass(World world) {
		super(world);
		this.setSize(2F, 2F);
		
		for(int i = 0; i < 50; i++) {
			MassFragmentType type = EnumUtil.grabEnumSafely(MassFragmentType.class, rand.nextInt(MassFragmentType.values().length));
			frags.add(new MassFragment(type, rand));
		}
	}

	@Override protected void fall(float dist) { }
	@Override public float getEyeHeight() { return this.height * 0.5F; }
	@Override protected void updateFallState(double distFallen, boolean onGround) { }
	@Override public boolean isOnLadder() { return false; }
	@Override public void addVelocity(double x, double y, double z) { }
	@Override public void faceEntity(Entity entity, float yaw, float pitch) { }

	@Override protected boolean isAIEnabled() { return false; }
	@Override protected boolean canDespawn() { return false; }

	@Override
	public void moveEntityWithHeading(float strafe, float forward) {

		this.motionX *= 0.9;
		this.motionY *= 0.9;
		this.motionZ *= 0.9;

		moveFlying(strafe, forward, 0.02F);
		moveEntity(this.motionX, this.motionY, this.motionZ);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		this.setDead();
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if(worldObj.isRemote) {
			for(MassFragment frag : this.frags) frag.updateMovement();
			
		} else {
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50, 50, 50));
			if(!players.isEmpty()) {
				EntityPlayer player = players.get(0);
				Vec3NT vec = new Vec3NT(player.posX - posX, player.posY - posY, player.posZ - posZ);
				if(vec.lengthVector() > 5) {
					vec.normalizeSelf().multiply(0.2);
					this.motionX = vec.xCoord;
					this.motionY = vec.yCoord;
					this.motionZ = vec.zCoord;
				}
			}
		}
	}

	@Override
	protected void updateEntityActionState() {

	}
	
	public static class MassFragment {
		
		public MassFragmentType type;
		public double posX;
		public double posY;
		public double posZ;
		public double prevX;
		public double prevY;
		public double prevZ;
		public double targetX;
		public double targetY;
		public double targetZ;
		public Random rand;
		public double yaw;
		public double pitch;
		public double roll;
		public double prevYaw;
		public double prevPitch;
		public double prevRoll;
		public double yawSpeed;
		public double pitchSpeed;
		public double rollSpeed;

		public static final double SPEED = 0.05D; // per-axis speed of parts moving
		public static final double LIMIT = 0.75D; // size limit (radius)
		
		public MassFragment(MassFragmentType type, Random rand) {
			this.type = type;
			this.rand = rand;
			this.setDefaultPos(rand);
		}
		
		public MassFragment setPos(double x, double y, double z) {
			this.posX = this.prevX = this.targetX = x;
			this.posY = this.prevY = this.targetY = y;
			this.posZ = this.prevZ = this.targetZ = z;
			return this;
		}
		
		public MassFragment setDefaultPos(Random rand) {
			double x = rand.nextDouble() * LIMIT * 2 - LIMIT;
			double y = rand.nextDouble() * LIMIT * 2 - LIMIT;
			double z = rand.nextDouble() * LIMIT * 2 - LIMIT;
			if(rand.nextInt(3) != 0) {
				if(rand.nextInt(3) == 0) {
					x = 1D;
				} else if(rand.nextBoolean()) {
					y = 1D;
				} else {
					z = 1D;
				}
				if(rand.nextBoolean()) {
					x *= -1;
					y *= -1;
					z *= -1;
				}
			}
			return setPos(x, y, z);
		}
		
		public void updateMovement() {
			this.prevX = this.posX;
			this.prevY = this.posY;
			this.prevZ = this.posZ;
			this.prevYaw = this.yaw;
			this.prevPitch = this.pitch;
			this.prevRoll = this.roll;
			
			// if we've reached our target, choose another one
			if(Math.abs(posX - targetX) < SPEED && Math.abs(posY - targetY) < SPEED && Math.abs(posZ - targetZ) < SPEED) {

				// choose one axis and move to a random point within the limit
				if(rand.nextInt(3) == 0) {
					this.targetX = rand.nextDouble() * LIMIT * 2 - LIMIT;
				} else if(rand.nextBoolean()) {
					this.targetY = rand.nextDouble() * LIMIT * 2 - LIMIT;
				} else {
					this.targetZ = rand.nextDouble() * LIMIT * 2 - LIMIT;
				}
				
			// the bit that moves shit
			} else {
				if(Math.abs(posX - targetX) >= SPEED) if(posX < targetX) posX += SPEED; else posX -= SPEED;
				if(Math.abs(posY - targetY) >= SPEED) if(posY < targetY) posY += SPEED; else posY -= SPEED;
				if(Math.abs(posZ - targetZ) >= SPEED) if(posZ < targetZ) posZ += SPEED; else posZ -= SPEED;
			}

			// randomly bop the part, altering its spin
			double speedVariation = 5D;
			if(rand.nextInt(20) == 0) this.yawSpeed += rand.nextGaussian() * speedVariation;
			if(rand.nextInt(20) == 0) this.pitchSpeed += rand.nextGaussian() * speedVariation;
			if(rand.nextInt(20) == 0) this.rollSpeed += rand.nextGaussian() * speedVariation;

			double angularVelocityLimit = 15;
			this.yawSpeed = MathHelper.clamp_double(yawSpeed, -angularVelocityLimit, angularVelocityLimit);
			this.pitchSpeed = MathHelper.clamp_double(pitchSpeed, -angularVelocityLimit, angularVelocityLimit);
			this.rollSpeed = MathHelper.clamp_double(rollSpeed, -angularVelocityLimit, angularVelocityLimit);

			this.yaw += this.yawSpeed;
			this.pitch += this.pitchSpeed;
			this.roll += this.rollSpeed;

			// angle limit crap
			if(this.yaw >= 360D) { this.yaw -= 360D; this.prevYaw -= 360D; }
			if(this.yaw < 0D) { this.yaw += 360D; this.prevYaw += 360D; }
			if(this.pitch >= 360D) { this.pitch -= 360D; this.prevPitch -= 360D; }
			if(this.pitch < 0D) { this.pitch += 360D; this.prevPitch += 360D; }
			if(this.roll >= 360D) { this.roll -= 360D; this.prevRoll -= 360D; }
			if(this.roll < 0D) { this.roll += 360D; this.prevRoll += 360D; }
		}
	}
	
	public static enum MassFragmentType {
		GEAR("Gear"),
		BEAM("Beam"),
		PANEL("Panel"),
		BAR("Bar"),
		ANGLE("Angle"),
		PIPE("Pipe"),
		DRILL("Drill");
		
		public final String model;
		
		private MassFragmentType(String model) {
			this.model = model;
		}
	}
}