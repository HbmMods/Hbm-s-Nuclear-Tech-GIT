package com.hbm.entity.mob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.items.weapon.sedna.factory.ConfettiUtil;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.main.NTMSounds;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.EnumUtil;

import api.hbm.entity.IResistanceProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityMass extends EntityCreature implements IMob, IResistanceProvider {
	
	public int homeX;
	public int homeY = -1;
	public int homeZ;
	
	public int moveTimer;
	public ForgeDirection moveDir = ForgeDirection.UNKNOWN;
	
	public List<MassFragment> frags = new ArrayList();

	public EntityMass(World world) {
		super(world);
		this.setSize(3F, 3F);
		this.noClip = true;
		
		for(int i = 0; i < 250; i++) {
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

	@Override public void onDeath(DamageSource source) { }

	@Override
	public void moveEntityWithHeading(float strafe, float forward) {

		//moveFlying(strafe, forward, 0.02F);
		moveEntity(this.motionX, this.motionY, this.motionZ);

		this.motionX *= 0.9;
		this.motionY *= 0.9;
		this.motionZ *= 0.9;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source.getEntity() instanceof EntityPlayer && !worldObj.isRemote) this.setDead(); // debug
		return false;
	}

	@Override
	protected void collideWithEntity(Entity touching) {
		touching.applyEntityCollision(this);
		
		if(touching instanceof EntityLivingBase && touching.isEntityAlive()) {
			EntityLivingBase living = (EntityLivingBase) touching;
			EntityDamageUtil.attackEntityFromNT(living, ModDamageSource.turbofan, 1_000F, true, false, 0F, 1000F, 2F);
			if(!touching.isEntityAlive()) ConfettiUtil.gib(living);
		}
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if(worldObj.isRemote) {
			for(MassFragment frag : this.frags) frag.updateMovement();
			
			if(rand.nextInt(40) == 0) {
				NBTTagCompound vdat = new NBTTagCompound();
				vdat.setString("type", "sweat");
				vdat.setInteger("count", 5);
				vdat.setInteger("entity", getEntityId());
				vdat.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
				MainRegistry.proxy.effectNT(vdat);
			}
			
		} else {
			
			// targeting
			/*List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(150, 150, 150));
			if(!players.isEmpty()) {
				EntityPlayer player = players.get(0);
				Vec3NT vec = new Vec3NT(player.posX - posX, player.posY - posY, player.posZ - posZ);
				if(vec.lengthVector() > 5) {
					vec.normalizeSelf().multiply(0.2);
					this.motionX = vec.xCoord;
					this.motionY = vec.yCoord;
					this.motionZ = vec.zCoord;
				}
			}*/
			
			this.moveTimer--;
			
			if(this.moveTimer <= 0) {
				ForgeDirection newDir = EnumUtil.grabEnumSafely(ForgeDirection.class, rand.nextInt(6));
				if(newDir != moveDir.getOpposite()) {
					this.moveDir = newDir;
					this.moveTimer = 100 + rand.nextInt(200);
				}
				
				if(rand.nextInt(3) == 0 && worldObj.getHeightValue((int) Math.floor(posX), (int) Math.floor(posZ)) < posY - 2) {
					this.moveDir = ForgeDirection.DOWN;
				}
			}
			
			if(homeY < 0) {
				this.homeX = (int) Math.floor(posX);
				this.homeY = (int) Math.floor(posY);
				this.homeZ = (int) Math.floor(posZ);
			}
			
			int range = 50;

			if(posY > homeY + range) this.moveDir = Library.NEG_Y;
			else if(posY < homeY - range) this.moveDir = Library.POS_Y;
			else if(posX > homeX + range) this.moveDir = Library.NEG_X;
			else if(posX < homeX - range) this.moveDir = Library.POS_X;
			else if(posZ > homeZ + range) this.moveDir = Library.NEG_Z;
			else if(posZ < homeZ - range) this.moveDir = Library.POS_Z;
			
			if(this.posY < 0) this.moveDir = ForgeDirection.UP;

			this.motionX = moveDir.offsetX * 0.2;
			this.motionY = moveDir.offsetY * 0.2;
			this.motionZ = moveDir.offsetZ * 0.2;
			
			// break intersecting blocks
			if((this.ticksExisted + this.getEntityId()) % 10 == 0) {
				int x = (int) Math.floor(posX);
				int y = (int) Math.floor(posY + 1.5);
				int z = (int) Math.floor(posZ);
				
				for(int i = x - 1; i <= x + 1; i++) {
					for(int j = y - 1; j <= y + 1; j++) {
						for(int k = z - 1; k <= z + 1; k++) {
							if(j > 0) worldObj.func_147480_a(i, j, k, false);
						}
					}
				}
			}
		}
		
		if((this.ticksExisted + this.getEntityId()) % 100 == 0)
			worldObj.playSoundEffect(posX, posY, posZ, NTMSounds.MASS_SCREAM, 10F, 0.5F);
		if(rand.nextInt(2) == 0)
			worldObj.playSoundEffect(posX, posY, posZ, NTMSounds.ASSEMBLER_STRIKE, 1F, 0.5F + rand.nextFloat() * 0.25F);
		if(rand.nextInt(20) == 0)
			worldObj.playSoundEffect(posX, posY, posZ, "hbm:block.crateBreak", 1F, 0.5F + rand.nextFloat() * 0.25F);
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
		public static final double LIMIT = 1.25D; // size limit (radius)
		
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

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		return new float[] {10_000F, 5F};
	}

	@Override public void onDamageDealt(DamageSource damage, float amount) { }
}