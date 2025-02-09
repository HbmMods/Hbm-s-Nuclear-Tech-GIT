package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.container.ContainerMachineRotaryFurnace;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineRotaryFurnace;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.RotaryFurnaceRecipes;
import com.hbm.inventory.recipes.RotaryFurnaceRecipes.RotaryFurnaceRecipe;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.IConditionalInvAccess;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.CrucibleUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineRotaryFurnace extends TileEntityMachinePolluting implements IFluidStandardTransceiver, IGUIProvider, IFluidCopiable, IConditionalInvAccess {
	
	public FluidTank[] tanks;
	public boolean isProgressing;
	public float progress;
	public int burnTime;
	public int maxBurnTime;
	public int steamUsed = 0;
	public boolean isVenting;
	public MaterialStack output;
	public static final int maxOutput = MaterialShapes.BLOCK.q(16);
	
	public int anim;
	public int lastAnim;

	public TileEntityMachineRotaryFurnace() {
		super(5, 50);
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(Fluids.NONE, 16_000);
		tanks[1] = new FluidTank(Fluids.STEAM, 4_000);
		tanks[2] = new FluidTank(Fluids.SPENTSTEAM, 40);
	}

	@Override
	public String getName() {
		return "container.machineRotaryFurnace";
	}

	@Override
	public void updateEntity() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		if(!worldObj.isRemote) {
			
			tanks[0].setType(3, slots);

			for(DirPos pos : getSteamPos()) {
				this.trySubscribe(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(tanks[2].getFill() > 0) this.sendFluid(tanks[2], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			if(tanks[0].getTankType() != Fluids.NONE) for(DirPos pos : getFluidPos()) {
				this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(smoke.getFill() > 0) this.sendFluid(smoke, worldObj, xCoord + rot.offsetX, yCoord + 5, zCoord + rot.offsetZ, Library.POS_Y);
			
			if(this.output != null) {
				
				int prev = this.output.amount;
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack leftover = CrucibleUtil.pourSingleStack(worldObj, xCoord + 0.5D + rot.offsetX * 2.875D, yCoord + 1.25D, zCoord + 0.5D + rot.offsetZ * 2.875D, 6, true, this.output, MaterialShapes.INGOT.q(1), impact);
				this.output = leftover;
				
				if(prev != this.output.amount) {
					this.output = leftover;
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", leftover.material.moltenColor);
					data.setByte("dir", (byte) rot.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, yCoord + 1 - (float) (Math.ceil(impact.yCoord) - 1.125)));
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5D + rot.offsetX * 2.875D, yCoord + 0.75, zCoord + 0.5D + rot.offsetZ * 2.875D), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 50));
				}
				
				if(output.amount <= 0) this.output = null;
			}
			
			RotaryFurnaceRecipe recipe = RotaryFurnaceRecipes.getRecipe(slots[0], slots[1], slots[2]);
			this.isProgressing = false;
			
			if(recipe != null) {
				
				if(this.burnTime <= 0 && slots[4] != null && TileEntityFurnace.isItemFuel(slots[4])) {
					this.maxBurnTime = this.burnTime = TileEntityFurnace.getItemBurnTime(slots[4]) / 2;
					this.decrStackSize(4, 1);
					this.markChanged();
				}
				
				if(this.canProcess(recipe)) {
					this.progress += 1F / recipe.duration;
					tanks[1].setFill(tanks[1].getFill() - recipe.steam);
					steamUsed += recipe.steam;
					this.isProgressing = true;
					
					if(this.progress >= 1F) {
						this.progress -= 1F;
						this.consumeItems(recipe);
						
						if(this.output == null) {
							this.output = recipe.output.copy();
						} else {
							this.output.amount += recipe.output.amount;
						}
						this.markDirty();
					}
					
				} else {
					this.progress = 0;
				}
				
				if(this.steamUsed >= 100) {
					int steamReturn = this.steamUsed / 100;
					int canReturn = tanks[2].getMaxFill() - tanks[2].getFill();
					int doesReturn = Math.min(steamReturn, canReturn);
					this.steamUsed -= doesReturn * 100;
					tanks[2].setFill(tanks[2].getFill() + doesReturn);
				}
				
			} else {
				this.progress = 0;
			}
			
			this.isVenting = false;
			if(this.burnTime > 0) {
				this.pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND / 10F);
				this.burnTime--;
			}
			
			this.networkPackNT(50);
			
		} else {
			
			if(this.burnTime > 0 && MainRegistry.proxy.me().getDistance(xCoord, yCoord, zCoord) < 25) {
				Random rand = worldObj.rand;
				worldObj.spawnParticle("flame", xCoord + 0.5 + dir.offsetX * 0.5 + rot.offsetX + rand.nextGaussian() * 0.25, yCoord + 0.375, zCoord + 0.5 + dir.offsetZ * 0.5 + rot.offsetZ + rand.nextGaussian() * 0.25, 0, 0, 0);
			}

			if(isVenting && worldObj.getTotalWorldTime() % 2 == 0) {
				
				NBTTagCompound fx = new NBTTagCompound();
				fx.setString("type", "tower");
				fx.setFloat("lift", 10F);
				fx.setFloat("base", 0.25F);
				fx.setFloat("max", 2.5F);
				fx.setInteger("life", 100 + worldObj.rand.nextInt(20));
				fx.setInteger("color",0x202020);
				fx.setDouble("posX", xCoord + 0.5 + rot.offsetX);
				fx.setDouble("posY", yCoord + 5);
				fx.setDouble("posZ", zCoord + 0.5 + rot.offsetZ);
				MainRegistry.proxy.effectNT(fx);
			}
			this.lastAnim = this.anim;
			if(this.isProgressing) {
				this.anim++;
			}
		}
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		tanks[2].serialize(buf);
		buf.writeBoolean(isVenting);
		buf.writeBoolean(isProgressing);
		buf.writeFloat(progress);
		buf.writeInt(burnTime);
		buf.writeInt(maxBurnTime);
		
		if(this.output != null) {
			buf.writeBoolean(true);
			buf.writeInt(this.output.material.id);
			buf.writeInt(this.output.amount);
		} else {
			buf.writeBoolean(false);
		}
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		tanks[2].deserialize(buf);
		isVenting = buf.readBoolean();
		isProgressing = buf.readBoolean();
		progress = buf.readFloat();
		burnTime = buf.readInt();
		maxBurnTime = buf.readInt();
		
		if(buf.readBoolean()) {
			this.output = new MaterialStack(Mats.matById.get(buf.readInt()), buf.readInt());
		} else {
			this.output = null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "t0");
		this.tanks[1].readFromNBT(nbt, "t1");
		this.tanks[2].readFromNBT(nbt, "t2");
		this.progress = nbt.getFloat("prog");
		this.burnTime = nbt.getInteger("burn");
		this.maxBurnTime = nbt.getInteger("maxBurn");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "t0");
		this.tanks[1].writeToNBT(nbt, "t1");
		this.tanks[2].writeToNBT(nbt, "t2");
		nbt.setFloat("prog", progress);
		nbt.setInteger("burn", burnTime);
		nbt.setInteger("maxBurn", maxBurnTime);
	}
	
	public DirPos[] getSteamPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite())
		};
	}
	
	public DirPos[] getFluidPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX + rot.offsetX * 3, yCoord, zCoord + dir.offsetZ + rot.offsetZ * 3, rot),
				new DirPos(xCoord - dir.offsetX + rot.offsetX * 3, yCoord, zCoord - dir.offsetZ + rot.offsetZ * 3, rot)
		};
	}
	
	public boolean canProcess(RotaryFurnaceRecipe recipe) {
		
		if(this.burnTime <= 0) return false;
		
		if(recipe.fluid != null) {
			if(this.tanks[0].getTankType() != recipe.fluid.type) return false;
			if(this.tanks[0].getFill() < recipe.fluid.fill) return false;
		}

		if(tanks[1].getFill() < recipe.steam) return false;
		if(tanks[2].getMaxFill() - tanks[2].getFill() < recipe.steam / 100) return false;
		if(this.steamUsed > 100) return false;
		
		if(this.output != null) {
			if(this.output.material != recipe.output.material) return false;
			if(this.output.amount + recipe.output.amount > this.maxOutput) return false;
		}
		
		return true;
	}
	
	public void consumeItems(RotaryFurnaceRecipe recipe) {
		
		for(AStack aStack : recipe.ingredients) {
			
			for(int i = 0; i < 3; i++) {
				ItemStack stack = slots[i];
				if(aStack.matchesRecipe(stack, true) && stack.stackSize >= aStack.stacksize) {
					this.decrStackSize(i, aStack.stacksize);
					break;
				}
			}
		}
		
		if(recipe.fluid != null) {
			this.tanks[0].setFill(tanks[0].getFill() - recipe.fluid.fill);
		}
	}
	
	@Override
	public void pollute(PollutionType type, float amount) {
		FluidTank tank = type == PollutionType.SOOT ? smoke : type == PollutionType.HEAVYMETAL ? smoke_leaded : smoke_poison;
		
		int fluidAmount = (int) Math.ceil(amount * 100);
		tank.setFill(tank.getFill() + fluidAmount);
		
		if(tank.getFill() > tank.getMaxFill()) {
			int overflow = tank.getFill() - tank.getMaxFill();
			tank.setFill(tank.getMaxFill());
			PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, type, overflow / 100F);
			this.isVenting = true;
		}
	}

	@Override public int[] getAccessibleSlotsFromSide(int side) { return new int[0]; }
	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return slot < 3 || slot == 4; }
	@Override public boolean canExtractItem(int slot, ItemStack  stack, int side) { return false; }

	@Override public boolean isItemValidForSlot(int x, int y, int z, int slot, ItemStack stack) { return slot < 3 || slot == 4; }
	@Override public boolean canExtractItem(int x, int y, int z, int slot, ItemStack stack, int side) { return false; }
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 5,
					zCoord + 3
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
	public int[] getAccessibleSlotsFromSide(int x, int y, int z, int side) {
		BlockPos pos = new BlockPos(x, y, z);
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		BlockPos core = new BlockPos(xCoord, yCoord, zCoord);
		
		//Red
		if(side == dir.getOpposite().ordinal() && pos.equals(core.clone().offset(dir, -1).offset(rot, -2))) return new int[] {0};
		//Yellow
		if(side == dir.getOpposite().ordinal() && pos.equals(core.clone().offset(dir, -1).offset(rot, -1))) return new int[] {1};
		//Green
		if(side == dir.getOpposite().ordinal() && pos.equals(core.clone().offset(dir, -1))) return new int[] {2};
		//Fuel
		if(side == dir.ordinal() && pos.equals(core.clone().offset(dir, 1).offset(rot, -1))) return new int[] {4};
		
		return new int[] { };
	}

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {tanks[0], tanks[1], tanks[2], smoke}; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {tanks[2], smoke}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tanks[0], tanks[1]}; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineRotaryFurnace(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineRotaryFurnace(player.inventory, this); }
}
