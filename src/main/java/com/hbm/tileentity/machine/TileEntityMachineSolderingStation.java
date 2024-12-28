package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.container.ContainerMachineSolderingStation;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineSolderingStation;
import com.hbm.inventory.recipes.SolderingRecipes;
import com.hbm.inventory.recipes.SolderingRecipes.SolderingRecipe;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.*;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineSolderingStation extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiver, IControlReceiver, IGUIProvider, IUpgradeInfoProvider, IFluidCopiable, IConfigurableMachine {

	public long power;
	public long consumption;
	public boolean collisionPrevention = false;

	public int progress;
	public int processTime = 1;

	public FluidTank tank;
	public ItemStack display;

	// configurable values
	public static long maxPower = 2_000;

	@Override
	public String getConfigName() {
		return "solderingStation";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		maxPower = IConfigurableMachine.grab(obj, "L:maxPower", maxPower);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:maxPower").value(maxPower);
	}

	public TileEntityMachineSolderingStation() {
		super(11);
		this.tank = new FluidTank(Fluids.NONE, 8_000);
	}

	@Override
	public String getName() {
		return "container.machineSolderingStation";
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);

		if(stack != null && stack.getItem() instanceof ItemMachineUpgrade && i >= 9 && i <= 10) {
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
		}
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.power = Library.chargeTEFromItems(slots, 7, this.getPower(), this.getMaxPower());
			this.tank.setType(8, slots);

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					if(tank.getTankType() != Fluids.NONE) this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}

			SolderingRecipe recipe = SolderingRecipes.getRecipe(new ItemStack[] {slots[0], slots[1], slots[2], slots[3], slots[4], slots[5]});
			long intendedMaxPower;

			UpgradeManager.eval(slots, 9, 10);
			int redLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int blueLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);

			if(recipe != null) {
				this.processTime = recipe.duration - (recipe.duration * redLevel / 6) + (recipe.duration * blueLevel / 3);
				this.consumption = recipe.consumption + (recipe.consumption * redLevel) - (recipe.consumption * blueLevel / 6);
				intendedMaxPower = recipe.consumption * 20;

				if(canProcess(recipe)) {
					this.progress++;
					this.power -= this.consumption;

					if(progress >= processTime) {
						this.progress = 0;
						this.consumeItems(recipe);

						if(slots[6] == null) {
							slots[6] = recipe.output.copy();
						} else {
							slots[6].stackSize += recipe.output.stackSize;
						}

						this.markDirty();
					}

					if(worldObj.getTotalWorldTime() % 20 == 0) {
						ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
						ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
						NBTTagCompound dPart = new NBTTagCompound();
						dPart.setString("type", "tau");
						dPart.setByte("count", (byte) 3);
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(dPart, xCoord + 0.5 - dir.offsetX * 0.5 + rot.offsetX * 0.5, yCoord + 1.125, zCoord + 0.5 - dir.offsetZ * 0.5 + rot.offsetZ * 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 25));
					}

				} else {
					this.progress = 0;
				}

			} else {
				this.progress = 0;
				this.consumption = 100;
				intendedMaxPower = 2000;
			}

			this.maxPower = Math.max(intendedMaxPower, power);

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setLong("maxPower", maxPower);
			data.setLong("consumption", consumption);
			data.setInteger("progress", progress);
			data.setInteger("processTime", processTime);
			data.setBoolean("collisionPrevention", collisionPrevention);
			if(recipe != null) {
				data.setInteger("display", Item.getIdFromItem(recipe.output.getItem()));
				data.setInteger("displayMeta", recipe.output.getItemDamage());
			}
			this.tank.writeToNBT(data, "t");
			this.networkPack(data, 25);
		}
	}

	public boolean canProcess(SolderingRecipe recipe) {

		if(this.power < this.consumption) return false;

		if(recipe.fluid != null) {
			if(this.tank.getTankType() != recipe.fluid.type) return false;
			if(this.tank.getFill() < recipe.fluid.fill) return false;
		}

		if(collisionPrevention && recipe.fluid == null && this.tank.getFill() > 0) return false;

		if(slots[6] != null) {
			if(slots[6].getItem() != recipe.output.getItem()) return false;
			if(slots[6].getItemDamage() != recipe.output.getItemDamage()) return false;
			if(slots[6].stackSize + recipe.output.stackSize > slots[6].getMaxStackSize()) return false;
		}

		return true;
	}

	public void consumeItems(SolderingRecipe recipe) {

		for(AStack aStack : recipe.toppings) {
			for(int i = 0; i < 3; i++) {
				ItemStack stack = slots[i];
				if(aStack.matchesRecipe(stack, true) && stack.stackSize >= aStack.stacksize) { this.decrStackSize(i, aStack.stacksize); break; }
			}
		}

		for(AStack aStack : recipe.pcb) {
			for(int i = 3; i < 5; i++) {
				ItemStack stack = slots[i];
				if(aStack.matchesRecipe(stack, true) && stack.stackSize >= aStack.stacksize) { this.decrStackSize(i, aStack.stacksize); break; }
			}
		}

		for(AStack aStack : recipe.solder) {
			for(int i = 5; i < 6; i++) {
				ItemStack stack = slots[i];
				if(aStack.matchesRecipe(stack, true) && stack.stackSize >= aStack.stacksize) { this.decrStackSize(i, aStack.stacksize); break; }
			}
		}

		if(recipe.fluid != null) {
			this.tank.setFill(tank.getFill() - recipe.fluid.fill);
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot < 3) {
			for(int i = 0; i < 3; i++) if(i != slot && slots[i] != null && slots[i].isItemEqual(stack)) return false;
			for(AStack t : SolderingRecipes.toppings) if(t.matchesRecipe(stack, true)) return true;
		} else if(slot < 5) {
			for(int i = 3; i < 5; i++) if(i != slot && slots[i] != null && slots[i].isItemEqual(stack)) return false;
			for(AStack t : SolderingRecipes.pcb) if(t.matchesRecipe(stack, true)) return true;
		} else if(slot < 6) {
			for(int i = 5; i < 6; i++) if(i != slot && slots[i] != null && slots[i].isItemEqual(stack)) return false;
			for(AStack t : SolderingRecipes.solder) if(t.matchesRecipe(stack, true)) return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 6;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6 };
	}

	protected DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX + rot.offsetX, yCoord, zCoord + dir.offsetZ + rot.offsetZ, dir),
				new DirPos(xCoord - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord - rot.offsetX, yCoord, zCoord - rot.offsetZ, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX - rot.offsetX, yCoord, zCoord - dir.offsetZ - rot.offsetZ, rot.getOpposite()),
				new DirPos(xCoord + rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2, rot),
				new DirPos(xCoord - dir.offsetX + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ + rot.offsetZ * 2, rot),
		};
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.consumption = nbt.getLong("consumption");
		this.progress = nbt.getInteger("progress");
		this.processTime = nbt.getInteger("processTime");
		this.collisionPrevention = nbt.getBoolean("collisionPrevention");

		if(nbt.hasKey("display")) {
			this.display = new ItemStack(Item.getItemById(nbt.getInteger("display")), 1, nbt.getInteger("displayMeta"));
		} else {
			this.display = null;
		}

		this.tank.readFromNBT(nbt, "t");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.progress = nbt.getInteger("progress");
		this.processTime = nbt.getInteger("processTime");
		this.collisionPrevention = nbt.getBoolean("collisionPrevention");
		tank.readFromNBT(nbt, "t");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		nbt.setInteger("progress", progress);
		nbt.setInteger("processTime", processTime);
		nbt.setBoolean("collisionPrevention", collisionPrevention);
		tank.writeToNBT(nbt, "t");
	}

	@Override
	public long getPower() {
		return Math.max(Math.min(power, maxPower), 0);
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineSolderingStation(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineSolderingStation(player.inventory, this);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 3,
					zCoord + 2
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
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_soldering_station));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 100 / 6) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 100 / 6) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_DELAY, "+" + (level * 100 / 3) + "%"));
		}
	}

	@Override
	public int getMaxLevel(UpgradeType type) {
		if(type == UpgradeType.SPEED) return 3;
		if(type == UpgradeType.POWER) return 3;
		return 0;
	}

	@Override
	public FluidTank getTankToPaste() {
		return tank;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		this.collisionPrevention = !this.collisionPrevention;
		this.markDirty();
	}
}
