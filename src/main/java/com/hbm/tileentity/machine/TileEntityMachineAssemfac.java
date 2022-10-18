package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAssemfac extends TileEntityMachineAssemblerBase implements IFluidStandardTransceiver, IFluidAcceptor, IFluidSource {
	
	public AssemblerArm[] arms;

	public FluidTank water;
	public FluidTank steam;

	public TileEntityMachineAssemfac() {
		super(14 * 8 + 4 + 1); //8 assembler groups with 14 slots, 4 upgrade slots, 1 battery slot
		
		arms = new AssemblerArm[6];
		for(int i = 0; i < arms.length; i++) {
			arms[i] = new AssemblerArm(i % 3 == 1 ? 1 : 0); //the second of every group of three becomes a welder
		}

		water = new FluidTank(Fluids.WATER, 64_000, 0);
		steam = new FluidTank(Fluids.SPENTSTEAM, 64_000, 1);
	}

	@Override
	public String getName() {
		return "container.assemfac";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				this.updateConnections();
			}
			
			this.speed = 100;
			this.consumption = 100;
			
			UpgradeManager.eval(slots, 1, 4);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 6);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			this.speed -= speedLevel * 15;
			this.consumption += speedLevel * 300;
			this.speed += powerLevel * 5;
			this.consumption -= powerLevel * 30;
			this.speed /= (overLevel + 1);
			this.consumption *= (overLevel + 1);
			
			for(DirPos pos : getConPos()) {
				this.sendFluid(steam.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(steam.getFill() > 0) {
				this.fillFluidInit(steam.getTankType());
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setIntArray("progress", this.progress);
			data.setIntArray("maxProgress", this.maxProgress);
			data.setBoolean("isProgressing", isProgressing);
			
			water.writeToNBT(data, "w");
			steam.writeToNBT(data, "s");
			
			this.networkPack(data, 150);
			
		} else {
			
			for(AssemblerArm arm : arms) {
				arm.updateInterp();
				if(isProgressing) {
					arm.updateArm();
				}
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getIntArray("progress");
		this.maxProgress = nbt.getIntArray("maxProgress");
		this.isProgressing = nbt.getBoolean("isProgressing");
		
		water.readFromNBT(nbt, "w");
		steam.readFromNBT(nbt, "s");
	}
	
	private int getWaterRequired() {
		return 1000 / this.speed;
	}

	@Override
	protected boolean canProcess(int index) {
		return super.canProcess(index) && this.water.getFill() >= getWaterRequired() && this.steam.getFill() + getWaterRequired() <= this.steam.getMaxFill();
	}

	@Override
	protected void process(int index) {
		super.process(index);
		this.water.setFill(this.water.getFill() - getWaterRequired());
		this.steam.setFill(this.steam.getFill() + getWaterRequired());
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(water.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord - dir.offsetX * 3 + rot.offsetX * 5, yCoord, zCoord - dir.offsetZ * 3 + rot.offsetZ * 5, rot),
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX * 5, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 5, rot),
				new DirPos(xCoord - dir.offsetX * 3 - rot.offsetX * 4, yCoord, zCoord - dir.offsetZ * 3 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX * 4, yCoord, zCoord + dir.offsetZ * 2 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 5 + rot.offsetX * 3, yCoord, zCoord - dir.offsetZ * 5 + rot.offsetZ * 3, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 5 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 5 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 4 + rot.offsetX * 3, yCoord, zCoord + dir.offsetZ * 4 + rot.offsetZ * 3, dir),
				new DirPos(xCoord + dir.offsetX * 4 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 4 - rot.offsetZ * 2, dir)
		};
	}
	
	public static class AssemblerArm {
		public double[] angles = new double[4];
		public double[] prevAngles = new double[4];
		public double[] targetAngles = new double[4];
		public double[] speed = new double[4];
		
		Random rand = new Random();
		
		int actionMode;
		ArmActionState state;
		int actionDelay = 0;
		
		public AssemblerArm(int actionMode) {
			this.actionMode = actionMode;
			
			if(this.actionMode == 0) {
				speed[0] = 15;	//Pivot
				speed[1] = 15;	//Arm
				speed[2] = 15;	//Piston
				speed[3] = 0.5;	//Striker
			} else if(this.actionMode == 1) {
				speed[0] = 3;		//Pivot
				speed[1] = 3;		//Arm
				speed[2] = 1;		//Piston
				speed[3] = 0.125;	//Striker
			}
			
			state = ArmActionState.ASSUME_POSITION;
			chooseNewArmPoistion();
			actionDelay = rand.nextInt(20);
		}
		
		public void updateArm() {
			
			if(actionDelay > 0) {
				actionDelay--;
				return;
			}
			
			switch(state) {
			//Move. If done moving, set a delay and progress to EXTEND
			case ASSUME_POSITION:
				if(move()) {
					if(this.actionMode == 0) {
						actionDelay = 2;
					} else if(this.actionMode == 1) {
						actionDelay = 10;
					}
					state = ArmActionState.EXTEND_STRIKER;
					targetAngles[3] = 1D;
				}
				break;
			case EXTEND_STRIKER:
				if(move()) {
					if(this.actionMode == 0) {
						state = ArmActionState.RETRACT_STRIKER;
						targetAngles[3] = 0D;
					} else if(this.actionMode == 1) {
						state = ArmActionState.WELD;
						targetAngles[2] -= 20;
						actionDelay = 5 + rand.nextInt(5);
					}
				}
				break;
			case WELD:
				if(move()) {
					state = ArmActionState.RETRACT_STRIKER;
					targetAngles[3] = 0D;
					actionDelay = 10 + rand.nextInt(5);
				}
				break;
			case RETRACT_STRIKER:
				if(move()) {
					if(this.actionMode == 0) {
						actionDelay = 2 + rand.nextInt(5);
					} else if(this.actionMode == 1) {
						actionDelay = 5 + rand.nextInt(3);
					}
					chooseNewArmPoistion();
					state = ArmActionState.ASSUME_POSITION;
				}
				break;
			
			}
		}
		
		public void chooseNewArmPoistion() {
			
			if(this.actionMode == 0) {
				targetAngles[0] = -rand.nextInt(50);		//Pivot
				targetAngles[1] = -targetAngles[0];			//Arm
				targetAngles[2] = rand.nextInt(30) - 15;	//Piston
			} else if(this.actionMode == 1) {
				targetAngles[0] = -rand.nextInt(30) + 10;	//Pivot
				targetAngles[1] = -targetAngles[0];			//Arm
				targetAngles[2] = rand.nextInt(10) + 10;	//Piston
			}
		}
		
		private void updateInterp() {
			for(int i = 0; i < angles.length; i++) {
				prevAngles[i] = angles[i];
			}
		}
		
		/**
		 * @return True when it has finished moving
		 */
		private boolean move() {
			boolean didMove = false;
			
			for(int i = 0; i < angles.length; i++) {
				if(angles[i] == targetAngles[i])
					continue;
				
				didMove = true;
				
				double angle = angles[i];
				double target = targetAngles[i];
				double turn = speed[i];
				double delta = Math.abs(angle - target);
				
				if(delta <= turn) {
					angles[i] = targetAngles[i];
					continue;
				}
				
				if(angle < target) {
					angles[i] += turn;
				} else {
					angles[i] -= turn;
				}
			}
			
			return !didMove;
		}
		
		public static enum ArmActionState {
			ASSUME_POSITION,
			EXTEND_STRIKER,
			WELD,
			RETRACT_STRIKER
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord,
					zCoord - 5,
					xCoord + 5,
					yCoord + 4,
					zCoord + 5
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
	public long getMaxPower() {
		return 10_000_000;
	}

	@Override
	public int getRecipeCount() {
		return 8;
	}

	@Override
	public int getTemplateIndex(int index) {
		return 17 + index * 14;
	}

	@Override
	public int[] getSlotIndicesFromIndex(int index) {
		return new int[] { 5 + index * 14, 16 + index * 14, 18 + index * 14};
	}

	ChunkCoordinates[] inpos;
	ChunkCoordinates[] outpos;
	
	@Override
	public ChunkCoordinates[] getInputPositions() {
		
		if(inpos != null)
			return inpos;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		inpos = new ChunkCoordinates[] {
				new ChunkCoordinates(xCoord + dir.offsetX * 4 - rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 4 - rot.offsetZ * 1),
				new ChunkCoordinates(xCoord - dir.offsetX * 5 + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 5 + rot.offsetZ * 2),
				new ChunkCoordinates(xCoord - dir.offsetX * 2 - rot.offsetX * 4, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ * 4),
				new ChunkCoordinates(xCoord + dir.offsetX * 1 + rot.offsetX * 5, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * 5)
		};
		
		return inpos;
	}

	@Override
	public ChunkCoordinates[] getOutputPositions() {
		
		if(outpos != null)
			return outpos;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		outpos = new ChunkCoordinates[] {
				new ChunkCoordinates(xCoord + dir.offsetX * 4 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 4 + rot.offsetZ * 2),
				new ChunkCoordinates(xCoord - dir.offsetX * 5 - rot.offsetX * 1, yCoord, zCoord - dir.offsetZ * 5 - rot.offsetZ * 1),
				new ChunkCoordinates(xCoord + dir.offsetX * 1 - rot.offsetX * 4, yCoord, zCoord + dir.offsetZ * 1 - rot.offsetZ * 4),
				new ChunkCoordinates(xCoord - dir.offsetX * 2 + rot.offsetX * 5, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * 5)
		};
		
		return outpos;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { steam };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { water };
	}

	@Override
	public void setFillForSync(int fill, int index) { }

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == water.getTankType()) water.setFill(fill);
		if(type == steam.getTankType()) steam.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) { }

	@Override
	public int getFluidFill(FluidType type) {
		if(type == water.getTankType()) return water.getFill();
		if(type == steam.getTankType()) return steam.getFill();
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		for(DirPos pos : getConPos()) {
			this.fillFluid(pos.getX(), pos.getY(), pos.getZ(), this.getTact(), type);
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 2 == 0;
	}

	private List<IFluidAcceptor> list = new ArrayList();
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return type == steam.getTankType() ? this.list : new ArrayList();
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type == water.getTankType() ? water.getMaxFill() : 0;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { water, steam };
	}
}
