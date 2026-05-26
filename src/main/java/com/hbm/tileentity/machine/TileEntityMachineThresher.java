package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.generic.BlockTallPlant;
import com.hbm.blocks.generic.BlockTallPlant.EnumTallFlower;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.main.NTMSounds;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.TrackerUtil;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineThresher extends TileEntityLoadedBase implements IBufPacketReceiver, IFluidStandardReceiverMK2, IFluidCopiable {

	public FluidTank tank;

	public boolean isOn;
	public boolean isSuspended;
	public int delay;

	private int turnProgress;
	public float syncAngle;
	public float angle;
	public float prevAngle;

	// 0: waiting, 1: extending, 2: retracting
	private int state = 0;

	public float spin;
	public float lastSpin;
	private AudioWrapper audio;

	public TileEntityMachineThresher() {
		this.tank = new FluidTank(Fluids.WOODOIL, 100);
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

			if(!isSuspended && worldObj.getTotalWorldTime() % 20 == 0) {
				if(tank.getFill() > 0) {
					tank.setFill(tank.getFill() - 1);
					this.isOn = true;
				} else {
					this.isOn = false;
				}

				trySubscribe(tank.getTankType(), worldObj, xCoord + rot.offsetX, yCoord, zCoord + rot.offsetZ, rot);
				trySubscribe(tank.getTankType(), worldObj, xCoord - rot.offsetX, yCoord, zCoord - rot.offsetZ, rot.getOpposite());
			}

			if(isOn && !isSuspended) {
				
				if(this.state == 0) {
					this.delay--;
					if(delay <= 0) this.state = 1;
				}
				
				if(this.state == 1) {
					this.angle += 82.5F / 60F;
					
					if(this.angle >= 82.5F) {
						this.angle = 82.5F;
						this.state = 2;
					}
				} else if(this.state == 2) {
					this.angle -= 82.5F / 60F;
					
					if(this.angle <= 0F) {
						this.angle = 0F;
						this.state = 0;
						this.delay = 200 + worldObj.rand.nextInt(100);
					}
				}
				
				if(this.angle != 0) {
					Vec3 pivot = Vec3.createVectorHelper(xCoord + 0.5 - dir.offsetX, yCoord + 0.5, zCoord + 0.5 - dir.offsetZ);
					Vec3 upperArm = Vec3.createVectorHelper(-dir.offsetX * 4, 0, -dir.offsetZ * 4);
					Vec3 lowerArm = Vec3.createVectorHelper(-dir.offsetX * 4, 0, -dir.offsetZ * 4);
					if(dir.offsetZ != 0) {
						upperArm.rotateAroundX((float) Math.toRadians(82.5 - angle));
						lowerArm.rotateAroundX((float) -Math.toRadians(82.5 - angle));
					}
					if(dir.offsetX != 0) {
						upperArm.rotateAroundZ((float) Math.toRadians(82.5 - angle));
						lowerArm.rotateAroundZ((float) -Math.toRadians(82.5 - angle));
					}
					Vec3 armTip = Vec3.createVectorHelper(-dir.offsetX * 2, 0, -dir.offsetZ * 2);
	
					double endX = pivot.xCoord + upperArm.xCoord + lowerArm.xCoord + armTip.xCoord;
					double endZ = pivot.zCoord + upperArm.zCoord + lowerArm.zCoord + armTip.zCoord;
					
					//ParticleUtil.spawnGasFlame(worldObj, endX, yCoord, endZ, 0, 0.2, 0);
					
					for(int i = -3; i <= 3; i++) {
						int hitX = (int) Math.floor(endX + rot.offsetX * i);
						int hitZ = (int) Math.floor(endZ + rot.offsetZ * i);
						
						Block b = worldObj.getBlock(hitX, yCoord, hitZ);
						int meta = worldObj.getBlockMetadata(hitX, yCoord, hitZ);
						
						if(b.isNormalCube()) {
							this.state = 2;
							break;
						}
							
						if(b == Blocks.double_plant) {
							// sunflower
							if((meta & 7) == 0 && worldObj.rand.nextInt(250) == 0) {
								worldObj.playAuxSFX(2001, hitX, yCoord, hitZ, Block.getIdFromBlock(b) + (meta << 12));
								this.dropItem(new ItemStack(Blocks.double_plant, 1, 0));
							}
							// tall grass
							if((meta & 7) == 2 && worldObj.rand.nextInt(100) == 0) {
								worldObj.playAuxSFX(2001, hitX, yCoord, hitZ, Block.getIdFromBlock(b) + (meta << 12));
								this.dropItem(new ItemStack(Items.wheat_seeds, 1, 0));
							}
							continue;
						}

						// NTM tall plants like hemp
						if(b instanceof BlockTallPlant) {
							this.cutTallPlant(b, meta, hitX, yCoord, hitZ);
							continue;
						}

						if(b == Blocks.reeds || b == Blocks.cactus) {
							this.cutCane(b, hitX, yCoord, hitZ);
							continue;
						}
						// IGrowable also covers anything that accepts bone
						// meal, so we have to handle actual crops last
						if(b instanceof IGrowable && !this.shouldIgnore(worldObj, hitX, yCoord, hitZ, b, meta)) this.cutCrop(b, meta, hitX, yCoord, hitZ);
					}
					
					List<EntityLivingBase> affected = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(endX, yCoord + 0.5, endZ, endX, yCoord + 0.5, endZ).expand(Math.abs(dir.offsetX * 0.5) + Math.abs(rot.offsetX * 4.5), 0.5, Math.abs(dir.offsetZ * 0.5) + Math.abs(rot.offsetZ * 4.5)));
					
					for(EntityLivingBase e : affected) {
						if(e.isEntityAlive() && e.attackEntityFrom(ModDamageSource.turbofan, 100)) {
							if(e instanceof IMob && !e.isEntityAlive()) this.dropItem(new ItemStack(ModItems.nitra_small));
							worldObj.playSoundEffect(e.posX, e.posY, e.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + worldObj.rand.nextFloat() * 0.2F);
							int count = Math.min((int)Math.ceil(e.getMaxHealth() / 4), 250);
							NBTTagCompound data = new NBTTagCompound();
							data.setString("type", "vanillaburst");
							data.setInteger("count", count * 4);
							data.setDouble("motion", 0.1D);
							data.setString("mode", "blockdust");
							data.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
							PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, e.posX, e.posY + e.height * 0.5, e.posZ), new TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 50));
						}
					}
				}
			}

			networkPackNT(100);
			
		} else {

			this.lastSpin = this.spin;

			if(isOn && !isSuspended) {
				if(this.angle > 0) this.spin += 15F;
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

				worldObj.spawnParticle("smoke", xCoord + 0.5 + dir.offsetX * 0.8125 + rot.offsetX * 0.375, yCoord + 1.5625, zCoord + 0.5 + dir.offsetZ * 0.8125 + rot.offsetZ * 0.375, 0, 0, 0);
			}
			
			if(isOn && !isSuspended && MainRegistry.proxy.me().getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 15 * 15) {
				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}

				audio.keepAlive();
				audio.updateVolume(this.getVolume(1F));
				
			} else {
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}

			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}

			this.prevAngle = this.angle;

			if(this.turnProgress > 0) {
				double d0 = MathHelper.wrapAngleTo180_double(this.syncAngle - (double) this.angle);
				this.angle = (float) ((double) this.angle + d0 / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.angle = this.syncAngle;
			}
		}
	}

	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound(NTMSounds.ENGINE_LOOP, xCoord, yCoord, zCoord, 1.0F, 10F, 1.0F + worldObj.rand.nextFloat() * 0.1F, 10);
	}

	@Override
	public void onChunkUnload() {
		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	public static boolean shouldIgnore(World world, int x, int y, int z, Block b, int meta) {
		
		if((b instanceof IGrowable)) {
			return ((IGrowable) b).func_149851_a(world, x, y, z, world.isRemote);
		}
		return false;
	}
	
	protected void cutTallPlant(Block b, int meta, int x, int y, int z) {
		
		// if we hit the lower block, shift focus one block up
		if(meta <= 7) {
			y++;
			// if it's a lower block and the block above isn't the same, cancel
			if(worldObj.getBlock(x, y, z) != b) return;
			meta = worldObj.getBlockMetadata(x, y, z);
		}
		
		// ignore immature willow
		if(meta == EnumTallFlower.CD2.ordinal() + 8 || meta == EnumTallFlower.CD3.ordinal() + 8) return;

		worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(b) + (meta << 12));
		ArrayList<ItemStack> drops = b.getDrops(worldObj, x, y, z, meta, 0);
		for(ItemStack drop : drops) dropItem(drop);
		worldObj.setBlock(x, y, z, Blocks.air);
	}
	
	/** Removes the to two blocks from a three block crop like cacti and sugar cane */
	protected void cutCane(Block target, int x, int y, int z) {
		
		// people may be inclined to incorrectly place this thing one block above
		// the intended operating level, so we compensate for that
		int offset = worldObj.getBlock(x, y - 1, z) == target ? -1 : 0;
		
		// top to bottom
		for(int i = 2 + offset; i > 0 + offset; i--) {
			Block b = worldObj.getBlock(x, y + i, z);
			int meta = worldObj.getBlockMetadata(x, y + i, z);
			worldObj.playAuxSFX(2001, x, y + i, z, Block.getIdFromBlock(b) + (meta << 12));
			ArrayList<ItemStack> drops = b.getDrops(worldObj, x, y + i, z, meta, 0);
			for(ItemStack drop : drops) dropItem(drop);
			worldObj.setBlock(x, y + i, z, Blocks.air);
		}
	}

	/** Harvests and re-plants crops like wheat */
	protected void cutCrop(Block b, int meta, int x, int y, int z) {

		Block soil = worldObj.getBlock(x, y - 1, z);

		worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(b) + (meta << 12));

		Block replacementBlock = Blocks.air;
		int replacementMeta = 0;

		if (!worldObj.isRemote && !worldObj.restoringBlockSnapshots) {
			ArrayList<ItemStack> drops = b.getDrops(worldObj, x, y, z, meta, 0);
			boolean replanted = false;

			for (ItemStack drop : drops) {
				if (!replanted && drop.getItem() instanceof IPlantable) {
					IPlantable seed = (IPlantable) drop.getItem();

					if(soil.canSustainPlant(worldObj, x, y - 1, z, ForgeDirection.UP, seed)) {
						replacementBlock = seed.getPlant(worldObj, x, y, z);
						replacementMeta = seed.getPlantMetadata(worldObj, x, y, z);
						replanted = true;
						drop.stackSize -= 1;
					}
				}
				
				dropItem(drop);
			}

			// Apparently, until 1.14 full-grown wheat could sometimes drop no seeds at all
			// This is a quick and dirty workaround for that.
			if (b == Blocks.wheat && !replanted) {
				replacementBlock = b;
				replacementMeta = 0;
				replanted = true;
			}
		}

		worldObj.setBlock(x, y, z, replacementBlock, replacementMeta, 3);
	}
	
	protected void dropItem(ItemStack drop) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		double spawnX = xCoord + 0.5 - dir.offsetX * 0.75;
		double spawnZ = zCoord + 0.5 - dir.offsetZ * 0.75;

		EntityItem entityItem = new EntityItem(worldObj, spawnX, yCoord, spawnZ, drop);
		entityItem.delayBeforeCanPickup = 10;
		worldObj.spawnEntityInWorld(entityItem);
		
		entityItem.motionX = dir.offsetX * -0.2 + 0.2;
		entityItem.motionZ = dir.offsetZ * -0.2;
		EntityTrackerEntry entry = TrackerUtil.getTrackerEntry((WorldServer) worldObj, entityItem.getEntityId());
		entry.func_151259_a(new S12PacketEntityVelocity(entityItem.getEntityId(), entityItem.motionX, entityItem.motionY, entityItem.motionZ));
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.isOn);
		buf.writeBoolean(this.isSuspended);
		buf.writeFloat(this.angle);
		this.tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.isOn = buf.readBoolean();
		this.isSuspended = buf.readBoolean();
		this.syncAngle = buf.readFloat();
		this.turnProgress = 3; //use 3-ply for extra smoothness
		this.tank.deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.isOn = nbt.getBoolean("isOn");
		this.isSuspended = nbt.getBoolean("isSuspended");
		this.angle = nbt.getFloat("angle");
		this.state = nbt.getInteger("state");
		this.tank.readFromNBT(nbt, "t");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isOn", this.isOn);
		nbt.setBoolean("isSuspended", this.isSuspended);
		nbt.setFloat("angle", this.angle);
		nbt.setInteger("state", this.state);
		tank.writeToNBT(nbt, "t");
	}

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {tank}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tank}; }

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 10,
					yCoord,
					zCoord - 10,
					xCoord + 11,
					yCoord + 7,
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
	
	@Override public FluidTank getTankToPaste() { return tank; }
}
