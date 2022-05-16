package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAssemfac extends TileEntityMachineAssemblerBase {
	
	public AssemblerArm[] arms;

	public TileEntityMachineAssemfac() {
		super(14 * 8 + 4 + 1); //8 assembler groups with 14 slots, 4 upgrade slots, 1 battery slot
		arms = new AssemblerArm[6];
		for(int i = 0; i < arms.length; i++) {
			arms[i] = new AssemblerArm(i % 3 == 1 ? 1 : 0); //the second of every group of three becomes a welder
		}
	}

	@Override
	public String getName() {
		return "container.assemfac";
	}

	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			for(AssemblerArm arm : arms) {
				arm.updateArm();
			}
		}
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
			updateInterp();
			
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
	public void setPower(long power) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getPower() {
		// TODO Auto-generated method stub
		return 0;
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
}
