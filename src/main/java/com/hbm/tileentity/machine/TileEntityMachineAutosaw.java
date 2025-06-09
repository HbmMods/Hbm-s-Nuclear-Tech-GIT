package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockTallPlant.EnumTallFlower;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAutosaw extends TileEntityLoadedBase implements IBufPacketReceiver, IFluidStandardReceiver, IFluidCopiable {

	public static final HashSet<FluidType> acceptedFuels = new HashSet();

	static {
		acceptedFuels.add(Fluids.WOODOIL);
		acceptedFuels.add(Fluids.ETHANOL);
		acceptedFuels.add(Fluids.FISHOIL);
		acceptedFuels.add(Fluids.HEAVYOIL);
	}

	public FluidTank tank;

	public boolean isOn;
	public boolean isSuspended;
	private int forceSkip;
	public float syncYaw;
	public float rotationYaw;
	public float prevRotationYaw;
	public float syncPitch;
	public float rotationPitch;
	public float prevRotationPitch;

	// 0: searching, 1: extending, 2: retracting
	private int state = 0;

	private int turnProgress;

	public float spin;
	public float lastSpin;

	public TileEntityMachineAutosaw() {
		this.tank = new FluidTank(Fluids.WOODOIL, 100);
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(!isSuspended && worldObj.getTotalWorldTime() % 20 == 0) {
				if(tank.getFill() > 0) {
					tank.setFill(tank.getFill() - 1);
					this.isOn = true;
				} else {
					this.isOn = false;
				}

				this.subscribeToAllAround(tank.getTankType(), this);
			}

			if(isOn && !isSuspended) {
				Vec3 pivot = Vec3.createVectorHelper(xCoord + 0.5, yCoord + 1.75, zCoord + 0.5);
				Vec3 upperArm = Vec3.createVectorHelper(0, 0, -4);
				upperArm.rotateAroundX((float) Math.toRadians(80 - rotationPitch));
				upperArm.rotateAroundY(-(float) Math.toRadians(rotationYaw));
				Vec3 lowerArm = Vec3.createVectorHelper(0, 0, -4);
				lowerArm.rotateAroundX((float) -Math.toRadians(80 - rotationPitch));
				lowerArm.rotateAroundY(-(float) Math.toRadians(rotationYaw));
				Vec3 armTip = Vec3.createVectorHelper(0, 0, -2);
				armTip.rotateAroundY(-(float) Math.toRadians(rotationYaw));

				double cX = pivot.xCoord + upperArm.xCoord + lowerArm.xCoord + armTip.xCoord;
				double cY = pivot.yCoord;
				double cZ = pivot.zCoord + upperArm.zCoord + lowerArm.zCoord + armTip.zCoord;

				List<EntityLivingBase> affected = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(cX - 1, cY - 0.25, cZ - 1, cX + 1, cY + 0.25, cZ + 1));

				for(EntityLivingBase e : affected) {
					if(e.isEntityAlive() && e.attackEntityFrom(ModDamageSource.turbofan, 100)) {
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

				if(state == 0) {

					this.rotationYaw += 1;

					if(this.rotationYaw >= 360) {
						this.rotationYaw -= 360;
					}

					if(forceSkip > 0) {
						forceSkip--;
					} else {
						final double CUT_ANGLE = Math.toRadians(5);
						double rotationYawRads = Math.toRadians((rotationYaw + 270) % 360);

						outer:
						for(int dx = -9; dx <= 9; dx++) {
							for(int dz = -9; dz <= 9; dz++) {
								int sqrDst = dx * dx + dz * dz;

								if(sqrDst <= 4 || sqrDst > 81)
									continue;
								
								double angle = Math.atan2(dz, dx);
								double relAngle = Math.abs(angle - rotationYawRads);
								relAngle = Math.abs((relAngle + Math.PI) % (2 * Math.PI) - Math.PI);

								if(relAngle > CUT_ANGLE)
									continue;

								int x = xCoord + dx;
								int y = yCoord + 1;
								int z = zCoord + dz;

								Block b = worldObj.getBlock(x, y, z);
								if(!(b.getMaterial() == Material.wood || b.getMaterial() == Material.leaves || b.getMaterial() == Material.plants))
									continue;

								int meta = worldObj.getBlockMetadata(x, y, z);
								if(shouldIgnore(worldObj, x, y, z, b, meta))
									continue;
								
								state = 1;
								break outer;
							}
						}
					}
				}

				int hitY = (int) Math.floor(cY);
				int hitX0 = (int) Math.floor(cX - 0.5);
				int hitZ0 = (int) Math.floor(cZ - 0.5);
				int hitX1 = (int) Math.floor(cX + 0.5);
				int hitZ1 = (int) Math.floor(cZ + 0.5);

				this.tryInteract(hitX0, hitY, hitZ0);
				this.tryInteract(hitX1, hitY, hitZ0);
				this.tryInteract(hitX0, hitY, hitZ1);
				this.tryInteract(hitX1, hitY, hitZ1);

				if(state == 1) {
					this.rotationPitch += 2;

					if(this.rotationPitch > 80) {
						this.rotationPitch = 80;
						state = 2;
					}
				}

				if(state == 2) {
					this.rotationPitch -= 2;

					if(this.rotationPitch <= 0) {
						this.rotationPitch = 0;
						state = 0;
					}
				}
			}

			networkPackNT(100);
		} else {

			this.lastSpin = this.spin;

			if(isOn && !isSuspended) {
				this.spin += 15F;

				Vec3 vec = Vec3.createVectorHelper(0.625, 0, 1.625);
				vec.rotateAroundY(-(float) Math.toRadians(rotationYaw));

				worldObj.spawnParticle("smoke", xCoord + 0.5 + vec.xCoord, yCoord + 2.0625, zCoord + 0.5 + vec.zCoord, 0, 0, 0);
			}

			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}

			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;

			if(this.turnProgress > 0) {
				double d0 = MathHelper.wrapAngleTo180_double(this.syncYaw - (double) this.rotationYaw);
				double d1 = MathHelper.wrapAngleTo180_double(this.syncPitch - (double) this.rotationPitch);
				this.rotationYaw = (float) ((double) this.rotationYaw + d0 / (double) this.turnProgress);
				this.rotationPitch = (float) ((double) this.rotationPitch + d1 / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.rotationYaw = this.syncYaw;
				this.rotationPitch = this.syncPitch;
			}
		}
	}

	/** Anything additionally that the detector nor the blades should pick up on, like non-mature willows */
	public static boolean shouldIgnore(World world, int x, int y, int z, Block b, int meta) {
		if(b == ModBlocks.plant_tall) {
			return meta == EnumTallFlower.CD2.ordinal() + 8 || meta == EnumTallFlower.CD3.ordinal() + 8;
		}

		if((b instanceof IGrowable)) {
			return ((IGrowable) b).func_149851_a(world, x, y, z, world.isRemote);
		}

		return false;
	}

	protected void tryInteract(int x, int y, int z) {

		Block b = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);

		if(!shouldIgnore(worldObj, x, y, z, b, meta)) {
			if(b.getMaterial() == Material.leaves || b.getMaterial() == Material.plants) {
				cutCrop(x, y, z);
			} else if(b.getMaterial() == Material.wood) {
				fellTree(x, y, z);
				if(state == 1) {
					state = 2;
				}
			}
		}

		// Return when hitting a wall
		if(state == 1 && worldObj.getBlock(x, y, z).isNormalCube(worldObj, x, y, z)) {
			state = 2;
			forceSkip = 5;
		}
	}

	protected void cutCrop(int x, int y, int z) {

		Block soil = worldObj.getBlock(x, y - 1, z);

		Block b = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);

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

				float delta = 0.7F;
				double dx = (double)(worldObj.rand.nextFloat() * delta) + (double)(1.0F - delta) * 0.5D;
				double dy = (double)(worldObj.rand.nextFloat() * delta) + (double)(1.0F - delta) * 0.5D;
				double dz = (double)(worldObj.rand.nextFloat() * delta) + (double)(1.0F - delta) * 0.5D;

				EntityItem entityItem = new EntityItem(worldObj, x + dx, y + dy, z + dz, drop);
				entityItem.delayBeforeCanPickup = 10;
				worldObj.spawnEntityInWorld(entityItem);
			}
		}

		worldObj.setBlock(x, y, z, replacementBlock, replacementMeta, 3);
	}

	protected void fellTree(int x, int y, int z) {

		if(worldObj.getBlock(x, y - 1, z).getMaterial() == Material.wood) {
			y--;
			if(worldObj.getBlock(x, y - 2, z).getMaterial() == Material.wood) {
				y--;
			}
		}

		int meta = -1;

		for(int i = y; i < y + 10; i++) {

			int[][] dir = new int[][] {{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};

			for(int[] d : dir) {
				Block b = worldObj.getBlock(x + d[0], i, z + d[1]);

				if(b.getMaterial() == Material.wood) {
					worldObj.func_147480_a(x + d[0], i, z + d[1], true);
				} else if(b instanceof BlockLeaves) {
					meta = worldObj.getBlockMetadata(x + d[0], i, z + d[1]) & 3;
					worldObj.func_147480_a(x + d[0], i, z + d[1], true);
				}
			}
		}

		if(meta >= 0) {
			if(Blocks.sapling.canPlaceBlockAt(worldObj, x, y, z)) {
				worldObj.setBlock(x, y, z, Blocks.sapling, meta, 3);
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.isOn);
		buf.writeBoolean(this.isSuspended);
		buf.writeFloat(this.rotationYaw);
		buf.writeFloat(this.rotationPitch);
		this.tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.isOn = buf.readBoolean();
		this.isSuspended = buf.readBoolean();
		this.syncYaw = buf.readFloat();
		this.syncPitch = buf.readFloat();
		this.turnProgress = 3; //use 3-ply for extra smoothness
		this.tank.deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.isOn = nbt.getBoolean("isOn");
		this.isSuspended = nbt.getBoolean("isSuspended");
		this.forceSkip = nbt.getInteger("skip");
		this.rotationYaw = nbt.getFloat("yaw");
		this.rotationPitch = nbt.getFloat("pitch");
		this.state = nbt.getInteger("state");
		this.tank.readFromNBT(nbt, "t");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isOn", this.isOn);
		nbt.setBoolean("isSuspended", this.isSuspended);
		nbt.setInteger("skip", this.forceSkip);
		nbt.setFloat("yaw", this.rotationYaw);
		nbt.setFloat("pitch", this.rotationPitch);
		nbt.setInteger("state", this.state);
		tank.writeToNBT(nbt, "t");
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 12,
					yCoord,
					zCoord - 12,
					xCoord + 13,
					yCoord + 10,
					zCoord + 13
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	@Override
	public FluidTank getTankToPaste() {
		return tank;
	}
}
