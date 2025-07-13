package com.hbm.entity.mob;

import java.util.function.Predicate;

import com.hbm.entity.mob.ai.EntityAIEatBread;
import com.hbm.entity.mob.ai.EntityAIStartFlying;
import com.hbm.entity.mob.ai.EntityAIStopFlying;
import com.hbm.entity.mob.ai.EntityAISwimmingConditional;
import com.hbm.entity.mob.ai.EntityAIWanderConditional;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.tool.ItemFertilizer;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class EntityPigeon extends EntityCreature implements IFlyingCreature, IAnimals {

	public float fallTime;
	public float dest;
	public float prevDest;
	public float prevFallTime;
	public float offGroundTimer = 1.0F;

	public EntityPigeon(World world) {
		super(world);
		Predicate noFlyCondition = x -> { return ((EntityPigeon) x).getFlyingState() == IFlyingCreature.STATE_WALKING; };
		this.tasks.addTask(0, new EntityAIStartFlying(this, this));
		this.tasks.addTask(0, new EntityAIStopFlying(this, this));
		this.tasks.addTask(1, new EntityAISwimmingConditional(this, noFlyCondition));
		this.tasks.addTask(2, new EntityAIEatBread(this, 0.4D));
		this.tasks.addTask(5, new EntityAIWanderConditional(this, 0.2D, noFlyCondition));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.setSize(0.5F, 1.0F);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		if(amount >= this.getMaxHealth() * 2 && !worldObj.isRemote) {
			this.setDead();

			for(int i = 0; i < 10; i++) {
				Vec3 vec = Vec3.createVectorHelper(rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian()).normalize();

				EntityItem feather = new EntityItem(worldObj);
				feather.setEntityItemStack(new ItemStack(Items.feather));
				feather.setPosition(posX + vec.xCoord, posY + height / 2D + vec.yCoord, posZ + vec.zCoord);
				feather.motionX = vec.xCoord * 0.5;
				feather.motionY = vec.yCoord * 0.5;
				feather.motionZ = vec.zCoord * 0.5;
				worldObj.spawnEntityInWorld(feather);
			}

			return true;
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(12, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(13, Byte.valueOf((byte) 0));
	}

	@Override
	protected Item getDropItem() {
		return Items.feather;
	}

	@Override
	protected void func_145780_a(int x, int y, int z, Block block) {
		this.playSound("mob.chicken.step", 0.15F, 1.0F);
	}

	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		int j = this.rand.nextInt(3) + this.rand.nextInt(1 + looting);

		for(int k = 0; k < j; ++k) {
			this.dropItem(Items.feather, 1);
		}

		if(this.isBurning()) {
			this.dropItem(Items.cooked_chicken, this.isFat() ? 3 : 1);
		} else {
			this.dropItem(Items.chicken, this.isFat() ? 3 : 1);
		}
	}

	@Override
	public int getFlyingState() {
		return this.dataWatcher.getWatchableObjectByte(12);
	}

	@Override
	public void setFlyingState(int state) {
		this.dataWatcher.updateObject(12, (byte) state);
	}

	public boolean isFat() {
		return this.dataWatcher.getWatchableObjectByte(13) == 1;
	}

	public void setFat(boolean fat) {
		this.dataWatcher.updateObject(13, (byte) (fat ? 1 : 0));
	}

	protected String getLivingSound() {
		return null;
	}

	protected String getHurtSound() {
		return null;
	}

	protected String getDeathSound() {
		return null;
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();

		if(this.getFlyingState() == this.STATE_FLYING) {
			int height = worldObj.getHeightValue((int) Math.floor(posX), (int) Math.floor(posZ));

			boolean ceil = posY - height > 10;

			this.motionY = this.getRNG().nextGaussian() * 0.05 + (ceil ? 0 : 0.04) + (this.isInWater() ? 0.2 : 0);

			if(onGround) this.motionY = Math.abs(this.motionY) + 0.1D;

			this.moveForward = 1.5F;
			if(this.getRNG().nextInt(20) == 0) this.rotationYaw += this.getRNG().nextGaussian() * 30;

			if(this.isFat() && this.getRNG().nextInt(50) == 0) {

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("type", "sweat");
				nbt.setInteger("count", 3);
				nbt.setInteger("block", Block.getIdFromBlock(Blocks.wool));
				nbt.setInteger("entity", getEntityId());
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(nbt, 0, 0, 0),  new TargetPoint(dimension, posX, posY, posZ, 50));

				int x = (int) Math.floor(posX);
				int y = (int) Math.floor(posY) - 1;
				int z = (int) Math.floor(posZ);
				EntityPlayer player = FakePlayerFactory.getMinecraft((WorldServer)worldObj);

				for(int i = 0; i < 25; i++) {

					if(ItemFertilizer.fertilize(worldObj, x, y - i, z, player, true)) {
						worldObj.playAuxSFX(2005, x, y - i, z, 0);
						break;
					}
				}

				if(this.getRNG().nextInt(10) == 0) {
					this.setFat(false);
				}
			}

		} else if(!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.8D;
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.prevFallTime = this.fallTime;
		this.prevDest = this.dest;
		this.dest = (float) ((double) this.dest + (double) (this.onGround ? -1 : 4) * 0.3D);

		if(this.dest < 0.0F) {
			this.dest = 0.0F;
		}

		if(this.dest > 1.0F) {
			this.dest = 1.0F;
		}

		if(!this.onGround && this.offGroundTimer < 1.0F) {
			this.offGroundTimer = 1.0F;
		}

		this.offGroundTimer = (float) ((double) this.offGroundTimer * 0.9D);

		if(!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.6D;
		}

		this.fallTime += this.offGroundTimer * 2.0F;
	}

	@Override public boolean doesEntityNotTriggerPressurePlate() { return true; }
	@Override protected boolean canTriggerWalking() { return false; }

	@Override protected void fall(float p_70069_1_) { }
	@Override protected void updateFallState(double p_70064_1_, boolean p_70064_3_) { }
}
