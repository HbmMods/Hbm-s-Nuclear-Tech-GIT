package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.entity.missile.EntityMissileBaseNT;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.MissileFormFactor;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLaunchPadLarge extends TileEntityLaunchPadBase {

	public int formFactor = -1;
	/** Whether the missile has already been placed on the launchpad. Missile will render statically on the pad if true */
	public boolean erected = false;
	/** Whether the missile can be lifted. Missile will not render at all if false and not erected */
	public boolean readyToLoad = false;
	/** Instead of setting erected to true outright, this makes it so that it ties into the delay,
	 * which prevents a jerky transition due to the animation of the erector lagging behind a bit */
	public boolean scheduleErect = false;
	public float lift = 1F;
	public float erector = 90F;
	public float prevLift = 1F;
	public float prevErector = 90F;
	public float syncLift;
	public float syncErector;
	private int sync;
	/** Delay between erector movements */
	public int delay = 20;

	private AudioWrapper audioLift;
	private AudioWrapper audioErector;

	protected boolean liftMoving = false;
	protected boolean erectorMoving = false;

	@Override public boolean isReadyForLaunch() { return this.erected && this.readyToLoad; }
	@Override public double getLaunchOffset() { return 2D; }

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.prevLift = this.lift;
			this.prevErector = this.erector;

			float erectorSpeed = 1.5F;
			float liftSpeed = 0.025F;

			if(this.isMissileValid()) {
				if(slots[0].getItem() instanceof ItemMissile) {
					ItemMissile missile = (ItemMissile) slots[0].getItem();
					this.formFactor = missile.formFactor.ordinal();

					if(missile.formFactor == MissileFormFactor.ATLAS || missile.formFactor == MissileFormFactor.HUGE) {
						erectorSpeed /= 2F;
						liftSpeed /= 2F;
					}
				}

				if(this.erector == 90F && this.lift == 1F) {
					this.readyToLoad = true;
				}
			} else {
				readyToLoad = false;
				erected = false;
				delay = 20;
			}

			if(this.power >= 75_000) {
				if(delay > 0) {
					delay--;

					if(delay < 10 && scheduleErect) {
						this.erected = true;
						this.scheduleErect = false;
					}

					// if there is no missile or the missile isn't ready (i.e. the erector hasn't returned to zero position yet), retract
					if(slots[0] == null || !readyToLoad) {
						//fold back erector
						if(erector < 90F) {
							erector = Math.min(erector + erectorSpeed, 90F);
							if(erector == 90F) delay = 20;
						//extend lift
						} else if(lift < 1F) {
							lift = Math.min(lift + liftSpeed, 1F);
							if(erector == 1F) {
								//if the lift is fully extended, the loading can begin
								readyToLoad = true;
								delay = 20;
							}
						}
					}

				} else {

					//only extend if the erector isn't up yet and the missile can be loaded
					if(!erected && readyToLoad) {
						this.state = this.STATE_LOADING;

						//first, rotate the erector
						if(erector != 0F) {
							erector = Math.max(erector - erectorSpeed, 0F);
							if(erector == 0F) delay = 20;
						//then retract the lift
						} else if(lift > 0) {
							lift = Math.max(lift - liftSpeed, 0F);
							if(lift == 0F) {
								//once the lift is at the bottom, the missile is deployed
								scheduleErect = true;
								delay = 20;
							}
						}
					} else {
						//first, fold back the erector
						if(erector < 90F) {
							erector = Math.min(erector + erectorSpeed, 90F);
							if(erector == 90F) delay = 20;
						//then extend the lift again
						} else if(lift < 1F) {
							lift = Math.min(lift + liftSpeed, 1F);
							if(erector == 1F) {
								//if the lift is fully extended, the loading can begin
								readyToLoad = true;
								delay = 20;
							}
						}
					}
				}
			}

			if(!this.hasFuel() || !this.isMissileValid()) this.state = this.STATE_MISSING;
			if(this.erected && this.canLaunch()) this.state = this.STATE_READY;

			boolean prevLiftMoving = this.liftMoving;
			boolean prevErectorMoving = this.erectorMoving;
			this.liftMoving = false;
			this.erectorMoving = false;
			if(this.prevLift != this.lift) this.liftMoving = true;
			if(this.prevErector != this.erector) this.erectorMoving = true;

			if(prevLiftMoving && !this.liftMoving) worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:door.wgh_stop", 2F, 1F);
			if(prevErectorMoving && !this.erectorMoving) worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:door.garage_stop", 2F, 1F);

		} else {
			this.prevLift = this.lift;
			this.prevErector = this.erector;

			if(this.sync > 0) {
				this.lift = this.lift + ((this.syncLift - this.lift) / (float) this.sync);
				this.erector = this.erector + ((this.syncErector - this.erector) / (float) this.sync);
				--this.sync;
			} else {
				this.lift = this.syncLift;
				this.erector = this.syncErector;
			}

			if(this.liftMoving) {
				if(this.audioLift == null || !this.audioLift.isPlaying()) {
					this.audioLift = MainRegistry.proxy.getLoopedSound("hbm:door.wgh_start", xCoord, yCoord, zCoord, 0.75F, 25F, 1.0F, 5);
					this.audioLift.startSound();
				}
				this.audioLift.keepAlive();
			} else {
				if(this.audioLift != null) {
					this.audioLift.stopSound();
					this.audioLift = null;
				}
			}

			if(this.erectorMoving) {
				if(this.audioErector == null || !this.audioErector.isPlaying()) {
					this.audioErector = MainRegistry.proxy.getLoopedSound("hbm:door.garage_move", xCoord, yCoord, zCoord, 1.5F, 25F, 1.0F, 5);
					this.audioErector.startSound();
				}
				this.audioErector.keepAlive();
			} else {
				if(this.audioErector != null) {
					this.audioErector.stopSound();
					this.audioErector = null;
				}
			}

			if(this.erected && (this.formFactor == MissileFormFactor.HUGE.ordinal() || this.formFactor == MissileFormFactor.ATLAS.ordinal()) && this.tanks[1].getFill() > 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 0F);
				data.setFloat("base", 0.5F);
				data.setFloat("max", 2F);
				data.setInteger("life", 70 + worldObj.rand.nextInt(30));
				data.setDouble("posX", xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.5);
				data.setDouble("posZ", zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.5);
				data.setDouble("posY", yCoord + 2);
				data.setBoolean("noWind", true);
				data.setFloat("alphaMod", 2F);
				data.setFloat("strafe", 0.05F);
				for(int i = 0; i < 3; i++) MainRegistry.proxy.effectNT(data);
			}

			List<EntityMissileBaseNT> entities = worldObj.getEntitiesWithinAABB(EntityMissileBaseNT.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord, zCoord - 0.5, xCoord + 1.5, yCoord + 10, zCoord + 1.5));

			if(!entities.isEmpty()) {
				for(int i = 0; i < 15; i++) {

					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
					if(worldObj.rand.nextBoolean()) dir = dir.getOpposite();
					float moX = (float) (worldObj.rand.nextGaussian() * 0.15F + 0.75) * dir.offsetX;
					float moZ = (float) (worldObj.rand.nextGaussian() * 0.15F + 0.75) * dir.offsetZ;

					NBTTagCompound data = new NBTTagCompound();
					data.setDouble("posX", xCoord + 0.5);
					data.setDouble("posY", yCoord + 0.25);
					data.setDouble("posZ", zCoord + 0.5);
					data.setString("type", "launchSmoke");
					data.setDouble("moX", moX);
					data.setDouble("moY", 0);
					data.setDouble("moZ", moZ);
					MainRegistry.proxy.effectNT(data);
				}
			}
		}

		super.updateEntity();
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeBoolean(this.liftMoving);
		buf.writeBoolean(this.erectorMoving);
		buf.writeBoolean(this.erected);
		buf.writeBoolean(this.readyToLoad);
		buf.writeByte((byte) this.formFactor);
		buf.writeFloat(this.lift);
		buf.writeFloat(this.erector);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		this.liftMoving = buf.readBoolean();
		this.erectorMoving = buf.readBoolean();
		this.erected = buf.readBoolean();
		this.readyToLoad = buf.readBoolean();
		this.formFactor = buf.readByte();
		this.syncLift = buf.readFloat();
		this.syncErector = buf.readFloat();

		if(this.lift != this.syncLift || this.erector != this.syncErector) {
			this.sync = 3;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.erected = nbt.getBoolean("erected");
		this.readyToLoad = nbt.getBoolean("readyToLoad");
		this.lift = nbt.getFloat("lift");
		this.erector = nbt.getFloat("erector");
		this.formFactor = nbt.getInteger("formFactor");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("erected", erected);
		nbt.setBoolean("readyToLoad", readyToLoad);
		nbt.setFloat("lift", lift);
		nbt.setFloat("erector", erector);
		nbt.setInteger("formFactor", formFactor);
	}

	@Override
	public void finalizeLaunch(Entity missile) {
		super.finalizeLaunch(missile);
		this.erected = false;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 5, yCoord, zCoord - 2, Library.POS_X),
				new DirPos(xCoord + 5, yCoord, zCoord + 2, Library.POS_X),
				new DirPos(xCoord - 5, yCoord, zCoord - 2, Library.NEG_X),
				new DirPos(xCoord - 5, yCoord, zCoord + 2, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 5, Library.POS_Z),
				new DirPos(xCoord + 2, yCoord, zCoord + 5, Library.POS_Z),
				new DirPos(xCoord - 2, yCoord, zCoord - 5, Library.NEG_Z),
				new DirPos(xCoord + 2, yCoord, zCoord - 5, Library.NEG_Z)
		};
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 10,
					yCoord,
					zCoord - 10,
					xCoord + 11,
					yCoord + 15,
					zCoord + 11
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
