package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.container.ContainerMachineWoodBurner;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.gui.GUIMachineWoodBurner;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.lib.Library;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineWoodBurner extends TileEntityMachineBase implements IFluidStandardReceiver, IControlReceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 100_000;
	public int burnTime;
	public int maxBurnTime;
	public boolean liquidBurn = false;
	public boolean isOn = false;
	
	public FluidTank tank;
	
	public static ModuleBurnTime burnModule = new ModuleBurnTime().setLogTimeMod(4).setWoodTimeMod(2);

	public int ashLevelWood;
	public int ashLevelCoal;
	public int ashLevelMisc;

	public TileEntityMachineWoodBurner() {
		super(6);
		this.tank = new FluidTank(Fluids.WOODOIL, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineWoodBurner";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.tank.setType(2, slots);
			this.tank.loadTank(3, 4, slots);
			this.power = Library.chargeItemsFromTE(slots, 5, power, maxPower);
			
			if(!liquidBurn) {
				
				if(this.burnTime <= 0) {
					
					if(slots[0] != null) {
						int burn = this.burnModule.getBurnTime(slots[0]);
						if(burn > 0) {
							EnumAshType type = TileEntityFireboxBase.getAshFromFuel(slots[0]);
							if(type == EnumAshType.WOOD) ashLevelWood += burn;
							if(type == EnumAshType.COAL) ashLevelCoal += burn;
							if(type == EnumAshType.MISC) ashLevelMisc += burn;
							int threshold = 2000;
							if(processAsh(ashLevelWood, EnumAshType.WOOD, threshold)) ashLevelWood -= threshold;
							if(processAsh(ashLevelCoal, EnumAshType.COAL, threshold)) ashLevelCoal -= threshold;
							if(processAsh(ashLevelMisc, EnumAshType.MISC, threshold)) ashLevelMisc -= threshold;
							
							this.maxBurnTime = this.burnTime = burn;
							this.decrStackSize(0, 1);
							this.markChanged();
						}
					}
					
				} else if(this.power < this.maxPower && isOn){
					this.burnTime--;
					this.power += 100;
					if(power > maxPower) this.power = this.maxPower;
					if(worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);
				}
				
			} else {
				
				if(this.power < this.maxPower && tank.getFill() > 0 && isOn) {
					FT_Flammable trait = tank.getTankType().getTrait(FT_Flammable.class);
					
					if(trait != null) {
						this.power += trait.getHeatEnergy() / 2L;
						tank.setFill(tank.getFill() - 1);
						if(worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("burnTime", burnTime);
			data.setInteger("maxBurnTime", maxBurnTime);
			data.setBoolean("isOn", isOn);
			data.setBoolean("liquidBurn", liquidBurn);
			this.networkPack(data, 25);
		} else {
			
			if(this.isOn && ((!this.liquidBurn && this.burnTime > 0) || (this.liquidBurn && this.tank.getTankType().hasTrait(FT_Flammable.class) && tank.getFill() > 0))) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				worldObj.spawnParticle("smoke", xCoord + 0.5 - dir.offsetX + rot.offsetX, yCoord + 4, zCoord + 0.5 - dir.offsetZ + rot.offsetZ, 0, 0.05, 0);
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.burnTime = nbt.getInteger("burnTime");
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.isOn = nbt.getBoolean("isOn");
		this.liquidBurn = nbt.getBoolean("liquidBurn");
	}
	
	protected boolean processAsh(int level, EnumAshType type, int threshold) {
		
		if(level >= threshold) {
			if(slots[1] == null) {
				slots[1] = DictFrame.fromOne(ModItems.powder_ash, type);
				ashLevelWood -= threshold;
				return true;
			} else if(slots[1].stackSize < slots[1].getMaxStackSize() && slots[1].getItem() == ModItems.powder_ash && slots[1].getItemDamage() == type.ordinal()) {
				slots[1].stackSize++;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toggle")) {
			this.isOn = !this.isOn;
			this.markChanged();
		}
		if(data.hasKey("switch")) {
			this.liquidBurn = !this.liquidBurn;
			this.markChanged();
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineWoodBurner(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineWoodBurner(player.inventory, this);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1 };
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && burnModule.getBurnTime(itemStack) > 0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return slot == 1;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}
}
