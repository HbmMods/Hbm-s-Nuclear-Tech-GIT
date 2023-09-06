package com.hbm.tileentity.machine;

import com.EconomyPlus.compatibility.ntm.HBMRecipes;
import com.EconomyPlus.lib.dataStructures.CraftingStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineEPress;
import com.hbm.inventory.gui.GUIMachineEPress;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.inventory.recipes.PressRecipes;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.machine.ItemStamp;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hfr.faction.relations.FactionRelations;

import akka.japi.Pair;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineEPress extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {

	public long power = 0;
	public final static long maxPower = 50000;

	public int press;
	public double renderPress;
	public double lastPress;
	private int syncPress;
	private int turnProgress;
	public final static int maxPress = 200;
	boolean isRetracting = false;
	private int delay;

	public ItemStack syncStack;

	public TileEntityMachineEPress() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.epress";
	}

	@Override
	public void updateEntity() {
		if(FactionRelations.isWarday())
			return;
		if(!worldObj.isRemote) {

			this.updateConnections();
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);

			boolean canProcess = this.canProcess();

			if((canProcess || this.isRetracting || this.delay > 0) && power >= 100) {

				power -= 100;

				if(delay <= 0) {

					UpgradeManager.eval(slots, 4, 4);
					int speed = 1 + Math.min(3, UpgradeManager.getLevel(UpgradeType.SPEED));

					int stampSpeed = this.isRetracting ? 20 : 45;
					stampSpeed *= (1D + (double) speed / 4D);

					if(this.isRetracting) {
						this.press -= stampSpeed;

						if(this.press <= 0) {
							this.isRetracting = false;
							this.delay = 5 - speed + 1;
						}
					} else if(canProcess) {
						this.press += stampSpeed;

						if(this.press >= this.maxPress) {
							this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.pressOperate", 1.5F, 1.0F);
							ItemStack output = PressRecipes.getOutput(slots[2], slots[1]);

							boolean damageStamp = output != null;
							int inputCount = 1;
							int stackSize = slots[2] == null ? 0 : slots[2].stackSize;
							{
								CraftingStack a = slots[1] == null ? null : new CraftingStack(slots[1]);
								CraftingStack b = slots[2] == null ? null : new CraftingStack(slots[2]);

								Pair<CraftingStack, CraftingStack> inputs = new Pair<CraftingStack, CraftingStack>(a, b);

								if (output == null && HBMRecipes.pressRecipes.containsKey(inputs)) {
									Pair<ItemStack, Boolean> out = HBMRecipes.pressRecipes.get(inputs);
									output = out.first();
									damageStamp = out.second();
									for (Pair<CraftingStack, CraftingStack> c : HBMRecipes.pressRecipes.keySet())
										if (c.second().hashCode() == b.hashCode())
											inputCount = c.second().amount;
								}

								if(output == null) {
									for (Pair<CraftingStack, CraftingStack> c : HBMRecipes.pressRecipes.keySet())
										if (a != null && b != null && c != null && c.second() != null && c.first() != null && c.second().hashCode2() == b.hashCode2() && c.first().hashCode2() == a.hashCode2()) {
											inputCount = c.second().amount;
											Pair<ItemStack, Boolean> out = HBMRecipes.pressRecipes.get(c);
											output = out.first();
											damageStamp = out.second();
										}
								}
							}

							if(slots[3] == null) {
								slots[3] = output.copy();
							} else {
								slots[3].stackSize += output.stackSize;
							}
							this.decrStackSize(2, inputCount);

							if(slots[1].getMaxDamage() != 0 && damageStamp) {
								slots[1].setItemDamage(slots[1].getItemDamage() + 1);
								if(slots[1].getItemDamage() >= slots[1].getMaxDamage()) {
									slots[1] = null;
								}
							}

							this.isRetracting = true;
							this.delay = 5 - speed + 1;

							this.markDirty();
						}
					} else if(this.press > 0){
						this.isRetracting = true;
					}
				} else {
					delay--;
				}
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("press", press);
			if(slots[2] != null) {
				NBTTagCompound stack = new NBTTagCompound();
				slots[2].writeToNBT(stack);
				data.setTag("stack", stack);
			}

			this.networkPack(data, 50);

		} else {

			// approach-based interpolation, GO!
			this.lastPress = this.renderPress;

			if(this.turnProgress > 0) {
				this.renderPress = this.renderPress + ((this.syncPress - this.renderPress) / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.renderPress = this.syncPress;
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.syncPress = nbt.getInteger("press");

		if(nbt.hasKey("stack")) {
			NBTTagCompound stack = nbt.getCompoundTag("stack");
			this.syncStack = ItemStack.loadItemStackFromNBT(stack);
		} else {
			this.syncStack = null;
		}

		this.turnProgress = 2;
	}

	public boolean canProcess() {
		if(power < 100) return false;
		if(slots[1] == null || slots[2] == null) return false;

		ItemStack output = PressRecipes.getOutput(slots[2], slots[1]);
		boolean damageStamp = output != null;
		int inputCount = 1;
		int stackSize = slots[2] == null ? 0 : slots[2].stackSize;
		{
			CraftingStack a = slots[1] == null ? null : new CraftingStack(slots[1]);
			CraftingStack b = slots[2] == null ? null : new CraftingStack(slots[2]);
			
			Pair<CraftingStack, CraftingStack> inputs = new Pair<CraftingStack, CraftingStack>(a, b);
			
			if (output == null && HBMRecipes.pressRecipes.containsKey(inputs)) {
				Pair<ItemStack, Boolean> out = HBMRecipes.pressRecipes.get(inputs);
				output = out.first();
				damageStamp = out.second();
				for (Pair<CraftingStack, CraftingStack> c : HBMRecipes.pressRecipes.keySet())
					if (c.second().hashCode() == b.hashCode())
						inputCount = c.second().amount;
			}
			if(output == null) {
				for (Pair<CraftingStack, CraftingStack> c : HBMRecipes.pressRecipes.keySet())
					if (a != null && b != null && c != null && c.second() != null && c.first() != null && c.second().hashCode2() == b.hashCode2() && c.first().hashCode2() == a.hashCode2()) {
						inputCount = c.second().amount;
						Pair<ItemStack, Boolean> out = HBMRecipes.pressRecipes.get(c);
						output = out.first();
						damageStamp = out.second();
					}
			}
		}
		if(output == null) return false;

		if(slots[3] == null) return true;
		if(slots[3].stackSize + output.stackSize <= slots[3].getMaxStackSize() && slots[3].getItem() == output.getItem() && slots[3].getItemDamage() == output.getItemDamage()) return true;
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {

		if(stack.getItem() instanceof ItemStamp && i == 1)
			return true;

		return i == 2;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1, 2, 3 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3;
	}

	private void updateConnections() {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("press", press);
		nbt.setLong("power", power);
		nbt.setBoolean("ret", isRetracting);
	}

	@Override
	public void setPower(long i) {
		power = i;

	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	AxisAlignedBB aabb;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(aabb != null)
			return aabb;

		aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 3, zCoord + 1);
		return aabb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineEPress(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineEPress(player.inventory, this);
	}
}
