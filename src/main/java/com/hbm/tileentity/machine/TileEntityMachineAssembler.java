package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineAssembler;
import com.hbm.inventory.gui.GUIMachineAssembler;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.storage.TileEntityCrateBase;
import com.hbm.tileentity.machine.storage.TileEntityCrateIron;
import com.hbm.tileentity.machine.storage.TileEntityCrateSteel;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAssembler extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {

	public long power;
	public static final long maxPower = 100000;
	public int progress;
	public int maxProgress = 100;
	public boolean isProgressing;
	int age = 0;
	int consumption = 100;
	int speed = 100;
	
	@SideOnly(Side.CLIENT)
	public int recipe; //don't initialize this, retard
	
	private AudioWrapper audio;
	
	Random rand = new Random();
	
	public TileEntityMachineAssembler() {
		super(18);
	}

	@Override
	public String getName() {
		return "container.assembler";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 0)
			if(itemStack.getItem() instanceof IBatteryItem)
				return true;
		
		if(i == 1)
			return true;
		
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("powerTime");
		this.progress = nbt.getInteger("progress");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", power);
		nbt.setInteger("progress", progress);
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / maxProgress;
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			//meta below 12 means that it's an old multiblock configuration
			if(this.getBlockMetadata() < 12) {
				int meta = this.getBlockMetadata();
				if(meta == 2 || meta == 14) meta = 4;
				else if(meta == 4 || meta == 13) meta = 3;
				else if(meta == 3 || meta == 15) meta = 5;
				else if(meta == 5 || meta == 12) meta = 2;
				//get old direction
				ForgeDirection dir = ForgeDirection.getOrientation(meta);
				//remove tile from the world to prevent inventory dropping
				worldObj.removeTileEntity(xCoord, yCoord, zCoord);
				//use fillspace to create a new multiblock configuration
				worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.machine_assembler, dir.ordinal() + 10, 3);
				MultiblockHandlerXR.fillSpace(worldObj, xCoord, yCoord, zCoord, ((BlockDummyable) ModBlocks.machine_assembler).getDimensions(), ModBlocks.machine_assembler, dir);
				//load the tile data to restore the old values
				NBTTagCompound data = new NBTTagCompound();
				this.writeToNBT(data);
				worldObj.getTileEntity(xCoord, yCoord, zCoord).readFromNBT(data);
				return;
			}
			
			this.updateConnections();

			this.consumption = 100;
			this.speed = 100;
			
			UpgradeManager.eval(slots, 1, 3);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			speed -= speedLevel * 25;
			consumption += speedLevel * 300;
			speed += powerLevel * 5;
			consumption -= powerLevel * 30;
			speed /= (overLevel + 1);
			consumption *= (overLevel + 1);

			isProgressing = false;
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			if(AssemblerRecipes.getOutputFromTempate(slots[4]) != null && AssemblerRecipes.getRecipeFromTempate(slots[4]) != null) {
				this.maxProgress = (ItemAssemblyTemplate.getProcessTime(slots[4]) * speed) / 100;
				
				if(power >= consumption && removeItems(AssemblerRecipes.getRecipeFromTempate(slots[4]), cloneItemStackProper(slots))) {
					
					if(slots[5] == null || (slots[5] != null && slots[5].getItem() == AssemblerRecipes.getOutputFromTempate(slots[4]).copy().getItem()) && slots[5].stackSize + AssemblerRecipes.getOutputFromTempate(slots[4]).copy().stackSize <= slots[5].getMaxStackSize()) {
						progress++;
						isProgressing = true;
						
						if(progress >= maxProgress) {
							progress = 0;
							if(slots[5] == null) {
								slots[5] = AssemblerRecipes.getOutputFromTempate(slots[4]).copy();
							} else {
								slots[5].stackSize += AssemblerRecipes.getOutputFromTempate(slots[4]).copy().stackSize;
							}
							
							removeItems(AssemblerRecipes.getRecipeFromTempate(slots[4]), slots);
							
							if(slots[0] != null && slots[0].getItem() == ModItems.meteorite_sword_alloyed)
								slots[0] = new ItemStack(ModItems.meteorite_sword_machined);
						}
						
						power -= consumption;
					}
				} else
					progress = 0;
			} else
				progress = 0;
			
			int meta = worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			
			TileEntity te1 = null;
			TileEntity te2 = null;
			
			if(meta == 14) {
				te1 = worldObj.getTileEntity(xCoord - 2, yCoord, zCoord);
				te2 = worldObj.getTileEntity(xCoord + 3, yCoord, zCoord - 1);
			}
			if(meta == 15) {
				te1 = worldObj.getTileEntity(xCoord + 2, yCoord, zCoord);
				te2 = worldObj.getTileEntity(xCoord - 3, yCoord, zCoord + 1);
			}
			if(meta == 13) {
				te1 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 2);
				te2 = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord - 3);
			}
			if(meta == 12) {
				te1 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 2);
				te2 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord + 3);
			}
			
			tryExchangeTemplates(te1, te2);
			
			//OUTPUT
			if(te1 instanceof TileEntityCrateBase || te1 instanceof TileEntityChest) {
				IInventory chest = (IInventory)te1;
				tryFillContainer(chest, 5);
			}
			
			if(te2 instanceof TileEntityCrateBase || te2 instanceof TileEntityChest) {
				IInventory chest = (IInventory)te2;
				
				for(int i = 0; i < chest.getSizeInventory(); i++)
					if(tryFillAssembler(chest, i))
						break;
			}
			
			int rec = -1;
			if(AssemblerRecipes.getOutputFromTempate(slots[4]) != null) {
				ComparableStack comp = ItemAssemblyTemplate.readType(slots[4]);
				rec = AssemblerRecipes.recipeList.indexOf(comp);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setInteger("maxProgress", maxProgress);
			data.setBoolean("isProgressing", isProgressing);
			data.setInteger("recipe", rec);
			this.networkPack(data, 150);
		} else {
			
			float volume = this.getVolume(2);

			if(isProgressing && volume > 0) {
				
				if(audio == null) {
					audio = this.createAudioLoop();
					audio.updateVolume(volume);
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
					audio.updateVolume(volume);
				}
				
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.assemblerOperate", xCoord, yCoord, zCoord, 1.0F, 10F, 1.0F);
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 3,				yCoord,	zCoord + rot.offsetZ * 3,				rot),
				new DirPos(xCoord - rot.offsetX * 2,				yCoord,	zCoord - rot.offsetZ * 2,				rot.getOpposite()),
				new DirPos(xCoord + rot.offsetX * 3 + dir.offsetX,	yCoord,	zCoord + rot.offsetZ * 3 + dir.offsetZ, rot),
				new DirPos(xCoord - rot.offsetX * 2 + dir.offsetX,	yCoord,	zCoord - rot.offsetZ * 2 + dir.offsetZ, rot.getOpposite())
		};
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
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.maxProgress = nbt.getInteger("maxProgress");
		this.isProgressing = nbt.getBoolean("isProgressing");
		this.recipe = nbt.getInteger("recipe");
	}
	
	private boolean removeItems(List<AStack> stack, ItemStack[] array) {

		if(stack == null)
			return false;
		
		for(int i = 0; i < stack.size(); i++) {
			for(int j = 0; j < stack.get(i).stacksize; j++) {
				AStack sta = stack.get(i).copy();
				sta.stacksize = 1;
			
				if(!canRemoveItemFromArray(sta, array))
					return false;
			}
		}
		
		return true;
	}
	
	public boolean canRemoveItemFromArray(AStack stack, ItemStack[] array) {

		AStack st = stack.copy();
		
		if(st == null)
			return true;
		
		for(int i = 6; i < 18; i++) {
			
			if(array[i] != null) {
				
				ItemStack sta = array[i].copy();
				sta.stackSize = 1;
			
				if(sta != null && st.isApplicable(sta) && array[i].stackSize > 0) {
					array[i].stackSize--;
					
					if(array[i].stackSize <= 0)
						array[i] = null;
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean tryExchangeTemplates(TileEntity te1, TileEntity te2) {
		//validateTe sees if it's a valid inventory tile entity
		boolean te1Valid = validateTe(te1);
		boolean te2Valid = validateTe(te2);
		
		if(te1Valid && te2Valid){
			IInventory iTe1 = (IInventory)te1;
			IInventory iTe2 = (IInventory)te2;
			boolean openSlot = false;
			boolean existingTemplate = false;
			boolean filledContainer = false;
			//Check if there's an existing template and an open slot
			for(int i = 0; i < iTe1.getSizeInventory(); i++){
				if(iTe1.getStackInSlot(i) == null){
					openSlot = true;
					
				}
				
			}
			if(this.slots[4] != null){
				existingTemplate = true;
			}
			//Check if there's a template in input
			for(int i = 0; i < iTe2.getSizeInventory(); i++){
				if(iTe2.getStackInSlot(i) != null && iTe2.getStackInSlot(i).getItem() instanceof ItemAssemblyTemplate){
					if(openSlot && existingTemplate){
						filledContainer = tryFillContainer(iTe1, 4);
						
					}
					if(filledContainer){
					ItemStack copy = iTe2.getStackInSlot(i).copy();
					iTe2.setInventorySlotContents(i, null);
					this.slots[4] = copy;
					}
				}
				
			}
			
		
		}
		return false;
		
	}
	
	private boolean validateTe(TileEntity te) {
		if(te instanceof TileEntityChest) {
			return true;
		}
		
		if(te instanceof TileEntityHopper) {
			return true;
		}
		
		if(te instanceof TileEntityCrateIron) {
			return true;
		}
		
		if(te instanceof TileEntityCrateSteel) {
			return true;
		}
		
		return false;
	}
	
	//I can't believe that worked.
	public ItemStack[] cloneItemStackProper(ItemStack[] array) {
		ItemStack[] stack = new ItemStack[array.length];
		
		for(int i = 0; i < array.length; i++)
			if(array[i] != null)
				stack[i] = array[i].copy();
			else
				stack[i] = null;
		
		return stack;
	}
	
	//Unloads output into chests
	public boolean tryFillContainer(IInventory inventory, int slot) {
		
		int size = inventory.getSizeInventory();

		for(int i = 0; i < size; i++) {
			if(inventory.getStackInSlot(i) != null) {
				
				if(slots[slot] == null)
					return false;
				
				ItemStack sta1 = inventory.getStackInSlot(i).copy();
				ItemStack sta2 = slots[slot].copy();
				if(sta1 != null && sta2 != null) {
					sta1.stackSize = 1;
					sta2.stackSize = 1;
				
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inventory.getStackInSlot(i).stackSize < inventory.getStackInSlot(i).getMaxStackSize()) {
						slots[slot].stackSize--;
						
						if(slots[slot].stackSize <= 0)
							slots[slot] = null;
						
						ItemStack sta3 = inventory.getStackInSlot(i).copy();
						sta3.stackSize++;
						inventory.setInventorySlotContents(i, sta3);
					
						return true;
					}
				}
			}
		}
		for(int i = 0; i < size; i++) {
			
			if(slots[slot] == null)
				return false;
			
			ItemStack sta2 = slots[slot].copy();
			if(inventory.getStackInSlot(i) == null && sta2 != null) {
				sta2.stackSize = 1;
				slots[slot].stackSize--;
				
				if(slots[slot].stackSize <= 0)
					slots[slot] = null;
				
				inventory.setInventorySlotContents(i, sta2);
					
				return true;
			}
		}
		
		return false;
	}
	
	public boolean tryFillAssembler(IInventory inventory, int slot) {
		
		if(AssemblerRecipes.getOutputFromTempate(slots[4]) == null || AssemblerRecipes.getRecipeFromTempate(slots[4]) == null)
			return false;
		else {
			List<AStack> list = copyItemStackList(AssemblerRecipes.getRecipeFromTempate(slots[4]));
			
			for(int i = 0; i < list.size(); i++)
				list.get(i).stacksize = 1;


			if(inventory.getStackInSlot(slot) == null)
				return false;
			
			ItemStack stack = inventory.getStackInSlot(slot).copy();
			stack.stackSize = 1;
			
			boolean flag = false;
			
			for(int i = 0; i < list.size(); i++)
				if(list.get(i).isApplicable(stack))
					flag = true;
			
			if(!flag)
				return false;
			
		}
		
		for(int i = 6; i < 18; i++) {
			
			if(slots[i] != null) {
			
				ItemStack sta1 = inventory.getStackInSlot(slot).copy();
				ItemStack sta2 = slots[i].copy();
				if(sta1 != null && sta2 != null) {
					sta1.stackSize = 1;
					sta2.stackSize = 1;
			
					if(sta1.isItemEqual(sta2) && slots[i].stackSize < slots[i].getMaxStackSize()) {
						ItemStack sta3 = inventory.getStackInSlot(slot).copy();
						sta3.stackSize--;
						if(sta3.stackSize <= 0)
							sta3 = null;
						inventory.setInventorySlotContents(slot, sta3);
				
						slots[i].stackSize++;
						return true;
					}
				}
			}
		}
		
		for(int i = 6; i < 18; i++) {

			ItemStack sta2 = inventory.getStackInSlot(slot).copy();
			if(slots[i] == null && sta2 != null) {
				sta2.stackSize = 1;
				slots[i] = sta2.copy();
				
				ItemStack sta3 = inventory.getStackInSlot(slot).copy();
				sta3.stackSize--;
				if(sta3.stackSize <= 0)
					sta3 = null;
				inventory.setInventorySlotContents(slot, sta3);
				
				return true;
			}
		}
		
		return false;
	}
	
	public static List<AStack> copyItemStackList(List<AStack> list){
        List<AStack> newList = new ArrayList<AStack>();
        if(list == null || list.isEmpty())
            return newList;
        for(AStack stack : list){
            newList.add(stack.copy());
        }
        return newList;
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
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(2, 1, 2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public int countMufflers() {
		
		int count = 0;

		for(int x = xCoord - 1; x <= xCoord + 1; x++)
			for(int z = zCoord - 1; z <= zCoord + 1; z++)
				if(worldObj.getBlock(x, yCoord - 1, z) == ModBlocks.muffler)
					count++;
		
		return count;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineAssembler(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineAssembler(player.inventory, this);
	}
}
