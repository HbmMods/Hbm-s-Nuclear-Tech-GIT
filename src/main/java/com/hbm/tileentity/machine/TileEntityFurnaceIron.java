package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerFurnaceIron;
import com.hbm.inventory.gui.GUIFurnaceIron;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceIron extends TileEntityMachineBase implements IGUIProvider, IUpgradeInfoProvider {

	public int maxBurnTime;
	public int burnTime;
	public boolean wasOn = false;

	public int progress;
	public int processingTime;
	public static final int baseTime = 160;

	public ModuleBurnTime burnModule;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityFurnaceIron() {
		super(5);

		burnModule = new ModuleBurnTime()
				.setLigniteTimeMod(1.25)
				.setCoalTimeMod(1.25)
				.setCokeTimeMod(1.5)
				.setSolidTimeMod(2)
				.setRocketTimeMod(2)
				.setBalefireTimeMod(2);
	}

	@Override
	public String getName() {
		return "container.furnaceIron";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			upgradeManager.checkSlots(this, slots, 4, 4);
			this.processingTime = baseTime - ((baseTime / 2) * upgradeManager.getLevel(UpgradeType.SPEED) / 3);

			wasOn = false;

			if(burnTime <= 0) {

				for(int i = 1; i < 3; i++) {
					if(slots[i] != null) {

						int fuel = burnModule.getBurnTime(slots[i]);

						if(fuel > 0) {
							this.maxBurnTime = this.burnTime = fuel;
							slots[i].stackSize--;

							if(slots[i].stackSize == 0) {
								slots[i] = slots[i].getItem().getContainerItem(slots[i]);
							}

							break;
						}
					}
				}
			}

			if(canSmelt()) {
				wasOn = true;
				this.progress++;
				this.burnTime--;

				if(this.progress % 15 == 0 && !this.muffled) {
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "fire.fire", 1.0F, 0.5F + worldObj.rand.nextFloat() * 0.5F);
				}

				if(this.progress >= this.processingTime) {
					ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(slots[0]);

					if(slots[3] == null) {
						slots[3] = result.copy();
					} else {
						slots[3].stackSize += result.stackSize;
					}

					this.decrStackSize(0, 1);

					this.progress = 0;
					this.markDirty();
				}
				if(worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);
			} else {
				this.progress = 0;
			}

			this.networkPackNT(50);
		} else {

			if(this.progress > 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

				double offset = this.progress % 2 == 0 ? 1 : 0.5;
				worldObj.spawnParticle("smoke", xCoord + 0.5 - dir.offsetX * offset - rot.offsetX * 0.1875, yCoord + 2, zCoord + 0.5 - dir.offsetZ * offset - rot.offsetZ * 0.1875, 0.0, 0.01, 0.0);

				if(this.progress % 5 == 0) {
					double rand = worldObj.rand.nextDouble();
					worldObj.spawnParticle("flame", xCoord + 0.5 + dir.offsetX * 0.25 + rot.offsetX * rand, yCoord + 0.25 + worldObj.rand.nextDouble() * 0.25, zCoord + 0.5 + dir.offsetZ * 0.25 + rot.offsetZ * rand, 0.0, 0.0, 0.0);
				}
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(this.maxBurnTime);
		buf.writeInt(this.burnTime);
		buf.writeInt(this.progress);
		buf.writeInt(this.processingTime);
		buf.writeBoolean(this.wasOn);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.maxBurnTime = buf.readInt();
		this.burnTime = buf.readInt();
		this.progress = buf.readInt();
		this.processingTime = buf.readInt();
		this.wasOn = buf.readBoolean();
	}

	public boolean canSmelt() {

		if(this.burnTime <= 0) return false;
		if(slots[0] == null) return false;

		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(slots[0]);

		if(result == null) return false;
		if(slots[3] == null) return true;

		if(!result.isItemEqual(slots[3])) return false;
		if(result.stackSize + slots[3].stackSize > slots[3].getMaxStackSize()) return false;

		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1, 2, 3 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {

		if(i == 0)
			return FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;

		if(i < 3)
			return burnModule.getBurnTime(itemStack) > 0;

		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.progress = nbt.getInteger("progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("maxBurnTime", maxBurnTime);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("progress", progress);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceIron(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceIron(player.inventory, this);
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
		return type == UpgradeType.SPEED;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.furnace_iron));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 50 / 3) + "%"));
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		return upgrades;
	}
}
