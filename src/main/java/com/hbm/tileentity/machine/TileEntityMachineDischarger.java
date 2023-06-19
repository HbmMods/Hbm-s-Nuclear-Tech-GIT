package com.hbm.tileentity.machine;

import com.hbm.config.VersatileConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.container.ContainerMachineDischarger;
import com.hbm.inventory.gui.GUIMachineDischarger;
import com.hbm.inventory.recipes.BreederRecipes;

import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCapacitor;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.tileentity.RenderTurretMaxwell;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import api.hbm.energy.IEnergyUser;
import api.hbm.energy.IEnergyConnector.ConnectionPriority;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineDischarger extends TileEntityMachineBase implements IEnergyGenerator, IGUIProvider, IPersistentNBT {

	public long power = 0;
	public int process = 0;
	public int temp = 20;
	public static final int maxtemp = 2000;
	public static final long maxPower = 500000000;
	public static long Gen = 20000000;
	public static final int processSpeed = 100;
	public static final int CoolDown = 400;
	
	private AudioWrapper audio;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 3, 2 };

	public TileEntityMachineDischarger() {
		super(2);
	}

	@Override
	public String getName() {
		return "container.machine_discharger";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		switch (i) {
		case 0:
			if(MachineRecipes.mODE(stack, OreDictManager.SA326.ingot()))
				return true;
		break;
		case 2:
			if(MachineRecipes.mODE(stack, OreDictManager.U233.ingot()))
				return true;
			break;
        default:
		case 1:
			if (stack.getItem() instanceof IBatteryItem)
				return true;
			break;
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		process = nbt.getInteger("process");
		temp = nbt.getInteger("temp");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setInteger("process", process);
		nbt.setInteger("temp", temp);
	}
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
		
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {

		if (i == 0) {
			return true;
		}

		if (i == 1) {
			if (stack.getItem() instanceof IBatteryItem && ((IBatteryItem)stack.getItem()).getCharge(stack) == 0)
				return true;
		}

		return false;
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public long getTempScaled(int i) {
		return (temp * i) / maxtemp;
	}
	
	public int getProgressScaled(int i) {
		return (process * i) / processSpeed;
	}
	
	public int getCoolDownScaled(int i) {
		return (temp * i) / CoolDown;
	}

	public boolean canProcess() {
		//please PLEASE tell me how i can do better ffs
			if (temp <= 20 && slots[0] != null && MachineRecipes.mODE(slots[0], OreDictManager.SA326.ingot())) {
				return true;
			}
			
			if (temp <= 20 && slots[0] != null && MachineRecipes.mODE(slots[0], OreDictManager.U233.ingot())) {
				return true;
			}
			
			if (temp <= 20 && slots[0] != null && slots[0].getItem() == ModItems.ingot_electronium) {
				return true;
			}
			if (temp <= 20 && slots[0] != null && slots[0].getItem() == ModItems.battery_creative) {
				return true;
			}
		return false;
	}

	public boolean isProcessing() {
		return process > 0;
	}

	public void process() {
		process++;
		if (process >= processSpeed) {

		
			process = 0;
			temp = maxtemp;
			
			slots[0].stackSize--;
			if (slots[0].stackSize <= 0 && slots[0].getItem() == ModItems.ingot_u233) {
				power += Gen * 0.8;
				slots[0] = null;
				slots[0] = new ItemStack(ModItems.ingot_titanium);
			}
			if (slots[0].stackSize <= 0 && slots[0].getItem() == ModItems.ingot_schrabidium) {
				power += Gen * 2;
				slots[0] = null;
				slots[0] = new ItemStack(ModItems.ingot_lanthanium);
				//if (slots[0].getItem() == ModItems.ingot_lanthanium && slots[0].stackSize < slots[0].getMaxStackSize()) {
				//	  slots[0].stackSize++;
				//}
			}
			if (slots[0].stackSize <= 0 && slots[0].getItem() == ModItems.ingot_electronium) {
				power += Gen * 4;
				slots[0] = null;
				slots[0] = new ItemStack(ModItems.ingot_dineutronium);
			}
			if (slots[0].stackSize <= 0 && slots[0].getItem() == ModItems.battery_creative) {
				EntityNukeExplosionMK3 ex = EntityNukeExplosionMK3.statFacFleija(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, (int) 120);
				if(!ex.isDead) {
					worldObj.spawnEntityInWorld(ex);
		
					EntityCloudFleija cloud = new EntityCloudFleija(worldObj, (int) 120);
					cloud.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
					worldObj.spawnEntityInWorld(cloud);
				}		
			}
			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "ambient.weather.thunder", 10000.0F,
					0.8F + this.worldObj.rand.nextFloat() * 0.2F);
			}

	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			this.updateConnections();
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir.getOpposite());
			power = Library.chargeItemsFromTE(slots, 1, power, maxPower);

			if(canProcess()) {
				process();
			} else {
				process = 0;
			}

			if(worldObj.getTotalWorldTime() % 10 == 0) {
				if(temp > 20) {
					temp = temp - 5;
				}	
				if(temp < 20) { //70k for the love of fuck this was only when i was debugging
					temp = 20;
				}
				
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", process);
			data.setInteger("temp", temp);
			this.networkPack(data, 50);
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			
			if(temp > 20) {
			if(worldObj.getTotalWorldTime() % 7 == 0)
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 11, this.zCoord, "random.fizz", 0.5F, 0.5F);
			data.setString("type", "tower");
			data.setFloat("lift", 0.1F);
			data.setFloat("base", 0.3F);
			data.setFloat("max", 1F);
			data.setInteger("life", 20 + worldObj.rand.nextInt(20));

			data.setDouble("posX", xCoord + 0.5 + worldObj.rand.nextDouble() - 0.5);
			data.setDouble("posZ", zCoord + 0.5 + worldObj.rand.nextDouble() -0.5);
			data.setDouble("posY", yCoord + 1);
			
			MainRegistry.proxy.effectNT(data);
		}	
		} else {

			if(process > 0) {
				
				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}

	}
	
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:weapon.tauChargeLoop", xCoord, yCoord, zCoord, 1.0F, 10F, 1.0F);
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}

	public void onChunkUnload() {

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	public void invalidate() {

		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {

		this.power = data.getLong("power");
		this.process = data.getInteger("progress");
		this.temp = data.getInteger("temp");
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
		return new ContainerMachineDischarger(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineDischarger(player.inventory, this);
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setInteger("progress", process);
		data.setInteger("temp", temp);
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		this.power = data.getLong("power");
		this.temp = data.getInteger("temp");
		this.process = data.getInteger("procsess");
	}
}
