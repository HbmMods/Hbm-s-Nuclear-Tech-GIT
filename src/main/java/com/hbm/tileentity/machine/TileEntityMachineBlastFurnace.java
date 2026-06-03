package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.container.ContainerBlastFurnace;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Polluting;
import com.hbm.inventory.fluid.trait.FluidTrait.FluidReleaseType;
import com.hbm.inventory.gui.GUIBlastFurnace;
import com.hbm.inventory.recipes.BlastFurnaceRecipesNT;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes.IOutput;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.main.NTMSounds;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineBlastFurnace extends TileEntityMachineBase implements IFluidStandardTransceiverMK2, IGUIProvider, IFluidCopiable {
	
	public FluidTank[] tanks;

	public boolean isProgressing;
	public float progress;
	public float speed;
	public int fuel;
	public static final int FUEL_COAL = 200 * 8;
	public static final int FUEL_RATE = 200 * 4; // half coal per operation
	public static final int MAX_FUEL = FUEL_COAL * 16; // 16 pieces of coal
	public static final int FLUE_GAS = 100; // per finished operation, not per tick
	
	public ModuleBurnTime burnModule = new ModuleBurnTime()
			.setWoodHeatMod(0D);

	public TileEntityMachineBlastFurnace() {
		super(5);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.AIRBLAST, 4_000);
		this.tanks[1] = new FluidTank(Fluids.FLUE, 1_000); // TEMP
	}

	@Override
	public String getName() {
		return "container.blastFurnace";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.checkTilt(TiltType.CONFIG, false);
			
			for(DirPos pos : this.getConPos()) {
				this.trySubscribe(tanks[0].getTankType(), worldObj, pos);
				if(this.tanks[1].getFill() > 0) this.tryProvide(tanks[1], worldObj, pos);
			}
			
			if(slots[0] != null) {
				int capacity = MAX_FUEL - fuel;
				int burnValue = getBurnTime(slots[0]);
				if(burnValue > 0 && burnValue <= capacity) {
					this.fuel += burnValue;
					this.decrStackSize(0, 1);
				}
			}
			
			this.speed = 0F;
			GenericRecipe recipe = BlastFurnaceRecipesNT.INSTANCE.getRecipe(slots[1], slots[2]);
			
			if(!this.tilted && recipe != null && this.fuel >= FUEL_RATE && this.canOutput(recipe)) {
				
				this.speed = MathHelper.clamp_float(0.5F + this.tanks[0].getFill() * 4F / this.tanks[0].getMaxFill(), 0.5F, 3F);
				
				this.isProgressing = true;
				this.progress += speed / recipe.duration;
				
				if(this.progress >= 1F) {
					this.process(recipe);
					this.progress = 0F;
					this.fuel -= FUEL_RATE;
					this.tanks[1].setFill(tanks[1].getFill() + FLUE_GAS);
					if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) {
						int spill = this.tanks[1].getFill() - this.tanks[1].getMaxFill();
						this.tanks[1].getTankType().onFluidRelease(worldObj, xCoord, yCoord + 7, zCoord, tanks[1], spill);
						FT_Polluting.pollute(worldObj, xCoord, yCoord, zCoord, tanks[1].getTankType(), FluidReleaseType.SPILL, spill);
						this.tanks[1].setFill(this.tanks[1].getMaxFill());
					}
				}
				
				if(worldObj.rand.nextInt(10) == 0 && !this.muffled) {
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, NTMSounds.VANILLA_FIRE, 1.0F, 0.5F + worldObj.rand.nextFloat() * 0.25F);
				}
				
			} else {
				this.isProgressing = false;
				this.progress = 0F;
			}
			
			if(this.tanks[0].getFill() > 0) this.tanks[0].setFill((int) (this.tanks[0].getFill() * 0.95));
			
			this.networkPackNT(100);
		} else {
			
			if(worldObj.getBlock(xCoord, yCoord + 7, zCoord).isAir(worldObj, xCoord, yCoord + 7, zCoord)) {
				if(isProgressing && worldObj.getTotalWorldTime() % 2 == 0) {
					Random rand = worldObj.rand;
					this.worldObj.spawnParticle("lava", xCoord + 0.25 + rand.nextDouble() * 0.5, yCoord + 7.25, zCoord + 0.25 + rand.nextDouble() * 0.5, 0, 0, 0);
					
					if(tanks[1].getFill() >= 100 && MainRegistry.proxy.me().getDistanceSq(xCoord + 0.5, yCoord + 7, zCoord + 0.5) < 100 * 100) {
						if(worldObj.getTotalWorldTime() % 2 == 0) {
							NBTTagCompound fx = new NBTTagCompound();
							fx.setString("type", "tower");
							fx.setFloat("lift", 10F);
							fx.setFloat("base", 0.25F);
							fx.setFloat("max", 2.5F);
							fx.setInteger("life", 100 + worldObj.rand.nextInt(20));
							fx.setInteger("color",0x202020);
							fx.setDouble("posX", xCoord + 0.5);
							fx.setDouble("posY", yCoord + 7);
							fx.setDouble("posZ", zCoord + 0.5);
							MainRegistry.proxy.effectNT(fx);
						}
					}
				}
			}
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + dir.offsetX * 2, yCoord + 3, zCoord + dir.offsetZ * 2, dir),
				new DirPos(xCoord + dir.offsetX * 2, yCoord + 5, zCoord + dir.offsetZ * 2, dir),
				new DirPos(xCoord, yCoord + 7, zCoord, Library.POS_Y)
		};
	}
	
	public boolean canOutput(GenericRecipe recipe) {
		
		for(int i = 0; i < recipe.outputItem.length; i++) {
			ItemStack slot = slots[3 + i];
			if(slot == null) continue;
			IOutput out = recipe.outputItem[i];
			if(out.possibleMultiOutput()) return false;
			ItemStack stack = out.collapse();
			if(stack.getItem() != slot.getItem()) return false;
			if(stack.getItemDamage() != slot.getItemDamage()) return false;
			if(stack.stackSize + slot.stackSize > stack.getMaxStackSize()) return false;
		}
		
		return true;
	}
	
	public void process(GenericRecipe recipe) {
		
		for(int i = 0; i < recipe.outputItem.length; i++) {
			IOutput out = recipe.outputItem[i];
			ItemStack stack = out.collapse();
			if(slots[3 + i] != null) slots[3 + i].stackSize += stack.stackSize;
			else slots[3 + i] = stack;
		}
		
		if(recipe.inputItem.length == 1) {
			if(recipe.inputItem[0].matchesRecipe(slots[1], false)) this.decrStackSize(1, recipe.inputItem[0].stacksize);
			else if(recipe.inputItem[0].matchesRecipe(slots[2], false)) this.decrStackSize(2, recipe.inputItem[0].stacksize);
			
		} else if(recipe.inputItem.length == 2) {
			if(recipe.inputItem[0].matchesRecipe(slots[1], false) && recipe.inputItem[1].matchesRecipe(slots[2], false)) {
				this.decrStackSize(1, recipe.inputItem[0].stacksize);
				this.decrStackSize(2, recipe.inputItem[1].stacksize);
			} else if(recipe.inputItem[0].matchesRecipe(slots[2], false) && recipe.inputItem[1].matchesRecipe(slots[1], false)) {
				this.decrStackSize(2, recipe.inputItem[0].stacksize);
				this.decrStackSize(1, recipe.inputItem[1].stacksize);
			}
		}
	}
	
	public int getBurnTime(ItemStack stack) {
		if(stack.getItem().hasContainerItem(stack)) return 0;
		return burnModule.getBurnHeat(burnModule.getBurnTime(stack, 0D), stack, 0D);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0 && getBurnTime(stack) > 0) return true;
		if(slot == 1 || slot == 2) return true;
		return false;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		
		if(slot == 1 || slot == 2) {

			// repetition prevention
			if(slot == 1 && slots[2] != null && stack.isItemEqual(slots[2])) return false;
			if(slot == 2 && slots[1] != null && stack.isItemEqual(slots[1])) return false;
			
			// needs to match at least one recipe
			for(GenericRecipe recipe : BlastFurnaceRecipesNT.INSTANCE.recipeOrderedList) {
				for(AStack input : recipe.inputItem) {
					if(input.matchesRecipe(stack, true)) return true;
				}
			}
			
			return false;
		}
		
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return slot >= 3;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1, 2, 0, 3, 4 };
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		
		buf.writeBoolean(isProgressing);
		buf.writeFloat(progress);
		buf.writeFloat(speed);
		buf.writeInt(fuel);

		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		
		this.isProgressing = buf.readBoolean();
		this.progress = buf.readFloat();
		this.speed = buf.readFloat();
		this.fuel = buf.readInt();

		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.progress = nbt.getFloat("progress");
		this.fuel = nbt.getInteger("fuel");
		tanks[0].readFromNBT(nbt, "t0");
		tanks[1].readFromNBT(nbt, "t1");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setFloat("progress", progress);
		nbt.setInteger("fuel", fuel);
		tanks[0].writeToNBT(nbt, "t0");
		tanks[1].writeToNBT(nbt, "t1");
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 7, zCoord + 2);
		}
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerBlastFurnace(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIBlastFurnace(player.inventory, this); }

	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] { tanks[0] }; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] { tanks[1] }; }
	@Override public FluidTank[] getAllTanks() { return tanks; }

	@Override public long getProviderSpeed(FluidType type, int pressure) { return Math.max(tanks[1].getFill() * 50 / tanks[1].getMaxFill(), 1); }
	
	@Override public FluidTank getTankToPaste() { return null; }
	
	@Override public int getFloorCount() { return 2 * 2; }
	@Override public BlockPos getFloorPosFromIndex(int index) { return this.standardFloor3x3(index); }
}
