package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerAssemfac;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIAssemfac;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAssemfac extends TileEntityMachineAssemblerBase implements IFluidStandardTransceiver, IUpgradeInfoProvider, IFluidCopiable {
	
	public AssemblerArm[] arms;

	public FluidTank water;
	public FluidTank steam;

	public TileEntityMachineAssemfac() {
		super(14 * 8 + 4 + 1); //8 assembler groups with 14 slots, 4 upgrade slots, 1 battery slot
		
		arms = new AssemblerArm[6];
		for(int i = 0; i < arms.length; i++) {
			arms[i] = new AssemblerArm(i % 3 == 1 ? 1 : 0); //the second of every group of three becomes a welder
		}

		water = new FluidTank(Fluids.WATER, 64_000);
		steam = new FluidTank(Fluids.SPENTSTEAM, 64_000);
	}

	@Override
	public String getName() {
		return "container.assemfac";
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 1 && i <= 4 && stack.getItem() instanceof ItemMachineUpgrade) {
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
		}
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
				this.sendFluid(steam, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			this.networkPackNT(150);
			
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
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		for(int i = 0; i < getRecipeCount(); i++) {
			buf.writeInt(progress[i]);
			buf.writeInt(maxProgress[i]);
		}
		buf.writeBoolean(isProgressing);
		water.serialize(buf);
		steam.serialize(buf);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		for(int i = 0; i < getRecipeCount(); i++) {
			progress[i] = buf.readInt();
			maxProgress[i] = buf.readInt();
		}
		isProgressing = buf.readBoolean();
		water.deserialize(buf);
		steam.deserialize(buf);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		
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

	DirPos[] inpos;
	DirPos[] outpos;
	
	@Override
	public DirPos[] getInputPositions() {
		
		if(inpos != null)
			return inpos;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		inpos = new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 4 - rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 4 - rot.offsetZ * 1, dir),
				new DirPos(xCoord - dir.offsetX * 5 + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 5 + rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX * 4, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 1 + rot.offsetX * 5, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * 5, rot)
		};
		
		return inpos;
	}

	@Override
	public DirPos[] getOutputPositions() {
		
		if(outpos != null)
			return outpos;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		outpos = new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 4 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 4 + rot.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 5 - rot.offsetX * 1, yCoord, zCoord - dir.offsetZ * 5 - rot.offsetZ * 1, dir.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 1 - rot.offsetX * 4, yCoord, zCoord + dir.offsetZ * 1 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX * 5, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * 5, rot)
		};
		
		return outpos;
	}

	@Override
	public int getPowerSlot() {
		return 0;
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
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { water, steam };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerAssemfac(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIAssemfac(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER || type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_assemfac));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 15) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 300) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 30) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_DELAY, "+" + (level * 5) + "%"));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public int getMaxLevel(UpgradeType type) {
		if(type == UpgradeType.SPEED) return 6;
		if(type == UpgradeType.POWER) return 3;
		if(type == UpgradeType.OVERDRIVE) return 12;
		return 0;
	}

	@Override
	public FluidTank getTankToPaste() {
		return null;
	}
}
