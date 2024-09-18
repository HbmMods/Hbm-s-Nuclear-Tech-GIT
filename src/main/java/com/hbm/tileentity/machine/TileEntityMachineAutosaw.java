package com.hbm.tileentity.machine;

import java.util.HashSet;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockTallPlant.EnumTallFlower;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class TileEntityMachineAutosaw extends TileEntityLoadedBase implements INBTPacketReceiver, IFluidStandardReceiver, IFluidCopiable {
	
	public static final HashSet<FluidType> acceptedFuels = new HashSet();
	
	static {
		acceptedFuels.add(Fluids.WOODOIL);
		acceptedFuels.add(Fluids.ETHANOL);
		acceptedFuels.add(Fluids.FISHOIL);
		acceptedFuels.add(Fluids.HEAVYOIL);
	}
	
	public FluidTank tank;
	
	public boolean isOn;
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
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				if(tank.getFill() > 0) {
					tank.setFill(tank.getFill() - 1);
					this.isOn = true;
				} else {
					this.isOn = false;
				}
				
				this.subscribeToAllAround(tank.getTankType(), this);
			}

			if(isOn) {
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
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, e.posX, e.posY + e.height * 0.5, e.posZ), new TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 50));
					}
				}
				
				if(state == 0) {
					
					this.rotationYaw += 1;
					
					if(this.rotationYaw >= 360) {
						this.rotationYaw -= 360;
					}
					
					Vec3 grace = Vec3.createVectorHelper(0, 0, -3.5);
					grace.rotateAroundY(-(float) Math.toRadians(rotationYaw));
					grace.xCoord += pivot.xCoord;
					grace.yCoord += pivot.yCoord;
					grace.zCoord += pivot.zCoord;
					
					Vec3 detector = Vec3.createVectorHelper(0, 0, -9);
					detector.rotateAroundY(-(float) Math.toRadians(rotationYaw));
					detector.xCoord += pivot.xCoord;
					detector.yCoord += pivot.yCoord;
					detector.zCoord += pivot.zCoord;
					MovingObjectPosition pos = worldObj.func_147447_a(grace, detector, false, false, false);
					
					if(pos != null && pos.typeOfHit == pos.typeOfHit.BLOCK) {
						
						Block b = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
						
						if(b.getMaterial() == Material.wood || b.getMaterial() == Material.leaves || b.getMaterial() == Material.plants) {
							
							int meta = worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);
							if(!shouldIgnore(b, meta)) {
								state = 1;
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
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", isOn);
			data.setFloat("yaw", this.rotationYaw);
			data.setFloat("pitch", this.rotationPitch);
			tank.writeToNBT(data, "t");
			INBTPacketReceiver.networkPack(this, data, 100);
		} else {
			
			this.lastSpin = this.spin;
			
			if(isOn) {
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
	public static boolean shouldIgnore(Block b, int meta) {
		if(b == ModBlocks.plant_tall) {
			return meta == EnumTallFlower.CD2.ordinal() + 8 || meta == EnumTallFlower.CD3.ordinal() + 8;
		}
		
		return false;
	}
	
	protected void tryInteract(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		
		if(shouldIgnore(b, meta)) {
			return;
		}
		
		if(b.getMaterial() == Material.leaves || b.getMaterial() == Material.plants) {
			worldObj.func_147480_a(x, y, z, true);
			return;
		}
		
		if(b.getMaterial() == Material.wood) {
			fellTree(x, y, z);
			if(state == 1) {
				state = 2;
			}
		}
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
	public void networkUnpack(NBTTagCompound nbt) {
		this.isOn = nbt.getBoolean("isOn");
		this.syncYaw = nbt.getFloat("yaw");
		this.syncPitch = nbt.getFloat("pitch");
		this.turnProgress = 3; //use 3-ply for extra smoothness
		this.tank.readFromNBT(nbt, "t");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.isOn = nbt.getBoolean("isOn");
		this.rotationYaw = nbt.getFloat("yaw");
		this.rotationPitch = nbt.getFloat("pitch");
		this.state = nbt.getInteger("state");
		this.tank.readFromNBT(nbt, "t");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isOn", this.isOn);
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
