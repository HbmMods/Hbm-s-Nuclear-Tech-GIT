package com.hbm.entity.projectile;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntitySawblade extends EntityThrowableInterp {

	public EntitySawblade(World world) {
		super(world);
		this.setSize(1F, 1F);
	}

	public EntitySawblade(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.setSize(1F, 1F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(10, new Integer(0));
		this.dataWatcher.addObject(11, new Integer(0));
	}

	public EntitySawblade setOrientation(int rot) {
		this.dataWatcher.updateObject(10, rot);
		return this;
	}

	public int getOrientation() {
		return this.dataWatcher.getWatchableObjectInt(10);
	}

	public int getMeta() {
		return this.dataWatcher.getWatchableObjectInt(11);
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!worldObj.isRemote) {

			if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.sawblade)))
				this.setDead();

			player.inventoryContainer.detectAndSendChanges();
		}

		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {

		if(worldObj != null && mop != null && mop.typeOfHit == MovingObjectType.ENTITY && mop.entityHit.isEntityAlive()) {
			Entity e = mop.entityHit;
			e.attackEntityFrom(ModDamageSource.rubble, 1000);
			if(!e.isEntityAlive() && e instanceof EntityLivingBase) {
				NBTTagCompound vdat = new NBTTagCompound();
				vdat.setString("type", "giblets");
				vdat.setInteger("ent", e.getEntityId());
				vdat.setInteger("cDiv", 5);
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(vdat, e.posX, e.posY + e.height * 0.5, e.posZ), new TargetPoint(e.dimension, e.posX, e.posY + e.height * 0.5, e.posZ, 150));

				worldObj.playSoundEffect(e.posX, e.posY, e.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + worldObj.rand.nextFloat() * 0.2F);
			}
		}

		if(this.ticksExisted > 1 && worldObj != null && mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {

			int orientation = this.dataWatcher.getWatchableObjectInt(10);

			if(orientation < 6) {

				if(Vec3.createVectorHelper(motionX, motionY, motionZ).lengthVector() < 0.75) {
					this.dataWatcher.updateObject(10, orientation + 6);
					orientation += 6;
				} else {
					ForgeDirection side = ForgeDirection.getOrientation(mop.sideHit);
					this.motionX *= 1 - (Math.abs(side.offsetX) * 2);
					this.motionY *= 1 - (Math.abs(side.offsetY) * 2);
					this.motionZ *= 1 - (Math.abs(side.offsetZ) * 2);
					worldObj.createExplosion(this, posX, posY, posZ, 3F, false);

					if(worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getExplosionResistance(this) < 50) {
						worldObj.func_147480_a(mop.blockX, mop.blockY, mop.blockZ, false);
					}
				}
			}

			if(orientation >= 6) {
				this.motionX = 0;
				this.motionY = 0;
				this.motionZ = 0;
				this.inGround = true;
			}
		}
	}

	@Override
	public void onUpdate() {

		if(!worldObj.isRemote) {
			int orientation = this.dataWatcher.getWatchableObjectInt(10);
			if(orientation >= 6 && !this.inGround) {
				this.dataWatcher.updateObject(10, orientation - 6);
			}
		}

		super.onUpdate();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	@Override
	public double getGravityVelocity() {
		return inGround ? 0 : 0.03D;
	}

	@Override
	protected int groundDespawn() {
		return 0;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("rot", this.getOrientation());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setOrientation(nbt.getInteger("rot"));
	}
}
