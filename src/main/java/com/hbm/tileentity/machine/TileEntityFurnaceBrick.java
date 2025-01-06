package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.blocks.machine.MachineBrickFurnace;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.container.ContainerFurnaceBrick;
import com.hbm.inventory.gui.GUIFurnaceBrick;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class TileEntityFurnaceBrick extends TileEntityMachineBase implements IGUIProvider {
	
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1, 3 };
	private static final int[] slotsSides = new int[] {1};
	
	public static HashMap<Item, Integer> burnSpeed = new HashMap();
	
	static {
		burnSpeed.put(Items.clay_ball,								4);
		burnSpeed.put(ModItems.ball_fireclay,						4);
		burnSpeed.put(Item.getItemFromBlock(Blocks.netherrack),		4);
		burnSpeed.put(Item.getItemFromBlock(Blocks.cobblestone),	2);
		burnSpeed.put(Item.getItemFromBlock(Blocks.sand),			2);
		burnSpeed.put(Item.getItemFromBlock(Blocks.log),			2);
		burnSpeed.put(Item.getItemFromBlock(Blocks.log2),			2);
	}
	
	public int burnTime;
	public int maxBurnTime;
	public int progress;

	public int ashLevelWood;
	public int ashLevelCoal;
	public int ashLevelMisc;

	public TileEntityFurnaceBrick() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.furnaceBrick";
	}

	@Override

	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			boolean wasBurning = this.burnTime > 0;
			boolean markDirty = false;
	
			if(this.burnTime > 0) {
				this.burnTime--;
			}
	
			if(this.burnTime != 0 || this.slots[1] != null && this.slots[0] != null) {
				if(this.burnTime == 0 && this.canSmelt()) {
					this.maxBurnTime = this.burnTime = TileEntityFurnace.getItemBurnTime(this.slots[1]);

					if(this.burnTime > 0) {
						markDirty = true;

						if(this.slots[1] != null) {
							this.slots[1].stackSize--;

							EnumAshType type = TileEntityFireboxBase.getAshFromFuel(slots[1]);
							if(type == EnumAshType.WOOD) ashLevelWood += burnTime;
							if(type == EnumAshType.COAL) ashLevelCoal += burnTime;
							if(type == EnumAshType.MISC) ashLevelMisc += burnTime;
							int threshold = 2000;
							if(processAsh(ashLevelWood, EnumAshType.WOOD, threshold)) ashLevelWood -= threshold;
							if(processAsh(ashLevelCoal, EnumAshType.COAL, threshold)) ashLevelCoal -= threshold;
							if(processAsh(ashLevelMisc, EnumAshType.MISC, threshold)) ashLevelMisc -= threshold;

							if(this.slots[1].stackSize == 0) {
								this.slots[1] = slots[1].getItem().getContainerItem(slots[1]);
							}
						}
					}
				}

				if(this.burnTime > 0 && this.canSmelt()) {
					this.progress += this.getBurnSpeed();

					if(this.progress >= 200) {
						this.progress = 0;
						this.smeltItem();
						markDirty = true;
					}
				} else {
					this.progress = 0;
				}
			}

			if(wasBurning != this.burnTime > 0) {
				markDirty = true;
				MachineBrickFurnace.updateBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
	
			if(markDirty) {
				this.markDirty();
			}
			
			this.networkPackNT(15);
		}
	}
	
	public int getBurnSpeed() {
		Integer speed = burnSpeed.get(slots[0].getItem());
		if(speed != null) return speed;
		return 1;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot >= 2 ? false : (slot == 1 ? TileEntityFurnace.getItemBurnTime(stack) > 0 : true);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSides);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(burnTime);
		buf.writeInt(maxBurnTime);
		buf.writeInt(progress);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.burnTime = buf.readInt();
		this.maxBurnTime = buf.readInt();
		this.progress = buf.readInt();
	}
	
	protected boolean processAsh(int level, EnumAshType type, int threshold) {
		
		if(level >= threshold) {
			if(slots[3] == null) {
				slots[3] = DictFrame.fromOne(ModItems.powder_ash, type);
				return true;
			} else if(slots[3].stackSize < slots[3].getMaxStackSize() && slots[3].getItem() == ModItems.powder_ash && slots[3].getItemDamage() == type.ordinal()) {
				slots[3].stackSize++;
				return true;
			}
		}
		
		return false;
	}

	private boolean canSmelt() {
		if(this.slots[0] == null) {
			return false;
		} else {
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			if(itemstack == null)
				return false;
			if(this.slots[2] == null)
				return true;
			if(!this.slots[2].isItemEqual(itemstack))
				return false;
			int result = slots[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.slots[2].getMaxStackSize();
		}
	}
	
	public void smeltItem() {
		if(this.canSmelt()) {
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);

			if(this.slots[2] == null) {
				this.slots[2] = itemstack.copy();
			} else if(this.slots[2].getItem() == itemstack.getItem()) {
				this.slots[2].stackSize += itemstack.stackSize;
			}

			--this.slots[0].stackSize;

			if(this.slots[0].stackSize <= 0) {
				this.slots[0] = null;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.burnTime = nbt.getInteger("burnTime");
		this.maxBurnTime = nbt.getInteger("maxBurn");
		this.progress = nbt.getInteger("progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("burnTime", this.burnTime);
		nbt.setInteger("maxBurn", this.maxBurnTime);
		nbt.setInteger("progress", this.progress);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceBrick(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceBrick(player.inventory, this);
	}
}
