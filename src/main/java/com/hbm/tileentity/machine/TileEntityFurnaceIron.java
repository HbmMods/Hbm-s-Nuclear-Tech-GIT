package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerFurnaceIron;
import com.hbm.inventory.gui.GUIFurnaceIron;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceIron extends TileEntityMachineBase implements IGUIProvider {
	
	public int maxBurnTime;
	public int burnTime;
	public boolean wasOn = false;

	public int progress;
	public int processingTime;
	public static final int baseTime = 200;
	
	public ModuleBurnTime burnModule;

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
			
			UpgradeManager.eval(slots, 4, 4);
			this.processingTime = baseTime - (100 * Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3) / 3);
			
			wasOn = false;
			
			if(burnTime <= 0) {
				
				for(int i = 1; i < 3; i++) {
					if(slots[i] != null) {
						
						int fuel = burnModule.getBurnTime(slots[i]);
						//int fuel = TileEntityFurnace.getItemBurnTime(slots[i]);
						
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
				
				if(this.progress % 15 == 0) {
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
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("maxBurnTime", this.maxBurnTime);
			data.setInteger("burnTime", this.burnTime);
			data.setInteger("progress", this.progress);
			data.setInteger("processingTime", this.processingTime);
			data.setBoolean("wasOn", this.wasOn);
			this.networkPack(data, 50);
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
	public void networkUnpack(NBTTagCompound nbt) {
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.progress = nbt.getInteger("progress");
		this.processingTime = nbt.getInteger("processingTime");
		this.wasOn = nbt.getBoolean("wasOn");
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
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
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
}
