package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.handler.atmosphere.ChunkAtmosphereManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerVacuumCircuit;
import com.hbm.inventory.gui.GUIVacuumCircuit;
import com.hbm.inventory.recipes.VacuumCircuitRecipes;
import com.hbm.inventory.recipes.VacuumCircuitRecipes.VacuumCircuitRecipe;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class TileEntityMachineVacuumCircuit extends TileEntityMachineBase implements IEnergyReceiverMK2, IGUIProvider, IUpgradeInfoProvider {

	public long power;
	public long maxPower = 2_000;
	public long consumption;
	
	public int progress;
	public int processTime = 1;
	
	private VacuumCircuitRecipe recipe;
	public ItemStack display;

	public boolean canOperate = true;
	
	public TileEntityMachineVacuumCircuit() {
		super(8);
	}

	@Override
	public String getName() {
		return "container.machineVacuumCircuit";
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && stack.getItem() instanceof ItemMachineUpgrade && i >= 6 && i <=7) {
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
		}
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			CBT_Atmosphere atmosphere = ChunkAtmosphereManager.proxy.getAtmosphere(worldObj, xCoord, yCoord, zCoord);
			canOperate = atmosphere == null || atmosphere.getPressure() <= 0.001;

			this.power = Library.chargeTEFromItems(slots, 5, this.getPower(), this.getMaxPower());
			
			recipe = VacuumCircuitRecipes.getRecipe(new ItemStack[] {slots[0], slots[1], slots[2], slots[3]});
			long intendedMaxPower;
			
			UpgradeManager.eval(slots, 6, 7);
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
						
						if(slots[4] == null) {
							slots[4] = recipe.output.copy();
						} else {
							slots[4].stackSize += recipe.output.stackSize;
						}
						
						this.markDirty();
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
			
			this.networkPackNT(25);
		}
	}
	
	public boolean canProcess(VacuumCircuitRecipe recipe) {
		if(!canOperate) return false;
		
		if(this.power < this.consumption) return false;

		if(slots[6] != null) {
			if(slots[6].getItem() != recipe.output.getItem()) return false;
			if(slots[6].getItemDamage() != recipe.output.getItemDamage()) return false;
			if(slots[6].stackSize + recipe.output.stackSize > slots[6].getMaxStackSize()) return false;
		}
		
		return true;
	}
	
	public void consumeItems(VacuumCircuitRecipe recipe) {
		
		for(AStack aStack : recipe.wafer) {
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
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot < 3) {
			for(int i = 0; i < 3; i++) if(i != slot && slots[i] != null && slots[i].isItemEqual(stack)) return false;
			for(AStack t : VacuumCircuitRecipes.wafer) if(t.matchesRecipe(stack, true)) return true;
		} else if(slot < 5) {
			for(int i = 3; i < 5; i++) if(i != slot && slots[i] != null && slots[i].isItemEqual(stack)) return false;
			for(AStack t : VacuumCircuitRecipes.pcb) if(t.matchesRecipe(stack, true)) return true;
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
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeLong(power);
		buf.writeLong(maxPower);
		buf.writeLong(consumption);
		buf.writeInt(progress);
		buf.writeInt(processTime);
		buf.writeBoolean(canOperate);

		if(recipe != null) {
			buf.writeBoolean(true);
			buf.writeInt(Item.getIdFromItem(recipe.output.getItem()));
			buf.writeInt(recipe.output.getItemDamage());
		} else {
			buf.writeBoolean(false);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		power = buf.readLong();
		maxPower = buf.readLong();
		consumption = buf.readLong();
		progress = buf.readInt();
		processTime = buf.readInt();
		canOperate = buf.readBoolean();

		if(buf.readBoolean()) {
			int id = buf.readInt();
			int meta = buf.readInt();
			display = new ItemStack(Item.getItemById(id), 1, meta);
		} else {
			display = null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.progress = nbt.getInteger("progress");
		this.processTime = nbt.getInteger("processTime");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		nbt.setInteger("progress", progress);
		nbt.setInteger("processTime", processTime);
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
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerVacuumCircuit(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIVacuumCircuit(player.inventory, this);
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
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_vacuum_circuit));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(KEY_DELAY, "-" + (level * 100 / 6) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(KEY_CONSUMPTION, "-" + (level * 100 / 6) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(KEY_DELAY, "+" + (level * 100 / 3) + "%"));
		}
	}

	@Override
	public int getMaxLevel(UpgradeType type) {
		if(type == UpgradeType.SPEED) return 3;
		if(type == UpgradeType.POWER) return 3;
		return 0;
	}

}
