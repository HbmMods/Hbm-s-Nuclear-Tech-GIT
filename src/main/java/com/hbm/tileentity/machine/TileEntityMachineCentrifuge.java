package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerCentrifuge;
import com.hbm.inventory.gui.GUIMachineCentrifuge;
import com.hbm.inventory.recipes.CentrifugeRecipes;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.I18nUtil;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCentrifuge extends TileEntityMachineBase implements IEnergyReceiverMK2, IGUIProvider, IUpgradeInfoProvider, IInfoProviderEC {
	
	public int progress;
	public long power;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 200;
	private int audioDuration = 0;
	
	private AudioWrapper audio;

	/*
	 * So why do we do this now? You have a funny mekanism/thermal/whatever pipe and you want to output stuff from a side
	 * that isn't the bottom, what do? Answer: make all slots accessible from all sides and regulate in/output in the 
	 * dedicated methods. Duh.
	 */
	private static final int[] slot_io = new int[] { 0, 2, 3, 4, 5 };

	public TileEntityMachineCentrifuge() {
		super(8);
	}
	
	public String getName() {
		return "container.centrifuge";
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_io;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		progress = nbt.getShort("progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setShort("progress", (short) progress);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i > 1;
	}

	public int getCentrifugeProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}

	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}

	public boolean canProcess() {

		if(slots[0] == null) {
			return false;
		}
		ItemStack[] out = CentrifugeRecipes.getOutput(slots[0]);
		
		if(out == null) {
			return false;
		}

		for(int i = 0; i < Math.min(4, out.length); i++) {

			//either the slot is null, the output is null or the output can be added to the existing slot
			if(slots[i + 2] == null)
				continue;

			if(out[i] == null)
				continue;

			if(slots[i + 2].isItemEqual(out[i]) && slots[i + 2].stackSize + out[i].stackSize <= out[i].getMaxStackSize())
				continue;

			return false;
		}

		return true;
	}

	private void processItem() {
		ItemStack[] out = CentrifugeRecipes.getOutput(slots[0]);

		for(int i = 0; i < Math.min(4, out.length); i++) {

			if(out[i] == null)
				continue;

			if(slots[i + 2] == null) {
				slots[i + 2] = out[i].copy();
			} else {
				slots[i + 2].stackSize += out[i].stackSize;
			}
		}

		this.decrStackSize(0, 1);
		this.markDirty();
	}

	public boolean hasPower() {
		return power > 0;
	}

	public boolean isProcessing() {
		return this.progress > 0;
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);

			power = Library.chargeTEFromItems(slots, 1, power, maxPower);
			
			int consumption = 200;
			int speed = 1;
			
			UpgradeManager.eval(slots, 6, 7);
			speed += Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			consumption += Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3) * 200;
			
			speed *= (1 + Math.min(UpgradeManager.getLevel(UpgradeType.OVERDRIVE), 3) * 5);
			consumption += Math.min(UpgradeManager.getLevel(UpgradeType.OVERDRIVE), 3) * 10000;
			
			consumption /= (1 + Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3));

			if(hasPower() && isProcessing()) {
				this.power -= consumption;

				if(this.power < 0) {
					this.power = 0;
				}
			}

			if(hasPower() && canProcess()) {
				isProgressing = true;
			} else {
				isProgressing = false;
			}

			if(isProgressing) {
				progress += speed;

				if(this.progress >= TileEntityMachineCentrifuge.processingSpeed) {
					this.progress = 0;
					this.processItem();
				}
			} else {
				progress = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setBoolean("isProgressing", isProgressing);
			this.networkPack(data, 50);
		} else {
			
			if(isProgressing) {
				audioDuration += 2;
			} else {
				audioDuration -= 3;
			}
			
			audioDuration = MathHelper.clamp_int(audioDuration, 0, 60);
			
			if(audioDuration > 10) {
				
				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}

				audio.updateVolume(getVolume(1F));
				audio.updatePitch((audioDuration - 10) / 100F + 0.5F);
				
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);
		
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
		this.isProgressing = data.getBoolean("isProgressing");
	}

	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.centrifugeOperate", xCoord, yCoord, zCoord, 1.0F, 10F, 1.0F);
	}

	@Override
	public void onChunkUnload() {

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord,
					yCoord,
					zCoord,
					xCoord + 1,
					yCoord + 4,
					zCoord + 1
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

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCentrifuge(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCentrifuge(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER || type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_centrifuge));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 100) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (100 - 100 / (level + 1)) + "%"));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public int getMaxLevel(UpgradeType type) {
		if(type == UpgradeType.SPEED) return 3;
		if(type == UpgradeType.POWER) return 3;
		if(type == UpgradeType.OVERDRIVE) return 3;
		return 0;
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, this.progress > 0);
		data.setInteger(CompatEnergyControl.B_ACTIVE, this.progress);
	}
}