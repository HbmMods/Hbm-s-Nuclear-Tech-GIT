package com.hbm.tileentity.machine.oil;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.container.ContainerPyroOven;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIPyroOven;
import com.hbm.inventory.recipes.PyroOvenRecipes;
import com.hbm.inventory.recipes.PyroOvenRecipes.PyroOvenRecipe;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachinePyroOven extends TileEntityMachinePolluting implements IEnergyReceiverMK2, IFluidStandardTransceiver, IGUIProvider, IFluidCopiable {
	
	public long power;
	public static final long maxPower = 1_000_000;
	public boolean isVenting;
	public boolean isProgressing;
	public float progress;
	public int consumption = 10_000;
	
	public int prevAnim;
	public int anim = 0;
	
	public FluidTank[] tanks;
	
	private AudioWrapper audio;

	public TileEntityMachinePyroOven() {
		super(6, 50);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.NONE, 24_000);
		tanks[1] = new FluidTank(Fluids.NONE, 24_000);
	}

	@Override
	public String getName() {
		return "container.machinePyroOven";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tanks[0].setType(3, slots);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(tanks[0].getTankType() != Fluids.NONE) this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(tanks[1].getFill() > 0) this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
			ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
			if(smoke.getFill() > 0) this.sendFluid(smoke, worldObj, xCoord - rot.offsetX, yCoord + 3, zCoord - rot.offsetZ, Library.POS_Y);
			
			//UpgradeManager.eval(slots, 4, 5);
			
			this.isProgressing = false;
			this.isVenting = false;
			
			if(this.canProcess()) {
				PyroOvenRecipe recipe = getMatchingRecipe();
				this.progress += 1F / recipe.duration;
				this.isProgressing = true;
				this.power -= this.consumption;
				
				if(progress >= 1F) {
					this.progress = 0F;
					this.finishRecipe(recipe);
					this.markDirty();
				}
				
				this.pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);
				
			} else {
				this.progress = 0F;
			}
			
			this.networkPackNT(50);
		} else {
			
			this.prevAnim = this.anim;
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
			ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
			
			if(isProgressing) {
				this.anim++;
				
				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}
	
				audio.keepAlive();
				audio.updateVolume(this.getVolume(1F));

				if(MainRegistry.proxy.me().getDistance(xCoord + 0.5, yCoord + 3, zCoord + 0.5) < 50) {
					if(worldObj.rand.nextInt(20) == 0) worldObj.spawnParticle("cloud", xCoord + 0.5 - rot.offsetX - dir.offsetX * 0.875, yCoord + 3, zCoord + 0.5 - rot.offsetZ - dir.offsetZ * 0.875, 0.0, 0.05, 0.0);
					if(worldObj.rand.nextInt(20) == 0) worldObj.spawnParticle("cloud", xCoord + 0.5 - rot.offsetX - dir.offsetX * 2.375, yCoord + 3, zCoord + 0.5 - rot.offsetZ - dir.offsetZ * 2.375, 0.0, 0.05, 0.0);
					if(worldObj.rand.nextInt(20) == 0) worldObj.spawnParticle("cloud", xCoord + 0.5 - rot.offsetX + dir.offsetX * 0.875, yCoord + 3, zCoord + 0.5 - rot.offsetZ + dir.offsetZ * 0.875, 0.0, 0.05, 0.0);
					if(worldObj.rand.nextInt(20) == 0) worldObj.spawnParticle("cloud", xCoord + 0.5 - rot.offsetX + dir.offsetX * 2.375, yCoord + 3, zCoord + 0.5 - rot.offsetZ + dir.offsetZ * 2.375, 0.0, 0.05, 0.0);
				}
				
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
			
			if(this.isVenting) {

				if(worldObj.getTotalWorldTime() % 2 == 0) {
					NBTTagCompound fx = new NBTTagCompound();
					fx.setString("type", "tower");
					fx.setFloat("lift", 10F);
					fx.setFloat("base", 0.25F);
					fx.setFloat("max", 2.5F);
					fx.setInteger("life", 100 + worldObj.rand.nextInt(20));
					fx.setInteger("color",0x202020);
					fx.setDouble("posX", xCoord + 0.5 - rot.offsetX);
					fx.setDouble("posY", yCoord + 3);
					fx.setDouble("posZ", zCoord + 0.5 - rot.offsetZ);
					MainRegistry.proxy.effectNT(fx);
				}
			}
		}
	}
	
	protected PyroOvenRecipe lastValidRecipe;
	
	public PyroOvenRecipe getMatchingRecipe() {
		
		if(lastValidRecipe != null && doesRecipeMatch(lastValidRecipe)) return lastValidRecipe;
		
		for(PyroOvenRecipe rec : PyroOvenRecipes.recipes) {
			if(doesRecipeMatch(rec)) {
				lastValidRecipe = rec;
				return rec;
			}
		}
		
		return null;
	}
	
	public boolean doesRecipeMatch(PyroOvenRecipe recipe) {
		
		if(recipe.inputFluid != null) {
			if(tanks[0].getTankType() != recipe.inputFluid.type) return false; // recipe needs fluid, fluid doesn't match
		}
		if(recipe.inputItem != null) {
			if(slots[1] == null) return false; // recipe needs item, no item present
			if(!recipe.inputItem.matchesRecipe(slots[1], true)) return false; // recipe needs item, item doesn't match
		} else {
			if(slots[1] != null) return false; // recipe does not need item, but item is present
		}
		
		return true;
	}
	
	public boolean canProcess() {
		if(power < consumption) return false; // not enough power
		PyroOvenRecipe recipe = this.getMatchingRecipe();
		if(recipe == null) return false; // no matching recipe
		if(recipe.inputFluid != null && tanks[0].getFill() < recipe.inputFluid.fill) return false; // not enough input fluid
		if(recipe.inputItem != null && slots[1].stackSize < recipe.inputItem.stacksize) return false; // not enough input item
		if(recipe.outputFluid != null && recipe.outputFluid.fill + tanks[1].getFill() > tanks[1].getMaxFill()) return false; // too much output fluid
		if(recipe.outputItem != null && slots[2] != null && recipe.outputItem.stackSize + slots[2].stackSize > slots[2].getMaxStackSize()) return false; // too much output item
		if(recipe.outputItem != null && slots[2] != null && recipe.outputItem.getItem() != slots[2].getItem()) return false; // output item doesn't match
		if(recipe.outputItem != null && slots[2] != null && recipe.outputItem.getItemDamage() != slots[2].getItemDamage()) return false; // output meta doesn't match
		
		return true;
	}
	
	public void finishRecipe(PyroOvenRecipe recipe) {
		if(recipe.outputItem != null) {
			if(slots[2] == null) {
				slots[2] = recipe.outputItem.copy();
			} else {
				slots[2].stackSize += recipe.outputItem.stackSize;
			}
		}
		if(recipe.outputFluid != null) {
			tanks[1].setTankType(recipe.outputFluid.type);
			tanks[1].setFill(tanks[1].getFill() + recipe.outputFluid.fill);
		}
		if(recipe.inputItem != null) {
			this.decrStackSize(1, recipe.inputItem.stacksize);
		}
		if(recipe.inputFluid != null) {
			tanks[0].setFill(tanks[0].getFill() - recipe.inputFluid.fill);
		}
	}
	
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX * 3, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 3, rot),
				new DirPos(xCoord + dir.offsetX * 1 + rot.offsetX * 3, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * 3, rot),
				new DirPos(xCoord + rot.offsetX * 3, yCoord, zCoord + rot.offsetZ * 3, rot),
				new DirPos(xCoord - dir.offsetX * 1 + rot.offsetX * 3, yCoord, zCoord - dir.offsetZ * 1 + rot.offsetZ * 3, rot),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX * 3, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * 3, rot),
		};
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		buf.writeLong(power);
		buf.writeBoolean(isVenting);
		buf.writeBoolean(isProgressing);
		buf.writeFloat(progress);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		power = buf.readLong();
		isVenting = buf.readBoolean();
		isProgressing = buf.readBoolean();
		progress = buf.readFloat();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "t0");
		this.tanks[1].readFromNBT(nbt, "t1");
		this.progress = nbt.getFloat("prog");
		this.power = nbt.getLong("power");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "t0");
		this.tanks[1].writeToNBT(nbt, "t1");
		nbt.setFloat("prog", progress);
		nbt.setLong("power", power);
	}
	
	@Override public int[] getAccessibleSlotsFromSide(int meta) { return new int[] { 1, 2 }; }
	@Override public boolean isItemValidForSlot(int i, ItemStack itemStack) { return i == 1; }
	@Override public boolean canExtractItem(int i, ItemStack itemStack, int j) { return i == 2; }
	
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

	@Override public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.pyroOperate", xCoord, yCoord, zCoord, 1.0F, 15F, 1.0F, 20);
	}
	
	@Override public void onChunkUnload() {
		if(audio != null) { audio.stopSound(); audio = null; }
	}

	@Override public void invalidate() {
		super.invalidate();
		if(audio != null) { audio.stopSound(); audio = null; }
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 3, yCoord, zCoord - 3, xCoord + 4, yCoord + 3.5, zCoord + 4);
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] { tanks[0], tanks[1], smoke }; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] { tanks[1], smoke }; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] { tanks[0] }; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerPyroOven(player.inventory, this); }
	@Override public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIPyroOven(player.inventory, this); }
}
